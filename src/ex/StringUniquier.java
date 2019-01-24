package ex;

import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import zzlast.helpers.Proceedable;

public class StringUniquier implements Runnable, Proceedable{

    public static class MyString {

        private final String value;
        private final String information;

        public MyString(String value, String information) {
            this.value = value;
            this.information = information;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MyString myString = (MyString) o;
            return Objects.equals(information, myString.information);
        }

        @Override
        public int hashCode() {
            return Objects.hash(information);
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private final Set<MyString> setOfString = new HashSet<>();

    @Override
    public void run() {
        Random r = new Random();
        final String[] ss = new String[]{"agent alice", "agent bob"};
        System.out.println("set of our two agents as they are appearing in field, with they current doings:");
        while (true) {
            try {
                Set<MyString> ls = setOfString;
                proceed(ls, new MyString(ss[r.nextInt(2)], String.valueOf(r.nextInt(5))));
                System.out.println(ls);
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void proceed(Set<MyString> ls, MyString myString) {
        ls.add(myString);
    }
}
