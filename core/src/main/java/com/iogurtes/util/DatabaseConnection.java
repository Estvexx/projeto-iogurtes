package com.iogurtes.util;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String user = dotenv.get("DB_USER");
    private static final String password = dotenv.get("DB_PASSWORD");
    private static final String ssl = dotenv.get("DB_SSL");
    private static final String host = dotenv.get("DB_HOST");
    private static final String port = dotenv.get("DB_PORT");
    private static final String db_name = dotenv.get("DB_NAME");
    private static final String URL = String.format(
            "jdbc:postgresql://%s:%s/%s", host, port, db_name
    );

    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        if (connection != null) return connection;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado: " + e.getMessage());
            System.exit(-1);
        }

        try {
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            props.setProperty("ssl", ssl);

            connection = DriverManager.getConnection(URL, props);
            System.out.println("✅ Ligação à BD estabelecida!");

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            System.exit(-2);
        }

        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔒 Ligação à BD fechada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar ligação: " + e.getMessage());
        }
    }
}