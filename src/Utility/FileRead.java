//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

public class FileRead {
    private static String filename = "";
    private static final Scanner in;

    public FileRead() {
    }

    public static String getFilename() {
        return filename;
    }

    public static void setFilename(String filename) {
        FileRead.filename = filename;
    }

    public static String readFromFile(String filePath) throws FileNotFoundException {
        String data = "";

        try {
            InputStream stream = new FileInputStream(filePath);
            Reader reader = new InputStreamReader(stream);

            for(int ch = reader.read(); ch != -1; ch = reader.read()) {
                data = data + (char)ch;
            }

            return data;
        } catch (FileNotFoundException var5) {
            return null;
        } catch (IOException var6) {
            var6.printStackTrace();
            return data;
        }
    }

    public static String readFromConsole() {
        System.out.print("$ ");
        return in.nextLine();
    }

    static {
        in = new Scanner(System.in);
    }
}
