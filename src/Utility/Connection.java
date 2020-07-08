//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Utility;

import App.ServerReceiver;
import App.ServerSender;
import Controller.Commandable;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

public class Connection implements Runnable {
    private Socket clientSocket;
    private String newuser;

    public Connection(Socket client) {
        this.clientSocket = client;
    }

    public void run() {
        ServerSender serverSender;
        try {
            DBClass dbWorking = new DBClass();
            serverSender = new ServerSender();
            ServerReceiver serverReceiver = new ServerReceiver();
            if (dbWorking.ConnectionToDB()) {
                String key = "";
                boolean islogged = false;

                boolean regist;
                for(regist = false; !key.toUpperCase().equals("YES") && !key.toUpperCase().equals("ДА") && !key.toUpperCase().equals("NO") && !key.toUpperCase().equals("НЕТ"); key = (String)((String)serverReceiver.receive(this.clientSocket))) {
                    serverSender.send(this.clientSocket, "Вы уже зарегистрированы?(Да|Yes/Нет|No,регистр не важен)", 2);
                }

                String login;
                if (key.toUpperCase().equals("YES") || key.toUpperCase().equals("ДА")) {
                    key = "";

                    label73:
                    while(true) {
                        while(true) {
                            if (islogged) {
                                break label73;
                            }

                            serverSender.send(this.clientSocket, "Введите логин", 2);
                            String login = (String)((String)serverReceiver.receive(this.clientSocket));
                            serverSender.send(this.clientSocket, "Введите пароль", 2);
                            login = ServerReceiver.PasswordCoder((String)((String)serverReceiver.receive(this.clientSocket)));
                            if (dbWorking.userExist(login, login)) {
                                this.newuser = login;
                                serverSender.send(this.clientSocket, "Вы успешно авторизованы!", 0);
                                islogged = true;
                                regist = false;
                            } else {
                                serverSender.send(this.clientSocket, "Логин или пароль неверный.Перейти к регистрации нового пользователя?(Да|Yes/Нет|No,регистр не важен)", 2);
                                key = (String)((String)serverReceiver.receive(this.clientSocket));
                                if (!key.toUpperCase().equals("YES") && !key.toUpperCase().equals("ДА")) {
                                    Thread.sleep(20L);
                                    key = "";
                                } else {
                                    regist = true;
                                    islogged = true;
                                    key = "";
                                }
                            }
                        }
                    }
                }

                islogged = false;
                if (key.toUpperCase().equals("NO") || key.toUpperCase().equals("НЕТ") || regist) {
                    boolean loginIsBusy = false;

                    while(!islogged) {
                        if (loginIsBusy) {
                            serverSender.send(this.clientSocket, "Имя пользователя занято,попробуйте ещё раз.\nВведите логин", 2);
                        } else {
                            serverSender.send(this.clientSocket, "Для работы потребуется регистрация.\nВведите логин", 2);
                        }

                        login = (String)((String)serverReceiver.receive(this.clientSocket));
                        serverSender.send(this.clientSocket, "Введите пароль", 2);
                        String password = ServerReceiver.PasswordCoder((String)((String)serverReceiver.receive(this.clientSocket)));
                        if (dbWorking.addNewUser(login, password)) {
                            serverSender.send(this.clientSocket, "Вы успешно зарегистрированы,теперь у вас есть доступ к информации о всех билетах и возможность изменять свои/добавлять свои билеты", 0);
                            islogged = true;
                            this.newuser = login;
                            Thread.sleep(20L);
                        } else {
                            loginIsBusy = true;
                        }
                    }
                }

                try {
                    while(true) {
                        System.out.println("Ожидаю команду от клиента с адресом: " + this.clientSocket.getLocalAddress() + this.clientSocket.getPort());
                        Object o = serverReceiver.receive(this.clientSocket);
                        Map<Commandable, String> commandStringMap = (Map)o;
                        System.out.println("Выполняю команду " + ((Commandable)((Entry)commandStringMap.entrySet().iterator().next()).getKey()).getClass().getName() + " от клиента с адресом: " + this.clientSocket.getLocalAddress() + this.clientSocket.getPort());
                        serverSender.send(this.clientSocket, (String)((Commandable)((Entry)commandStringMap.entrySet().iterator().next()).getKey()).execute(((Entry)commandStringMap.entrySet().iterator().next()).getValue(), this.clientSocket, this.newuser), 0);
                    }
                } catch (NullPointerException | IOException var10) {
                    System.out.println("Клиент с адресом:" + this.clientSocket.getLocalAddress() + this.clientSocket.getPort() + " отключился");
                }
            }
        } catch (SQLException var11) {
            serverSender = new ServerSender();
            serverSender.send(this.clientSocket, "", 3);
            System.out.println("Нет подключения к бд,принудительно отключаю клиента:" + this.clientSocket.getLocalAddress() + this.clientSocket.getPort());
        } catch (InterruptedException var12) {
        } catch (NullPointerException var13) {
            System.out.println("Клиент с адресом:" + this.clientSocket.getLocalAddress() + this.clientSocket.getPort() + " отключился");
        }

    }
}
