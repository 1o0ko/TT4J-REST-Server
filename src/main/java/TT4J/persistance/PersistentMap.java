package TT4J.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by asobk_000 on 2015-07-10.
 */
public class PersistentMap {
    private static final String DB_NAME = "pers.db";

    public static void init() {
        Connection connection;
        Statement stmt ;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db",DB_NAME));
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = connection.createStatement();
            String sql = "CREATE TABLE PERSISTANCE " +
                    "(GUID TEXT PRIMARY KEY     NOT NULL," +
                    " VALUE          TEXT    NOT NULL)";

            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            connection.close();
            System.out.println("Database created successfully");
        } catch ( Exception e ) {
            System.out.println("Database already exists");
        }

    }

    public static void put(String guid, String value) throws Exception {
        Connection connection;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db",DB_NAME));
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = connection.createStatement();
            String sql = String.format("INSERT INTO PERSISTANCE (GUID, VALUE) VALUES ('%s','%s')",guid,value);

            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            connection.close();
            System.out.println("Record added successfully");
        } catch ( Exception e ) {
            System.out.println("Error adding record");
            throw new Exception("Record already exists");
        }

    }

    public static String get(String guid) {
        Connection connection;
        Statement stmt;
        String result = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db",DB_NAME));
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = connection.createStatement();
            String sql = String.format("SELECT VALUE FROM PERSISTANCE WHERE GUID = '%s'", guid);
            ResultSet rs = stmt.executeQuery(sql);
            while ( rs.next() ) {
                result = rs.getString("VALUE");
            }
            stmt.close();
            connection.commit();
            connection.close();
            System.out.println("Queried successfully");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.out.println("Error while querying");
        }
        return result;
    }

    public static String getOrDefault(String guid, String defaultValue) {
        String result = get(guid);
        if (result == null) return defaultValue;
        else return result;
    }
}
