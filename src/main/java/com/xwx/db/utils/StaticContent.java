package com.xwx.db.utils;

import com.xwx.db.ClientInfo;

import java.io.*;
import java.net.Socket;

/**
 * @description:
 * @author:肖文轩
 * @date:2020/9/21
 **/
public class StaticContent {
    public static void printBase(BufferedWriter writer) {
        try {
            writer.write("0:聊天模式");
            writer.newLine();
            writer.write("1:表达式计算模式");
            writer.newLine();
            writer.write("2:SQL模式");
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ClientInfo login(BufferedWriter writer, BufferedReader reader, Socket socket) {
        String username = "";
        String password = "";
        try {
            writer.write("username:");
            writer.flush();
            username = reader.readLine();
            writer.write("password:");
            writer.flush();
            password = reader.readLine();
            BufferedReader fb = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\jworkspace\\comxiaowenxuandatabase\\src\\main\\resources\\login.txt")));
            String cont;
            while((cont = fb.readLine()) != null) {
                if(cont.contains(username)) {
                    cont = cont.replace(username,"").trim();
                    if(cont.equals(password)) {
                        ClientInfo clientInfo = new ClientInfo(username,socket);
                        return clientInfo;
                    }else {
                        break;
//                        return null;
                    }
                }
            }
            return  null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
