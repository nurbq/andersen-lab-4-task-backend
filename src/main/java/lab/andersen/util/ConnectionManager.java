package lab.andersen.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

        private static final String JDBC_URL;
        private static final String JDBC_USER;
        private static final String JDBC_PASSWORD;

        private static final ResourceBundle bundle = ResourceBundle.getBundle("jdbc_credentials");

        static {
            try {
                Class.forName(bundle.getString("jdbc.driver"));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            JDBC_URL = bundle.getString("jdbc.url");
            JDBC_USER = bundle.getString("jdbc.user");
            JDBC_PASSWORD = bundle.getString("jdbc.password");
        }
    }
}
