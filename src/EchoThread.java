import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class EchoThread extends Thread {
    protected Socket socket;

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        InputStream in = null;
        BufferedReader bin = null;
        DataOutputStream out = null;
        BufferedWriter bout = null;
        int offset = 0;
        try {
            in = socket.getInputStream();
            bin = new BufferedReader(new InputStreamReader(in));
            out = new DataOutputStream(socket.getOutputStream());
            bout = new BufferedWriter(new OutputStreamWriter(out));
            
            /*
             * Init Client
             */
            Client user = new Client();
            byte[] initBytes = new byte[5];
            in.read(initBytes, 0, 5);
            
            //Read JS5 param
            user.js5Status = getUnsignedByte(initBytes, offset);
            
            //Read client revision
            user.clientRevision = byteToInt(initBytes, offset);
            
            //Send connected response (0 = connected, 6 = can't connect, 7/9 = full)
            out.write(new byte[] {0});
        	System.out.println(userInetAddress()+" Initial connection approved");

        } catch (IOException e) {
        	System.out.println(userInetAddress()+" Communication error");
            return;
        }
        
        while (true) {
        	try {
				Thread.sleep(600); // Temporary to control cpu usage.
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	/*
        	 * TODO: Client <-> Server synchronization block
        	 */
        }
    }
    
    public byte getUnsignedByte(byte[] bytes, int offset) {
        byte[] tempBytes = bytes;
        try {
			socket.getInputStream().read(tempBytes, offset, offset);
	        return tempBytes[offset];
		} catch (IOException e) {
			e.printStackTrace();
		}
        offset++;
		return 0;

    }
    
    public int byteToInt(byte[] bytes, int offset) {
        int val = 0;
        for (int i = offset; i < offset+4; i++) {
            val=val<<8;
            val=val|(bytes[i] & 0xFF);
        }
        offset+=4;
        return val;
    }
    
    public String userInetAddress() {
    	return "["+socket.getInetAddress().toString().replace("/", "")+"] ";
    }
}