package com.xwx.db.manager;

import com.xwx.db.dbserver.TelnetServer;

import java.util.Scanner;

/**
 * @description:
 * @author:肖文轩
 * @date:2020/9/21
 **/
public class ServerManager implements Runnable{
    ClientManager clientManager;
    TelnetServer telnetServer;
    public ServerManager(ClientManager clientManager,TelnetServer telnetServer) {
        this.clientManager = clientManager;
        this.telnetServer = telnetServer;
    }
    public void shutdown() {
        telnetServer.shutdown();
        clientManager.addMsg("服务端已经关闭");

    }
    public void showClientNum() {
        System.out.println("现在在线人数:"+clientManager.getClientInfos().size());
    }
    public void showAllClientInfo() {

    }
    public void run() {


        while(true) {
            Scanner scanner = new Scanner(System.in);
            if(scanner.hasNext()) {
                String msg = scanner.nextLine();
                if(msg.equals("shutdown")) {
                    shutdown();
                }else if(msg.equals("showclientnum")) {
                    showClientNum();
                }else if(msg.equals("showallclientinfo")) {
                    showAllClientInfo();
                }else {
                    clientManager.addMsg("服务端发送："+msg);
                }
            }
        }
    }
}
