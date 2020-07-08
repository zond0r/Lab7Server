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
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Show implements CommandWithoutArg {
    private TicketCollection ticketCollection = new TicketCollection();

    public Show() {
    }

    public Object execute(Object o, Socket socket, String user) throws SQLException {
        try {
            DBClass dbClass = new DBClass();
            dbClass.ConnectionToDB();
            dbClass.loadAllTickets();
            if (this.ticketCollection.getSize() != 0) {
                ArrayList<String> answer = new ArrayList();
                this.ticketCollection.getTickets().entrySet().stream().forEach((t) -> {
                    answer.add("\n" + ((Ticket)t.getValue()).getInfo() + "\n");
                });
                return FromArrayToString.convert(answer);
            } else {
                return "Коллекция пустая";
            }
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public String getName() {
        return "show";
    }
}
