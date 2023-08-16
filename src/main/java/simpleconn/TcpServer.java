package simpleconn;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TcpServer extends Thread {

    static List<String> jokes;

    private Socket socket;

    public TcpServer(Socket conexao) {
        this.socket = conexao;
    }

    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String textIn = dataInputStream.readUTF();
            System.out.println(textIn);

            final int jokeNumber = (int) ((Math.random() * (jokes.size())));
            String textOut = jokes.get(jokeNumber);

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(textOut);
            socket.close();
        } catch(Exception e) { }
    }

    public static void main(String[] args) {
        readJokes();
        try {
            ServerSocket server = new ServerSocket(50000);
            while(!jokes.isEmpty()) {
                Socket conexao = server.accept();
                TcpServer sThread = new TcpServer(conexao);
                System.out.println("Client asking for joke...");
                sThread.start();
            }
        } catch(Exception e) { }
    }

    private static void readJokes() {
        jokes = new ArrayList<>();
        InputStream inputStream = TcpServer.class.getClassLoader().getResourceAsStream("jokes.txt");
//        InputStream inputStream = TcpServer.class.getResourceAsStream("jokes.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                jokes.add(line);
            }
        } catch (IOException e) {}
    }

}
