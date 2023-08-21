/**
 * Copyright (C), 2015-2018,
 * FileName: stu_res
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
public class stu_res implements Initializable {
    private Client client = null;
    private JSONObject params = null;
    private index app;
    @FXML
    private Text name;
    @FXML
    private Text grade;
    @FXML
    private Text claname;



    public void setApp(index app){
        this.app = app;
    }

    @FXML
    public void maiHandler(){
        //返回主菜单
        app.params = params;
        app.draw(10,"" );
    }

    @FXML
    public void quiHandler(){
        //返回查询页面
        app.params = params;
        app.draw(8,"" );
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void set(Client client, JSONObject ja){
        this.client = client;
        this.params = ja;
        this.name.setText(ja.getString("name"));
        this.claname.setText(ja.getString("classname"));
        this.grade.setText(ja.getString("grade"));

    }

}

