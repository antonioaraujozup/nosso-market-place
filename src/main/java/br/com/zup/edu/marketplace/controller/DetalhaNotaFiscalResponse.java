package br.com.zup.edu.marketplace.controller;

import br.com.zup.edu.marketplace.model.NotaFiscal;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class DetalhaNotaFiscalResponse {

    private UsuarioResponse destinatario;
    private List<ProdutoResponse> itens;
    private BigDecimal valorFinal;

    public DetalhaNotaFiscalResponse(NotaFiscal notaFiscal) {
        this.destinatario = new UsuarioResponse(notaFiscal.getDestinatario());
        this.itens = notaFiscal.getItens().stream()
                .map(i -> new ProdutoResponse(i))
                .collect(Collectors.toList());
        this.valorFinal = notaFiscal.calculaValorFinal();
    }

    public UsuarioResponse getDestinatario() {
        return destinatario;
    }

    public List<ProdutoResponse> getItens() {
        return itens;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }
}
