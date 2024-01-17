package es;

public class RunIServer implements  Runnable{

    @Override
    public void run() {
        while (true) {
            try {
              IServer server = ServerFactory.createServer();
              server.recieve("incoming");
              server.send("out-coming");
              Thread.sleep(500);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
