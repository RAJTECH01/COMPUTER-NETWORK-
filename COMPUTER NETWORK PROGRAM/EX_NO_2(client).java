import java.net.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Client {
    public static void main(String[] args) {
        Socket soc = null;
        BufferedImage img = null;

        try {
            // Connect to server
            soc = new Socket("localhost", 4000);
            System.out.println("Client is running...");

            // Read image from file
            System.out.println("Reading image from disk...");
            img = ImageIO.read(new File("digital_image_processing.jpg"));

            // Convert image to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);
            baos.flush();
            byte[] bytes = baos.toByteArray();
            baos.close();

            // Send image to server
            System.out.println("Sending image to server...");
            OutputStream out = soc.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeInt(bytes.length);  // Send image size
            dos.write(bytes, 0, bytes.length);  // Send image data

            System.out.println("Image sent to server.");
            dos.close();
            out.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            try {
                if (soc != null) soc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
