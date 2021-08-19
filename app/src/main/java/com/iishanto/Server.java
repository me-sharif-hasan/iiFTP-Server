package com.iishanto;

import android.os.Environment;

import com.iishanto.services.FTPServer;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    FTPServer ftpServer;
    int port;
    public Server(String user,String pass,int port,String home) throws Exception{
            ServerSocket serverSocket=new ServerSocket(port);
            serverSocket.close();
            this.port=port;
            ftpServer=new FTPServer();
            ftpServer.setPort(port);
            ftpServer.setUser(user,pass.toCharArray(),home);
    }

    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Util.getInstance().setServerStatus(ftpServer.start());
                if(Util.getInstance().isServerState()){
                    Util.getInstance().makeToast("Server started at port: "+port);
                }else{
                    Util.getInstance().makeToast("Failed to start server!");
                }
            }
        }).start();
    }
    public void stop(){
        ftpServer.stop();
        Util.getInstance().setServerStatus(false);
        Util.getInstance().makeToast("Server disconnected");
    }
}
