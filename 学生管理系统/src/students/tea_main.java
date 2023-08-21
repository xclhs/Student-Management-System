/**
 * Copyright (C), 2015-2018,
 * FileName: tea_main
 * Author:   xclhs
 * Date:     2018/12/2 12:43
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * xclhs           修改时间           版本号              描述
 */
package students;

import com.alibaba.fastjson.JSONObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


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
public class tea_main implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    private Client client;
    private JSONObject params;
    private index app;

    @FXML
    private Text tname;
    @FXML
    private Text tid;
    @FXML
    private Text depart;
    @FXML
    private Text school;





    public void setApp(index app){
        this.app = app;
    }

    @FXML
    public void quiHandler(){
        app.params.clear();
        app.draw(1,"" );
    }


    @FXML
    public void chaHandler(){
        app.params=params;
        app.draw(15,"" );

    }

    @FXML
    public void insHandler(){
        app.params=params;
        app.draw(16,"" );

    }

    @FXML
    public void seaHandler(){
        app.params=params;
        app.draw(2,"" );

    }


    @FXML
    public void seaAHandler(){
        app.params=params;
        app.draw(18,"" );

    }


    public void set(Client client, JSONObject ja){
       this.client = client;
       this.params = ja;
       this.tid.setText(ja.getString("id"));
       tname.setText(ja.getString("name"));
       school.setText(ja.getString("school"));
       depart.setText(ja.getString("depart"));
    }


}

