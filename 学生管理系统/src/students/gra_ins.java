/**
 * Copyright (C), 2015-2018,
 * FileName: gra_ins
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


import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
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
public class gra_ins implements Initializable {
    private Client client = null;
    private JSONObject params = null;
    private index app;
    @FXML
    private TextField cid;
    @FXML
    private TextField sid;
    @FXML
    private TextField grade;



    public void setApp(index app){
        this.app = app;
    }

    @FXML
    public void maiHandler(){
        GraAll.mainHandler(params.getInteger("type"),app ,client ,params );

    }

    @FXML
    public void quiHandler(){
        //退出登录
        app.params = new JSONObject();
        app.draw(1,"" );

    }

    @FXML
    public void insHandler(){
        params.put("mid",5 );
        params.put("cid",cid.getText() );
        params.put("sid",sid.getText());
        params.put("grade",grade.getText() );
        this.app.SendInfo(params);
        new Thread(new Parser(client,app ,params )).start();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void set(Client client, JSONObject ja){
        this.client = client;
        this.params = ja;
    }
}

