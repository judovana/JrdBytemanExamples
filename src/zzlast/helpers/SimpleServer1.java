/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zzlast.helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author jvanek
 */
public class SimpleServer1 implements Runnable {

    private final ServerSocket serverSocket;
    private final Random r = new Random();

    public SimpleServer1() throws IOException {
        serverSocket = new ServerSocket(0);
        while (!serverSocket.isBound()) {
            System.out.println("binding");
        };

    }

    public int getServerSocket() {
        return serverSocket.getLocalPort();
    }

    @Override
    public void run() {

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                BufferedWriter out
                        = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "utf-8"));
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream(), "utf-8"));
                while (true) {
                    String s = in.readLine();
                    if (s == null) {
                        in.close();
                        out.close();
                        break;
                    } else {
                        if (s.trim().equals("dig")) {
                            out.write("" + r.nextInt(4) + "\n");
                        } else if (s.trim().equals("op")) {
                            switch (r.nextInt(4)) {
                                case 0:
                                    out.write("add\n");
                                    break;
                                case 1:
                                    out.write("sub\n");
                                    break;
                                case 2:
                                    out.write("mult\n");
                                    break;
                                case 3:
                                    out.write("div\n");
                                    break;
                            }
                        }
                        out.flush();
                    }
                }
                in.close();
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
