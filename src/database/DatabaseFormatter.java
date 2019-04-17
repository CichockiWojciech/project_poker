package database;

import java.util.Formatter;

public class DatabaseFormatter {
    private StringBuilder sb = new StringBuilder();
    private Formatter f = new Formatter(sb);

    public synchronized String format(String str, Object... args) {
        sb.setLength(0);
        f.format(str, args);
        return f.toString();
    }
}
