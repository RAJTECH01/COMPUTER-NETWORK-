import java.net.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(4000);
            System.out.println("Server waiting for image...");

            socket = server.accept();
            System.out.println("Client connected.");

            // Read image size and data
            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);
            int len = dis.readInt();
            System.out.println("Image Size: " + (len / 1024) + " KB");

            byte[] data = new byte[len];
            dis.readFully(data);

            // Convert byte array to image
            InputStream ian = new ByteArrayInputStream(data);
            BufferedImage bImage = ImageIO.read(ian);

            // Display image in JFrame
            JFrame f = new JFrame("Server - Received Image");
            JLabel l = new JLabel(new ImageIcon(bImage));
            f.add(l);
            f.pack();
            f.setVisible(true);

            dis.close();
            in.close();
            socket.close();
            server.close();

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
