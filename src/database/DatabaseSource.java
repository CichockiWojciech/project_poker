package database;

import java.sql.*;

import static database.Account.*;

public class DatabaseSource {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String hostname = "localhost";
    private static final String dbName = "POKER";
    private static final String url = "jdbc:mysql://" + hostname + ":3306";
    private static final String user = "root";
    private static final String password = "";

    private static DatabaseFormatter tool = new DatabaseFormatter();

    static {
        if(!loadDriver())
            throw new RuntimeException("cannot load driver");
    }

    private static boolean loadDriver() {
        try {
            Class.forName(driver);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static Connection connect(String url) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }



    public static Connection getConnection() throws SQLException {
        Connection con = connect();
        Statement sta = con.createStatement();

        ResultSet result = sta.executeQuery(tool.format(
                "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '%s'", dbName));
        if(result.next()) {
            sta.close();
            con.close();
            con = connect(url + "/" + dbName);
            sta = con.createStatement();
            useDatabase(sta);
        }else {
            createDatabase(sta);
            sta.close();
            con.close();
            con = connect(url + "/" + dbName);
            sta = con.createStatement();
            useDatabase(sta);
            createSchema(sta);
            createConstraints(sta);
        }
        sta.close();
        return con;
    }

    public static void createDatabase(Statement sta) throws SQLException {
        sta.executeUpdate(tool.format("CREATE DATABASE IF NOT EXISTS %s;", dbName));
    }

    public static void useDatabase(Statement sta) throws SQLException {
        sta.executeUpdate(tool.format("USE %s", dbName));
    }


    public static void dropDatabase(Statement sta) throws SQLException {
        sta.executeUpdate(tool.format("DROP DATABASE IF EXISTS POKER"));
    }

    public static void createConstraints(Statement sta) throws SQLException {

        sta.executeUpdate(tool.format("ALTER TABLE ACCOUNT MODIFY username VARCHAR(%d) NOT NULL;", USERNAME_LENGTH));
        sta.executeUpdate(tool.format("ALTER TABLE ACCOUNT MODIFY login VARCHAR(%d) NOT NULL;", LOGIN_LENGTH));
        sta.executeUpdate(tool.format("ALTER TABLE ACCOUNT MODIFY password VARCHAR(%d) NOT NULL;", SHA1_LENGTH));
        sta.executeUpdate(tool.format("ALTER TABLE ACCOUNT MODIFY tokens INT(%d) NOT NULL;", TOKENS_LENGTH));
        sta.executeUpdate(tool.format("ALTER TABLE ACCOUNT ADD CONSTRAINT ACCOUNT_UNIQUE UNIQUE (username, login);"));

        sta.executeUpdate(tool.format("ALTER TABLE GAME ADD FOREIGN KEY (player_login) REFERENCES ACCOUNT(login);"));
    }


    public static void createSchema(Statement sta) throws SQLException {

        sta.executeUpdate(tool.format("CREATE TABLE IF NOT EXISTS ACCOUNT(\n" +
                "   login VARCHAR(%d) PRIMARY KEY,\n" +
                "   username VARCHAR(%d),\n" +
                "   password CHAR(%d),\n" +
                "   tokens INT(%d)\n" +
                "    );", LOGIN_LENGTH, USERNAME_LENGTH, SHA1_LENGTH, TOKENS_LENGTH));

        sta.executeUpdate(tool.format("CREATE TABLE IF NOT EXISTS GAME(\n" +
                "   id INT(32) AUTO_INCREMENT PRIMARY KEY," +
                "   player_login VARCHAR(%d),\n" +
                "   game_result ENUM('lose', 'win'),\n" +
                "   tokens INT(%d)\n" +
                "    );", LOGIN_LENGTH, TOKENS_LENGTH));
    }


}
