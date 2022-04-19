package br.com.zup.edu.marketplace.controller;

import br.com.zup.edu.marketplace.model.Produto;
import br.com.zup.edu.marketplace.model.Usuario;
import br.com.zup.edu.marketplace.repository.ProdutoRepository;
import br.com.zup.edu.marketplace.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;

@RestController
public class AdicionarProdutoListaDesejosUsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;

    public AdicionarProdutoListaDesejosUsuarioController(UsuarioRepository usuarioRepository, ProdutoRepository produtoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    @PostMapping("/usuarios/{usuarioId}/lista-desejos/{produtoId}")
    public ResponseEntity<?> adicionar(@PathVariable("usuarioId") Long usuarioId,
                                       @PathVariable("produtoId") Long produtoId,
                                       UriComponentsBuilder uriComponentsBuilder) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        if (usuario.constaNaListaDeDesejos(produto)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Produto já consta na lista de desejos do usuário");
        }

        usuario.adicionarNaListaDeDesejos(produto);

        usuarioRepository.save(usuario);

        URI location = uriComponentsBuilder.path("/usuarios/{usuarioId}/lista-desejos/{produtoId}")
                .buildAndExpand(usuario.getId(), produto.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }
}
