import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Fredrik on 2015-01-26.
 */
public class ServerConnection {

    private Collection<ChatConnection> connectedClients=new ArrayList<ChatConnection>();

    private ServerConnection(){

        try {
            ServerSocket ss=new ServerSocket(portNumber);
            Socket s=null;
            while((s=ss.accept())!=null){
                ChatConnection chatConnection=new ChatConnection(s,this);
                chatConnection.start();
                connectedClients.add(chatConnection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final int portNumber=2635;

    private void listenForConnection(){

    }

    public void sendMessage(String message,String name){
        for(ChatConnection con: connectedClients){
            con.sendMessage(message,name);
        }
    }

    public static void main(String[] args) {
        final ServerConnection serverConnection=new ServerConnection();

    }
}
