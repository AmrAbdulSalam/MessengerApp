/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.assignment2part2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 *
 * @author Msys
 */
public class Threadser implements Runnable{
        private DatagramSocket socket;
    private boolean running;
    public static String packetMessage;
    
    public void createSocket(int port){
        try{
            socket = new DatagramSocket(port);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    public void run() {
        System.out.println("inside run function");
        
        running = true;
        
        while(running){
            try{
                byte [] data = new byte[1024];
                DatagramPacket packet = new DatagramPacket(data , data.length);
                socket.receive(packet);
                data = packet.getData();
                String message = new String(data);
                System.out.println("from me"+message);
                Client.statusText.setText("received from " + packet.getAddress() +" : " + packet.getPort());
                Client.messageArea.append(message + '\n');
                Client.statusText.setText("Recevied From IP = " + packet.getAddress() + " , Port = " + packet.getPort());
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public void start (){
        Thread thread = new Thread(this);
        thread.start();
    }
    public void stop (){
        //running = false;
        socket.close();
    }
    
    public void sendTo(InetSocketAddress address , String mesg){
        try{
            byte[] data = mesg.getBytes();
            DatagramPacket packet = new DatagramPacket(data , data.length);
            packet.setSocketAddress(address);
            socket.send(packet);
            System.out.println("packet sent");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
