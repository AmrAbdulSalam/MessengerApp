/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.assignment2part2;

import static com.mycompany.assignment2part2.Client.jList1;
import static com.mycompany.assignment2part2.Client.listModel;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Msys
 */
public class ThreadClient implements Runnable{
    private String serverIp;
    private int port;
    private String localIp ;
    private String localPort;
    private Socket clientSocket;
    private BufferedReader inFromServer;
    private String onlineUsers;
    private String sentence;
    private DataOutputStream outToServer;
    public void init(String serverIp , int port , String localIp , String localPort , Socket clientSocket){
        this.serverIp = serverIp;
        this.port = port;
        this.localPort = localPort;
        this.localIp = localIp;
        this.clientSocket = clientSocket;
        
         try{

            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            sentence = "online,"+localIp + ":" + localPort;
             
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void start (){
        flag = true;
        Thread thread = new Thread(this);
        thread.start();
        
    }
    public void stop(){
       flag = false;
    }
    private boolean flag = false;
    public void run(){
        try{
                while(flag){
                outToServer.writeBytes(sentence+"\n");
                inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                onlineUsers = inFromServer.readLine();
                onlineUsers = onlineUsers.replace("[", "");
                onlineUsers = onlineUsers.replace("]", "");
                String [] online = onlineUsers.split(", ");
                Client.listModel.removeAllElements();
                for(int i = 0 ; i < online.length ;i++){      
                    Client.listModel.addElement(online[i]);
                }
                System.out.println(Client.listModel);
                //}
                }
            
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
}
