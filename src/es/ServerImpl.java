package es;

public class ServerImpl implements IServer {

  public void send(String s) {
      System.out.println("Message sending: " + new java.util.Date() + " " + s);
  }
  public void recieve(String s) {
      System.out.println("Message received: " + new java.util.Date() + " " + s);
  }
}
