//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Ticket;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class TicketCollection implements Serializable {
    private static Map<Long, Ticket> tickets = Collections.synchronizedMap(new HashMap());
    private static ZonedDateTime DateOFCreation;

    public TicketCollection() {
    }

    public static void setDateOFCreation(ZonedDateTime dateOFCreation) {
        DateOFCreation = dateOFCreation;
    }

    public Map<Long, Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(HashMap<Long, Ticket> collection) {
        tickets = collection;
    }

    public void putTicket(Long key, Ticket ticket) {
        ticket.setId(key);
        tickets.put(key, ticket);
    }

    public void replaceMovie(Long key, Ticket ticket) {
        tickets.replace(key, ticket);
    }

    public void removeTicket(Long key) {
        tickets.remove(key);
    }

    public Set<Long> getKeySet() {
        return tickets.keySet();
    }

    public void getValue(Long key) {
        tickets.get(key);
    }

    public Integer getSize() {
        return tickets.size();
    }

    public void putAllInCollection(TreeMap<Long, Ticket> collection) {
        tickets.putAll(collection);
    }

    public String getInfo() {
        return "Тип коллекции: HashMap\nКоличество элементов коллекции: " + tickets.size();
    }

    public Ticket getTicket(Long key) {
        return (Ticket)tickets.get(key);
    }

    public boolean isKeyFree(Long ind) {
        try {
            Iterator var2 = tickets.entrySet().iterator();

            Entry entry;
            do {
                if (!var2.hasNext()) {
                    return true;
                }

                entry = (Entry)var2.next();
            } while(entry.getKey() != ind);

            return false;
        } catch (Exception var4) {
            var4.printStackTrace();
            return true;
        }
    }
}
