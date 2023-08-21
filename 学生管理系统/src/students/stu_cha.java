/**
 * Copyright (C), 2015-2018,
 * FileName: stu_cha
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xclhs
 * @create 2018/12/2
 * @since 1.0.0
 */
public class stu_cha implements Initializable {


    @FXML
    private Text sid;
    @FXML
    private TextField sname;
    @FXML
    private TextField sschool;
    @FXML
    private TextField smajor;
    @FXML
    private TextField ssex;
    @FXML
    private TextField spsw;
    @FXML
    private DatePicker sbirth;



    private Client client;
    private JSONObject params;
    private index app;


    public void set(Client client,JSONObject ja) {

        //设置初始值
        this.params = ja;
        this.client = client;

        //设置学生原有的信息
        sid.setText(ja.getString("id"));
        sname.setText(ja.getString("name"));
        smajor.setText(ja.getString("major"));
        sschool.setText(ja.getString("school"));
        ssex.setText(ja.getString("sex"));
        spsw.setText(ja.getString("psw"));

        LocalDate ld = null;

        try {
            ld = getLocalDate(ja.getString("birth"));
        } catch (ParseException e) {
            client.logError(e.getMessage());
        }
        sbirth.setValue(ld);

    }

    private LocalDate getLocalDate(String  date) throws ParseException {
        //将yyyy-mm-dd的字符串转换成localdate的类型
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date date1 = df.parse(date);
        Instant instant = date1.toInstant();
        ZoneId zi = ZoneId.systemDefault();
        LocalDate ld = instant.atZone(zi).toLocalDate();
        //atZone()方法返回资金时区从Instant的ZoneDatetime;
        return ld;
    }



    public void setApp(index app){
        this.app = app;
    }




    @FXML
    public void subHandler(){//处理修改信息请求
        params.put("id",sid.getText() );
        params.put("name",sname.getText());
        params.put("birth",sbirth.getValue() );
        params.put("school",sschool.getText());
        params.put("major",smajor.getText() );
        params.put("sex",ssex.getText() );
        params.put("psw",spsw.getText() );
        params.put("mid",3);
        this.app.params = params;
        this.app.SendInfo(params );
        new Thread(new Parser(client,app ,params )).start();


    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    public void quiHandler(){
        app.draw(10,"" );
    }





}

