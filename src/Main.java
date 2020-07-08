//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import App.ServerReceiver;
import Utility.Connection;
import java.io.IOException;
import java.net.Socket;

public class Main {
    public Main() {
    }

    public static void main(String[] args) throws IOException {
        ServerReceiver.create();

        while(!ServerReceiver.server.isClosed()) {
            Socket socket = ServerReceiver.server.accept();
            (new Thread(new Connection(socket))).start();
            System.out.println("Новый клиент с андресом " + socket.getLocalAddress() + socket.getPort());
        }

    }
}
