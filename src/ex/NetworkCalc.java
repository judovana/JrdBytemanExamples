
package ex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author jvanek
 */
public class NetworkCalc implements Runnable {

    final int port;

    public NetworkCalc(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                Socket clientSocket = new Socket("localhost", port);
                BufferedWriter out
                        = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "utf-8"));
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream(), "utf-8"));
                out.write("op\n");
                out.flush();
                String op = in.readLine();
                out.write("dig\n");
                out.flush();
                String d1 = in.readLine();
                out.write("dig\n");
                out.flush();
                String d2 = in.readLine();
                out.close();
                in.close();
                //System.out.println(op + " " + d1 + " " + d2);
                Method m = this.getClass().getDeclaredMethod(op, int.class, int.class);
                Object result = m.invoke(this, Integer.valueOf(d1), Integer.valueOf(d2));
                Integer r = (Integer) result;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public int add(int a, int b) {
        int r = a + b;
        System.out.println(a + " + " + b + " = " + r);
        return r;
    }

    public int sub(int a, int b) {
        int r = a + b;
        System.out.println(a + " - " + b + " = " + r);
        return r;
    }

    public int mul(int a, int b) {
        int r = a * b;
        System.out.println(a + " * " + b + " = " + r);
        return r;
    }

    public Integer div(int a, int b) {
        int r = a / b;
        System.out.println(a + " / " + b + " = " + r);
        return r;
    }

}
