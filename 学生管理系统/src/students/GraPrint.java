/**
 * Copyright (C), 2015-2018,
 * FileName: GraPrint
 * Author:   xclhs
 * Date:     2018/12/18 14:54
 * Description: 成绩打印
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
 * 〈成绩打印〉
 *
 * @author xclhs
 * @create 2018/12/18
 * @since 1.0.0
 */
public class GraPrint implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    @FXML
    private TextField cid;
    @FXML
    private TextField fp;


    private Client client;
    private JSONObject params;
    private index app;









    public void setApp(index app){
        this.app = app;
    }



    public void priHandler(){//成绩打印
        params.put("mid", 6);
        params.put("path",fp.getText() );
        params.put("cid",cid.getText() );
        this.app.SendInfo(params );
        new Thread(new Parser(client,app ,params )).start();


    }


    public void quiHandler(){
        app.params.clear();
        app.draw(1,"" );
    }


    public void set(Client client, JSONObject ja){
        this.client = client;
        this.params = ja;
    }

}

