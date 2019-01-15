package ex;

public class ExceptionThrower implements Runnable {

    @Override
    public void run() {
        int num = 10;
        while (num == 10) {
            num--;
            num--;
            try {
                while (num == 8) {
                    num++;
                    while (num == 9) {
                        while (num < 50) {
                            if (num <= 500) {
                                int random = (int) (Math.random() * 3);
                                switch (random) {
                                    case 0:
                                        while (true) {
                                            num = num / num - 1;
                                        }
                                    case 1:
                                        String c = null;
                                        final String lower = c.toLowerCase();
                                        break;
                                    case 2:
                                        final int[] arr = new int[5];
                                        num = 4;
                                        while (true) {
                                            arr[num] = num;
                                            num--;
                                        }
                                }
                            } else {
                                num /= num - 1;
                            }
                        }
                        num = 9;
                    }
                    num = 8;
                }
            } catch (ArithmeticException e) {
                System.out.println("Zero Div Err");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Arr Idx Err");
            } catch (NullPointerException e) {
                System.out.println("Null Ptr Err");
            } finally {
                num = 10;
            }
        }
    }

}
