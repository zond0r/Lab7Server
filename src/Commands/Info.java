//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Commands;

import Controller.CommandWithoutArg;
import Ticket.TicketCollection;
import Utility.DBClass;
import java.net.Socket;
import java.sql.SQLException;

public class Info implements CommandWithoutArg {
    public Info() {
    }

    public Object execute(Object o, Socket socket, String user) throws SQLException {
        DBClass dbClass = new DBClass();
        dbClass.ConnectionToDB();
        dbClass.loadAllTickets();
        TicketCollection ticketCollection = new TicketCollection();
        return ticketCollection.getInfo();
    }

    public String getName() {
        return "info";
    }
}
