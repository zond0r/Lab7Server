//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Utility;

import Ticket.Coordinates;
import Ticket.Event;
import Ticket.EventType;
import Ticket.Ticket;
import Ticket.TicketCollection;
import Ticket.TicketType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DBClass {
    private static final String url = "jdbc:postgresql://pg:5432/studs";
    private static final String user = "s286560";
    private static final String password = "esi357";
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement preparedStatement;
    private static ResultSet rs;

    public DBClass() {
    }

    public Boolean ConnectionToDB() throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://pg:5432/studs", "s286560", "esi357");
            return true;
        } catch (SQLException var2) {
            var2.printStackTrace();
            throw var2;
        }
    }

    public Boolean userExist(String user, String password) {
        try {
            preparedStatement = connection.prepareStatement("select *  from userdb d where exists( select * from userdb where d.login = ? and d.password= ?)");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
            return rs.next() ? true : false;
        } catch (SQLException var4) {
            return false;
        }
    }

    public Boolean ticketExist(Long id) {
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select *  from ticketdb d where exists( select * from userdb where d.id =" + id + ")");
            return rs.next() ? true : false;
        } catch (SQLException var3) {
            return false;
        }
    }

    public Boolean addNewUser(String user, String password) {
        try {
            preparedStatement = connection.prepareStatement("insert into userdb values (?,?)");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
            return true;
        } catch (SQLException var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public void uploadAllTickets() {
        TicketCollection ticketCollection = new TicketCollection();

        try {
            stmt = connection.createStatement();
            stmt.execute("TRUNCATE ticketdb");
            Map<Long, Ticket> ticketTreeMap = ticketCollection.getTickets();
            ticketTreeMap.entrySet().stream().forEach((x) -> {
                try {
                    preparedStatement = connection.prepareStatement("INSERT into ticketdb values(?,?,?,?,?,?,?,?,?,?,?,?)");
                    preparedStatement.setLong(1, (long)Math.toIntExact(((Ticket)x.getValue()).getId()));
                    preparedStatement.setString(2, ((Ticket)x.getValue()).getName());
                    preparedStatement.setInt(3, ((Ticket)x.getValue()).getCoordinates().getX());
                    preparedStatement.setInt(4, ((Ticket)x.getValue()).getCoordinates().getY());
                    preparedStatement.setTimestamp(5, ((Ticket)x.getValue()).getCreationDate());
                    preparedStatement.setDouble(6, ((Ticket)x.getValue()).getPrice());
                    preparedStatement.setString(7, ((Ticket)x.getValue()).getType().toString());
                    preparedStatement.setLong(8, ((Ticket)x.getValue()).getEvent().getId());
                    preparedStatement.setString(9, ((Ticket)x.getValue()).getEvent().getName());
                    preparedStatement.setTimestamp(10, ((Ticket)x.getValue()).getEvent().getDate());
                    preparedStatement.setString(11, ((Ticket)x.getValue()).getEvent().getEventType().toString());
                    preparedStatement.setString(12, ((Ticket)x.getValue()).getUser());
                    preparedStatement.execute();
                } catch (SQLException var2) {
                    var2.printStackTrace();
                }

            });
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    public void loadAllTickets() {
        try {
            TicketCollection ticketCollection = new TicketCollection();
            ticketCollection.getTickets().clear();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from ticketdb");

            while(rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getLong(1));
                ticket.setName(rs.getString(2));
                Coordinates coordinates = new Coordinates();
                coordinates.setX(rs.getInt(3));
                coordinates.setY(rs.getInt(4));
                ticket.setCoordinates(coordinates);
                ticket.setCreationDate(rs.getTimestamp(5));
                ticket.setPrice(rs.getDouble(6));
                ticket.setType(TicketType.valueOf(rs.getString(7)));
                Event event = new Event();
                event.setId(rs.getLong(8));
                event.setName(rs.getString(9));
                event.setDate(rs.getTimestamp(10));
                event.setEventType(EventType.valueOf(rs.getString(11)));
                ticket.setEvent(event);
                ticket.setUser(rs.getString(12));
                ticketCollection.putTicket(ticket.getId(), ticket);
            }
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

    }

    public Long getNewID() {
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT nextval('eventid')");
            return rs.next() ? rs.getLong(1) : null;
        } catch (SQLException var2) {
            var2.printStackTrace();
            return null;
        }
    }
}
