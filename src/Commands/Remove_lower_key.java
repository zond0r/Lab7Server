//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Commands;

import Controller.Commandable;
import Ticket.Ticket;
import Ticket.TicketCollection;
import Utility.DBClass;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;

public class Remove_lower_key implements Commandable {
    public Remove_lower_key() {
    }

    public Object execute(Object o, Socket socket, String user) throws IOException, SQLException {
        try {
            DBClass dbClass = new DBClass();
            dbClass.ConnectionToDB();
            dbClass.loadAllTickets();
            TicketCollection ticketCollection = new TicketCollection();
            long size = (long)ticketCollection.getSize();
            if (size == 0L) {
                return "Коллекция пустая.";
            } else {
                long givenId = Long.parseLong((String)o);
                HashMap<Long, Ticket> result = new HashMap();
                ticketCollection.getTickets().entrySet().stream().filter((t) -> {
                    return (Long)t.getKey() > givenId;
                }).filter((x) -> {
                    return ((Ticket)x.getValue()).getUser().equals(user);
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
        return "remove_lower_key";
    }
}
