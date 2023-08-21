/**
 * Copyright (C), 2015-2018,
 * FileName: SysCho
 * Author:   xclhs
 * Date:     2018/12/2 12:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * xclhs           修改时间           版本号              描述
 */
package students;

import com.alibaba.fastjson.JSONObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


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
public class SysCho implements Initializable {
    private Client client = null;
    private JSONObject params = null;
    private index app;




    public void setApp(index app){
        this.app = app;
    }



    @FXML
    public void quiHandler(){
        //返回查询页面
        GraAll.mainHandler(4,app ,client,params );
    }

    @FXML
    public void seaHandler(){
        //进入信息查询页面
        app.params=params;
        app.draw(14,"" );

    }

    @FXML
    public void chaHandler(){
        //进入信息修改页面
        app.params=params;
        app.draw(4,"" );
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void set(Client client, JSONObject ja){
        this.client = client;
        this.params = ja;


    }

}

