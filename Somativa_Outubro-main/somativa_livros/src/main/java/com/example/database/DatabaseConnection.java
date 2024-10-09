package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // URL de conexão com o banco de dados PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    // Nome de usuário para acessar o banco de dados
    private static final String USER = "postgres";
    // Senha para o usuário do banco de dados
    private static final String PASSWORD = "postgres";

    /**
     * Método estático para estabelecer a conexão com o banco de dados.
     *
     * @return uma instância de Connection, que representa a conexão com o banco
     * de dados.
     * @throws SQLException se houver um erro ao tentar conectar ao banco de
     * dados.
     */
    public static Connection connect() throws SQLException {
        // Retorna uma nova conexão com o banco de dados usando as credenciais fornecidas
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
