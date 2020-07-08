//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Commands;

import Controller.CommandWithObject;
import Ticket.Ticket;
import Ticket.TicketCollection;
import Utility.DBClass;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;

public class Replace_if_greater implements CommandWithObject {
    public Replace_if_greater() {
    }

    public Object execute(Object o, Socket socket, String user) throws IOException, SQLException {
        DBClass dbClass = new DBClass();
        dbClass.ConnectionToDB();
        dbClass.loadAllTickets();

        try {
            TicketCollection ticketCollection = new TicketCollection();
            long size = (long)ticketCollection.getSize();
            if (size == 0L) {
                return "Коллекция как бы пустая.";
            } else {
                long givenId = Long.parseLong((String)o);
                HashMap<Long, Ticket> result = new HashMap();
                ticketCollection.getTickets().entrySet().stream().filter((t) -> {
                    return (Long)t.getKey() < givenId;
                }).filter((x) -> {
                    return !((Ticket)x.getValue()).getUser().equals(user);
                }).forEach((entry) -> {
                    Ticket var10000 = (Ticket)result.put(entry.getKey(), entry.getValue());
                });
                ticketCollection.setTickets(result);
                dbClass.uploadAllTickets();
                return size == (long)ticketCollection.getSize() ? "Коллекция осталась без изменений" : "Все возможные обьекты были удалены.";
            }
        } catch (NullPointerException | NumberFormatException var11) {
            return "Неверный аргумент команды.";
        }
    }

    public String getName() {
        return "replace_if_greater";
    }

    public boolean check(Object arg) {
        return false;
    }

    public Ticket getNewTicket(Socket socket) {
        return null;
    }
}
