package org.proyect.server;

import java.io.IOException;
import java.net.*;

public class ServerClient extends Thread{
    private int port;

    /**
     * Genera los hilos en ServerClient. Encargado de mantener en ejecución el servidor.
     */
    @Override
    public void run() {
        try{
            ServerSocket server = new ServerSocket(48120);
            while (true){
                Socket client = server.accept();
                Thread hilo = new ClientGame(client);
                hilo.start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
