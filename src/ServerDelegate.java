import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerDelegate implements Runnable {
   Socket socket;

   public ServerDelegate(Socket s) {
      socket = s;
   }

   public void Connect(String mFile) {
      try {
         System.out.println("Server initialized!");
         DataInputStream dis = new DataInputStream(new BufferedInputStream(
               socket.getInputStream()));
         FileOutputStream fos = new FileOutputStream(mFile);
         BufferedOutputStream bos = new BufferedOutputStream(fos);
         int bytesToRead = (int) dis.readLong();
         byte[] bytearray = new byte[bytesToRead];

         dis.read(bytearray, 0, bytearray.length);
         bos.write(bytearray, 0, bytesToRead);
         bos.flush();
         System.out.println("Calling parseXML");
         parseXML(mFile, "Object");
         sendXML(socket, new File("temp.xml"));
         dis.close();
         socket.close();
      } catch (IOException e) {
         System.out.println("Unable to connect server");
         e.printStackTrace();
      }
   }

   public void parseXML(String mFile, String indivNode) {
      Scanner reader = null;
      System.out.println("Parsing XML");
      try {
         String mString = "";
         reader = new Scanner(new File(mFile));
         while (reader.hasNext())
            mString += ("\n" + reader.nextLine());
         PrintWriter transfer = new PrintWriter("temp.xml");
         DataParser dp = new DataParser(mString, indivNode);
         String parsedResult = dp.parse();
         transfer.print(dp.parse());
         transfer.flush();
         transfer.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
   }

   public void sendXML(Socket socket, File file) {
      System.out.println("Sending XML");
      try {
         BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
               file));
         byte[] mByteArray = new byte[(int) file.length()];
         bis.read(mByteArray, 0, mByteArray.length);
         DataOutputStream os = new DataOutputStream(new BufferedOutputStream(
               socket.getOutputStream()));
         // header for size of file
         os.writeLong(file.length());
         os.write(mByteArray, 0, mByteArray.length);
         os.flush();
      } catch (Exception e) {
         System.out.println("File not able to be read");
      }
   }

   @Override
   public void run() {
      Connect("newCSV.csv");
   }
}
