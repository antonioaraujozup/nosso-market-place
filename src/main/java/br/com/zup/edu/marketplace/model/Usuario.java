package br.com.zup.edu.marketplace.model;

import javax.persistence.*;

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
}
