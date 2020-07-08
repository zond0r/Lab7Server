//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package App;

import Ticket.TicketCollection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerReceiver {
    public static ServerSocket server;
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public ServerReceiver() {
    }

    public Object receive(Socket client) {
        ServerReceiver.Receiver receiver = new ServerReceiver.Receiver(client);
        Future future = this.executorService.submit(receiver);

        try {
            return future.get();
        } catch (InterruptedException var5) {
            var5.printStackTrace();
        } catch (ExecutionException var6) {
            var6.printStackTrace();
        }

        return null;
    }

    public static void checkForExitCommand() throws IOException {
        Thread backgroundReaderThread = new Thread(new Runnable() {
            public void run() {
                System.out.println("Запускаю проверку на выход по команде exit в консоли.");

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    Throwable var2 = null;

                    try {
                        while(!Thread.interrupted()) {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }

                            if (line.equalsIgnoreCase("exit")) {
                                System.out.println("Отключаюсь");
                                System.exit(0);
                            }
                        }
                    } catch (Throwable var12) {
                        var2 = var12;
                        throw var12;
                    } finally {
                        if (bufferedReader != null) {
                            if (var2 != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Throwable var11) {
                                    var2.addSuppressed(var11);
                                }
                            } else {
                                bufferedReader.close();
                            }
                        }

                    }

                } catch (IOException var14) {
                    throw new RuntimeException(var14);
                }
            }
        });
        backgroundReaderThread.setDaemon(true);
        backgroundReaderThread.start();
    }

    public static void create() throws IOException {
        new TicketCollection();
        System.out.println("Коллекция готова к заполнению.");
        checkForExitCommand();

        try {
            System.out.println("Запускаю сервер");
            server = new ServerSocket(5018);
            System.out.println("Сервер успешно запущен");
        } catch (BindException var2) {
            System.out.println("Данный порт уже занят,возможно сервер уже запущен!\n Принудительно завершаю работу.");
            System.exit(0);
        } catch (IOException var3) {
            System.out.println("ОШИБКА");
        }

    }

    public static String PasswordCoder(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext;
            for(hashtext = no.toString(16); hashtext.length() < 32; hashtext = "0" + hashtext) {
            }

            return hashtext;
        } catch (NoSuchAlgorithmException var5) {
            return null;
        }
    }

    public class Receiver implements Callable {
        Socket client = null;

        public Receiver(Socket socket) {
            this.client = socket;
        }

        public Object call() throws Exception {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(this.client.getInputStream());
                return objectInputStream.readObject();
            } catch (SocketException var2) {
                return null;
            } catch (IOException | ClassNotFoundException var3) {
                return null;
            }
        }
    }
}
