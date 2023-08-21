/**
 * Copyright (C), 2015-2018,
 * FileName: Parser
 * Author:   xclhs
 * Date:     2018/12/16 21:02
 * Description: parser the information for server
 * History:
 * <author>          <time>          <version>          <desc>
 * xclhs           修改时间           版本号              描述
 */
package students;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈parser the information for server〉
 *
 * @author xclhs
 * @create 2018/12/16
 * @since 1.0.0
 */
public class Parser implements Runnable{
    private Client client;
    private index app;
    private JSONObject params1;
    private static int type = 0;
    private JSONObject ja;

    public void run() {
        //更新JavaFX的主线程的代码放在此处
        System.out.println("运行Parser文件");
        /*
         *获取服务器端的信息：
         * 一：首先判断mid(1/2)
         * 二：判断类型(1/2/3/4)
         * 三：获取具体数据
         * */

        StringBuilder sb = new StringBuilder();
        String reader;
        while (true) {
            sb.setLength(0);
            try {
                System.out.println("读取数据");
                reader = app.bf.readLine();
                System.out.println("reading:"+reader);
                while (reader != null && reader.trim().length() != 0) {
                    sb.append(reader);
                    reader = app.bf.readLine();
                }
                System.out.println("over");
                ja = JSON.parseObject(sb.toString());
                int mid = ja.getInteger("mid");
                System.out.println("mid:"+mid);
                if(mid==0){
                    dealM0(ja);
                } else if(mid==1){
                    dealM1(ja);
                    break;
                    //跳出循环
                } else if(mid==2) {
                    dealM2(ja);
                    break;
                } else if(mid==3) {
                    dealM3(ja);
                    break;
                } else if(mid==4) {
                    dealM4(ja);
                    break;
                } else if(mid==5){
                    dealM5(ja);
                    break;
                } else if(mid==6){
                    dealM6(ja);
                    break;
                } else if(mid==7){
                    dealM7(ja);
                    break;
                } else if(mid==8){
                    dealM8(ja);
                    break;
                }


            } catch (NullPointerException | java.net.SocketException e1){
                errorDeal();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }


        }
        System.out.println("over-all-of-run");
    }

    public Parser(Client client,index app,JSONObject ja){
        this.client = client;
        this.app = app;
        this.params1 = ja;

    }

   public boolean errorDeal() {
        try {
            this.client.client.close();//服务器端关闭情况下，关闭客户端
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //分别解决不同mid的返回内容

    public void dealM0(JSONObject ja){
        if(ja.getInteger("etype")==2){
            client.logError("信息查询不到");
            GraAll.mainHandler(type, app, client, params1);
        } else if(ja.getInteger("etype")==3) {
            this.app.draw(1,"密码或账户错误" );
        }else{
            //编码等其他报错
            errorDeal();
        }

    }


    public void dealM1(JSONObject ja) throws IOException {
        if(!ja.getBoolean("status")){
            this.client.client.close();
            this.client.logError(ja.getString("describe"));
        }else{
            this.client.logError("服务器与客户端成功连接");
            System.out.println("成功连接");

        }
    }


    public void dealM2(JSONObject ja) {
        System.out.println(ja.toString());
        System.out.println("处理主页信息");
        int type = ja.getInteger("type");
        ja.put("psw", params1.getString("psw"));
        System.out.println(type);
            if (type == 1){
                System.out.println("处理type为1");
                this.type = 1;
                dealT1(ja);
                //跳转到学生主页
                //跳出循环
            } else if (type == 2) {
                System.out.println("处理type为2");
                this.type = 2;
                dealT2(ja);
                //跳转到老师主页
            } else if (type == 3) {
                System.out.println("处理type==3");
                this.type = 3;
                dealT3(ja);
                //跳转到教务员主页
            } else if (type == 4) {
                System.out.println("处理type为4");
                this.type = 4;
                dealT4(ja);
                //跳转到系统管理员页面

            } else {
                ja = new JSONObject();
                ja.put("mid", 0);
                ja.put("error", "数据类型不匹配/数据不匹配");
                this.app.SendInfo(ja);
                //向服务器发送报错信息
                this.app.params = ja;
                app.draw(1, "服务器无法连接" );
            }

        }



    public void dealM3(JSONObject ja)  {
        //处理修改信息返回
        int type = params1.getInteger("type");
        if(ja.getBoolean("status")){
            //说明信息修改成功
            //修改成功后默认转入到主页
            if(type==1){
                app.draw(9,"" );
            }else if(type==2){
                app.draw(15,"" );
            }else if(type==3){
                app.draw(5,"" );
            }
        }


    }

    public void dealM4(JSONObject ja)  {
        int gtype = ja.getInteger("gtype");
        System.out.println("deal gtype :"+gtype);
        if(gtype == 1){
            //显示学生单科成绩
            dealG1(ja);
        }else if(gtype == 2){
            dealG2(ja);
        }else if(gtype==3) {
            dealG3(ja);
        }else {
                params1.put("mid",0 );
                params1.put("describe", "gtype is error");
                SendInfo(params1);

            }


    }

    public void dealM5(JSONObject ja){
        app.params =params1;
        if(ja.getBoolean("status")){
            app.draw(16, "");
        }else{
            client.logError("数据插入失败");
            app.draw(16,"" );
        }

    }

    public void dealM6(JSONObject ja){
        PrintWriter pw = null;
        try {
           pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(params1.getString("path")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.println(ja.getString("classname"));
        int num = ja.getInteger("num");
        int i=1;
        for(;i<=num;i++){
            pw.println(ja.getString("name"+i)+"\t"+ja.getString("grade"+i));
        }
        pw.flush();
        pw.close();
        app.params=params1;
        app.draw(6, "");
    }

    public void dealM7(JSONObject ja){
        System.out.println("deal mid = 7 ");
        app.params = ja;
        app.draw(14,"true" );
    }

    public void dealM8(JSONObject ja){
        System.out.println("deal mid = 8 ");
        if(ja.getBoolean("status")){
            app.draw(4,"" );
        }

    }


    public void dealG1(JSONObject ja){
        params1.put("grade",ja.getString("grade") );
        params1.put("classname",ja.getString("classname") );
        this.app.params=params1;
        app.draw(11, "");

    }

    public void dealG2(JSONObject ja){
        System.out.println("deal gtype 2");
        ja.put("psw",params1.getString("psw") );
        ja.put("id",params1.getString("id") );
        this.app.params=ja;
        this.app.draw(3,"" );
    }

    public void dealG3(JSONObject ja){
        ja.put("id",params1.getString("id") );
        ja.put("psw",params1.getString("psw") );
        ja.put("cname",ja.getString("name") );
        ja.put("name",params1.getString("name") );
        ja.put("type",params1.getInteger("type") );
        this.app.params=ja;
        this.app.draw(19,"" );
    }



    public void dealT1(JSONObject ja)  {
        System.out.println("student");
        this.app.params=ja;
        this.app.draw(10, "");

    }

    public void dealT2(JSONObject ja)  {
        System.out.println("teacher");
        this.app.params=ja;
        this.app.draw(17,"" );
        //跳转到老师主页

    }

    public void dealT3(JSONObject ja) {
        System.out.println("searcher");
        this.app.params=ja;
        this.app.draw(7,"");
    }

    public void dealT4(JSONObject ja)  {
        System.out.println("system");
        this.app.params=ja;
        this.app.draw(13,"" );
    }


    public void SendInfo(JSONObject ja){
        this.app.SendInfo(ja);
    }




}

