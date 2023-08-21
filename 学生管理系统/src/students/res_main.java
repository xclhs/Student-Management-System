/**
 * Copyright (C), 2015-2018,
 * FileName: res_main
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
public class res_main implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }



    private Client client;
    private JSONObject params;
    private index app;

    @FXML
    private Text rname;
    @FXML
    private Text rid;
    @FXML
    private Text rschool;







    public void setApp(index app){
        this.app = app;
    }

    public void chaHandler(){//修改个人信息
        app.params = params;
        app.draw(5,"" );

    }

    public void priHandler(){//成绩打印
        app.params = params;
        app.draw(6,"" );
    }


    public void quiHandler(){
        app.params.clear();
        app.draw(1,"" );
    }

    @FXML
    public void finHandler(){
        app.params = params;
        app.draw(2,"" );
    }


    @FXML
    public void finAHandler(){
        app.params = params;
        app.draw(18,"" );
    }



    public void set(Client client, JSONObject ja){
        this.client = client;
        this.params = ja;
        this.rid.setText(ja.getString("id"));
        this.rname.setText(ja.getString("name"));
        this.rschool.setText(ja.getString("school"));
    }


}

