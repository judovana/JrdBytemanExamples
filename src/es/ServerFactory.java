package es;

public class ServerFactory {

   public static IServer createServer() {
     return new ServerImplNw();
   }

}
