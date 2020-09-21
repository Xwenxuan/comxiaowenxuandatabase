package com.xwx.db.dbserver;

import com.xwx.db.ClientInfo;
import com.xwx.db.manager.ClientManager;
import com.xwx.db.utils.StaticContent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @description:
 * @author:肖文轩
 * @date:2020/9/20
 **/
public class ChildProcess implements Runnable {
    public Socket socket;
    public ClientManager clientManager;
    public ChildProcess(Socket s, ClientManager c) {
        socket = s;
        clientManager = c;
    }
    public void run() {

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer.write("welcomto hnu\n");
            writer.newLine();
            writer.flush();
//            clientManager.addSocket(socket);
            ClientInfo clientInfo = null;

            while((clientInfo = StaticContent.login(writer,reader,socket)) == null);
            clientManager.addClient(clientInfo);
            while(true) {

                if(writer != null && reader != null) {
//                    if((clientInfo = login(writer,reader,socket)) == null) continue;
//                    else {
                        StaticContent.printBase(writer);
                        String msg = "";

                        msg = reader.readLine();
                        if(msg.equals("0")) {
                            //聊天模式
                            writer.write("welcome："+clientInfo.getUsername());
                            writer.newLine();
                            writer.flush();
                            while(true) {
                                msg = "";

                                msg = reader.readLine();
                                if(msg.equals("return")) break;
                                //把消息加入到消息集合中
                                clientManager.addMsg(clientInfo.getUsername()+":"+msg);
                            }


//                            writer.newLine();
                        }else if(msg.equals("1")) {


                        }else if(msg.equals("2")) {
                            //sql模式
                            boolean flag = false;
                            while(!flag) {
                                msg = "";
                                while(true) {

                                    String msgt = reader.readLine();
                                    if(msgt.equals("return")){
                                        flag = true;
                                        break;
                                    }
                                    if (msgt.contains(";")){
                                        msgt = msgt.substring(0,msgt.indexOf(";"));
                                        msg += msgt;

                                        break;
                                    }else {
                                        msg += msgt;
                                    }
                                }
                                clientManager.addMsg(msg);
                            }


                        }else {
                            continue;
                        }
//                    }





                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
        }

    }

//    void printBase(BufferedWriter writer) {
//        try {
//            writer.write("0:聊天模式");
//            writer.newLine();
//            writer.write("1:表达式计算模式");
//            writer.newLine();
//            writer.write("2:SQL模式");
//            writer.newLine();
//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//    ClientInfo login(BufferedWriter writer, BufferedReader reader, Socket socket) {
//        String username = "";
//        String password = "";
//        try {
//            writer.write("username:");
//            writer.flush();
//            username = reader.readLine();
//            writer.write("password:");
//            writer.flush();
//            password = reader.readLine();
//            BufferedReader fb = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\jworkspace\\comxiaowenxuandatabase\\src\\main\\resources\\login.txt")));
//            String cont;
//            while((cont = fb.readLine()) != null) {
//                if(cont.contains(username)) {
//                    cont = cont.replace(username,"").trim();
//                    if(cont.equals(password)) {
//                        ClientInfo clientInfo = new ClientInfo(username,socket);
//                        return clientInfo;
//                    }else {
//                        break;
////                        return null;
//                    }
//                }
//            }
//            return  null;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
