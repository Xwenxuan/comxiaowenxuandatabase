package com.xwx.db.dbserver;

import com.xwx.db.manager.ClientManager;
import com.xwx.db.manager.ServerManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author:肖文轩
 * @date:2020/9/20
 **/
public class TelnetServer {
    private ServerSocket serverSocket;
//    ThreadPoolExecutor executor = new ThreadPoolExecutor();
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    //端口号
    private  final int port = 12306;
    ClientManager clientManager = new ClientManager();
    ServerManager serverManager = new ServerManager(clientManager,this);
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务端已经开启了");
            //用来管理客户的

            Thread threadServer = new Thread(serverManager);
            threadServer.start();
            Thread thread = new Thread(clientManager);
            thread.start();
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                executorService.execute(new ChildProcess(socket,clientManager));
            }

        } catch (IOException e) {
//            e.printStackTrace();




        }finally {
            try {
                serverSocket.close();
                Thread.currentThread().interrupt();
                for (int i =0 ;i < clientManager.getClientInfos().size();i++) {
                    clientManager.getClientInfos().get(i).getSocket().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void shutdown() {
        if(serverSocket!= null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
