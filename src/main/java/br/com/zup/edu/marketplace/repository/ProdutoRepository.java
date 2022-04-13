package br.com.zup.edu.marketplace.repository;

import br.com.zup.edu.marketplace.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
