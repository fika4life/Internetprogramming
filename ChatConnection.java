import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatConnection extends Thread {

    private BufferedReader in;
    private PrintWriter out;
    private ServerConnection parent;
    private String name;

    public ChatConnection(Socket socket, ServerConnection parent) {
        this.parent=parent;
        try {
            in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out= new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMessage(String message,String name){
        out.println(name+": "+message);
        out.flush();
    }

    @Override
    public void run() {
        while(true){
            try {
                String message=in.readLine();
                if(message==null){
                    break;
                }
                else if(message.startsWith("!name!: ")){
                    name=message.replaceFirst("!name!: ","");
                }
                else {
                    parent.sendMessage(message,name);
                }

            }
            catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

    }
}