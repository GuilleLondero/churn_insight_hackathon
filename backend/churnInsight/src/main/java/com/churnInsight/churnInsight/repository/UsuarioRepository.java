package com.churnInsight.churnInsight.repository;

import com.churnInsight.churnInsight.domain.model.Rol;
import com.churnInsight.churnInsight.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u where rol != 'ADMIN' and rol != 'SUPER_ADMIN'")
    List<Usuario> getAllSinAdmins();

    // Busca por nombre de usuario 
    Optional<Usuario> findByUsuario(String usuario);

    Boolean existsByRol(Rol rol);
}
