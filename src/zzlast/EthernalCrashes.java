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
import es.*;
import java.io.IOException;
import java.util.Set;
import zzlast.helpers.Proceedable;
import zzlast.helpers.SimpleServer1;

/**
 *
 * @author jvanek
 */
public class EthernalCrashes implements Proceedable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String[] known = new String[]{"date1", "okl", "math", "uniq", "row", "dead", "server"};
        if (args.length == 0) {
            for (String s : known) {
                System.out.print(s + " ");

            }
        } else {
            for (String s : args) {
                if (known[0].equals(s)) {
                    new Thread(new DatePrinter()).start();
                } else if (known[1].equals(s)) {
                    new Thread(new ExceptionThrower()).start();
                } else if (known[2].equals(s)) {
                    SimpleServer1 server = new SimpleServer1();
                    NetworkCalc client = new NetworkCalc(server.getServerSocket());
                    new Thread(server).start();
                    new Thread(client).start();
                } else if (known[3].equals(s)) {
                    new Thread(new StringUniquier()).start();
                } else if (known[4].equals(s)) {
                    new Thread(new DivisionRow()).start();
                } else if (known[5].equals(s)) {
                    new Thread(new RaceGameForResource()).start();
                } else if (known[6].equals(s)) {
                    new Thread(new RunIServer()).start();
                } else {
                    new EthernalCrashes().proceed(null, null);
                }

            }
        }

    }

    @Override
    public void proceed(Set<StringUniquier.MyString> ls, StringUniquier.MyString myString) {
        throw new UnsupportedOperationException("unsupported op");
    }

}
