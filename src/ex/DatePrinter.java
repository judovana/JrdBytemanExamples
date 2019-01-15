
package ex;

import java.util.Date;


public class DatePrinter implements Runnable {

    @Override
    public void run() {
        System.out.println("This is ptinting current time and date evey seond");
        while (true) {
            try {
                Thread.sleep(1000);
                printDate();
            } catch (Exception ex) {
                System.err.println("Consumed exception, hahaha");
            }
        }
    }

    private void printDate() {
        Date d1 = new Date();
        Date d2 = null;
        printDate(d2);
    }

    private void printDate(Date d) {
        System.out.println(d.toString());
    }

}
