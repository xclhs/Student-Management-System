/**
 * Copyright (C), 2015-2018,
 * FileName: GraOneAll
 * Author:   xclhs
 * Date:     2018/12/25 19:34
 * Description: gra_of_all
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
 * 〈gra_of_all〉
 *
 * @author xclhs
 * @create 2018/12/25
 * @since 1.0.0
 */
public class GraOneAll implements Initializable {
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
    private TextField sid;




    private Client client;
    private JSONObject params;
    private index app;

    public void setApp(index app){
        this.app = app;
    }


    @FXML
    public void maiHandler(){//修改个人信息
        int type = params.getInteger("type");
        GraAll.mainHandler(type,app ,client ,params );
    }



    @FXML
    public void quiHandler(){
        this.params = new JSONObject();
        app.draw(1,"" );

    }
    @FXML
    public void reaHandler(){
        this.params.put("mid",4 );
        this.params.put("gtype", 3);
        this.params.put("sid", sid.getText());
        System.out.println("查询单人成绩");
        new Thread(new Parser(client,app ,params )).start();
        app.SendInfo(params);

    }



    public void set(Client client, JSONObject ja){
        this.client = client;
        this.params = ja;

    }

}

