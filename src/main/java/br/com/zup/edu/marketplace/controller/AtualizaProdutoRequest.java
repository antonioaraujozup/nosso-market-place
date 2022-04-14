package br.com.zup.edu.marketplace.controller;

import javax.validation.constraints.NotBlank;

public class AtualizaProdutoRequest {

    @NotBlank
    private String descricao;

    public AtualizaProdutoRequest(String descricao) {
        this.descricao = descricao;
    }

    public AtualizaProdutoRequest() {
    }

    public String getDescricao() {
        return descricao;
    }
}
