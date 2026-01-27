package com.churnInsight.churnInsight.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.churnInsight.churnInsight.entity.Usuario;
import com.churnInsight.churnInsight.service.UsuarioService;

@SpringBootTest
public class UsuarioServiceTest {
        
        @Autowired
        private UsuarioService service;
        private Usuario usuario;


        @BeforeEach
        void setup(){
            //Setando un usuario valido
            usuario = new Usuario();
            crearUsuarioValido();
        }

        private void crearUsuarioValido(){
            usuario.setEmail("usuario@gmail.com"); //unique not null
            usuario.setUsuario("usuario"); // unique, not null
            usuario.setPassword("usuarioPass"); //not null
        }

        @Test
        public void persistenciaDeUsuarioCorrecta(){
            //Seteo un usuario valido para que se guarde en Base de datos
            usuario.setEmail("usuarioValido@gmail.com");
            usuario.setUsuario("usuarioValido");
            usuario.setPassword("usuarioPass");
            //Se realiza la persistencia mediante el repository
            Usuario usuarioPers = service.crearUsuario(usuario);
            
            //Se verifica que el usuario seteado y 
            //el usuario de la base de datos tengan los mismos datos
            
            assertEquals(usuarioPers.getEmail(), usuario.getEmail());
            assertEquals(usuarioPers.getUsuario(), usuario.getUsuario());
            assertEquals(usuarioPers.getPassword(), usuario.getPassword());
        }

        @Test 
        public void verificaUsuarioDuplicado(){
            service.crearUsuario(usuario);
            Usuario usuario2 = new Usuario();
            usuario2.setUsuario(usuario.getUsuario());
            usuario2.setEmail("usuarioCambiado@gmail.com");
            usuario2.setPassword("otraPassword");
            assertThrows(DataIntegrityViolationException.class, () -> service.crearUsuario(usuario2));
        }
        
        @Test 
        public void verificaUsuarioNull(){
            usuario.setUsuario( null);
            assertThrows(DataIntegrityViolationException.class, () -> service.crearUsuario(usuario));
        }

        @Test 
        public void verificaEmailDuplicado(){
            service.crearUsuario(usuario);
            Usuario usuario2 = new Usuario();
            usuario2.setUsuario("Pedro");
            usuario2.setEmail(usuario.getEmail());
            usuario2.setPassword("otraPassword");
            assertThrows(DataIntegrityViolationException.class, () -> service.crearUsuario(usuario2));
        }

        @Test 
        public void verificaEmailNull(){
            usuario.setEmail( null);
            assertThrows(DataIntegrityViolationException.class, () -> service.crearUsuario(usuario));
        }

        @Test 
        public void verificaPasswordNull(){
            usuario.setPassword( null);
            assertThrows(DataIntegrityViolationException.class, () -> service.crearUsuario(usuario));
        }

        @Test
        public void updateUsuarioCorrecta(){
            Usuario usuarioPers = service.crearUsuario(usuario);
            //Se verifica que el usuario seteado y 
            //el usuario de la base de datos tengan los mismos datos
            assertEquals(usuarioPers.getEmail(), usuario.getEmail());
            assertEquals(usuarioPers.getUsuario(), usuario.getUsuario());
            assertEquals(usuarioPers.getPassword(), usuario.getPassword());
            
            usuario.setEmail("emailNuevo@gmail.com");
            usuario.setPassword("passNueva");
            usuario.setPassword("CambioDeUsuario");
            usuarioPers = service.actualizarUsuario(usuario);
            //Se verifica la actualizacion
            assertEquals(usuarioPers.getEmail(), usuario.getEmail());
            assertEquals(usuarioPers.getUsuario(), usuario.getUsuario());
            assertEquals(usuarioPers.getPassword(), usuario.getPassword());
        }

        @Test
        public void getAllUsuarios(){
            assertEquals(0, service.getAll().size());

            Usuario user1 = new Usuario();
            user1.setUsuario("user1");
            user1.setEmail("user1@gmail.com");
            user1.setPassword("user1");
            service.crearUsuario(user1);
            
            Usuario user2 = new Usuario();
            user2.setUsuario("user2");
            user2.setEmail("user2@gmail.com");
            user2.setPassword("user2");
            service.crearUsuario(user2);
            
            Usuario user3 = new Usuario();
            user3.setUsuario("user3");
            user3.setEmail("user3@gmail.com");
            user3.setPassword("user3");
            service.crearUsuario(user3);

            assertEquals(3, service.getAll().size());

        }

        @Test
        public void getById(){
            Usuario usuarioPers = service.crearUsuario(usuario);
            Usuario usuarioById = service.getUsuarioById(usuarioPers.getId());

            assertEquals(usuarioPers.getId(), usuarioById.getId());
            assertEquals(usuarioPers.getEmail(), usuarioById.getEmail());
            assertEquals(usuarioPers.getUsuario(), usuarioById.getUsuario());
            assertEquals(usuarioPers.getPassword(), usuarioById.getPassword());
        }

        @Test
        public void getByUsuario(){
            Usuario usuarioPers = service.crearUsuario(usuario);
            Usuario usuarioById = service.getByUsuario(usuarioPers.getUsuario());

            assertEquals(usuarioPers.getId(), usuarioById.getId());
            assertEquals(usuarioPers.getEmail(), usuarioById.getEmail());
            assertEquals(usuarioPers.getUsuario(), usuarioById.getUsuario());
            assertEquals(usuarioPers.getPassword(), usuarioById.getPassword());
        }

        @Test
        public void deleteUsuario(){
            Usuario usuarioPers = service.crearUsuario(usuario);
            assertNotNull(service.getUsuarioById(usuarioPers.getId()));
            service.deleteUsuarioById(usuarioPers.getId());
            assertThrows(RuntimeException.class, () -> service.getUsuarioById(usuarioPers.getId()));
            
        }



        //esto se ejecuta despues de los test
        //se usa para eliminar todo de la base de datos,
        //asi no da errores al volver a ejecutar los test
        @AfterEach
        private void deleteAll(){
            service.deleteAll();
        }

}
