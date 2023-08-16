package simpleconn;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TcpClient extends Thread {

    private static Socket socket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    private static final String ADDRESS = "localhost";
    private static final int PORT = 50000;

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        try {
            socket = new Socket(ADDRESS, PORT);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Tell me a joke...");
            String jokeReceived = dataInputStream.readUTF();
            System.out.println(jokeReceived);
            socket.close();
        } catch(Exception e) { }

    }

}
