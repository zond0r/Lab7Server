//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Commands;

import Controller.CommandWithoutArg;
import Ticket.Ticket;
import Ticket.TicketCollection;
import Ticket.TicketType;
import Utility.DBClass;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Print_field_descending_type implements CommandWithoutArg {
    public Print_field_descending_type() {
    }

    public Object execute(Object o, Socket socket, String user) throws FileNotFoundException, IOException, SQLException {
        DBClass dbClass = new DBClass();
        dbClass.ConnectionToDB();
        dbClass.loadAllTickets();
        TicketCollection ticketCollection = new TicketCollection();
        Map<Long, Ticket> tickets = ticketCollection.getTickets();
        ArrayList<TicketType> types = new ArrayList();
        if (tickets.size() <= 0) {
            return "Коллекция пуста.";
        } else {
            Iterator var8 = tickets.entrySet().iterator();

            while(var8.hasNext()) {
                Entry<Long, Ticket> entry = (Entry)var8.next();
                Ticket ticket = (Ticket)entry.getValue();
                types.add(ticket.getType());
            }

            Collections.sort(types);
            String message = "Поля type всех элементов в порядке убывания:";

            for(int i = types.size() - 1; i >= 0; --i) {
                message = message + "Тип: " + types.get(i) + "\n ";
            }

            return message;
        }
    }

    public String getName() {
        return "print_field_descending_type";
    }
}
