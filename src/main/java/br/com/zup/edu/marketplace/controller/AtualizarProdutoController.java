package br.com.zup.edu.marketplace.controller;

import br.com.zup.edu.marketplace.model.Produto;
import br.com.zup.edu.marketplace.model.StatusProduto;
import br.com.zup.edu.marketplace.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class AtualizarProdutoController {

    private final ProdutoRepository repository;

    public AtualizarProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @PatchMapping("/produtos/{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody @Valid AtualizaProdutoRequest request) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!produto.temStatusIgualA(StatusProduto.PENDENTE)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        produto.setDescricao(request.getDescricao());

        repository.save(produto);

        return ResponseEntity.noContent().build();
    }
}
