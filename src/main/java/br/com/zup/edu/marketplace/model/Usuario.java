package br.com.zup.edu.marketplace.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String telefone;

    @ManyToMany
    private List<Produto> listaDesejos = new ArrayList<>();

    public Usuario(String nome, String cpf, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    /**
     * @deprecated  construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void adicionarNaListaDeDesejos(Produto produto) {
        produto.adicionar(this);
        this.listaDesejos.add(produto);
    }

    public boolean constaNaListaDeDesejos(Produto produto) {
        return this.listaDesejos.contains(produto);
    }
}
