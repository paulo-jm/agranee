/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.agranee.task02.chat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author MoreiraP
 */
public class ChatServerThread extends Thread {

    public static final String USER_NAME = "User Name:";

    public static final String MESSAGE = "Message:";

    public static final String DISCONNECT = "Disconnect:";

    public static final String CONNECT = "Connect:";

    private ChatServer server;
    private Socket socket;
    private int id;
    private String loggedUserName;
    private DataInputStream in;
    private DataOutputStream out;

    public ChatServerThread(ChatServer server, Socket socket) {
        this.id = socket.getPort();
        this.socket = socket;
        this.server = server;
    }

    public void send(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException ioe) {
            System.out.println(id + " ERROR sending: " + ioe.getMessage());
            server.remove(id);
            stop();
        }
    }

    public int getID() {
        return id;
    }

    public void run() {
        System.out.println("Server Thread " + id + " running.");
        while (true) {

            try {

                String input = in.readUTF();

                if (input.startsWith(USER_NAME)) {
                    this.loggedUserName = input.replace(USER_NAME, "").trim();
                    server.printLoggedUser();
                } else if (input.startsWith(MESSAGE)) {
                    String msg = formatedMessage(input.replace(MESSAGE, "").trim());
                    server.handle(msg);
                } else if (input.startsWith(DISCONNECT)) {
                    String msg = formatedMessage("Exited.");
                    server.exit(id, msg);
                    server.printLoggedUser();
                } else if (input.startsWith(CONNECT)) {
                    String msg = formatedMessage("Entered.");
                    server.handle(msg);
                }else if (input.startsWith(CONNECT)) {
                    String msg = formatedMessage("Entered.");
                    server.handle(msg);
                }
                

            } catch (IOException ioe) {
                System.out.println(id + " ERROR reading: " + ioe.getMessage());
                server.remove(id);
                stop();
            }
        }
    }

    public void open() throws IOException {
        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
    }

    private String formatedMessage(String msg) {

        Calendar date = Calendar.getInstance();

        SimpleDateFormat dateParse = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        return String.format("[%s] %s : %s", dateParse.format(date.getTime()), loggedUserName, msg);

    }

    public String getLoggedUserName() {
        return loggedUserName;
    }

}
