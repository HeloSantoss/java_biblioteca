# 📚 Manual do Usuário - Sistema de Gerenciamento de Biblioteca

---

## 1. Introdução
Este manual descreve como utilizar o **Sistema de Gerenciamento de Biblioteca** para cadastrar e gerenciar livros. O sistema permite que o usuário adicione, visualize e consulte livros através de uma interface gráfica simples.

---

## 🎯 Objetivo do Projeto

Desenvolver um **sistema de gerenciamento de biblioteca** que permita o cadastro, consulta e controle de empréstimos de livros de forma eficiente e intuitiva, utilizando uma interface gráfica amigável e conectando-se a um banco de dados PostgreSQL para a persistência de dados.

### 📝 Específicos
- Criar uma interface gráfica em Java Swing para facilitar o uso do sistema por parte dos usuários.
- Implementar funcionalidades de **cadastro**, **consulta**, **edição** e **exclusão** de livros no banco de dados.
- Desenvolver uma área de controle de **empréstimos** de livros, possibilitando o monitoramento e a devolução de itens.
- Integrar o sistema a um banco de dados PostgreSQL, garantindo a **persistência dos dados**.
- Gerar relatórios em formato `.csv` para análise e auditoria.

![image](https://github.com/user-attachments/assets/6f9197fe-0060-4d4c-a80f-79033ce680b1)

### 📏 Mensuráveis
- O sistema deve permitir o cadastro de **no mínimo 100 livros** e possibilitar a consulta eficiente dos mesmos em **menos de 3 segundos**.
- A funcionalidade de relatórios deve ser capaz de gerar um documento `.csv` contendo **todos os livros e empréstimos realizados**, em **menos de 5 segundos**.
- Deve ser possível realizar **operações CRUD** (Criar, Ler, Atualizar e Deletar) de livros em **menos de 4 cliques**.

### 🎯 Atingíveis
- A interface gráfica será simples e direta, com base em elementos visuais intuitivos.
- O uso de um banco de dados PostgreSQL permitirá a escalabilidade e manutenção dos registros de forma confiável.
- As funcionalidades de CRUD e geração de relatórios serão implementadas com bibliotecas e técnicas amplamente utilizadas e bem documentadas.

### 🗝️ Relevantes
- O projeto auxiliará bibliotecas a **automatizar o gerenciamento de livros**, evitando registros manuais e melhorando a eficiência na gestão de empréstimos.
- O sistema será útil para pequenas bibliotecas que buscam **uma solução simples, mas eficaz**, para organizar seus acervos e o fluxo de empréstimos.

### ⏰ Temporais
- O desenvolvimento do projeto será concluído em **12 semanas**, seguindo o seguinte cronograma:
  - **Semana 1-2:** Planejamento e criação da estrutura de classes do sistema.
  - **Semana 3-5:** Desenvolvimento das funcionalidades de cadastro e consulta de livros.
  - **Semana 6-8:** Implementação da funcionalidade de controle de empréstimos e relatórios.
  - **Semana 9-10:** Integração com o banco de dados e testes do sistema.
  - **Semana 11-12:** Refinamento, correções de bugs e documentação final.

---

## 2. Requisitos do Sistema
Para usar o sistema, você precisa ter:

- **Java 8** ou superior.
- **PostgreSQL** instalado e em execução.
- **Driver JDBC para PostgreSQL** integrado ao projeto.

---

## 3. Instalação e Configuração

### 3.1. Banco de Dados
1. Crie um banco de dados chamado `biblioteca` no PostgreSQL.
2. Execute o seguinte script SQL para criar as tabelas necessárias:

    ```sql
    CREATE TABLE usuarios (
        id SERIAL PRIMARY KEY,
        username VARCHAR(50) UNIQUE NOT NULL,
        senha VARCHAR(255) NOT NULL
    );

    CREATE TABLE livros (
        id SERIAL PRIMARY KEY,
        titulo VARCHAR(100) NOT NULL,
        autor VARCHAR(100) NOT NULL,
        ano_publicacao INT NOT NULL,
        genero VARCHAR(50) NOT NULL,
        usuario_id INT REFERENCES usuarios(id),
        criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
    ```

### 3.2. Execução do Sistema
1. Faça o download do código-fonte do projeto.
2. Compile o projeto utilizando uma IDE como **Eclipse** ou **IntelliJ** ou pelo terminal com o **Maven**.
3. Execute a classe `LoginPage` para iniciar o sistema de login.

---

## 4. Fluxo Básico de Uso

![image](https://github.com/user-attachments/assets/778bda43-fe1e-4f30-b3c6-9ec649ca0a97)


### 4.1. Login
1. Ao iniciar a aplicação, a tela de **Login** será exibida.
2. Digite seu nome de usuário e senha cadastrados.
3. Caso não tenha cadastro, clique em **Cadastrar** para criar uma nova conta.

![image](https://github.com/user-attachments/assets/ecd7dcd9-fc7e-4929-8fde-a5c5028e9aa1)

### 4.2. Cadastro de Livros
1. Após o login, você será direcionado para a tela de **Cadastro de Livros**.
2. Preencha os campos obrigatórios: **Título**, **Autor**, **Ano de Publicação**, **Gênero** e **Imagem** (opcional).
3. Clique no botão **Salvar** para adicionar o livro ao banco de dados.

![image](https://github.com/user-attachments/assets/6037f530-318e-4c9d-a6ef-d6951bb8043e)

### 4.3. Visualização de Livros
1. Para visualizar os livros cadastrados, clique em **Visualizar Livros**.
2. Uma lista de livros será exibida, mostrando informações como o título, autor, ano, gênero e a data de criação.
3. Utilize os botões **Avançar** e **Voltar** para navegar entre as páginas de livros.

![image](https://github.com/user-attachments/assets/a4cffa74-1748-4c4f-ad6e-4c9bbd34b9d9)

---

## 5. Funcionalidades da Interface Gráfica
- **Login**: Tela para autenticação do usuário.
- **Cadastro de Livros**: Formulário para adicionar novos livros à biblioteca.
- **Visualizar Livros**: Exibe os livros cadastrados no sistema em formato de tabela.

![image](https://github.com/user-attachments/assets/61853481-d5ad-4494-b1cb-2f3fe8b26846)

---

## 6. Finalizando a Aplicação
Para encerrar o sistema, basta fechar a janela principal.

---

## 7. Observações Finais
Este sistema foi projetado para facilitar o gerenciamento de livros de uma biblioteca pequena, com navegação simples e intuitiva. Além disso, a funcionalidade de edição e registro garante um controle total sobre a biblioteca, permitindo ajustes e melhorias conforme necessário.

---
## Diagramas
**Diagrama de Fluxo**:

![image](https://github.com/user-attachments/assets/07d8a725-bef9-4d14-ae91-7727de868d21)

**Diagrama de Classe**:

![image](https://github.com/user-attachments/assets/508025d6-59a3-406f-b938-ceb8ceb655b3)
