--DQL


--todos os espaços físicos cadastrados
SELECT e.*, string_agg(eq.nome_equipamento, ', ') as equipamentos
FROM EspacosFisicos e
LEFT JOIN Espacos_Equipamentos ee ON e.id = ee.id_espaco
LEFT JOIN Equipamentos eq ON ee.id_equipamento = eq.id
GROUP BY e.id
ORDER BY e.nome_espaco;

--todos 'SOLICITANTE' cadastrados
SELECT * FROM Usuarios 
WHERE tipo_usuario = 'SOLICITANTE'
ORDER BY nome;

--todos 'GESTOR' cadastrados
SELECT * FROM Usuarios 
WHERE tipo_usuario = 'GESTOR'
ORDER BY nome;

--yodas as solicitações feitas
SELECT s.id, u.nome AS solicitante, e.nome_espaco, s.data_reserva, s.hora_inicio_reserva, s.hora_fim_reserva, s.data_solicitacao, s.status_solicitacao
FROM SolicitacoesReserva s
JOIN Usuarios u ON s.id_solicitante = u.id
JOIN EspacosFisicos e ON s.id_espaco = e.id
ORDER BY s.data_solicitacao DESC;

--o histórico de avaliações feitas pelo gestor
SELECT h.id, h.id_solicitacao, s.data_reserva, s.hora_inicio_reserva, s.hora_fim_reserva, e.nome_espaco, us.nome AS solicitante, ug.nome AS gestor, h.data_avaliacao, h.novo_status, h.justificativa
FROM HistoricoAvaliacoes h
JOIN SolicitacoesReserva s ON h.id_solicitacao = s.id
JOIN EspacosFisicos e ON s.id_espaco = e.id
JOIN Usuarios us ON s.id_solicitante = us.id
JOIN Usuarios ug ON h.id_gestor = ug.id
ORDER BY h.data_avaliacao DESC;

--lista de todas as solicitações aprovadas
SELECT s.id, u.nome AS solicitante, e.nome_espaco, e.tipo_espaco, e.localizacao, s.data_reserva, s.hora_inicio_reserva, s.hora_fim_reserva, s.data_solicitacao
FROM SolicitacoesReserva s
JOIN Usuarios u ON s.id_solicitante = u.id
JOIN EspacosFisicos e ON s.id_espaco = e.id
WHERE s.status_solicitacao = 'APROVADA'
ORDER BY s.data_reserva, s.hora_inicio_reserva;
