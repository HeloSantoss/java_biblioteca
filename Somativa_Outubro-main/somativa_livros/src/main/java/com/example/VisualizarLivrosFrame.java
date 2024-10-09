package com.example; // Declara o pacote onde a classe está localizada

import javax.swing.*; // Importa as classes Swing para interface gráfica
import javax.swing.table.DefaultTableCellRenderer; // Importa o renderizador de células padrão da tabela
import javax.swing.table.DefaultTableModel; // Importa o modelo de tabela padrão
import java.awt.*; // Importa classes de layout e eventos gráficos
import java.awt.event.ActionEvent; // Importa a classe para eventos de ação
import java.awt.event.ActionListener; // Importa a interface para ouvir eventos de ação
import java.sql.*; // Importa classes para manipulação de banco de dados

public class VisualizarLivrosFrame extends JFrame { // Classe que estende JFrame para criar a interface de visualização de livros

    private Connection connection; // Conexão com o banco de dados
    private JTable tabelaLivros; // Tabela para exibir os livros
    private DefaultTableModel modeloTabela; // Modelo de dados da tabela
    private JButton avancarButton; // Botão para avançar as páginas
    private JButton voltarButton; // Botão para voltar as páginas
    private int paginaAtual = 1; // Número da página atual
    private final int livrosPorPagina = 5; // Número de livros a serem mostrados por página

    // Construtor da classe, recebe a conexão com o banco de dados
    public VisualizarLivrosFrame(Connection connection) {
        this.connection = connection; // Inicializa a conexão

        // Configurações da janela
        setTitle("Visualizar Livros Cadastrados"); // Título da janela
        setSize(600, 400); // Tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Permite fechar a janela sem encerrar a aplicação
        setLayout(new BorderLayout()); // Define o layout da janela

        // Criação da tabela e do modelo de dados, incluindo a coluna da imagem
        modeloTabela = new DefaultTableModel(new String[]{"ID", "Título", "Autor", "Ano", "Gênero", "Usuário", "Criado Em", "Imagem"}, 0);
        tabelaLivros = new JTable(modeloTabela); // Cria a tabela com o modelo definido
        JScrollPane scrollPane = new JScrollPane(tabelaLivros); // Adiciona a tabela a um painel de rolagem
        add(scrollPane, BorderLayout.CENTER); // Adiciona o painel de rolagem ao centro da janela

        // Define um renderizador personalizado para exibir imagens na tabela
        tabelaLivros.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value instanceof ImageIcon) { // Se o valor for uma imagem
                    setIcon((ImageIcon) value); // Define o ícone da célula
                } else {
                    setIcon(null); // Caso contrário, define o ícone como nulo
                }
            }
        });

        // Painel para os botões de navegação
        JPanel painelBotoes = new JPanel(); // Cria um painel para os botões
        voltarButton = new JButton("Voltar"); // Cria o botão "Voltar"
        avancarButton = new JButton("Avançar"); // Cria o botão "Avançar"
        painelBotoes.add(voltarButton); // Adiciona o botão "Voltar" ao painel
        painelBotoes.add(avancarButton); // Adiciona o botão "Avançar" ao painel
        add(painelBotoes, BorderLayout.SOUTH); // Adiciona o painel de botões ao sul da janela

        // Carrega os livros da primeira página ao inicializar
        carregarLivros();

        // Ação do botão de avançar página
        avancarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paginaAtual++; // Aumenta o número da página atual
                carregarLivros(); // Carrega os livros da nova página
            }
        });

        // Ação do botão de voltar página
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paginaAtual > 1) { // Verifica se não está na primeira página
                    paginaAtual--; // Decrementa o número da página atual
                    carregarLivros(); // Carrega os livros da nova página
                }
            }
        });
    }

    // Método para carregar livros do banco de dados com paginação
    private void carregarLivros() {
        // Consulta SQL para buscar os livros com paginação
        String sql = "SELECT l.id, l.titulo, l.autor, l.ano_publicacao, l.genero, u.username, l.criado_em, l.imagem "
                + "FROM livros l JOIN usuarios u ON l.usuario_id = u.id "
                + "ORDER BY l.id LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) { // Prepara a consulta SQL
            stmt.setInt(1, livrosPorPagina); // Define o limite de livros por página
            stmt.setInt(2, (paginaAtual - 1) * livrosPorPagina); // Define o deslocamento (offset)

            ResultSet rs = stmt.executeQuery(); // Executa a consulta e obtém os resultados
            modeloTabela.setRowCount(0); // Limpa a tabela antes de carregar novos dados

            while (rs.next()) { // Itera pelos resultados da consulta
                // Carrega a imagem como um array de bytes
                byte[] imagemBytes = rs.getBytes("imagem"); // Obtém os bytes da imagem

                // Converte os bytes para um ImageIcon
                ImageIcon imagemIcon = null; // Inicializa a variável da imagem
                if (imagemBytes != null) { // Verifica se a imagem não é nula
                    Image imagem = Toolkit.getDefaultToolkit().createImage(imagemBytes); // Cria a imagem a partir dos bytes
                    imagemIcon = new ImageIcon(imagem.getScaledInstance(50, 50, Image.SCALE_SMOOTH)); // Escala a imagem para 50x50
                }

                // Adiciona os dados do livro à tabela
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("ano_publicacao"),
                    rs.getString("genero"),
                    rs.getString("username"),
                    rs.getTimestamp("criado_em"),
                    imagemIcon // Adiciona a imagem à tabela
                };
                modeloTabela.addRow(row); // Adiciona a linha ao modelo da tabela
            }

            // Habilita ou desabilita os botões de navegação com base nos resultados
            avancarButton.setEnabled(rs.last() && rs.getRow() == livrosPorPagina); // Habilitar "Avançar" se houver mais resultados
            voltarButton.setEnabled(paginaAtual > 1); // Habilitar "Voltar" se não estivermos na primeira página

        } catch (SQLException e) { // Captura exceções SQL
            e.printStackTrace(); // Imprime o rastreamento de pilha para depuração
            JOptionPane.showMessageDialog(this, "Erro ao carregar livros: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); // Exibe uma mensagem de erro
        }
    }

    // Método main para iniciar o JFrame de visualização de livros
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Connection connection = null; // Inicializa a conexão como nula
            try {
                // Conexão com o PostgreSQL
                String url = "jdbc:postgresql://localhost:5432/postgres"; // URL do banco de dados
                String user = "postgres"; // Nome de usuário do banco de dados
                String password = "postgres"; // Senha do banco de dados
                connection = DriverManager.getConnection(url, user, password); // Estabelece a conexão

                // Iniciar a interface de visualização de livros
                new VisualizarLivrosFrame(connection).setVisible(true); // Cria e torna visível a janela de visualização

            } catch (SQLException e) { // Captura exceções SQL
                e.printStackTrace(); // Imprime o rastreamento de pilha para depuração
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); // Exibe uma mensagem de erro
            }
        });
    }
}
