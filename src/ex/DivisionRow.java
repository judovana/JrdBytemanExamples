package ex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Consumer;

public class DivisionRow implements Runnable {

    private static int N; // tune this for load size
    private static final Random r = new Random();

    private static byte[] getLoad(int v) {
        //we need a lot of ram here; Each entry will eat a 5 kilos now
        byte[] b = new byte[5 * 1024];
        return b;
    }

    @Override
    public void run() {
        //if emptied, all alloced here will be dispossed, so the maps will get freed too.
        List<Result> randomDivisibles = new ArrayList<>();
        System.out.println("Incrementally growing list of magical numbers: ");
        while (true) {
            N = r.nextInt(100) * 10;
            try {
                int loopN = N >> 2;
                for (int i = 11; i <= loopN; i++) {
                    addObsucreNumbers(i, randomDivisibles);

                }
                System.out.println("random keys divisible by [11-" + loopN + "]");
                System.out.print(randomDivisibles.size() + ": ");
                randomDivisibles.forEach(new Consumer<Result>() {
                    @Override
                    public void accept(Result t) {
                        System.out.print(t.getKey() + "; ");
                    }
                });
                System.out.println();
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            } catch (Error e) {
                e.printStackTrace();
                randomDivisibles = new ArrayList<>();
            }
        }
    }

    private void addObsucreNumbers(int i, List<Result> randomDivisibles) throws InterruptedException {
        List<Result> results = obscureQueryNumbersDivisibleBy(i);
        randomDivisibles.add(results.get(r.nextInt(results.size())));
    }

    private static List<Result> obscureQueryNumbersDivisibleBy(int divisibleBy) throws InterruptedException {
        List<Result> queriedEntries = new ArrayList<>();
        for (Map.Entry<Integer, byte[]> e : query().entrySet()) {
            if (e.getKey() % divisibleBy == 0) {
                queriedEntries.add(new Result(e));
            }
        }
        return queriedEntries;
    }

    private static Map<Integer, byte[]> query() {
        final Map<Integer, byte[]> queryResult = new TreeMap<>();
        for (int i = 0; i < N; i++) {
            int v = r.nextInt();
            queryResult.put(v, getLoad(v));
        }
        return queryResult;
    }

    private final static class Result {

        Map.Entry<Integer, byte[]> entry;

        Result(Map.Entry<Integer, byte[]> entry) {
            this.entry = entry;
        }

        Integer getKey() {
            return entry.getKey();
        }

        byte[] getValue() {
            return entry.getValue();
        }
    }
}
