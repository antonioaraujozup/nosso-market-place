package br.com.zup.edu.marketplace.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @ManyToMany(mappedBy = "listaDesejos")
    private List<Usuario> usuarios = new ArrayList<>();

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

    public Boolean temStatusIgualA(StatusProduto statusProduto) {
        return this.status == statusProduto;
    }

    public Long getId() {
        return id;
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

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void adicionar(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
