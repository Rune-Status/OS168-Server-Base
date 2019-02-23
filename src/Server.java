import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static final int PORT = 45954;

    @SuppressWarnings("resource")
	public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();

        }
        System.out.println("[INFO] OS168-Server-Base running on port: "+PORT);
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new EchoThread(socket).start();
            System.out.println("["+socket.getInetAddress().toString().replace("/", "")+"] "+" Connection opened");
        }
    }
}