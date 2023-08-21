/**
 * Copyright (C), 2015-2018,
 * FileName: SysInf
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
import javafx.scene.control.Button;
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
public class SysInf implements Initializable {
    private Client client = null;
    private JSONObject params = null;
    private index app;
    @FXML
    private TextField id;
    @FXML
    private Text sid1;
    @FXML
    private Text sname1;
    @FXML
    private Text ssex1;
    @FXML
    private Text sbirth1;
    @FXML
    private Text smajor1;
    @FXML
    private Text sschool1;
    @FXML
    private Label sid2;
    @FXML
    private Label sname2;
    @FXML
    private Label ssex2;
    @FXML
    private Label sbirth2;
    @FXML
    private Label smajor2;
    @FXML
    private Label sschool2;




    public void setStu(Client client,JSONObject ja){
        sbirth1.setText(ja.getString("birth"));
        sbirth1.setVisible(true);
        sbirth2.setVisible(true);
        sname1.setText(ja.getString("name"));
        sname1.setVisible(true);
        sname2.setVisible(true);
        sschool1.setText(ja.getString("school"));
        sschool1.setVisible(true);
        sschool2.setVisible(true);
        ssex1.setText(ja.getString("sex"));
        ssex1.setVisible(true);
        ssex2.setVisible(true);
        sbirth1.setText(ja.getString("birth"));
        sbirth1.setVisible(true);
        sbirth2.setVisible(true);
        sid1.setText(ja.getString("id2"));
        sid1.setVisible(true);
        sid2.setVisible(true);
        smajor1.setText(ja.getString("major"));
        smajor2.setVisible(true);
        smajor1.setVisible(true);

    }

    @FXML
    private Label tname2;
    @FXML
    private Label tschool2;
    @FXML
    private Label tid2;
    @FXML
    private Label tdepart2;
    @FXML
    private Text tname1;
    @FXML
    private Text tid1;
    @FXML
    private Text tdepart1;
    @FXML
    private Text tschool1;

    public void setTea(Client client,JSONObject ja){
        tname1.setText(ja.getString("name"));
        tname1.setVisible(true);
        tname2.setVisible(true);
        tid1.setText(ja.getString("id2"));
        tid1.setVisible(true);
        tid2.setVisible(true);
        tschool1.setText(ja.getString("school"));
        tschool1.setVisible(true);
        tschool2.setVisible(true);
        tdepart1.setText(ja.getString("depart"));
        tdepart1.setVisible(true);
        tdepart2.setVisible(true);

    }

    @FXML
    private Label rname2;
    @FXML
    private Label rid2;
    @FXML
    private Label rschool2;
    @FXML
    private Text rname1;
    @FXML
    private Text rid1;
    @FXML
    private Text rschool1;
    @FXML
    private Button ssear;
    @FXML
    private Label off;


    public void setRea(Client client,JSONObject ja){
        rname1.setText(ja.getString("name"));
        rname1.setVisible(true);
        rname2.setVisible(true);
        rschool1.setText(ja.getString("school"));
        rschool1.setVisible(true);
        rschool2.setVisible(true);
        rid1.setText(ja.getString("id2"));
        rid1.setVisible(true);
        rid2.setVisible(true);

    }




    public void setApp(index app){
        this.app = app;
    }

    public void setOff(){
        off.setVisible(false);
        id.setVisible(false);
        ssear.setVisible(false);

    }

    public void subHandler(){
        params.put("mid",7 );
        params.put("id2", id.getText());
        app.SendInfo(params );
        System.out.println(params.toString());
        new Thread(new Parser(client,app ,params )).start();

    }





    @FXML
    public void quiHandler(){
        //返回查询页面
        GraAll.mainHandler(4,app ,client,params );
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void set(Client client, JSONObject ja){
        this.client = client;
        this.params = ja;


    }
}

