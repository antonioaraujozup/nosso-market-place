package br.com.zup.edu.marketplace.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class NotaFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario destinatario;

    @ManyToMany
    private List<Produto> itens= new ArrayList<>();


    public NotaFiscal(Usuario destinatario, List<Produto> itens) {
        this.destinatario = destinatario;
        this.itens = itens;
    }

    /**
     * @deprecated construtor para uso exclusivo do Hibernate
     */
    @Deprecated
    public NotaFiscal() {
    }

    public BigDecimal calculaValorFinal() {
        BigDecimal valorFinal = this.itens.stream()
                .map(i -> i.getPreco())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return valorFinal;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public List<Produto> getItens() {
        return itens;
    }

}
