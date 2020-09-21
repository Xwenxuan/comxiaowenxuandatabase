package com.xwx.db.manager;

import com.xwx.db.ClientInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author:肖文轩
 * @date:2020/9/20
 **/
public class ClientManager implements Runnable {
    private List<String> msg;
    private List<Socket> sockets;

    public List<ClientInfo> getClientInfos() {
        return clientInfos;
    }

    public void setClientInfos(List<ClientInfo> clientInfos) {
        this.clientInfos = clientInfos;
    }

    private List<ClientInfo> clientInfos;
    public ClientManager() {
        msg = new ArrayList<String>();
//        sockets = new ArrayList<Socket>();
        clientInfos = new ArrayList<ClientInfo>();
    }
    public synchronized void addClient(ClientInfo clientInfo) {
        clientInfos.add(clientInfo);
    }
    public synchronized void deleteClient(int i) {
        clientInfos.remove(i);
    }
    public synchronized void addMsg(String s) {
        msg.add(s);
    }
    public synchronized void deleteMsg(int i) {
        msg.remove(i);
    }
    public void addSocket(Socket s) {
        sockets.add(s);
    }
    public void deleteSocket(int s) {
        sockets.remove(s);
    }

    public List<String> getMsg() {
        return msg;
    }

    public void setMsg(List<String> msg) {
        this.msg = msg;
    }

    public List<Socket> getSockets() {
        return sockets;
    }

    public void setSockets(List<Socket> sockets) {
        this.sockets = sockets;
    }
    //消息广播
    public void msgBroadcast() {
        for(int i = 0;i < clientInfos.size();i++) {
            for(int j = 0;j < msg.size();j++) {
                try {
                    if(clientInfos.get(i) != null && !clientInfos.get(i).getSocket().isClosed()) {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientInfos.get(i).getSocket().getOutputStream()));
                        writer.newLine();
                        writer.write(msg.get(j));
                        writer.newLine();
                        writer.flush();

                    }else {
                        deleteClient(i);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        msg.clear();

    }
    public void run() {
        try {
            while(true) {
                Thread.sleep(200);
                msgBroadcast();
//                System.out.println("总共有"+sockets.size()+"个连接");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
