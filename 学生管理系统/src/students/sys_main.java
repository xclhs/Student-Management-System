/**
 * Copyright (C), 2015-2018,
 * FileName: sys_main
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
public class sys_main implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }


    private Client client;
    private JSONObject params;
    private index app;





    public void setApp(index app){
        this.app = app;
    }

    @FXML
    public void seaHandler(){//处理查询请求
        app.params=params;
        app.draw(2,"" );
    }

    @FXML
    public void seaAHandler(){//处理查询请求
        app.params=params;
        app.draw(18,"" );
    }

    @FXML
    public void peoHandler(){//处理修改信息请求
        app.params=params;
        app.draw(12,"" );
    }

    public void quiHandler(){
        app.params.clear();
        app.draw(1,"" );
    }


    public void set(Client client,  JSONObject ja){
        this.client = client;
        this.params = ja;

    }


}

