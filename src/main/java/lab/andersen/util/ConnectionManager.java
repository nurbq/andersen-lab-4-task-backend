package lab.andersen.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private ConnectionManager() {
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(Connector.JDBC_URL, Connector.JDBC_USER, Connector.JDBC_PASSWORD);
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return connection;
    }

    private class Connector {

        private static final String JDBC_URL = System.getenv("JDBC_DATABASE_URL");
        private static final String JDBC_USER = System.getenv("JDBC_DATABASE_USERNAME");
        private static final String JDBC_PASSWORD = System.getenv("JDBC_DATABASE_PASSWORD");

        static {
            String jdbc_driver = "org.postgresql.Driver";
            System.out.println(jdbc_driver);
            try {
                Class.forName(jdbc_driver);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
