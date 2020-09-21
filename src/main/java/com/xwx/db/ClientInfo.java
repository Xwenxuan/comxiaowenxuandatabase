package com.xwx.db;

import java.net.Socket;

/**
 * @description:
 * @author:肖文轩
 * @date:2020/9/21
 **/
public class ClientInfo {
    private String username;
//    private String passwork;
    private Socket socket;

    public ClientInfo(String username,Socket socket) {
        this.username = username;
        this.socket = socket;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getPasswork() {
//        return passwork;
//    }
//
//    public void setPasswork(String passwork) {
//        this.passwork = passwork;
//    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
