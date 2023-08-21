/**
 * Copyright (C), 2015-2018,
 * FileName: fin_gra
 * Author:   xclhs
 * Date:     2018/12/17 21:30
 * Description: 单科成绩查询
 * History:
 * <author>          <time>          <version>          <desc>
 * xclhs           修改时间           版本号              描述
 */
package students;

import com.alibaba.fastjson.JSONObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * 〈一句话功能简述〉<br> 
 * 〈单科成绩查询〉
 *
 * @author xclhs
 * @create 2018/12/17
 * @since 1.0.0
 */
public class fin_gra implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    private Client client;
    private JSONObject params;
    private index app;

    @FXML
    private TextField cid;






    public void setApp(index app){
        this.app = app;
    }

    @FXML
    public void quiHandler(){
        app.params=new JSONObject();
        app.draw(1, "");
    }


    @FXML
    public void reaHandler(){
        params.put("cid",cid.getText() );
        params.put("mid", 4);
        params.put("gtype", 2);
        this.app.SendInfo(params );
        new Thread(new Parser(client,app ,params )).start();
    }



    @FXML
    public void maiHandler(){
        int type = params.getInteger("type");
        GraAll.mainHandler(type, app, client, params);
    }


    public void set(Client client, JSONObject ja){
        this.client = client;
        this.params = ja;
    }

}

