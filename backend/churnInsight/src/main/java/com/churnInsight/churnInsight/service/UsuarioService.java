package com.churnInsight.churnInsight.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.churnInsight.churnInsight.entity.Usuario;
import com.churnInsight.churnInsight.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Usuario crearUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    
    public Usuario actualizarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario getByUsuario (String usuario){
        return usuarioRepository.findByUsuario(usuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void deleteUsuarioById(Long id){
        usuarioRepository.deleteById(id);
    }

    public void deleteAll(){
        usuarioRepository.deleteAll();
    }
}
