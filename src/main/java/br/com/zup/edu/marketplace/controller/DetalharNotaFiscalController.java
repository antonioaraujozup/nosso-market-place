package br.com.zup.edu.marketplace.controller;

import br.com.zup.edu.marketplace.model.NotaFiscal;
import br.com.zup.edu.marketplace.repository.NotaFiscalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DetalharNotaFiscalController {

    private final NotaFiscalRepository repository;

    public DetalharNotaFiscalController(NotaFiscalRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/notas-fiscais/{id}")
    public ResponseEntity<?> detalhar(@PathVariable("id") Long id) {
        NotaFiscal notaFiscal = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(new DetalhaNotaFiscalResponse(notaFiscal));
    }
}
