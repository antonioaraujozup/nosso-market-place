package br.com.zup.edu.marketplace.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusProduto status=StatusProduto.PENDENTE;

    @Column(nullable = false)
    private BigDecimal preco;

    public Produto(String titulo, String descricao, BigDecimal preco) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
    }

    /**
     * @deprecated construtor para uso exclusivo do Hibernate
     */
    @Deprecated
    public Produto() {
    }

    public Long getId() {
        return id;
    }
}
