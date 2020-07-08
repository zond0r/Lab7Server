//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.sql.SQLException;

public interface Commandable extends Serializable {
    Object execute(Object var1, Socket var2, String var3) throws FileNotFoundException, IOException, SQLException;

    String getName();
}
