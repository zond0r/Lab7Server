//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Commands;

import Controller.CommandWithoutArg;
import Ticket.Ticket;
import Ticket.TicketCollection;
import Utility.DBClass;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;

public class Clear implements CommandWithoutArg {
    TicketCollection ticketCollection = new TicketCollection();

    public Clear() {
    }

    public Object execute(Object o, Socket socket, String user) throws SQLException {
        DBClass dbClass = new DBClass();
        dbClass.ConnectionToDB();
        dbClass.loadAllTickets();
        if (this.ticketCollection.getTickets().size() > 0) {
            HashMap<Long, Ticket> ticketsToDelete = new HashMap();
            this.ticketCollection.getTickets().entrySet().stream().forEach((x) -> {
                if (((Ticket)x.getValue()).getUser().equals(user)) {
                    ticketsToDelete.put(x.getKey(), x.getValue());
                }

            });
            if (ticketsToDelete.size() > 0) {
                ticketsToDelete.entrySet().stream().forEach((x) -> {
                    this.ticketCollection.removeTicket((Long)x.getKey());
                });
                dbClass.uploadAllTickets();
                return "Ваши билеты удалены";
            } else {
                return "Ваших билетов не найдено";
            }
        } else {
            return "Коллекция уже пуста.";
        }
    }

    public String getName() {
        return "clear";
    }
}
