/**
 * Copyright (C), 2015-2018,
 * FileName: Server
 * Author:   xclhs
 * Date:     2018/12/10 10:54
 * Description: 服务器端
 * History:
 * <author>          <time>          <version>          <desc>
 * xclhs           修改时间           版本号              描述
 */
//package students;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Driver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.text.ParseException;


/**
 * 〈一句话功能简述〉<br>
 * 〈服务器端〉
 *
 * @author xclhs
 * @create 2018/12/10
 * @since 1.0.0
 */
public class Server {
    private ServerSocket serverSocket;

    public void startServer() throws SQLException, ClassNotFoundException {
        Socket socket =null;
        try {
            serverSocket = new ServerSocket(6000);
            while(true) {
                socket = serverSocket.accept();
                new CreateServerThread(socket);//当有请求时，启一个线程处理
            }
        }  catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket!=null){
                try{
                    socket.close();
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //线程类
    class CreateServerThread extends Thread {
        private int count=0;
        private Socket client;
        private BufferedReader bufferedReader;
        private PrintWriter printWriter;
        private JSONObject retJa=new JSONObject();

        String databaseName = "java5_1";// 已经在MySQL数据库中创建好的数据库。
        String userName = "root";// MySQL默认的root账户名
        String password = "360725";// 默认的root账户密码为空

        Statement stmt;
        Connection conn;

        public CreateServerThread(Socket s) throws IOException, SQLException, ClassNotFoundException {
            client = s;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName+"?characterEncoding=utf8", userName, password);//连接数据库
            if(conn==null){
                System.out.println("连接数据库错误！");
            }else{
                System.out.println("成功连接服务器！");
            }
            stmt = conn.createStatement();//创建sql语句
            bufferedReader =new BufferedReader(new InputStreamReader(new DataInputStream(client.getInputStream())));
            printWriter =new PrintWriter(client.getOutputStream(),true);
            System.out.println("Client(" + getName() +") come in...");

            start();
        }




        public void run() {
            /*
             *获取服务器端的信息：
             * 一：首先判断mid(1/2)
             * 二：判断类型(1/2/3/4)
             * 三：获取具体数据
             * */


            try {
                StringBuilder sb = new StringBuilder();
                String reader = null;
                long id=0;
                int type=0;
                //char ch;

                    while (true) {
                        try{
                        System.out.println(count++);
                        reader = this.bufferedReader.readLine();
                        System.out.println("start while");
                        while (reader != null && reader.trim().length() != 0) {
                            sb.append(reader);
                            System.out.println("append");
                            reader = this.bufferedReader.readLine();
                        }
                        System.out.println("end while");

                        System.out.println("ja:"+sb.toString());
                        JSONObject ja = JSON.parseObject(sb.toString());
                        int mid = ja.getInteger("mid");

                        if (mid == 1) {
                            Dealmid001(ja);
                        } else if (mid == 2) {
                            id=ja.getLongValue("id");
                            type=Id2Type(id);
                            Dealmid2(ja);
                        } else if (mid == 3) {
                            Dealmid3(ja,id);
                        } else if (mid == 4) {
                            Dealmid4(ja,id,type);
                        } else if (mid == 5) {
                            Dealmid5(ja,id);
                        } else if (mid == 6) {
                            Dealmid6(ja);
                        } else if (mid == 7) {
                            Dealmid7(ja);
                        } else if (mid == 8) {
                            Dealmid8(ja);
                        } else {
                            retJa.put("mid", 0);
                            retJa.put("etype", 1);
                            retJa.put("error", "不存在此类访问数据库方法");
                        }
                        System.out.println("retJa:"+retJa.toString());
                        printWriter.println(retJa.toString());
                        printWriter.println("");
                        printWriter.flush();
                        retJa.clear();
                        sb.setLength(0);
                        System.out.println("end");


                    }catch(java.net.SocketTimeoutException e){
                            System.out.println(e.getMessage());
                            continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public int Id2Type(long id) throws SQLException {
            String type="Student";
            String sql="SELECT id FROM "+type+" WHERE id="+Long.toString(id);
            ResultSet result;
            result = stmt.executeQuery(sql);
            if(result.next()){return 1;}
            else{
                type="Teacher";
                sql="SELECT id FROM "+type+" WHERE id="+Long.toString(id);
                result=stmt.executeQuery(sql);
                if(result.next()){return 2;}
                else{
                    type="Officer";
                    sql="SELECT id FROM "+type+" WHERE id="+Long.toString(id);
                    result=stmt.executeQuery(sql);
                    if(result.next()){return 3;}
                    else{
                        type="Administrator";
                        sql="SELECT id FROM "+type+" WHERE id="+Long.toString(id);
                        result=stmt.executeQuery(sql);
                        if(result.next()){return 4;}
                        else{
                            return -1;
                        }
                    }
                }
            }
        }

        public void Dealmid001(JSONObject ja){//处理客户端的连接请求
            retJa.put("mid",1);
            retJa.put("status",true);
        }
        public void Dealmid2(JSONObject ja) throws SQLException {//处理客户端的登录请求
            System.out.println("enter Dealmid2");
            int type=Id2Type(ja.getLongValue("id"));
            System.out.println("type:"+type);
            String sql;
            if(type==1){
                sql="SELECT id,psw,name,sex,school,birth,major FROM Student WHERE id="+ja.getBigInteger("id").toString();
                System.out.println("sql:"+sql);
                ResultSet result=stmt.executeQuery(sql);//执行sql语句
                if(result.next()){//id存在
                    //BigInteger psw=new BigInteger(result.getString("psw"));
                    long psw=result.getLong("psw");
                    if(psw==ja.getLongValue("psw")){//psw正确
                        retJa.put("mid",2);
                        retJa.put("type",1);
                        retJa.put("name",result.getString("name"));
                        retJa.put("id",result.getLong("id"));
                        retJa.put("sex",result.getString("sex"));
                        retJa.put("school",result.getString("school"));
                        retJa.put("birth",result.getString("birth"));
                        retJa.put("major",result.getString("major"));
                    }else{//psw错误
                        retJa.put("mid",0);
                        retJa.put("etype",3);
                        retJa.put("error","登陆错误：密码错误");
                    }
                }else{//不存在此id
                    retJa.put("mid",0);
                    retJa.put("etype",3);
                    retJa.put("error","登陆错误：账号不存在");
                }
            }else if(type==2){
                sql="SELECT id,psw,name,school,depart FROM Teacher WHERE id="+ja.getBigInteger("id").toString();
                ResultSet result=stmt.executeQuery(sql);
                if(result.next()){//id存在
                    //BigInteger psw=new BigInteger(result.getString("psw"));
                    long psw=result.getLong("psw");
                    if(psw==ja.getLongValue("psw")){//psw正确
                        retJa.put("mid",2);
                        retJa.put("type",2);
                        retJa.put("name",result.getString("name"));
                        retJa.put("id",result.getLong("id"));
                        retJa.put("school",result.getString("school"));
                        retJa.put("depart",result.getString("depart"));
                    }else{//psw错误
                        retJa.put("mid",0);
                        retJa.put("etype",3);
                        retJa.put("error","登陆错误：密码错误");
                    }
                }else{//不存在此id
                    retJa.put("mid",0);
                    retJa.put("etype",3);
                    retJa.put("error","登陆错误：账号不存在");
                }
            }else if(type==3){
                sql="SELECT id,psw,name,school FROM Officer WHERE id="+ja.getBigInteger("id").toString();
                ResultSet result=stmt.executeQuery(sql);
                if(result.next()){//id存在
                    //BigInteger psw=new BigInteger(result.getString("psw"));
                    long psw=result.getLong("psw");
                    if(psw==ja.getLongValue("psw")){//psw正确
                        retJa.put("mid",2);
                        retJa.put("type",3);
                        retJa.put("name",result.getString("name"));
                        retJa.put("id",result.getLong("id"));
                        retJa.put("school",result.getString("school"));
                    }else{//psw错误
                        retJa.put("mid",0);
                        retJa.put("etype",3);
                        retJa.put("error","登陆错误：密码错误");
                    }
                }else{//不存在此id
                    retJa.put("mid",0);
                    retJa.put("etype",3);
                    retJa.put("error","登陆错误：账号不存在");
                }
            }else if(type==4){
                sql="SELECT id,psw FROM Administrator WHERE id="+ja.getBigInteger("id").toString();
                ResultSet result=stmt.executeQuery(sql);
                if(result.next()){//id存在
                    //BigInteger psw=new BigInteger(result.getString("psw"));
                    long psw=result.getLong("psw");
                    if(psw==ja.getLongValue("psw")){//psw正确
                        retJa.put("mid",2);
                        retJa.put("type",4);
                        retJa.put("id",result.getLong("id"));
                        retJa.put("psw",result.getLong("psw"));
                    }else{//psw错误
                        retJa.put("mid",0);
                        retJa.put("etype",3);
                        retJa.put("error","登陆错误：密码错误");
                    }
                }else{//不存在此id
                    retJa.put("mid",0);
                    retJa.put("etype",3);
                    retJa.put("error","登陆错误：账号不存在");
                }
            }else{
                retJa.put("mid",0);
                retJa.put("etype",1);
                retJa.put("error","登陆错误：没有这种类型的身份");
            }
        }

        public void Dealmid3(JSONObject ja,long id) throws ParseException, SQLException {//处理修改信息请求
            System.out.println("进入dealmid3");
            //int type=Id2Type(ja.getLongValue("id"));
            int type=Id2Type(id);
            System.out.println("type:"+type);
            if(type==1){//学生
                //long id=Long.parseLong(ja.getString("id"));
                //long id=ja.getLongValue("id");
                System.out.println("id:"+id);
                long psw=Long.parseLong(ja.getString("psw"));
                System.out.println("psw:"+psw);
                String name=ja.getString("name");
                System.out.println("name:"+name);
                String sex=ja.getString("sex");
                System.out.println("sex:"+sex);
                String school=ja.getString("school");
                System.out.println("school:"+school);
                //DateFormat df=new SimpleDateFormat("yyyy-mm-dd");
                //Date birth=df.parse(df.format(ja.getDate("birth")));
                String birth=ja.getString("birth");
                System.out.println("birth:"+birth);
                String major=ja.getString("major");
                System.out.println("major:"+major);
//            String sql="UPDATE Student SET id='"+id+"',psw='"+psw+"',name='"+name+"',sex='"+sex+
//                    "',school='"+school+"',birth='"+birth.toString()+"',major='"+major+"' WHERE id='"+id+"'";
                String sql="UPDATE Student SET psw=?,name=?,sex=?,school=?,birth=?,major=? WHERE id=?";
                //String sql=String.format("UPDATE IGNORE Student SET psw='%d', name='%s', sex='%s', school='%s', birth='%s', major='%s' WHERE id=%d",psw,name,sex,school,birth,major,id);
                PreparedStatement ps=conn.prepareStatement(sql);
                ps.setLong(1,psw);
                ps.setString(2,name);
                ps.setString(3,sex);
                ps.setString(4,school);
                ps.setString(5, birth);
                ps.setString(6,major);
                ps.setLong(7,id);
                System.out.println("sql:"+sql);
                int result=ps.executeUpdate();

                //以上为修改数据的sql语句

                //int result=ps.executeUpdate();
                //当选择修改，增加，删除数据库文件操作时，用executeUpdate()方法，如果执行正确该方法返回非-1值

                //System.out.println("开始验证是否更新成功");
                //sql="SELECT psw,name,sex,school,birth,major FROM Student WHERE id="+id;
                // ResultSet re=stmt.executeQuery(sql);
                // if(re.next()){
                //     for(int i=1;i<=6;i++){
                //         System.out.print(re.getString(i)+"  ");
                //     }
                //     System.out.flush();
                // }

                retJa.put("mid",3);
                retJa.put("type",1);
                if(result!=-1){//修改成功
                    retJa.put("status",true);
                }else{
                    retJa.put("status",false);
                }

            }else if(type==2){//老师
                //long id=Long.parseLong(ja.getString("id"));
                long psw=Long.parseLong(ja.getString("psw"));
                String name=ja.getString("name");
                String school=ja.getString("school");
                String depart=ja.getString("depart");

//            String sql="UPDATE Student SET id='"+id+"',psw='"+psw+"',name='"+name+"',sex='"+sex+
//                    "',school='"+school+"',birth='"+birth.toString()+"',major='"+major+"' WHERE id='"+id+"'";
                String sql="UPDATE Teacher SET psw=?,name=?,school=?,depart=? WHERE id=?";
                PreparedStatement ps=conn.prepareStatement(sql);
                ps.setLong(1,psw);
                ps.setString(2,name);
                ps.setString(3,school);
                ps.setString(4,depart);
                ps.setLong(5,id);

                int result=ps.executeUpdate();

                //当执行修改，更新，删除数据库文件时，用executeUpdate()方法，如果操作成功，返回非-1值

                retJa.put("mid",3);
                retJa.put("type",2);
                if(result!=-1){
                    retJa.put("status",true);
                }else{
                    retJa.put("status",false);
                }

            }else if(type==3){//老师
                //long id=Long.parseLong(ja.getString("id"));
                long psw=Long.parseLong(ja.getString("psw"));
                String name=ja.getString("name");
                String school=ja.getString("school");

//            String sql="UPDATE Student SET id='"+id+"',psw='"+psw+"',name='"+name+"',sex='"+sex+
//                    "',school='"+school+"',birth='"+birth.toString()+"',major='"+major+"' WHERE id='"+id+"'";
                String sql="UPDATE Officer SET psw=?,name=?,school=? WHERE id=?";
                PreparedStatement ps=conn.prepareStatement(sql);
                ps.setLong(1,psw);
                ps.setString(2,name);
                ps.setString(3,school);
                ps.setLong(4,id);

                int result=ps.executeUpdate();

                //当执行修改，更新，删除数据库文件时，用executeUpdate()方法，如果操作成功，返回非-1值

                retJa.put("mid",3);
                retJa.put("type",3);
                if(result!=-1){
                    retJa.put("status",true);
                }else{
                    retJa.put("status",false);
                }

            }else{
                retJa.put("mid",0);
                retJa.put("etype",1);
                retJa.put("error","修改错误：type错误");
            }
        }

        public void Dealmid4(JSONObject ja,long id,int type) throws SQLException {//处理客户端（学生）查询成绩请求
            int gtype=ja.getInteger("gtype");
            if(gtype==1){//查询单人单科成绩
                if(type!=1){
                    id=Long.parseLong(ja.getString("id"));
                }
                String cid=ja.getString("cid");
                String sql="SELECT\n" +
                        "\t`Student`.`id` AS `id`,\n" +
                        "\t`Student`.`name` AS `name`,\n" +
                        "\t`Course`.`name` AS `course`,\n" +
                        "\t`Grade`.`grade` AS `grade` \n" +
                        "FROM\n" +
                        "\t(\n" +
                        "\t\t( `Student` JOIN `Grade` ON ( ( `Grade`.`stu_id` = `Student`.`id` ) ) )\n" +
                        "\tJOIN `Course` ON ( ( `Grade`.`cour_id` = `Course`.`id` ) ) \n" +
                        "\t)\n"+
                        "WHERE (`Course`.`id`='"+cid+"' AND `Student`.`id`="+id+")";
                ResultSet result=stmt.executeQuery(sql);
                if(result.next()){//查询到的信息非空
                    retJa.put("mid",4);
                    retJa.put("gtype",1);
                    retJa.put("classname",result.getString("course"));
                    retJa.put("grade",result.getString("grade"));
                }else{
                    retJa.put("mid",0);
                    retJa.put("etype",2);
                    retJa.put("error","查询错误：该同学不存在此类课程");
                }
            }else if(gtype==2){//返回单科所有人成绩
                String cid=ja.getString("cid");
                String sql="SELECT\n" +
                        "\t`Student`.`id` AS `id`,\n" +
                        "\t`Student`.`name` AS `name`,\n" +
                        "\t`Course`.`name` AS `course`,\n" +
                        "\t`Grade`.`grade` AS `grade` \n" +
                        "FROM\n" +
                        "\t(\n" +
                        "\t\t( `Student` JOIN `Grade` ON ( ( `Grade`.`stu_id` = `Student`.`id` ) ) )\n" +
                        "\tJOIN `Course` ON ( ( `Grade`.`cour_id` = `Course`.`id` ) ) \n" +
                        "\t)\n"+
                        "WHERE `Course`.`id`='"+cid+"'";
                ResultSet result=stmt.executeQuery(sql);
                if(result.next()){//查询到的信息非空
                    retJa.put("mid",4);
                    retJa.put("gtype",2);
                    retJa.put("classname",result.getString("course"));
                    retJa.put("grade1",result.getString("grade"));
                    retJa.put("name1",result.getString("name"));
                    int cnt=2;
                    while(result.next()){
                        String gradeX="grade"+cnt;
                        String nameX="name"+cnt;
                        retJa.put(gradeX,result.getString("grade"));
                        retJa.put(nameX,result.getString("name"));
                        cnt++;
                    }
                    retJa.put("num",cnt-1);
                }else{
                    retJa.put("mid",0);
                    retJa.put("etype",2);
                    retJa.put("error","查询错误：不存在该课程");
                }
            }else if(gtype==3){//查询单人全部成绩
                long sid;
                if(type!=1){
                    sid=Long.parseLong(ja.getString("sid"));
                }else{
                    sid=id;
                }
                String sql="SELECT\n" +
                        "\t`Student`.`id` AS `id`,\n" +
                        "\t`Student`.`name` AS `name`,\n" +
                        "\t`Course`.`name` AS `course`,\n" +
                        "\t`Grade`.`grade` AS `grade` \n" +
                        "FROM\n" +
                        "\t(\n" +
                        "\t\t( `Student` JOIN `Grade` ON ( ( `Grade`.`stu_id` = `Student`.`id` ) ) )\n" +
                        "\tJOIN `Course` ON ( ( `Grade`.`cour_id` = `Course`.`id` ) ) \n" +
                        "\t)\n"+
                        "WHERE (`Student`.`id`="+sid+")";
                ResultSet result=stmt.executeQuery(sql);
                if(result.next()){//查询到的信息非空
                    retJa.put("mid",4);
                    retJa.put("gtype",3);
                    retJa.put("classname1",result.getString("course"));
                    retJa.put("grade1",result.getString("grade"));
                    int cnt=2;
                    while(result.next()){
                        String gradeX="grade"+cnt;
                        String classnameX="classname"+cnt;
                        retJa.put(gradeX,result.getString("grade"));
                        retJa.put(classnameX,result.getString("course"));
                        cnt++;
                    }
                    retJa.put("num",cnt-1);
                }else{
                    retJa.put("mid",0);
                    retJa.put("etype",2);
                    retJa.put("error","查询错误：该同学不存在");
                }

            }else{
                retJa.put("mid",0);
                retJa.put("etype",2);
                retJa.put("error","查询错误：etype不存在");
            }
        }

        public void Dealmid5(JSONObject ja,long id) throws SQLException {//处理客户端（老师）插入学生成绩请求
            long sid=Long.parseLong(ja.getString("sid"));
            System.out.println("sid:"+sid);
            String cid=ja.getString("cid");
            System.out.println("cid:"+cid);
            long tid=id;
            long oid=0;
            String grade=ja.getString("grade");
            System.out.println("grade"+grade);
            String sql1="SELECT\n" +
                    "\t`Course`.`id` AS `cour_id`,\n" +
                    "\t`Officer`.`id` AS `off_id` \n" +
                    "FROM\n" +
                    "\t( `Course` JOIN `Officer` ON ( ( `Course`.`off_id` = `Officer`.`id` ) ) )\n"+
                    "WHERE `Course`.`id`="+cid;
            ResultSet result1=stmt.executeQuery(sql1);
            if(result1.next()){
                oid=result1.getLong("off_id");
            }else{
                System.out.println("居然找不到该课程！");
            }
            String sql3=String.format("INSERT INTO Grade(grade,stu_id,off_id,cour_id,tea_id) VALUES('%s','%d','%d','%s','%d')",grade,sid,oid,cid,tid);
            int result3=stmt.executeUpdate(sql3);
            retJa.put("mid",5);
            if(result3!=-1){
                System.out.println("成功插入成绩！");
                retJa.put("status",true);
            }else{
                retJa.put("status",false);
            }
        }
        public void Dealmid6(JSONObject ja) throws SQLException {//处理客户端（教务员）打印成绩请求
            String cid=ja.getString("cid");
            String sql="SELECT\n" +
                    "\t`Student`.`id` AS `id`,\n" +
                    "\t`Student`.`name` AS `name`,\n" +
                    "\t`Course`.`name` AS `course`,\n" +
                    "\t`Grade`.`grade` AS `grade` \n" +
                    "FROM\n" +
                    "\t(\n" +
                    "\t\t( `Student` JOIN `Grade` ON ( ( `Grade`.`stu_id` = `Student`.`id` ) ) )\n" +
                    "\tJOIN `Course` ON ( ( `Grade`.`cour_id` = `Course`.`id` ) ) \n" +
                    "\t)\n"+
                    "WHERE `Course`.`id`='"+cid+"'";
            ResultSet result=stmt.executeQuery(sql);
            if(result.next()){//查询到的信息非空
                retJa.put("mid",6);
                retJa.put("classname",result.getString("course"));
                retJa.put("name1",result.getString("name"));
                retJa.put("grade1",result.getString("grade"));
                int cnt=1;
                while(result.next()){
                    cnt++;
                    String n="name"+cnt;
                    String g="grade"+cnt;
                    retJa.put(g,result.getString("grade"));
                    retJa.put(n,result.getString("name"));
                }
                retJa.put("num",cnt);
            }else{
                retJa.put("mid",0);
                retJa.put("etype",2);
                retJa.put("error","查询错误：该成绩单不存在");
            }
        }
        public void Dealmid7(JSONObject ja) throws SQLException {//处理客户端（管理员）查询某个id信息的请求
            int type=Id2Type(Long.parseLong(ja.getString("id2")));
            System.out.println("dealmid7,id:"+ja.getLongValue("id")+",type:"+type);
            String sql;
            if(type==1){
                sql="SELECT id,psw,name,sex,school,birth,major FROM Student WHERE id="+ja.getBigInteger("id2").toString();
                ResultSet result=stmt.executeQuery(sql);
                if(result.next()){//id2存在
                    retJa.put("mid",7);
                    retJa.put("type",4);
                    retJa.put("type2",1);
                    retJa.put("name",result.getString("name"));
                    retJa.put("id",1);
                    retJa.put("id2",result.getLong("id"));
                    retJa.put("sex",result.getString("sex"));
                    retJa.put("school",result.getString("school"));
                    retJa.put("birth",result.getString("birth"));
                    retJa.put("major",result.getString("major"));
                }else{//不存在此id
                    retJa.put("mid",0);
                    retJa.put("etype",2);
                    retJa.put("error","查询错误：账号不存在");
                }
            }else if(type==2){
                sql="SELECT id,psw,name,school,depart FROM Teacher WHERE id="+ja.getBigInteger("id2").toString();
                ResultSet result=stmt.executeQuery(sql);
                if(result.next()){//id2存在
                    retJa.put("mid",7);
                    retJa.put("type",4);
                    retJa.put("type2",2);
                    retJa.put("name",result.getString("name"));
                    retJa.put("id",1);
                    retJa.put("id2",result.getLong("id"));
                    retJa.put("school",result.getString("school"));
                    retJa.put("depart",result.getString("depart"));
                }else{//不存在此id
                    retJa.put("mid",0);
                    retJa.put("etype",2);
                    retJa.put("error","查询错误：账号不存在");
                }
            }else if(type==3){
                sql="SELECT id,psw,name,school FROM Officer WHERE id="+ja.getBigInteger("id2").toString();
                ResultSet result=stmt.executeQuery(sql);
                if(result.next()){//id存在
                    retJa.put("mid",7);
                    retJa.put("type",4);
                    retJa.put("type2",3);
                    retJa.put("name",result.getString("name"));
                    retJa.put("id",1);
                    retJa.put("id2",result.getLong("id"));
                    retJa.put("school",result.getString("school"));
                }else{//不存在此id
                    retJa.put("mid",0);
                    retJa.put("etype",2);
                    retJa.put("error","查询错误：账号不存在");
                }
            }else{
                retJa.put("mid",0);
                retJa.put("etype",1);
                retJa.put("error","查询错误：没有这种类型的身份");
            }
        }

        public void Dealmid8(JSONObject ja) throws SQLException {//处理客户端（管理员）修改某个id的某条数据请求
            long id2=Long.parseLong(ja.getString("id2"));
            System.out.println("id2:"+id2);
            int type=Id2Type(id2);
            System.out.println("type:"+type);
            String key=ja.getString("key");
            String value=ja.getString("value");
            if(type==1){
                String sql;
                if(key.equals("id")||key.equals("psw")){
                    long iValue=Long.parseLong(value);
                    sql=String.format("UPDATE Student SET %s='%d' WHERE id=%d",key,iValue,id2);
                }else{
                    sql=String.format("UPDATE Student SET %s='%s' WHERE id=%d",key,value,id2);
                }
                System.out.println(sql);
                int result=stmt.executeUpdate(sql);
                retJa.put("mid",8);
                if(result!=-1){
                    retJa.put("status",true);
                }else{
                    retJa.put("status",false);
                }
            }else if(type==2){
                String sql;
                if(key.equals("id")||key.equals("psw")){
                    long iValue=Long.parseLong(value);
                    sql=String.format("UPDATE Teacher SET %s='%d' WHERE id=%d",key,iValue,id2);
                }else{
                    sql=String.format("UPDATE Teacher SET %s='%s' WHERE id=%d",key,value,id2);
                }
                System.out.println(sql);
                int result=stmt.executeUpdate(sql);
                retJa.put("mid",8);
                if(result!=-1){
                    retJa.put("status",true);
                }else{
                    retJa.put("status",false);
                }
            }else if(type==3){
                String sql;
                if(key.equals("id")||key.equals("psw")){
                    long iValue=Long.parseLong(value);
                    sql=String.format("UPDATE Officer SET %s='%d' WHERE id=%d",key,iValue,id2);
                }else{
                    sql=String.format("UPDATE Officer SET %s='%s' WHERE id=%d",key,value,id2);
                }
                System.out.println(sql);
                int result=stmt.executeUpdate(sql);
                retJa.put("mid",8);
                if(result!=-1){
                    retJa.put("status",true);
                }else{
                    retJa.put("status",false);
                }
            }else if(type==4){
                String sql;
                if(key.equals("id")||key.equals("psw")){
                    long iValue=Long.parseLong(value);
                    sql=String.format("UPDATE Administrator SET %s='%d' WHERE id=%d",key,iValue,id2);
                }else{
                    sql=String.format("UPDATE Administrator SET %s='%s' WHERE id=%d",key,value,id2);
                }
                System.out.println(sql);
                int result=stmt.executeUpdate(sql);
                retJa.put("mid",8);
                if(result!=-1){
                    retJa.put("status",true);
                }else{
                    retJa.put("status",false);
                }
            }else{
                retJa.put("mid",0);
                retJa.put("etype",1);
                retJa.put("error","修改错误：type错误");
            }
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub

        Server server = new Server();
        server.startServer();

    }


}

