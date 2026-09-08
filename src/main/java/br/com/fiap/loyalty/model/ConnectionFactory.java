package br.com.fiap.loyalty.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {


    private static final String URL  = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "rm569898";
    private static final String PASS = "191207";

    private ConnectionFactory() {
        // classe utilitária: não instanciável
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
