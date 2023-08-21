/**
 * Copyright (C), 2015-2018,
 * FileName: Client
 * Author:   xclhs
 * Date:     2018/12/2 13:03
 * Description: 用于传递数据到后台
 * History:
 * <author>          <time>          <version>          <desc>
 * xclhs           修改时间           版本号              描述
 */
package students;


import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用于传递数据到后台〉
 *
 * @author xclhs
 * @create 2018/12/2
 * @since 1.0.0
 */
public class Client {
    private long id;
    private int port = 6000;
    private String server = "144.34.135.50";//"144.34.135.50"
    public Socket client;
    private File log;
    private FileWriter fw;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");//设置日期格式

    public PrintWriter Send() throws IOException {//向服务器端发送数据
         PrintWriter send = null;
         send = new PrintWriter(new BufferedOutputStream(this.client.getOutputStream()));
         fw.write(df.format(new Date()) + ":发送数据到服务器\n");
         return send;
    }

    public BufferedReader Receive() throws IOException {//接收服务器端发送的数据
        BufferedReader receive = null;
        try {
            receive = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        }catch (java.lang.NullPointerException e){
            this.logError(e.getMessage());
        }
        return receive;
    }

    public void logError(String e){
        try {
            this.fw.write(e);
            this.fw.write("\n");
            this.fw.write(df.format(new Date()));
            this.fw.write("\n");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }



    public Client() throws IOException {
        log = new File("E:/stuSys/log.md");

        //创建日志文件
        if(!log.exists()){
           File p = log.getParentFile();
            if(!p.exists()){
                p.mkdirs();
            }
            log.createNewFile();

        }

        //在文件中写入
       if(this.log.isFile()){
           FileWriter fw = null;//保留原有内容的基础上添加新的内容;
           fw = new FileWriter(this.log,true);
           this.fw = fw;
           fw.write(df.format(new Date())+":成功连接服务器"+server+":"+port);
       }

        Socket client = null;
        try {
            client = new Socket(server,port);
        } catch (java.net.ConnectException e){//服务器拒绝连接
            fw.write(df.format(new Date())+":服务器拒接连接"+server+":"+port);
            return;
            }
        this.client = client;
        //连接到主机
    }
}

