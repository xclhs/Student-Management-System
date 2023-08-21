/**
 * Copyright (C), 2015-2018,
 * FileName: gra_sear
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
public class gra_sear implements Initializable {
   private Client client = null;
   private JSONObject params = null;
   private index app;

   @FXML
   private TextField scour;
   @FXML
   private Text warning;






    public void setApp(index app){
        this.app = app;
    }

    public void error(String error){
        warning.setText(error);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void quiHandler(){
        app.params=params;
        app.draw(10, "");
        client.logError("转到学生主页面");
    }

    @FXML
    public void seaHandler(){
        if(params.getInteger("type")==1) {
            params.put("cid", scour.getText());
            params.put("mid",4 );
            params.put("gtype", 1);
            this.app.SendInfo(params);
            new Thread(new Parser(client,app ,params )).start();

        }
    }



    public void set(Client client, JSONObject ja){
        this.client = client;
        this.params = ja;

    }


}

