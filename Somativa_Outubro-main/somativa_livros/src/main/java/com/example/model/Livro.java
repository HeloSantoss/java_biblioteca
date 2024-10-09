package com.example.model;

public class Livro {

    // Atributo que armazena o ID do livro
    private int id;
    // Atributo que armazena o título do livro
    private String titulo;
    // Atributo que armazena o gênero do livro
    private String genero;
    // Atributo que referencia o autor do livro
    private Autor autor;

    /**
     * Construtor da classe Livro.
     *
     * @param id O ID do livro.
     * @param titulo O título do livro.
     * @param genero O gênero do livro.
     * @param autor O autor do livro, representado pela classe Autor.
     */
    public Livro(int id, String titulo, String genero, Autor autor) {
        this.id = id;             // Inicializa o ID do livro
        this.titulo = titulo;     // Inicializa o título do livro
        this.genero = genero;     // Inicializa o gênero do livro
        this.autor = autor;       // Inicializa o autor do livro
    }

    // Getters e Setters
    /**
     * Método que retorna o ID do livro.
     *
     * @return O ID do livro.
     */
    public int getId() {
        return id; // Retorna o ID do livro
    }

    /**
     * Método que define o ID do livro.
     *
     * @param id O novo ID do livro.
     */
    public void setId(int id) {
        this.id = id; // Atualiza o ID do livro
    }

    /**
     * Método que retorna o título do livro.
     *
     * @return O título do livro.
     */
    public String getTitulo() {
        return titulo; // Retorna o título do livro
    }

    /**
     * Método que define o título do livro.
     *
     * @param titulo O novo título do livro.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo; // Atualiza o título do livro
    }

    /**
     * Método que retorna o gênero do livro.
     *
     * @return O gênero do livro.
     */
    public String getGenero() {
        return genero; // Retorna o gênero do livro
    }

    /**
     * Método que define o gênero do livro.
     *
     * @param genero O novo gênero do livro.
     */
    public void setGenero(String genero) {
        this.genero = genero; // Atualiza o gênero do livro
    }

    /**
     * Método que retorna o autor do livro.
     *
     * @return O autor do livro.
     */
    public Autor getAutor() {
        return autor; // Retorna o autor do livro
    }

    /**
     * Método que define o autor do livro.
     *
     * @param autor O novo autor do livro, representado pela classe Autor.
     */
    public void setAutor(Autor autor) {
        this.autor = autor; // Atualiza o autor do livro
    }

    /**
     * Método que retorna uma representação em String do livro.
     *
     * @return Uma string formatada com o título do livro e o nome do autor.
     */
    @Override
    public String toString() {
        return titulo + " de " + autor.getNome(); // Formata a saída como "Título de Autor"
    }
}
