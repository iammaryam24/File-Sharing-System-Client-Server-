// FileClient.java
import java.net.*; import java.io.*; public class FileClient {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost",7000);
        File f = new File("file_to_send.ext"); // set file path
        try(DataOutputStream dos = new DataOutputStream(s.getOutputStream()); FileInputStream fis = new FileInputStream(f)) {
            dos.writeUTF(f.getName());
            dos.writeLong(f.length());
            byte[] buf = new byte[8192]; int r;
            while((r=fis.read(buf))!=-1) dos.write(buf,0,r);
            dos.flush();
        }
        s.close();
    }
}
