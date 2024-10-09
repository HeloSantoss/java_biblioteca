package com.example.ui;

import javax.swing.*;  // Importa classes da biblioteca Swing para a interface gráfica
import com.example.model.Livro;  // Importa a classe Livro do pacote model
import com.example.model.Autor;  // Importa a classe Autor do pacote model
import java.awt.event.ActionEvent;  // Importa a classe para manipulação de eventos de ação
import java.awt.event.ActionListener;  // Importa a interface ActionListener para ouvir eventos

public class BibliotecaUI {

    // Declaração dos componentes da interface gráfica
    private JFrame frame;  // A janela principal da aplicação
    private JTextField tituloField, generoField, autorField;  // Campos de texto para entrada de dados
    private JButton adicionarLivroButton;  // Botão para adicionar um livro

    // Construtor da classe BibliotecaUI
    public BibliotecaUI() {
        // Inicializa o frame da aplicação
        frame = new JFrame("Biblioteca");  // Título da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Comportamento ao fechar a janela
        frame.setSize(400, 300);  // Tamanho da janela

        // Inicializa os campos de texto para entrada de dados
        tituloField = new JTextField(20);  // Campo para título do livro
        generoField = new JTextField(20);  // Campo para gênero do livro
        autorField = new JTextField(20);  // Campo para nome do autor

        // Inicializa o botão para adicionar livro
        adicionarLivroButton = new JButton("Adicionar Livro");
        // Adiciona um ActionListener ao botão
        adicionarLivroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtém os textos dos campos de entrada
                String titulo = tituloField.getText();  // Título do livro
                String genero = generoField.getText();  // Gênero do livro
                String autorNome = autorField.getText();  // Nome do autor

                // Cria um objeto Autor com nome e nacionalidade "Desconhecida"
                Autor autor = new Autor(autorNome, "Desconhecida");  // Simples para exemplo
                // Cria um objeto Livro com os dados obtidos
                Livro livro = new Livro(0, titulo, genero, autor);
                // Exibe uma mensagem informando que o livro foi adicionado
                JOptionPane.showMessageDialog(frame, "Livro adicionado: " + livro.toString());
            }
        });

        // Define o layout da janela como FlowLayout
        frame.setLayout(new java.awt.FlowLayout());
        // Adiciona componentes à janela
        frame.add(new JLabel("Título:"));  // Rótulo para o campo título
        frame.add(tituloField);  // Campo de texto para título
        frame.add(new JLabel("Gênero:"));  // Rótulo para o campo gênero
        frame.add(generoField);  // Campo de texto para gênero
        frame.add(new JLabel("Autor:"));  // Rótulo para o campo autor
        frame.add(autorField);  // Campo de texto para autor
        frame.add(adicionarLivroButton);  // Botão para adicionar livro

        // Torna a janela visível
        frame.setVisible(true);
    }

    // Método principal da aplicação
    public static void main(String[] args) {
        new BibliotecaUI();  // Cria uma nova instância da interface da biblioteca
    }
}
