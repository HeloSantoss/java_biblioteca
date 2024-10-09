package com.example.ui;  // Declara o pacote onde a classe está localizada

import java.awt.GridLayout;  // Importa a classe para layouts em grade
import java.awt.event.ActionEvent;  // Importa a classe para eventos de ação
import java.awt.event.ActionListener;  // Importa a interface para ouvintes de ação
import java.sql.Connection;  // Importa a classe para conexão com o banco de dados
import java.sql.PreparedStatement;  // Importa a classe para execução de comandos SQL
import java.sql.ResultSet;  // Importa a classe para resultados de consultas SQL
import java.sql.SQLException;  // Importa a classe para tratamento de exceções SQL
import javax.swing.JButton;  // Importa a classe para botões
import javax.swing.JFrame;  // Importa a classe para janelas
import javax.swing.JLabel;  // Importa a classe para rótulos
import javax.swing.JOptionPane;  // Importa a classe para diálogos de mensagens
import javax.swing.JPasswordField;  // Importa a classe para campos de senha
import javax.swing.JTextField;  // Importa a classe para campos de texto
import org.mindrot.jbcrypt.BCrypt;  // Importa a biblioteca BCrypt para hashing de senhas

public class LoginPage extends JFrame {

    private Connection connection;  // Conexão com o banco de dados
    private JTextField usernameField;  // Campo de texto para o nome de usuário
    private JPasswordField passwordField;  // Campo de texto para a senha

    // Construtor da classe LoginPage
    public LoginPage(Connection connection) {
        this.connection = connection;  // Armazena a conexão recebida

        // Configuração da janela
        setTitle("Login");  // Define o título da janela
        setSize(300, 200);  // Define o tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Define o comportamento ao fechar a janela
        setLayout(new GridLayout(3, 2));  // Define um layout de 3 linhas e 2 colunas

        // Criação dos componentes da interface
        JLabel usernameLabel = new JLabel("Usuário:");  // Rótulo para o campo de nome de usuário
        usernameField = new JTextField();  // Campo de texto para entrada do nome de usuário
        JLabel passwordLabel = new JLabel("Senha:");  // Rótulo para o campo de senha
        passwordField = new JPasswordField();  // Campo de texto para entrada da senha
        JButton loginButton = new JButton("Login");  // Botão para login
        JButton cadastroButton = new JButton("Cadastro");  // Botão para cadastro

        // Adicionando componentes à janela
        add(usernameLabel);  // Adiciona o rótulo do nome de usuário
        add(usernameField);  // Adiciona o campo de texto do nome de usuário
        add(passwordLabel);  // Adiciona o rótulo da senha
        add(passwordField);  // Adiciona o campo de texto da senha
        add(loginButton);  // Adiciona o botão de login
        add(cadastroButton);  // Adiciona o botão de cadastro

        // Ação do botão de login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  // Método que é chamado ao clicar no botão
                String username = usernameField.getText();  // Obtém o nome de usuário do campo de texto
                String password = new String(passwordField.getPassword());  // Obtém a senha do campo de senha

                // Verificação de autenticação
                boolean usuarioAutenticado = autenticarUsuario(username, password);  // Chama o método de autenticação

                if (usuarioAutenticado) {
                    // Se o usuário for autenticado com sucesso
                    dispose();  // Fecha a tela de login
                    LivroFrame livroFrame = new LivroFrame(connection);  // Cria a tela de livros
                    livroFrame.setVisible(true);  // Exibe a tela de livros
                } else {
                    // Se a autenticação falhar
                    JOptionPane.showMessageDialog(LoginPage.this, "Usuário ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);  // Exibe mensagem de erro
                }
            }
        });

        // Ação do botão de cadastro
        cadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  // Método que é chamado ao clicar no botão
                // Fecha a tela de login e abre a tela de cadastro
                dispose();  // Fecha a tela de login
                CadastroPage cadastroPage = new CadastroPage(connection);  // Cria a tela de cadastro
                cadastroPage.setVisible(true);  // Exibe a tela de cadastro
            }
        });
    }

    // Método para autenticar o usuário no banco de dados
    private boolean autenticarUsuario(String username, String password) {
        String sql = "SELECT password FROM usuarios WHERE username = ?";  // Consulta SQL para obter a senha armazenada
        try (PreparedStatement statement = connection.prepareStatement(sql)) {  // Prepara a consulta
            statement.setString(1, username);  // Define o parâmetro da consulta

            ResultSet resultSet = statement.executeQuery();  // Executa a consulta
            if (resultSet.next()) {  // Verifica se o usuário foi encontrado
                String storedPassword = resultSet.getString("password");  // Obtém a senha armazenada

                // Verifique se a senha corresponde ao hash armazenado
                if (BCrypt.checkpw(password, storedPassword)) {  // Verifica se a senha fornecida corresponde à senha armazenada
                    return true;  // Senha correta
                } else {
                    // Senha incorreta
                    return false;  // Retorna false se a senha não corresponder
                }
            } else {
                // Usuário não encontrado
                return false;  // Retorna false se o usuário não for encontrado
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Exibe a pilha de erros se ocorrer uma exceção SQL
            return false;  // Retorna false em caso de erro
        }
    }
}
