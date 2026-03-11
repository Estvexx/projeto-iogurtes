package com.iogurtes.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String URL      = "jdbc:postgresql://localhost:5432/iogurtes_db";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "578999";

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
            props.setProperty("user", USER);
            props.setProperty("password", PASSWORD);
            props.setProperty("ssl", "false");

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