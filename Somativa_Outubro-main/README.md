Controle de Cadastro de Livros

-- Criação do banco de dados
CREATE DATABASE gerenciador_livros;

-- Seleciona o banco de dados criado
\c gerenciador_livros;

-- Criação da tabela de usuários
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY, -- Chave primária
    username VARCHAR(50) NOT NULL UNIQUE, -- Nome de usuário único
    senha VARCHAR(255) NOT NULL, -- Senha do usuário
    email VARCHAR(100) NOT NULL UNIQUE, -- Email único do usuário
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Data de criação
);

-- Criação da tabela de livros
CREATE TABLE livros (
    id SERIAL PRIMARY KEY, -- Chave primária
    titulo VARCHAR(100) NOT NULL, -- Título do livro
    autor VARCHAR(100) NOT NULL, -- Autor do livro
    ano_publicacao INT NOT NULL CHECK (ano_publicacao > 0), -- Ano de publicação
    genero VARCHAR(50), -- Gênero do livro
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE, -- Chave estrangeira para a tabela de usuários
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Data de criação
    imagem BYTEA -- Coluna para armazenar a imagem do livro
);

-- Criação de índices para melhorar o desempenho das consultas
CREATE INDEX idx_titulo ON livros(titulo); -- Índice no título do livro
CREATE INDEX idx_autor ON livros(autor); -- Índice no autor do livro
CREATE INDEX idx_genero ON livros(genero); -- Índice no gênero do livro

-- (Opcional) Criação de uma tabela para registrar o histórico de empréstimos (se necessário)
CREATE TABLE emprestimos (
    id SERIAL PRIMARY KEY, -- Chave primária
    livro_id INT REFERENCES livros(id) ON DELETE CASCADE, -- Chave estrangeira para a tabela de livros
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE, -- Chave estrangeira para a tabela de usuários
    data_emprestimo TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Data do empréstimo
    data_devolucao TIMESTAMP, -- Data de devolução
    CONSTRAINT uq_emprestimos UNIQUE (livro_id, usuario_id, data_emprestimo) -- Garantir que um livro não seja emprestado ao mesmo usuário mais de uma vez na mesma data
);

Documentação Tecnica
diagrama de classe: 
Classe Livro
├── Atributos
│   ├── int id
│   ├── String titulo
│   ├── String autor
│   ├── int anoPublicacao
│   ├── String genero
│   ├── int usuarioId
│   ├── Timestamp criadoEm
│   └── byte[] imagem
└── Métodos
    ├── getters e setters para os atributos

Classe Usuario
├── Atributos
│   ├── int id
│   ├── String username
│   └── String senha
└── Métodos
    ├── getters e setters para os atributos

Classe VisualizarLivrosFrame
├── Atributos
│   ├── Connection connection
│   ├── JTable tabelaLivros
│   ├── DefaultTableModel modeloTabela
│   ├── JButton avancarButton
│   ├── JButton voltarButton
│   ├── int paginaAtual
│   └── final int livrosPorPagina
└── Métodos
    ├── carregarLivros()
    └── main()

Classe LoginPage
├── Atributos
│   ├── Connection connection
│   ├── JTextField usernameField
│   └── JPasswordField passwordField
└── Métodos
    ├── autenticarUsuario(String username, String password)
    └── main()

Classe CadastroPage
├── Atributos
│   ├── Connection connection
│   ├── JTextField usernameField
│   ├── JPasswordField passwordField
│   └── JPasswordField confirmarPasswordField
└── Métodos
    ├── cadastrarUsuario()
    └── main()

Relações
├── Associação
│   ├── Livro associa-se a Usuario através do atributo usuarioId.
│   └── LoginPage, CadastroPage, e VisualizarLivrosFrame associam-se ao banco de dados usando o atributo Connection.
└── Composição
    └── VisualizarLivrosFrame compõe elementos de interface gráfica como JTable, DefaultTableModel, e botões de navegação.

    Diagrama de Fluxo :
    Início
