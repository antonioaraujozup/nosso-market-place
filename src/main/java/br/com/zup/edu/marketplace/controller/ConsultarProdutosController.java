package br.com.zup.edu.marketplace.controller;

import br.com.zup.edu.marketplace.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsultarProdutosController {

    private final ProdutoRepository repository;

    public ConsultarProdutosController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/produtos")
    public ResponseEntity<?> consultar(@PageableDefault(size = 3, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable paginacao) {
        Page<ProdutoResponse> produtos = repository.findAll(paginacao)
                .map(ProdutoResponse::new);

        return ResponseEntity.ok(produtos);
    }
}
