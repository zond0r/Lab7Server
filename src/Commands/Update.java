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
import Utility.CreateTicket;
import Utility.DBClass;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Update implements CommandWithObject {
    public Update() {
    }

    public Object execute(Object o, Socket socket, String user) throws SQLException {
        boolean keyExist = false;
        DBClass dbClass = new DBClass();
        dbClass.ConnectionToDB();
        dbClass.loadAllTickets();
        ServerSender serverSender = new ServerSender();

        try {
            TicketCollection ticketCollection = new TicketCollection();
            Map<Long, Ticket> tickets = ticketCollection.getTickets();
            long keyn = Long.parseLong((String)o);
            if (tickets.size() <= 0) {
                return "Коллекция пуста";
            } else {
                Iterator var11 = tickets.entrySet().iterator();

                while(var11.hasNext()) {
                    Entry<Long, Ticket> entry = (Entry)var11.next();
                    if ((Long)entry.getKey() == keyn && ((Ticket)entry.getValue()).getUser().equals(user)) {
                        keyExist = true;
                        break;
                    }
                }

                if (keyExist) {
                    serverSender.send(socket, "kek", 1);
                    Ticket ticket = this.getNewTicket(socket);
                    ticket.setUser(user);
                    ticket.getEvent().setId(dbClass.getNewID());
                    if (dbClass.ticketExist(Long.parseLong((String)o))) {
                        ticketCollection.replaceMovie(Long.parseLong((String)o), ticket);
                        dbClass.uploadAllTickets();
                        return "Элемент с ключом " + o + " обновлён.";
                    } else {
                        return "Элемент был удалён,команду update выполнить невозможно.";
                    }
                } else {
                    return "Элемент с заданным ключом не существует или не принадлежит вам, попробуйте ввести команду снова.";
                }
            }
        } catch (NullPointerException | NumberFormatException var13) {
            return "Неверный аргумент команды.";
        }
    }

    public String getName() {
        return "update";
    }

    public boolean check(Object arg) {
        return false;
    }

    public Ticket getNewTicket(Socket socket) {
        new CreateTicket();
        ServerReceiver serverReceiver = new ServerReceiver();
        return !CreateTicket.isInScript ? (Ticket)serverReceiver.receive(socket) : null;
    }
}
