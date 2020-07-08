//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Commands;

import App.ServerReceiver;
import App.ServerSender;
import Controller.CommandWithObject;
import Ticket.Ticket;
import Ticket.TicketCollection;
import Utility.DBClass;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Map;

public class Replace_if_lower implements CommandWithObject {
    public Replace_if_lower() {
    }

    public Object execute(Object object, Socket socket, String user) throws IOException, SQLException {
        try {
            DBClass dbClass = new DBClass();
            dbClass.ConnectionToDB();
            dbClass.loadAllTickets();
            TicketCollection ticketCollection = new TicketCollection();
            ServerSender serverSender = new ServerSender();
            ServerReceiver serverReceiver = new ServerReceiver();
            if (ticketCollection.getSize() == 0) {
                return "Коллекция пустая.";
            } else {
                long arg = Long.parseLong((String)object);
                Map<Long, Ticket> tickets = ticketCollection.getTickets();
                if (tickets.get(arg) != null && ((Ticket)tickets.get(arg)).getUser().equals(user)) {
                    serverSender.send(socket, "key", 1);
                    Ticket exmp = (Ticket)serverReceiver.receive(socket);
                    if (dbClass.ticketExist(arg)) {
                        if (exmp.compareTo(tickets.get(arg)) < 0) {
                            tickets.put(arg, exmp);
                            dbClass.uploadAllTickets();
                            return "Элемент с id " + object + " успешно заменен.";
                        } else {
                            return "Заданный элемент оказался больше.";
                        }
                    } else {
                        return "Билет был удалён";
                    }
                } else {
                    return "Нет элемента с таким id или он не принадлежит вам.";
                }
            }
        } catch (NullPointerException | NumberFormatException var12) {
            return "Ключ указан некорректно,попробуйте ещё раз.";
        }
    }

    public String getName() {
        return "replace_if_lower";
    }

    public boolean check(Object arg) {
        return false;
    }

    public Ticket getNewTicket(Socket socket) {
        return null;
    }
}
