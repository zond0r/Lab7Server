//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Commands;

import Controller.Commandable;
import Ticket.Ticket;
import Ticket.TicketCollection;
import Utility.DBClass;
import Utility.FromArrayToString;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Filter_starts_with_name implements Commandable {
    public Filter_starts_with_name() {
    }

    public Object execute(Object o, Socket socket, String user) throws IOException, SQLException {
        DBClass dbClass = new DBClass();
        dbClass.ConnectionToDB();
        dbClass.loadAllTickets();
        TicketCollection ticketCollection = new TicketCollection();
        String subSt = (String)o;
        ArrayList<String> answer = new ArrayList();
        ticketCollection.getTickets().entrySet().stream().filter((t) -> {
            return ((Ticket)t.getValue()).getName().indexOf(subSt) == 0;
        }).forEach((t) -> {
            answer.add(((Ticket)t.getValue()).getInfo() + "\n");
        });
        return answer.size() == 0 ? "Нет таких элементов в коллекции" : FromArrayToString.convert(answer);
    }

    public String getName() {
        return "filter_starts_with_name";
    }
}