├──> Tela de Login
│   ├──> Usuário insere username e senha
│   ├──> Clique no botão "Login"
│   │   ├──> Verificar credenciais
│   │   │   ├──> Se credenciais válidas:
│   │   │   │   └──> Abre a Tela de Visualização de Livros
│   │   │   └──> Se credenciais inválidas:
│   │   │       └──> Exibe mensagem de erro
│   └──> Clique no botão "Cadastro"
│       └──> Abre a Tela de Cadastro
│           ├──> Usuário insere username e senha
│           ├──> Clique no botão "Cadastrar"
│           │   └──> Cadastrar usuário no banco de dados
│           │       ├──> Se sucesso:
│           │       │   └──> Exibe mensagem de sucesso
│           │       └──> Se falha:
│           │           └──> Exibe mensagem de erro
│           └──> Retorna à Tela de Login

Tela de Visualização de Livros
├──> Carregar livros do banco de dados
│   ├──> Se livros disponíveis:
│   │   ├──> Exibir livros na tabela
│   │   └──> Ativar botão "Avançar"
│   └──> Se nenhum livro:
│       └──> Exibe mensagem "Nenhum livro encontrado"
│   ├──> Clique no botão "Avançar"
│   │   └──> Avançar para próxima página de livros
│   └──> Clique no botão "Voltar"
│       └──> Voltar para página anterior de livros

Fim
Diagrama de Uso:
Ator: Usuário
├──> Caso de Uso: Login
│   ├──> Descrição: Permite ao usuário fazer login no sistema.
│   ├──> Pré-condições: O usuário deve ter um cadastro prévio.
│   └──> Pós-condições: Usuário autenticado, acesso à tela de visualização de livros.

├──> Caso de Uso: Cadastro
│   ├──> Descrição: Permite ao usuário criar um novo cadastro.
│   ├──> Pré-condições: O usuário não deve estar cadastrado no sistema.
│   └──> Pós-condições: Novo usuário cadastrado, acesso à tela de login.

├──> Caso de Uso: Visualizar Livros
│   ├──> Descrição: Permite ao usuário visualizar a lista de livros cadastrados.
│   ├──> Pré-condições: O usuário deve estar autenticado.
│   └──> Pós-condições: Exibição da lista de livros na interface.

├──> Caso de Uso: Avançar Página
│   ├──> Descrição: Permite ao usuário avançar para a próxima página de livros.
│   ├──> Pré-condições: Livros devem estar disponíveis para visualização.
│   └──> Pós-condições: Exibição da próxima página de livros.

├──> Caso de Uso: Voltar Página
│   ├──> Descrição: Permite ao usuário voltar para a página anterior de livros.
│   ├──> Pré-condições: O usuário não deve estar na primeira página.
│   └──> Pós-condições: Exibição da página anterior de livros.

└──> Caso de Uso: Logout
    ├──> Descrição: Permite ao usuário encerrar a sessão.
    ├──> Pré-condições: O usuário deve estar autenticado.
    └──> Pós-condições: Usuário desautenticado, redirecionado para a tela de login.

Especificação de Requisitos do Sistema de Gerenciamento de Livros
1. Introdução
Este documento descreve os requisitos funcionais e não funcionais do Sistema de Gerenciamento de Livros, que permite aos usuários cadastrar, visualizar e gerenciar informações sobre livros.

2. Objetivos
Facilitar o cadastro de livros.
Permitir a visualização e navegação entre os livros cadastrados.
Prover funcionalidades de autenticação para usuários.
3. Requisitos Funcionais
3.1. Cadastro de Usuário
RF1: O sistema deve permitir que um novo usuário se cadastre com informações como nome de usuário e senha.
RF2: O sistema deve validar se o nome de usuário já existe antes de concluir o cadastro.
3.2. Autenticação
RF3: O sistema deve permitir que um usuário faça login usando seu nome de usuário e senha.
RF4: O sistema deve exibir uma mensagem de erro caso o login falhe.
3.3. Cadastro de Livros
RF5: O sistema deve permitir que um usuário cadastrado adicione novos livros com as seguintes informações: título, autor, ano de publicação, gênero, imagem (opcional).
RF6: O sistema deve validar as informações do livro antes de cadastrá-lo.
3.4. Visualização de Livros
RF7: O sistema deve permitir que um usuário visualize uma lista de livros cadastrados.
RF8: O sistema deve suportar paginação, exibindo um número fixo de livros por página (5 por padrão).
RF9: O sistema deve permitir que o usuário navegue entre as páginas de livros.
3.5. Logout
RF10: O sistema deve permitir que o usuário encerre a sessão, retornando à tela de login.
4. Requisitos Não Funcionais
4.1. Usabilidade
RNF1: O sistema deve ter uma interface gráfica amigável e intuitiva.
RNF2: As mensagens de erro devem ser claras e descritivas.
4.2. Desempenho
RNF3: O sistema deve responder a ações do usuário em até 2 segundos.
RNF4: A navegação entre as páginas de livros deve ser rápida e eficiente.
4.3. Segurança
RNF5: As senhas dos usuários devem ser armazenadas de forma segura (criptografadas).
RNF6: O sistema deve proteger contra ataques comuns, como SQL Injection.
4.4. Compatibilidade
RNF7: O sistema deve ser compatível com as principais versões dos navegadores (Chrome, Firefox, Safari).
5. Requisitos de Dados
RD1: O sistema deve armazenar informações sobre os usuários em uma tabela de usuarios.
RD2: O sistema deve armazenar informações sobre os livros em uma tabela de livros.
6. Conclusão
Este documento fornece uma visão geral dos requisitos necessários para o desenvolvimento do Sistema de Gerenciamento de Livros. Os requisitos devem ser revisados e validados pela equipe de desenvolvimento e partes interessadas antes do início do desenvolvimento.

