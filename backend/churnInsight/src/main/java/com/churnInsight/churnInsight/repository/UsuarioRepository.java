package com.churnInsight.churnInsight.repository;

import com.churnInsight.churnInsight.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Busca por nombre de usuario 
    Optional<Usuario> findByUsuario(String usuario);
}
