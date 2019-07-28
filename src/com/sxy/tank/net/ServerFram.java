package com.sxy.tank.net;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFram extends Frame {
    public static final ServerFram INSTANCE=new ServerFram();
    private Server server=new Server();
    TextArea taServer=new TextArea();
    TextArea taClient=new TextArea();

    private ServerFram(){
        this.setTitle("tank server");
        this.setSize(800, 600);
        this.setLocation(300, 30);
        Panel p=new Panel(new GridLayout(1,2));
        p.add(taServer);
        p.add(taClient);

        taServer.setFont(new Font("YouYuan", Font.PLAIN, 25));
        taClient.setFont(new Font("YouYuan", Font.PLAIN, 25));

        this.add(p);
        this.updateServerMsg("Server:");
        this.updateClientMsg("Client:");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    public void updateServerMsg(String str){
        this.taServer.setText(taServer.getText()+str+System.getProperty("line.separator"));
    }

    public void updateClientMsg(String str){
        this.taClient.setText(taClient.getText()+str+System.getProperty("line.separator"));
    }
    public static void main(String[] args) {
        ServerFram.INSTANCE.setVisible(true);
        ServerFram.INSTANCE.server.serverStart();
    }

}

