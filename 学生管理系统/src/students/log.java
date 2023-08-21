/**
 * Copyright (C), 2015-2018,
 * FileName: log
 * Author:   xclhs
 * Date:     2018/12/16 20:17
 * Description: the controller for log.fxml
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

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 〈一句话功能简述〉<br> 
 * 〈the controller for log.fxml 〉
 *
 * @author xclhs
 * @create 2018/12/16
 * @since 1.0.0
 */
public class log implements Initializable {
    @FXML
    private TextField id;
    @FXML
    private TextField psw;
    @FXML
    private Text warning;
    private JSONObject params;
    private index app;
    private Client client;




    public void setApp(index app){
        this.app = app;
    }



    public void error(String error){
        this.psw.setText(null);
        this.id.setText(null);
        this.warning.setText(error);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void set(Client client,JSONObject ja){
        this.client = client;
        this.params = ja;

    }

    @FXML
    protected void handleClick(){

        System.out.println("点击登录");
            params.put("mid",2 );
            params.put("psw",psw.getText() );
            params.put("id",id.getText() );
            this.app.SendInfo(params);
            new Thread(new Parser(client,app ,params )).start();
    }
}


