/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.agranee.task02.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MoreiraP
 */
public class ChatServer implements Runnable {

    private static final int MAX_CLIENT = 10;
    
    public static String LOGGED_USER = "Logged User:";

    private List<ChatServerThread> clients;

    private ServerSocket serverSocket;

    private Thread thread;

    public ChatServer(int port) throws IOException {

        System.out.println("Binding to port " + port + ", please wait  ...");
        serverSocket = new ServerSocket(port);
        System.out.println("Server started: " + serverSocket);

        clients = new ArrayList<>();
        start();
    }

    public static void main(String[] args) throws IOException {

        int port = 8080;

        if (args.length >= 1) {

            port = Integer.parseInt(args[0]);

        }

        ChatServer chatServer = new ChatServer(port);

    }

    @Override
    public void run() {

        while (thread != null) {
            try {
                System.out.println("Waiting for a client ...");

                Socket socket = serverSocket.accept();

                addClient(socket);

            } catch (IOException ioe) {
                System.out.println("Server accept error: " + ioe);
                stop();
            }
        }

    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop() {
        if (thread != null) {
            thread.stop();
            thread = null;
        }
    }

    private void addClient(Socket socket) {

        if (clients.size() < MAX_CLIENT) {
            System.out.println("Client accepted: " + socket);

            ChatServerThread client = new ChatServerThread(this, socket);

            try {
                client.open();
                client.start();
                clients.add(client);
            } catch (IOException ioe) {
                System.out.println("Error opening thread: " + ioe);
            }
        } else {
            System.out.println("Client refused: maximum " + MAX_CLIENT + " reached.");
        }
    }

    private ChatServerThread findClientById(int id) {

        for (ChatServerThread chatServerThread : clients) {
            if (chatServerThread.getID() == id) {
                return chatServerThread;
            }
        }
        return null;
    }

    public synchronized void handle(String input) {

            for (ChatServerThread chatServerThread : clients) {
            chatServerThread.send(input);
        }

    }

    public synchronized void exit(int id, String input) {

        for (ChatServerThread chatServerThread : clients) {
            chatServerThread.send(input);
        }

        ChatServerThread chatServerThread = findClientById(id);

        if (chatServerThread != null) {
            clients.remove(chatServerThread);
        }

    }

    public synchronized void remove(int id) {

        ChatServerThread toRemove = findClientById(id);
        if (toRemove != null) {

            System.out.println("Removing client thread " + id + " at " + toRemove);

            clients.remove(toRemove);

            try {
                toRemove.close();
            } catch (IOException ioe) {
                System.out.println("Error closing thread: " + ioe);
            }
            toRemove.stop();
        }
    }
    
    public void printLoggedUser(){
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(LOGGED_USER);
        boolean firtUser = true;
        
        for (ChatServerThread chatServerThread : clients) {
            
            if(firtUser) {
                sb.append(String.format("%s", chatServerThread.getLoggedUserName()));
                firtUser = false;
            }else{
                sb.append(String.format(";%s", chatServerThread.getLoggedUserName()));
            }
            
        }
    
        
        for (ChatServerThread chatServerThread : clients) {
            chatServerThread.send(sb.toString());
        }
    }

}
