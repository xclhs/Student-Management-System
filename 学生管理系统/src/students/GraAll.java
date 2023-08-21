/**
 * Copyright (C), 2015-2018,
 * FileName: GraAll
 * Author:   xclhs
 * Date:     2018/12/17 21:39
 * Description: 单科全部成绩
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
import javafx.scene.text.Text;


import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 〈一句话功能简述〉<br> 
 * 〈单科全部成绩〉
 *
 * @author xclhs
 * @create 2018/12/17
 * @since 1.0.0
 */
public class GraAll implements Initializable {

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
    private Text n1;
    @FXML
    private Text n2;
    @FXML
    private Text n3;
    @FXML
    private Text n4;
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
    private Label cname;






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
        n1.setText(params.getString("name"+count));
        n1.setVisible(true);
        g1.setText(params.getString("grade"+count));
        g1.setVisible(true);
        count++;
        n2.setText(params.getString("name"+count));
        n2.setVisible(true);
        g2.setText(params.getString("grade"+count));
        g2.setVisible(true);
        count++;
        n3.setText(params.getString("name"+count));
        n3.setVisible(true);
        g3.setText(params.getString("grade"+count));
        g3.setVisible(true);
        count++;
        n4.setText(params.getString("name"+count));
        n4.setVisible(true);
        g4.setText(params.getString("grade"+count));
        g4.setVisible(true);
        next.setVisible(true);
        p-=1;
        page.setText("第"+p+"页");

    }


    @FXML
    public void nextP(){
        p+=1;
        last.setVisible(true);
        page.setText("第"+p+"页");

        int Max;
        if(num-count>=base){//跳转到下一页等同于给数据赋新值
            Max = base*2;
        }else {
            Max=(num-count)*2;
        }
        n1.setVisible(false);
        n2.setVisible(false);
        n3.setVisible(false);
        n4.setVisible(false);
        g1.setVisible(false);
        g2.setVisible(false);
        g3.setVisible(false);
        g4.setVisible(false);

        if(Max>=2){
            count++;
            n1.setText(params.getString("name"+count));
            n1.setVisible(true);
            g1.setText(params.getString("grade"+count));
            g1.setVisible(true);

        }
        if(Max>=4){
            count++;
            n2.setText(params.getString("name"+count));
            n2.setVisible(true);
            g2.setText(params.getString("grade"+count));
            g2.setVisible(true);

        }
        if(Max>=6){
            count++;
            n3.setText(params.getString("name"+count));
            n3.setVisible(true);
            g3.setText(params.getString("grade"+count));
            g3.setVisible(true);


        }
        if(Max>=8){
            count++;
            n4.setText(params.getString("name"+count));
            n4.setVisible(true);
            g4.setText(params.getString("grade"+count));
            g4.setVisible(true);

        }
        if(count==num){
            next.setVisible(false);
        }


    }



    @FXML
    public void maiHandler(){
        params.put("mid", 2);
        this.app.SendInfo(params );
        new Thread(new Parser(client,app ,params )).start();
    }

    static void mainHandler(int type, index app, Client client, JSONObject params) {

        app.params = params;
        if(type==1) {
            app.draw(10,"" );
        }else if(type == 2){
            app.draw(17,"" );
        }else if(type == 3) {
            app.draw(7,"" );
        }else if(type == 4){
            app.draw(13,"" );
        }else{
            params.put("mid",0 );
            params.put("describe","type数据类型错失" );
            app.SendInfo(params);
            app.params.clear();
            app.draw(1, "");

        }
    }






    public void set(Client client, JSONObject ja){
        System.out.println("runnning");
        System.out.println(ja.toString());
        this.client = client;
        this.params = ja;
        num = ja.getInteger("num");
        last.setVisible(false);
        cname.setText(ja.getString("classname"));
        if(num<=base){
            next.setVisible(false);
        }
        nextP();
        last.setVisible(false);


    }

}

