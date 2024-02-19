package es;

public class ServerImplNw implements IServer {

  public void send(String s) {
      System.out.println("Story sending: " + new java.util.Date() + " " + s);
  }
  public void recieve(String s) {
      System.out.println("Story received: " + new java.util.Date() + " " + s);
  }
}
