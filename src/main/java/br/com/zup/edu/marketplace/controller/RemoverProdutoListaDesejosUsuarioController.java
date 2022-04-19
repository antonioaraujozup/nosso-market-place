package br.com.zup.edu.marketplace.controller;

import br.com.zup.edu.marketplace.model.Produto;
import br.com.zup.edu.marketplace.model.Usuario;
import br.com.zup.edu.marketplace.repository.ProdutoRepository;
import br.com.zup.edu.marketplace.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@RestController
public class RemoverProdutoListaDesejosUsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;

    public RemoverProdutoListaDesejosUsuarioController(UsuarioRepository usuarioRepository, ProdutoRepository produtoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    @DeleteMapping("/usuarios/{usuarioId}/produtos/{produtoId}")
    public ResponseEntity<?> remover(@PathVariable("usuarioId") Long usuarioId,
                                     @PathVariable("produtoId") Long produtoId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        if(!usuario.constaNaListaDeDesejos(produto)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Produto não consta na lista de desejos do usuário");
        }

        usuario.removerDaListaDeDesejos(produto);

        usuarioRepository.save(usuario);

        return ResponseEntity.noContent().build();
    }
}
