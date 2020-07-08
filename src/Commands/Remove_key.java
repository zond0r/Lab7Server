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
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Remove_key implements Commandable {
    public Remove_key() {
    }

    public Object execute(Object o, Socket socket, String user) throws IOException, SQLException {
        try {
            DBClass dbClass = new DBClass();
            dbClass.ConnectionToDB();
            dbClass.loadAllTickets();
            TicketCollection ticketCollection = new TicketCollection();
            Map<Long, Ticket> tickets = ticketCollection.getTickets();
            Long key = Long.parseLong((String)o);
            boolean keyExist = false;
            if (tickets.size() > 0) {
                Iterator var9 = tickets.entrySet().iterator();

                while(var9.hasNext()) {
                    Entry<Long, Ticket> entry = (Entry)var9.next();
                    if (entry.getKey() == key && ((Ticket)entry.getValue()).getUser().equals(user)) {
                        keyExist = true;
                    }
                }

                if (keyExist) {
                    ticketCollection.removeTicket(key);
                    dbClass.uploadAllTickets();
                    return "Элемент с ключом " + key + " удалён из коллекции.";
                } else {
                    return "Элемент с заданным ключом не существует или не принадлежит вам";
                }
            } else {
                return "Коллекция пуста";
            }
        } catch (NullPointerException | NumberFormatException var11) {
            return "Ключ указан некорректно,попробуйте ещё раз.";
        }
    }

    public String getName() {
        return "remove_key";
    }
}
