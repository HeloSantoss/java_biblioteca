package com.example.ui;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LivroFrame extends JFrame {

    private Connection connection;  // Conexão com o banco de dados
    private JTextField tituloField;
    private JTextField autorField;
    private JTextField anoField;
    private JTextField generoField;
    private JComboBox<String> usuarioComboBox;
    private JLabel imagemLabel;  // Para mostrar a imagem selecionada
    private byte[] imagemBytes;  // Para armazenar os bytes da imagem

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Connection connection = null;
            try {
                // Conexão com o PostgreSQL
                String url = "jdbc:postgresql://localhost:5432/postgres";
                String user = "postgres";
                String password = "postgres";
                connection = DriverManager.getConnection(url, user, password);

                // Iniciar a interface
                new LivroFrame(connection).setVisible(true);

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public LivroFrame(Connection connection) {
        this.connection = connection;  // Recebe a conexão do PostgreSQL

        setTitle("Gerenciamento de Livros");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2));  // Modifiquei para suportar 8 linhas

        // Componentes
        JLabel tituloLabel = new JLabel("Título:");
        tituloField = new JTextField();

        JLabel autorLabel = new JLabel("Autor:");
        autorField = new JTextField();

        JLabel anoLabel = new JLabel("Ano de Publicação:");
        anoField = new JTextField();

        JLabel generoLabel = new JLabel("Gênero:");
        generoField = new JTextField();

        JLabel usuarioLabel = new JLabel("Usuário:");
        usuarioComboBox = new JComboBox<>();
        carregarUsuarios();  // Carregar usuários cadastrados

        JLabel imagemLabelInfo = new JLabel("Imagem:");
        JButton selecionarImagemButton = new JButton("Selecionar Imagem");
        imagemLabel = new JLabel();  // Para exibir a imagem

        selecionarImagemButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    // Exibe a imagem no JLabel
                    Image imagem = ImageIO.read(selectedFile);
                    ImageIcon imageIcon = new ImageIcon(imagem.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                    imagemLabel.setIcon(imageIcon);

                    // Lê a imagem como um array de bytes para armazenar no banco de dados
                    FileInputStream fis = new FileInputStream(selectedFile);
                    imagemBytes = fis.readAllBytes();  // Armazena os bytes da imagem
                    fis.close();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao carregar a imagem: " + ex.getMessage());
                }
            }
        });

        JButton salvarButton = new JButton("Salvar Livro");
        salvarButton.addActionListener(e -> {
            String titulo = tituloField.getText();
            String autor = autorField.getText();
            int ano = Integer.parseInt(anoField.getText());
            String genero = generoField.getText();
            String usuarioSelecionado = (String) usuarioComboBox.getSelectedItem();

            // Chama o método para salvar no banco de dados
            salvarLivro(titulo, autor, ano, genero, usuarioSelecionado);
        });

        // Botão para gerar relatório
        JButton gerarRelatorioButton = new JButton("Gerar Relatório");
        gerarRelatorioButton.addActionListener(e -> gerarRelatorio());

        // Adicionar componentes ao frame
        add(tituloLabel);
        add(tituloField);
        add(autorLabel);
        add(autorField);
        add(anoLabel);
        add(anoField);
        add(generoLabel);
        add(generoField);
        add(usuarioLabel);
        add(usuarioComboBox);
        add(imagemLabelInfo);
        add(selecionarImagemButton);
        add(imagemLabel);
        add(salvarButton);
        add(gerarRelatorioButton);
    }

    // Carregar usuários cadastrados da tabela 'usuarios' no banco de dados
    private void carregarUsuarios() {
        String sql = "SELECT username FROM usuarios";  // Assumindo que a tabela tem a coluna 'username'
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                usuarioComboBox.addItem(rs.getString("username"));  // Adiciona o nome de usuário ao comboBox
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para salvar livro no banco de dados
    private void salvarLivro(String titulo, String autor, int ano, String genero, String usuario) {
        String sql = "INSERT INTO livros (titulo, autor, ano_publicacao, genero, usuario_id, criado_em, imagem) "
                + "VALUES (?, ?, ?, ?, (SELECT id FROM usuarios WHERE username = ?), ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.setString(2, autor);
            stmt.setInt(3, ano);
            stmt.setString(4, genero);
            stmt.setString(5, usuario);  // Usa o nome do usuário para buscar o ID correspondente
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));  // Define o campo criado_em com a data atual
            stmt.setBytes(7, imagemBytes);  // Adiciona os bytes da imagem

            stmt.executeUpdate();  // Executa o comando de inserção
            JOptionPane.showMessageDialog(this, "Livro salvo com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar o livro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para gerar relatório
    private void gerarRelatorio() {
        String sql = "SELECT l.id, l.titulo, l.autor, l.ano_publicacao, l.genero, u.username, l.criado_em "
                + "FROM livros l JOIN usuarios u ON l.usuario_id = u.id";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery(); BufferedWriter writer = new BufferedWriter(new FileWriter("relatorio_livros.csv"))) {

            // Escrever cabeçalho do CSV
            writer.write("ID,Título,Autor,Ano de Publicação,Gênero,Usuário,Criado Em");
            writer.newLine();

            // Escrever dados dos livros no arquivo CSV
            while (rs.next()) {
                writer.write(rs.getInt("id") + ","
                        + rs.getString("titulo") + ","
                        + rs.getString("autor") + ","
                        + rs.getInt("ano_publicacao") + ","
                        + rs.getString("genero") + ","
                        + rs.getString("username") + ","
                        + rs.getTimestamp("criado_em"));
                writer.newLine();
            }

            JOptionPane.showMessageDialog(null, "Relatório gerado com sucesso!");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e.getMessage());
        }
    }
}
