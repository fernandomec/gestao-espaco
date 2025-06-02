package com.fergabgabsam.atividadefinal.gestao_espaco.repository;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private final RowMapper<Usuario> rowMapper = (rs, rowNum) -> {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setLogin(rs.getString("login"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setTipoUsuario(rs.getString("tipo_usuario"));
        usuario.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());
        return usuario;
    };
    
    public List<Usuario> findAll() {
        return jdbcTemplate.query("SELECT * FROM Usuarios", rowMapper);
    }
    
    public Optional<Usuario> findById(Integer id) {
        List<Usuario> usuarios = jdbcTemplate.query("SELECT * FROM Usuarios WHERE id = ?", rowMapper, id);
        return usuarios.isEmpty() ? Optional.empty() : Optional.of(usuarios.get(0));
    }
    
    public Optional<Usuario> findByLogin(String login) {
        List<Usuario> usuarios = jdbcTemplate.query("SELECT * FROM Usuarios WHERE login = ?", rowMapper, login);
        return usuarios.isEmpty() ? Optional.empty() : Optional.of(usuarios.get(0));
    }
    
    public List<Usuario> findAllSolicitantes() {
        return jdbcTemplate.query("SELECT * FROM Usuarios WHERE tipo_usuario = 'SOLICITANTE'", rowMapper);
    }
    
    public List<Usuario> findAllGestores() {
        return jdbcTemplate.query("SELECT * FROM Usuarios WHERE tipo_usuario = 'GESTOR'", rowMapper);
    }
}
