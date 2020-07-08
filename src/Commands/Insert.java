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
import java.util.InputMismatchException;

public class Insert implements CommandWithObject {
    TicketCollection ticketCollection = new TicketCollection();
    long id;

    public Insert() {
    }

    public boolean check(Object arg) {
        return this.ticketCollection.isKeyFree((Long)arg);
    }

    public Ticket getNewTicket(Socket socket) {
        new CreateTicket();
        ServerReceiver serverReceiver = new ServerReceiver();
        return !CreateTicket.isInScript ? (Ticket)serverReceiver.receive(socket) : null;
    }

    public Object execute(Object o, Socket socket, String user) throws SQLException {
        DBClass dbClass = new DBClass();
        dbClass.ConnectionToDB();
        dbClass.loadAllTickets();
        ServerSender serverSender = new ServerSender();

        try {
            Long id = Long.parseLong((String)o);
            if (this.ticketCollection.isKeyFree(id)) {
                if (this.check(id)) {
                    serverSender.send(socket, "kek", 1);
                }

                Ticket ticket = this.getNewTicket(socket);
                ticket.setId(id);
                ticket.setUser(user);
                ticket.getEvent().setId(dbClass.getNewID());
                this.ticketCollection.putTicket(id, ticket);
                dbClass.uploadAllTickets();
                return "Билет успешно добавлен в коллекцию.";
            } else {
                return "Указанный ключ занят";
            }
        } catch (InputMismatchException | NumberFormatException var8) {
            return "Аргумент команды должен быть типа \"long\"";
        }
    }

    public String getName() {
        return "insert";
    }
}
