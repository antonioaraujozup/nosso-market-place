package br.com.zup.edu.marketplace.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
}
