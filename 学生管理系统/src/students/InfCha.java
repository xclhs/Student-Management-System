/**
 * Copyright (C), 2015-2018,
 * FileName: InfCha
 * Author:   xclhs
 * Date:     2018/12/2 12:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * xclhs           修改时间           版本号              描述
 */
package students;

import com.alibaba.fastjson.JSONObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xclhs
 * @create 2018/12/2
 * @since 1.0.0
 */
public class InfCha implements Initializable {
    private Client client = null;
    private JSONObject params = null;
    private index app;

    @FXML
    private TextField id;
    @FXML
    private ChoiceBox cho;
    @FXML
    private TextField info;




    public void setApp(index app){
        this.app = app;
    }



    @FXML
    public void quiHandler(){
        //返回查询页面
        app.draw(13,"" );
    }

    @FXML
    public void subHandler(){
        String key= (String) cho.getValue();
        if(key.equals("密码")){
            key="psw";
        }else if(key.equals("姓名")){
            key="name";
        }else if(key.equals("性别")){
            key="sex";
        }else if(key.equals("专业")){
            key="major";
        }else if(key.equals("学院")){
            key="school";
        }else if(key.equals("系")){
            key="depart";
        }else if(key.equals("生日")){
            key="birth";
        }
        //进入信息查询页面
        params.put("mid",8);
        params.put("id2",id.getText() );
        params.put("key",key);
        params.put("value",info.getText() );
        this.app.SendInfo(params );
        new Thread(new Parser(client,app ,params )).start();

    }

    @FXML
    public void maiHandler(){
        GraAll.mainHandler(4, app, client, app.params);
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void set(Client client, JSONObject ja){
        this.client = client;
        this.params = ja;


    }

}

