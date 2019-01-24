package ex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class RaceGameForResource implements Runnable {

    private static final AtomicInteger al = new AtomicInteger(0);

    @Override
    public void run() {
        try {
            runImpl();
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void runImpl() throws InterruptedException, IOException {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(2, 4, 6, 8, 10));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 9));

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    moveListItem(list1, list2, al.addAndGet(1) % 11);
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    moveListItem(list2, list1, al.addAndGet(1) % 11);
                }
            }
        });
        System.out.println("press enter to start game");
        System.in.read();
        thread1.start();
        Thread.sleep(20);
        System.out.println("Race is up!");
        thread2.start();
    }

    private static void moveListItem(List<Integer> from, List<Integer> to, Integer ShiftRacer) {
        synchronized (from) {
            synchronized (to) {
                if (from.remove(ShiftRacer)) {
                    to.add(ShiftRacer);
                }
                from.forEach(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer d) {
                        System.out.print(d + "; ");
                    }
                });
                System.out.println(from.size() + "/" + al.toString());
                to.forEach(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer d) {
                        System.out.print(d + "; ");
                    }
                });
                System.out.println(to.size() + "/" + al.toString());
            }
        }
    }
}
