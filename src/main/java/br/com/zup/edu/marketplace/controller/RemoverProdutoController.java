package br.com.zup.edu.marketplace.controller;

import br.com.zup.edu.marketplace.model.Produto;
import br.com.zup.edu.marketplace.model.StatusProduto;
import br.com.zup.edu.marketplace.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@RestController
public class RemoverProdutoController {

    private final ProdutoRepository repository;

    public RemoverProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<?> remover(@PathVariable("id") Long id) {
        Produto produto = repository.findById(id).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        if (!produto.temStatusIgualA(StatusProduto.INATIVO)) {
            throw new ResponseStatusException((HttpStatus.UNPROCESSABLE_ENTITY));
        }

        repository.delete(produto);

        return ResponseEntity.noContent().build();
    }
}
