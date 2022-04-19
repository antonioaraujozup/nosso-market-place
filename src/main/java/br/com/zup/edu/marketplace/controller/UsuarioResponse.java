package br.com.zup.edu.marketplace.controller;

import br.com.zup.edu.marketplace.model.Usuario;

public class UsuarioResponse {

    private String nome;
    private String endereco;
    private String telefone;
    private String cpf;

    public UsuarioResponse(Usuario usuario) {
        this.nome = usuario.getNome();
        this.endereco = usuario.getEndereco();
        this.telefone = usuario.getTelefone();
        this.cpf = usuario.getCpf();
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCpf() {
        return cpf;
    }
}
