package com.example.model;

public class Autor {

    // Atributo que armazena o nome do autor
    private String nome;
    // Atributo que armazena a nacionalidade do autor
    private String nacionalidade;

    /**
     * Construtor da classe Autor.
     *
     * @param nome O nome do autor.
     * @param nacionalidade A nacionalidade do autor.
     */
    public Autor(String nome, String nacionalidade) {
        this.nome = nome;             // Inicializa o nome do autor
        this.nacionalidade = nacionalidade; // Inicializa a nacionalidade do autor
    }

    // Getters e Setters
    /**
     * Método que retorna o nome do autor.
     *
     * @return O nome do autor.
     */
    public String getNome() {
        return nome; // Retorna o nome do autor
    }

    /**
     * Método que define o nome do autor.
     *
     * @param nome O novo nome do autor.
     */
    public void setNome(String nome) {
        this.nome = nome; // Atualiza o nome do autor
    }

    /**
     * Método que retorna a nacionalidade do autor.
     *
     * @return A nacionalidade do autor.
     */
    public String getNacionalidade() {
        return nacionalidade; // Retorna a nacionalidade do autor
    }

    /**
     * Método que define a nacionalidade do autor.
     *
     * @param nacionalidade A nova nacionalidade do autor.
     */
    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade; // Atualiza a nacionalidade do autor
    }

    /**
     * Método que retorna uma representação em String do autor.
     *
     * @return Uma string formatada com o nome e a nacionalidade do autor.
     */
    @Override
    public String toString() {
        return nome + " (" + nacionalidade + ")"; // Formata a saída como "Nome (Nacionalidade)"
    }
}
