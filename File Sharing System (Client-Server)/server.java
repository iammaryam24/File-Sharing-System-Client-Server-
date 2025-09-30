// FileServer.java
import java.net.*; import java.io.*;
public class FileServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(7000);
        System.out.println("File server up");
        while(true){
            Socket s = ss.accept();
            new Thread(()->{
                try(Socket socket = s; DataInputStream dis = new DataInputStream(socket.getInputStream())) {
                    String name = dis.readUTF();
                    long len = dis.readLong();
                    try(FileOutputStream fos = new FileOutputStream("recv_"+name)) {
                        byte[] buf = new byte[8192]; long read=0;
                        while(read < len) {
                            int r = dis.read(buf, 0, (int)Math.min(buf.length, len-read));
                            if(r==-1) break;
                            fos.write(buf,0,r); read+=r;
                        }
                    }
                    System.out.println("Received: "+name);
                }catch(Exception e){ e.printStackTrace(); }
            }).start();
        }
    }
}
