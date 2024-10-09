package com.example.ui;  // Pacote da classe

import javax.swing.*;  // Importa classes da biblioteca Swing para interface gráfica
import java.awt.*;  // Importa classes para manipulação de layouts
import java.awt.event.ActionEvent;  // Importa classe para eventos de ação
import java.awt.event.ActionListener;  // Importa interface para ouvir eventos de ação
import java.sql.Connection;  // Importa a classe para conexão com o banco de dados
import java.sql.PreparedStatement;  // Importa a classe para execução de comandos SQL
import java.sql.SQLException;  // Importa a classe para tratamento de exceções SQL
import org.mindrot.jbcrypt.BCrypt; // Importa a biblioteca BCrypt para hash de senhas

public class CadastroPage extends JFrame {

    private Connection connection;  // Variável para armazenar a conexão com o banco de dados
    private JTextField usernameField;  // Campo de texto para nome de usuário
    private JPasswordField passwordField;  // Campo de senha para entrada da senha

    // Construtor da classe CadastroPage
    public CadastroPage(Connection connection) {
        this.connection = connection;  // Recebe a conexão com o banco de dados

        // Configuração da janela
        setTitle("Cadastro");  // Título da janela
        setSize(300, 200);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Comportamento ao fechar a janela
        setLayout(new GridLayout(3, 2));  // Layout em grade com 3 linhas e 2 colunas

        // Criação dos componentes da interface
        JLabel usernameLabel = new JLabel("Usuário:");  // Rótulo para campo de usuário
        usernameField = new JTextField();  // Campo de texto para entrada do nome de usuário
        JLabel passwordLabel = new JLabel("Senha:");  // Rótulo para campo de senha
        passwordField = new JPasswordField();  // Campo para entrada da senha
        JButton registerButton = new JButton("Cadastrar");  // Botão para cadastrar usuário

        // Adicionando componentes à janela
        add(usernameLabel);  // Adiciona rótulo de usuário
        add(usernameField);  // Adiciona campo de texto de usuário
        add(passwordLabel);  // Adiciona rótulo de senha
        add(passwordField);  // Adiciona campo de senha
        add(registerButton);  // Adiciona botão de cadastro

        // Ação do botão de cadastro
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtém os valores dos campos de entrada
                String username = usernameField.getText();  // Nome de usuário
                String password = new String(passwordField.getPassword());  // Senha

                // Verifica se os campos estão vazios
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(CadastroPage.this, "Os campos não podem estar vazios!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;  // Sai do método se os campos estiverem vazios
                }

                // Tenta cadastrar o usuário no banco de dados
                try {
                    cadastrarUsuario(username, password);  // Chama o método para cadastrar usuário
                    JOptionPane.showMessageDialog(CadastroPage.this, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Fecha a tela de cadastro
                    new LoginPage(connection).setVisible(true); // Abre a tela de login
                } catch (SQLException ex) {
                    // Exibe mensagem de erro caso ocorra uma exceção SQL
                    JOptionPane.showMessageDialog(CadastroPage.this, "Erro ao cadastrar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Método para cadastrar usuário no banco de dados
    private void cadastrarUsuario(String username, String password) throws SQLException {
        // Comando SQL para inserir um novo usuário na tabela 'usuarios'
        String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)"; // Certifique-se de que a coluna seja 'password'

        // Faz o hash da senha antes de armazená-la
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());  // Gera o hash da senha

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);  // Define o nome de usuário
            statement.setString(2, hashedPassword); // Define a senha hash
            statement.executeUpdate();  // Executa a atualização no banco de dados
        }
    }
}
