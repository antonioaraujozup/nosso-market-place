package br.com.zup.edu.marketplace.controller;

import br.com.zup.edu.marketplace.model.Produto;
import br.com.zup.edu.marketplace.model.StatusProduto;

import java.math.BigDecimal;

public class ProdutoResponse {

    private String titulo;
    private String descricao;
    private StatusProduto status;
    private BigDecimal preco;

    public ProdutoResponse(Produto produto) {
        this.titulo = produto.getTitulo();
        this.descricao = produto.getDescricao();
        this.status = produto.getStatus();
        this.preco = produto.getPreco();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public StatusProduto getStatus() {
        return status;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}
