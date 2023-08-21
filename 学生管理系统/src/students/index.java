package students;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;


/*
* 鉴于与服务器连接的时候速度会比较慢，同时因为是多线程，在本地可以打开多个窗口
* */
public class index extends Application {
    private int count=0;
    public Stage stage;
    public BufferedReader bf;
    private PrintWriter pw;
    public Client client;
    private Parser parser;


    //与服务器进行信息交换的格式：json格式
    public JSONObject params ;

    //设置最低长*宽
    private double MinWidth = 610.00;
    private double MinHeight = 420.00;

    public static void main(String[] args) {
        System.out.println("start");
        //客户端与服务器进行连接
        launch(args);


   }


     public void SendInfo(JSONObject params) {
            //数据出现错误
            this.pw.println(params.toString());
            this.pw.println(" ");
            this.pw.flush();
            System.out.println("发送数据："+params.toString());
    }


    public void apply(Client client){
        this.client = client;
        try {
            this.bf = this.client.Receive();
            this.pw = this.client.Send();
        } catch (IOException e) {
            client.logError(e.getMessage());
        }
        this.params = new JSONObject();
        this.params.put("ip",this.client.client.getInetAddress() );
        this.params.put("mid",1 );
        this.SendInfo(params );

/*
        发送请求连接的请求:
        ip:
        mid:1
        结束：空行
        完成初始化
         */


    }



    @Override
    public void start(Stage primaryStage) {
        try {
            Client client = new Client();
            apply(client);
            System.out.println(count++);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage = primaryStage;
        stage.setTitle("学生成绩管理系统");
        gotoLog(client,false ,"" , params);
        stage.show();
        new Thread(new Parser(client,this ,params )).start();
    }

    public void draw(int type,String error){
        Boolean flag = false;
        if(error.trim().length()!=0){
            flag = true;
        }
        System.out.println("flag:"+flag);
        Boolean finalFlag = flag;
        Platform.runLater(() -> {
            switch (type){
                case 1:gotoLog(client, finalFlag,error , params);break;
                case 2:gotoFinGra(client,params );break;
                case 3:gotoGraAll(client,params );break;
                case 4:gotoInfoCha(client,params );break;
                case 5:gotoReaCha(client,params );break;
                case 6:gotoReaPri(client, params);break;
                case 7:gotoResMain(client,params );break;
                case 8:gotoResOne(client,params ,finalFlag ,error );break;
                case 9:gotoStuCha(client, params);break;
                case 10:gotoStuMain(client,params );break;
                case 11:gotoStuRes(client, params);break;
                case 12:gotoSyscho(client,params );break;
                case 13:gotoSysMain(client,params );break;
                case 14:gotoSysSea(client,params , finalFlag);break;
                case 15:gotoTeaCha(client,params );break;
                case 16:gotoTeaIns(client,params );break;
                case 17:gotoTeaMain(client,params );break;
                case 18:gotoGraOne(client,params);break;
                case 19:gotoShowOne(client,params );break;
                default:break;
            }
        });
    }

    public void gotoGraOne(Client client,JSONObject ja){
        try {

            GraOneAll main = (GraOneAll) replaceSceneContent("gra_one_all.fxml");
            main.set(client,ja);
            main.setApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoShowOne(Client client,JSONObject ja){
        try {
            showOneAll main = (showOneAll) replaceSceneContent("show_one_all.fxml");
            main.set(client,ja);
            main.setApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void gotoLog(Client client, Boolean flag,String error,JSONObject ja){//登陆界面
        try{
            log main = (log)replaceSceneContent("log.fxml");
            main.set(client,ja);
            main.setApp(this);
            if(flag){
                main.error(error);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void gotoInfoCha(Client client,JSONObject ja){
        try {
            InfCha main = (InfCha) replaceSceneContent("inf_cha.fxml");
            main.set(client,ja);
            main.setApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoStuMain(Client client,JSONObject ja){
        try {
            stu_main main = (stu_main)replaceSceneContent("stu_main.fxml");
            main.set(client,ja);
            main.setApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoTeaMain(Client client,JSONObject ja){
            try {
                tea_main main = (tea_main)replaceSceneContent("tea_main.fxml");
                main.set(client,ja );
                main.setApp(this);

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void gotoResOne(Client client,JSONObject ja,boolean flag,String error){
        try {
            gra_sear main = (gra_sear) replaceSceneContent("gra_sear.fxml");
            main.set(client,ja );
            if(flag){
                main.error(error);
            }
            main.setApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void gotoSysMain(Client client,JSONObject ja){
        try {
            sys_main main = (sys_main)replaceSceneContent("sys_main.fxml");
            main.set(client,ja );
            main.setApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoStuCha(Client client,JSONObject ja){
        try {
            stu_cha main = (stu_cha)replaceSceneContent("stu_cha.fxml");
            main.set(client,ja );
            main.setApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void gotoStuRes(Client client,JSONObject ja){
        try{
            stu_res main = (stu_res)replaceSceneContent("stu_res.fxml");
            main.set(client,ja );
            main.setApp(this);
        }catch (Exception e){
            client.logError(e.getMessage());
        }


    }

    public void gotoResMain(Client client,JSONObject ja){
        try {
            res_main main = (res_main) replaceSceneContent("res_main.fxml");
            main.set(client,ja );
            main.setApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoFinGra(Client client,JSONObject ja){
        try{
            fin_gra main = (fin_gra) replaceSceneContent("res_fin_gra.fxml");
            main.set(client,ja );
            main.setApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoTeaCha(Client client,JSONObject ja){
        try {
            tea_cha main = (tea_cha) replaceSceneContent("tea_cha.fxml");
            main.set(client,ja );
            main.setApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }





    public void gotoTeaIns(Client client,JSONObject ja){
        try {
            gra_ins main = (gra_ins) replaceSceneContent("gra_ins.fxml");
            main.set(client,ja );
            main.setApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void gotoReaCha(Client client,JSONObject ja){
        try {
            res_cha main= (res_cha)replaceSceneContent("res_cha.fxml");
            main.set(client,ja );
            main.setApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    public void gotoReaPri(Client client,JSONObject ja){
        try {
            GraPrint main = (GraPrint) replaceSceneContent("gra_print.fxml");
            main.set(client,ja );
            main.setApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void gotoSysSea(Client client,JSONObject ja,Boolean flag){
        try {
            SysInf main = (SysInf) replaceSceneContent("sys_inf.fxml");
            main.set(client,ja );
            main.setApp(this);
            System.out.println("处理信息查询");

            //ja服务器传回信息

            if(flag){
                main.setOff();
                System.out.println("信息查询");
                System.out.println("跳转查询信息页面");
                int type2 = ja.getInteger("type2");
                if(type2==1){
                   main.setStu(client,ja );
                }else if(type2==2){
                    main.setTea(client,ja );
                }else if(type2==3){
                    main.setRea(client,ja );
                }else{
                    draw(14,"" );

                }
            }





        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void gotoSyscho(Client client,JSONObject ja){
        try {
            SysCho main = (SysCho) replaceSceneContent("sys_cho.fxml");
            main.set(client,ja );
            main.setApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void gotoGraAll(Client client,JSONObject ja){
        try {
            GraAll main = (GraAll) replaceSceneContent("graAll.fxml");
            main.set(client,ja );
            main.setApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = index.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(index.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, MinWidth, MinHeight);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }



}

