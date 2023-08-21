/**
 * Copyright (C), 2015-2018,
 * FileName: showOneAll
 * Author:   xclhs
 * Date:     2018/12/25 19:33
 * Description: show
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
 * 〈show 〉
 *
 * @author xclhs
 * @create 2018/12/25
 * @since 1.0.0
 */
public class showOneAll implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    private Client client;
    private JSONObject params;
    private index app;
    private int num;
    private int count=0;
    private int base = 4;
    private int p=0;

    @FXML
    private Text c1;
    @FXML
    private Text c2;
    @FXML
    private Text c3;
    @FXML
    private Text c4;
    @FXML
    private Text g1;
    @FXML
    private Text g2;
    @FXML
    private Text g3;
    @FXML
    private Text g4;
    @FXML
    private Button next;
    @FXML
    private Button last;
    @FXML
    private Label page;
    @FXML
    private Label name;



    private Text[] all = {c1,g1,c2,g2,c3,g3,c4,g4};




    public void setApp(index app){
        this.app = app;
    }

    @FXML
    public void quiHandler(){
        app.params = new JSONObject();
        app.draw(1,"" );
    }


    @FXML
    public void lastP(){
        if(count<=base){
            last.setVisible(false);
        }else{
            last.setVisible(true);
        }
        count-=4;
        count++;
        c1.setText(params.getString("classname"+count));
        c1.setVisible(true);
        g1.setText(params.getString("grade"+count));
        g1.setVisible(true);
        count++;
        c2.setText(params.getString("classname"+count));
        c2.setVisible(true);
        g2.setText(params.getString("grade"+count));
        g2.setVisible(true);
        count++;
        c3.setText(params.getString("classname"+count));
        c3.setVisible(true);
        g3.setText(params.getString("grade"+count));
        g3.setVisible(true);
        count++;
        c4.setText(params.getString("classname"+count));
        c4.setVisible(true);
        g4.setText(params.getString("grade"+count));
        g4.setVisible(true);
        next.setVisible(true);
        p-=1;
        page.setText("第"+p+"页");

    }


    @FXML
    public void nextP(){
        last.setVisible(true);
        int Max;
        if(num-count>=base){//跳转到下一页等同于给数据赋新值
            Max = base*2;
        }else {
            Max=(num-count)*2;
        }
        c1.setVisible(false);
        c2.setVisible(false);
        c3.setVisible(false);
        c4.setVisible(false);
        g1.setVisible(false);
        g2.setVisible(false);
        g3.setVisible(false);
        g4.setVisible(false);

        if(Max>=2){
            count++;
            c1.setText(params.getString("classname"+count));
            c1.setVisible(true);
            g1.setText(params.getString("grade"+count));
            g1.setVisible(true);

        }
        if(Max>=4){
            count++;
            c2.setText(params.getString("classname"+count));
            c2.setVisible(true);
            g2.setText(params.getString("grade"+count));
            g2.setVisible(true);

        }
        if(Max>=6){
            count++;
            c3.setText(params.getString("classname"+count));
            c3.setVisible(true);
            g3.setText(params.getString("grade"+count));
            g3.setVisible(true);


        }
        if(Max>=8){
            count++;
            c4.setText(params.getString("classname"+count));
            c4.setVisible(true);
            g4.setText(params.getString("grade"+count));
            g4.setVisible(true);

        }
        if(count==num){
            next.setVisible(false);
        }
        p+=1;
        page.setText("第"+p+"页");


    }



    @FXML
    public void maiHandler(){
        params.put("mid", 2);
        this.app.SendInfo(params );
        new Thread(new Parser(client,app ,params )).start();
    }


    public void set(Client client, JSONObject ja){
        this.client = client;
        this.params = ja;
        num = ja.getInteger("num");
        last.setVisible(false);
        page.setText("第"+p+"页");
        name.setText(ja.getString("cname"));
        nextP();
        last.setVisible(false);

    }
}

