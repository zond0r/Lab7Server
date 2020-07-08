//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Commands;

import Controller.CommandWithoutArg;
import Ticket.Ticket;
import Ticket.TicketCollection;
import Utility.DBClass;
import Utility.FromArrayToString;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Print_ascending implements CommandWithoutArg {
    public Print_ascending() {
    }

    public Object execute(Object o, Socket socket, String user) throws FileNotFoundException, IOException, SQLException {
        DBClass dbClass = new DBClass();
        dbClass.ConnectionToDB();
        dbClass.loadAllTickets();
        TicketCollection ticketCollection = new TicketCollection();
        ArrayList<String> answer = new ArrayList();
        if (ticketCollection.getSize() != 0) {
            ticketCollection.getTickets().entrySet().stream().sorted((e1, e2) -> {
                return ((Ticket)e2.getValue()).compareTo(e1.getValue());
            }).forEach((t) -> {
                answer.add(((Ticket)t.getValue()).getInfo());
            });
            return FromArrayToString.convert(answer);
        } else {
            return "Коллекция пуста.";
        }
    }

    public String getName() {
        return "print_ascending";
    }
}