Manual do Usuário - Sistema de Gerenciamento de Livros
1. Introdução
Este manual orienta os usuários sobre como instalar e utilizar o Sistema de Gerenciamento de Livros. O sistema permite que os usuários cadastrem livros, visualizem os livros cadastrados e gerenciem suas informações.

2. Requisitos do Sistema
Antes de instalar o sistema, verifique se o seu computador atende aos seguintes requisitos:

Java Development Kit (JDK): versão 8 ou superior.
Banco de Dados PostgreSQL: configurado e em execução.
Bibliotecas JDBC: para a conexão com o banco de dados PostgreSQL.
3. Instalando o Sistema
3.1. Configuração do Banco de Dados
Crie um banco de dados no PostgreSQL chamado biblioteca.

Execute o seguinte script SQL para criar as tabelas necessárias:

sql
Copiar código
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
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    imagem BYTEA
);
3.2. Executando o Sistema
Baixe o código-fonte do projeto.
Abra o terminal ou prompt de comando.
Navegue até o diretório do projeto.
Compile o projeto utilizando o comando:
bash
Copiar código
javac -d bin src/com/example/*.java
Execute o sistema com o comando:
bash
Copiar código
java -cp bin com.example.VisualizarLivrosFrame
4. Usando o Sistema
4.1. Fluxo Básico de Uso
Tela de Login: Ao iniciar o sistema, você verá a tela de login. Insira seu nome de usuário e senha. Se você não tiver uma conta, você precisará se cadastrar.

Cadastro de Usuário: Se você é um novo usuário, clique no botão de cadastro na tela de login e preencha as informações solicitadas (nome de usuário e senha).

Login: Após se cadastrar, entre com suas credenciais. Você será redirecionado para a tela principal.

Cadastro de Livros:

Acesse a opção de cadastro de livros.
Preencha os campos obrigatórios: título, autor, ano de publicação, gênero e (opcionalmente) a imagem do livro.
Clique no botão "Salvar" para cadastrar o livro.
Visualização de Livros:

Acesse a opção para visualizar livros cadastrados.
Os livros serão exibidos em uma tabela, com informações como ID, título, autor, ano de publicação, gênero e imagem.
Utilize os botões "Avançar" e "Voltar" para navegar entre as páginas de livros.
Logout: Para sair do sistema, clique no botão de logout disponível na interface.

4.2. Explicação dos Componentes da Interface
Botão "Salvar": Usado para salvar os dados de um novo livro durante o cadastro.
Tabela de Livros: Exibe todos os livros cadastrados no sistema. Cada linha representa um livro, e as colunas contêm suas respectivas informações.
Botões de Navegação:
Avançar: Permite navegar para a próxima página de livros.
Voltar: Permite retornar à página anterior de livros.
Botão "Logout": Finaliza a sessão do usuário, retornando à tela de login.
5. Solução de Problemas
Problemas de Conexão: Se o sistema não conseguir se conectar ao banco de dados, verifique se o PostgreSQL está em execução e se as credenciais estão corretas.
Erro ao Salvar Livros: Certifique-se de que todos os campos obrigatórios estão preenchidos antes de tentar salvar.
6. Conclusão
Este manual fornece orientações básicas para a instalação e uso do Sistema de Gerenciamento de Livros. Para mais informações ou ajuda, consulte o suporte técnico ou a equipe de desenvolvimento.
