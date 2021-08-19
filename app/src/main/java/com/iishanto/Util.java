package com.iishanto;

import android.content.Context;
import android.widget.Toast;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class Util {
    private static Util instance=null;
    private Context appContext;
    private boolean serverState=false;
    MainActivity mainActivity=null;
    public void ui(MainActivity m){
        mainActivity=m;
    }
    private Util(){
    }
    public void setContext(Context context){
        appContext=context;
    }
    public void makeToast(String text){
        if(mainActivity==null){
            System.out.println("ii: Failed to make toast.");
            return;
        }
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(appContext,text,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setServerStatus(boolean stat){
        serverState=stat;
    }

    public boolean isServerState() {
        return serverState;
    }

    public String[] getIpAddreses(){
        ArrayList ips =new ArrayList();
        ips.add("127.0.0.1");
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        System.out.println("ii: "+inetAddress.getHostAddress());
                        ips.add(inetAddress.getHostAddress());
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        String []aip=new String[ips.size()];
        for(int i=0;i<ips.size();i++){
            aip[i]= (String) ips.get(i);
        }
        return aip;
    }

    public static Util getInstance(){
        if(instance==null) instance=new Util();
        return instance;
    }
}
