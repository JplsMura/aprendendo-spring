package com.joao.aprendendospring.business;

import com.joao.aprendendospring.infrastructure.entity.Usuario;
import com.joao.aprendendospring.infrastructure.exceptions.ConflictException;
import com.joao.aprendendospring.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario usuario) {
        try {
            emailExiste(usuario.getEmail());
            return usuarioRepository.save(usuario);
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado no sistema ", e.getCause());
        }
    }

    public void emailExiste(String email) {
        try {
            boolean existente = verificaEmailExistente(email);

            if (existente) {
                throw new ConflictException("Email já cadastrado no sistema " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado no sistema ", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
