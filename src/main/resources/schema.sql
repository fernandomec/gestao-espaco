--DDL



DROP TABLE IF EXISTS Auditoria;
DROP TABLE IF EXISTS HistoricoAvaliacoes;
DROP TABLE IF EXISTS SolicitacoesReserva;
DROP TABLE IF EXISTS Espacos_Equipamentos;
DROP TABLE IF EXISTS Equipamentos;
DROP TABLE IF EXISTS EspacosFisicos;
DROP TABLE IF EXISTS Usuarios;

CREATE TABLE Usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    login VARCHAR(50) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL CHECK (tipo_usuario IN ('SOLICITANTE', 'GESTOR')),
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE EspacosFisicos (
    id SERIAL PRIMARY KEY,
    nome_espaco VARCHAR(100) UNIQUE NOT NULL,
    tipo_espaco VARCHAR(50) NOT NULL,
    metragem DECIMAL(10, 2),
    capacidade INT,
    localizacao VARCHAR(200),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Equipamentos (
    id SERIAL PRIMARY KEY,
    nome_equipamento VARCHAR(100) UNIQUE NOT NULL,
    descricao TEXT
);

--N:N espaços físicos e equipamentos
CREATE TABLE Espacos_Equipamentos (
    id_espaco INT NOT NULL,
    id_equipamento INT NOT NULL,
    PRIMARY KEY (id_espaco, id_equipamento),
    FOREIGN KEY (id_espaco) REFERENCES EspacosFisicos(id) ON DELETE CASCADE,
    FOREIGN KEY (id_equipamento) REFERENCES Equipamentos(id) ON DELETE CASCADE
);

CREATE TABLE SolicitacoesReserva (
    id SERIAL PRIMARY KEY,
    id_solicitante INT NOT NULL,
    id_espaco INT NOT NULL,
    data_solicitacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_reserva DATE NOT NULL,
    hora_inicio_reserva TIME NOT NULL,
    hora_fim_reserva TIME NOT NULL,
    status_solicitacao VARCHAR(20) NOT NULL CHECK (status_solicitacao IN ('PENDENTE', 'APROVADA', 'REJEITADA')),
    FOREIGN KEY (id_solicitante) REFERENCES Usuarios(id) ON DELETE RESTRICT,
    FOREIGN KEY (id_espaco) REFERENCES EspacosFisicos(id) ON DELETE RESTRICT
);

CREATE TABLE HistoricoAvaliacoes (
    id SERIAL PRIMARY KEY,
    id_solicitacao INT NOT NULL,
    id_gestor INT NOT NULL,
    data_avaliacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    novo_status VARCHAR(20) NOT NULL CHECK (novo_status IN ('APROVADA', 'REJEITADA')),
    justificativa TEXT,
    FOREIGN KEY (id_solicitacao) REFERENCES SolicitacoesReserva(id) ON DELETE CASCADE,
    FOREIGN KEY (id_gestor) REFERENCES Usuarios(id) ON DELETE RESTRICT
);

CREATE TABLE Auditoria (
    id SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    data_hora_acao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo_acao VARCHAR(50) NOT NULL,
    detalhes_acao TEXT,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id) ON DELETE RESTRICT
);
