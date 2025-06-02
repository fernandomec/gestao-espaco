--DML


INSERT INTO Usuarios (nome, email, login, senha, tipo_usuario) VALUES
('Fernando Cezar', 'fernando.cezar@email.com', 'fernando.cezar', 'senha123', 'GESTOR'),
('Fernando Matos', 'fernando@email.com', 'fernandomec', 'senha123', 'SOLICITANTE'),
('Gabriel Arruda', 'gabriel.arruda@email.com', 'gabriel.arruda', 'senha123', 'SOLICITANTE'),
('Gabriel La Torre', 'gabriel.torre@email.com', 'gabriel.torre', 'senha123', 'SOLICITANTE'),
('Samuel', 'samuel@email.com', 'samuel.g', 'senha123', 'SOLICITANTE');

INSERT INTO EspacosFisicos (nome_espaco, tipo_espaco, metragem, capacidade, localizacao, ativo) VALUES
('LAMI 1', 'Laboratório', 40.00, 40, 'Bloco B, 2º andar', TRUE),
('LAMI 2', 'Laboratório', 20.00, 20, 'Bloco B, 4º andar', TRUE),
('Sala dos Professores', 'Sala', 20.50, 15, 'Bloco X', TRUE),
('Auditório Principal', 'Auditório', 150.00, 100, 'Bloco C', TRUE);

INSERT INTO Equipamentos (nome_equipamento, descricao) VALUES
('Projetor', 'Projetor Multimídia'),
('Lona para projeção', 'Tela de 100 polegadas'),
('Computador desktop', 'PC com Windows 10'),
('Ar condicionado', 'Ar condicionado X'),
('Computador notebook', 'Thinkpad');

--N:N espaços físicos e equipamentos
INSERT INTO Espacos_Equipamentos (id_espaco, id_equipamento) VALUES
(1, 1), (1, 2), (1, 4),
(2, 1), (2, 5),
(3, 3), (3, 4),
(4, 1), (4, 2), (4, 4);

INSERT INTO SolicitacoesReserva (id_solicitante, id_espaco, data_reserva, hora_inicio_reserva, hora_fim_reserva, status_solicitacao)
VALUES 
(2, 1, '2025-06-10', '08:00', '10:00', 'PENDENTE'),
(3, 4, '2025-06-11', '14:00', '17:00', 'PENDENTE'),
(4, 3, '2025-06-12', '09:00', '11:00', 'PENDENTE');