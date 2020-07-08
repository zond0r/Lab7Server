//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Controller;

import Ticket.Ticket;
import java.net.Socket;

public interface CommandWithObject extends Commandable {
    boolean check(Object var1);

    Ticket getNewTicket(Socket var1);
}
