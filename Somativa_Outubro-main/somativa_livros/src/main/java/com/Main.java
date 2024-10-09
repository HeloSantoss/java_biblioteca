package com;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import com.example.database.DatabaseConnection;
import com.example.ui.LoginPage;   // Import da interface gráfica para gerenciamento de livros

public class Main {

    public static void main(String[] args) {
        // Conectando ao banco de dados
        try {
            Connection connection = DatabaseConnection.connect(); // Conecta ao banco
            System.out.println("Conexão com o banco de dados estabelecida!");

            // Criando a interface gráfica - Iniciando com a tela de Login
            SwingUtilities.invokeLater(() -> {
                LoginPage loginPage = new LoginPage(connection); // Passa a conexão para o login
                loginPage.setVisible(true); // Torna a janela de login visível
            });

        } catch (SQLException e) {
            System.err.println("Falha ao conectar ao banco de dados: " + e.getMessage()); // Trata exceção de conexão
        }
    }
}
