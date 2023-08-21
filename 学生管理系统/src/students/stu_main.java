/**
 * Copyright (C), 2015-2018,
 * FileName: stu_main
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

public class stu_main implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    @FXML
    private Text sid;
    @FXML
    private Text sname;
    @FXML
    private Text sschool;
    @FXML
    private Text smajor;
    @FXML
    private Text ssex;
    @FXML
    private Text sbirth;



    private Client client;
    private JSONObject params;
    private index app;



    public void setApp(index app){
        this.app = app;
    }

    @FXML
    public void ResHandler(){//处理查询请求
        app.params=params;
        app.draw(8,"" );
    }

    @FXML
    public void ResAHandler(){//处理查询请求
        app.params=params;
        app.params.put("sid",params.getString("id") );
        app.params.put("gtype",3 );
        app.params.put("mid", 4);
        app.SendInfo(app.params);
        new Thread(new Parser(client,app ,app.params )).start();
    }

    @FXML
    public void ChaHandler(){//处理修改信息请求
        app.params=params;
        app.draw(9,"" );

    }

    public void QuiHandler(){
        app.params.clear();
        app.draw(1,"" );
    }


    public void set(Client client,JSONObject ja){
        this.client = client;
        this.params = ja;
        sbirth.setText(ja.getString("birth"));
        sid.setText(ja.getString("id"));
        sname.setText(ja.getString("name"));
        smajor.setText(ja.getString("major"));
        sschool.setText(ja.getString("school"));
        ssex.setText(ja.getString("sex"));


    }


}

