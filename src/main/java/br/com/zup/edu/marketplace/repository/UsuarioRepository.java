package br.com.zup.edu.marketplace.repository;

import br.com.zup.edu.marketplace.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
