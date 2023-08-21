/**
 * Copyright (C), 2015-2018,
 * FileName: res_cha
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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 〈一句话功能简述〉<br> 
 * 〈教务员成绩修改〉
 *
 * @author xclhs
 * @create 2018/12/2
 * @since 1.0.0
 */
public class res_cha implements Initializable {
    /**
     * 〈一句话功能简述〉<br>
     * 〈〉
     *
     * @author xclhs
     * @create 2018/12/2
     * @since 1.0.0
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    @FXML
    private Text rid;
    @FXML
    private TextField rname;
    @FXML
    private TextField rschool;
    @FXML
    private TextField rpsw;



    private Client client;
    private JSONObject params;
    private index app;

    public void setApp(index app){
        this.app = app;
    }


    @FXML
    public void subHandler(){//修改个人信息
        params.put("mid",3 );
        params.put("psw",rpsw.getText() );
        params.put("name",rname.getText() );
        params.put("school",rschool.getText() );
        this.app.params = params;
        this.app.SendInfo(params );
        new Thread(new Parser(client,app ,params )).start();
    }


    @FXML
    public void quiHandler(){
        app.draw(7,"" );
    }


    public void set(Client client, JSONObject ja){
        this.client = client;
        this.params = ja;
        this.rid.setText(ja.getString("id"));
        this.rname.setText(ja.getString("name"));
        this.rpsw.setText(ja.getString("psw"));
        this.rschool.setText(ja.getString("school"));

    }




}

