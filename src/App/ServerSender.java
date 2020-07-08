//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package App;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class ServerSender {
    ForkJoinPool forkJoinPool = new ForkJoinPool();

    public ServerSender() {
    }

    public void send(Socket client, String message, Integer needAnswer) {
        this.forkJoinPool.execute(new ServerSender.Sender(client, message, needAnswer));
        System.out.println("Отправляю данные : " + client.getLocalAddress() + client.getPort());
    }

    public class Sender implements Runnable {
        private Socket client;
        private String message;
        private Integer needAnswer;

        public Sender(Socket client, String message, Integer needAnswer) {
            this.client = client;
            this.message = message;
            this.needAnswer = needAnswer;
        }

        public void run() {
            Map<String, Integer> answer = new HashMap();
            answer.put(this.message, this.needAnswer);
            HashMap o = answer;

            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.client.getOutputStream());
                objectOutputStream.writeObject(o);
                answer.clear();
            } catch (IOException var4) {
            }

        }
    }
}
