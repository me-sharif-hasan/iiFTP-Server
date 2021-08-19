package com.iishanto;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iishanto.services.FTPServer;

public class MainActivity extends AppCompatActivity {
    Server server;
    EditText port;
    TextView user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.getInstance().setContext(getApplicationContext());
        Util.getInstance().ui(this);

        setContentView(R.layout.activity_main);

        doWork();

    }
    private void doWork(){

        port=findViewById(R.id.port);
        Button startBtn = findViewById(R.id.start);

        EditText showIp=findViewById(R.id.ip_addres);
        showIp.setKeyListener(null);

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if(!Environment.isExternalStorageManager())
                      requestPermission();
                }else{
                    askPermission(Manifest.permission.READ_EXTERNAL_STORAGE,1);
                    askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,2);
                }

                System.out.println("ii: " + ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.MANAGE_EXTERNAL_STORAGE));
                if (Util.getInstance().isServerState()) {
                    server.stop();
                    startBtn.setText("START SERVER");
                    showIp.setText(R.string.info1);
                } else {
                    try {
                        String uname=getUserAndPass()[0];
                        String upass=getUserAndPass()[1];
                        if(upass.equals("")){
                            Util.getInstance().makeToast("You must enter a password");
                            return;
                        }
                        server = new Server(uname, upass, Integer.parseInt(port.getText().toString()), root);
                        server.start();
                        startBtn.setText("STOP SERVER");
                        showIp.setText(getIpText(port.getText().toString())+"\nHome DIR: "+root);
                    } catch (Exception e) {
                        Util.getInstance().makeToast(e.getLocalizedMessage());
                        System.out.println("ii: "+e.getLocalizedMessage());
                    }
                }
            }
        });
        System.out.println("ii: " + Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    private String []getUserAndPass(){
        String []info=new String[2];
        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);

        if(user.getText().toString().equals("")){
            info[0]="iishanto";
            user.setText("iishanto");
        }else{
            info[0]=user.getText().toString();
        }
        info[1]=pass.getText().toString();
        return info;
    }
    private String getIpText(String port){
        String ipString;
        String []ips=Util.getInstance().getIpAddreses();
        ipString="IP Addresses:\n";

        for(String s:ips){
            ipString+=s+"\n";
        }
        ipString+="\nPORT: "+port+"\n\n";

        ipString+="Username: "+getUserAndPass()[0]+"\n";
        ipString+="Password: Your given password"+"\n";

        return ipString;
    }

    private void askPermission(String permission,int code){
        if(ContextCompat.checkSelfPermission(MainActivity.this,permission)==PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,permission)){
                new AlertDialog.Builder(this).setTitle("Permission required")
                        .setMessage("This "+permission+" is required for reading and writing files from storage.")
                        .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{permission},code);
                                }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                    })
                        .create().show();
            }else{
                ActivityCompat.requestPermissions(this,new String[]{permission},code);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 9999:
                System.out.println("ii: "+data.getAction());
        }
    }

    private void requestPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2296);
        }
    }

}