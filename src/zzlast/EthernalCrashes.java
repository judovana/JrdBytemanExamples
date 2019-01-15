/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zzlast;

import ex.DatePrinter;
import ex.DivisionRow;
import ex.ExceptionThrower;
import ex.NetworkCalc;
import ex.RaceGameForResource;
import ex.StringUniquier;
import java.io.IOException;
import zzlast.helpers.SimpleServer1;

/**
 *
 * @author jvanek
 */
public class EthernalCrashes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String[] known = new String[]{"date1", "okl", "math", "uniq", "row", "dead"};
        if (args.length == 0) {
            for (String s : known) {
                System.out.print(s + " ");

            }
        } else {
            for (String s : args) {
                if (known[0].equals(s)) {
                    new Thread(new DatePrinter()).start();
                }
                if (known[1].equals(s)) {
                    new Thread(new ExceptionThrower()).start();
                }
                if (known[2].equals(s)) {
                    SimpleServer1 server = new SimpleServer1();
                    NetworkCalc client = new NetworkCalc(server.getServerSocket());
                    new Thread(server).start();
                    new Thread(client).start();

                }
                if (known[3].equals(s)) {
                    new Thread(new StringUniquier()).start();
                }
                if (known[4].equals(s)) {
                    new Thread(new DivisionRow()).start();
                }
                if (known[5].equals(s)) {
                    new Thread(new RaceGameForResource()).start();
                }

            }
        }

    }

}
