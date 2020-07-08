//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Ticket;

import java.io.Serializable;
import java.sql.Timestamp;

public class Ticket implements Comparable, Serializable {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private Timestamp creationDate;
    private Double price;
    private TicketType type;
    private Event event;
    private String user;

    public Ticket() {
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TicketType getType() {
        return this.type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getCsvTicket() {
        return this.id + "," + this.name + "," + this.coordinates.getX() + "," + this.coordinates.getY() + "," + this.creationDate + "," + this.price + "," + this.type + "," + this.event.getName() + "," + this.event.getEventType() + "," + this.event.getDate();
    }

    public String getInfo() {
        return "Билет (id:" + this.id + "):\n\tВладелец: " + this.user + "\n\tИмя: " + this.name + ",\n\tКоординаты:\n\t\tx: " + this.coordinates.getX() + ",\n\t\ty: " + this.coordinates.getY() + ",\n\tДата создания: " + this.creationDate + ",\n\tЦена: " + this.price + ",\n\tТип билета: " + this.type + ",\n\tМероприятие (id:" + this.event.getId() + "):\n\t\tИмя: " + this.event.getName() + ",\n\t\tДата проведения " + this.event.getDate() + ",\n\t\tТип: " + this.event.getEventType() + ".";
    }

    public int compareTo(Object o) {
        Ticket ticket = (Ticket)o;
        return this.getInfo().length() - ticket.getInfo().length();
    }
}
