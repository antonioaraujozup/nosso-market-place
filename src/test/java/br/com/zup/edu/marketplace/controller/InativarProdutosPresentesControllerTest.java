package br.com.zup.edu.marketplace.controller;

import br.com.zup.edu.marketplace.model.Produto;
import br.com.zup.edu.marketplace.model.StatusProduto;
import br.com.zup.edu.marketplace.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class InativarProdutosPresentesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    private Produto celular;

    private Produto televisao;

    @BeforeEach
    void setUp() {
        this.produtoRepository.deleteAll();
        this.celular = new Produto("Celular", "Smartphone", new BigDecimal("3500"), StatusProduto.ATIVO, LocalDateTime.now());
        this.televisao = new Produto("Televisão", "Smart TV", new BigDecimal("2000"), StatusProduto.ATIVO, LocalDateTime.now());
        this.produtoRepository.saveAll(List.of(celular, televisao));
    }

    @Test
    @DisplayName("Deve retornar lista vazia pois não existem produtos pendentes")
    void deveRetornarListaVaziaPoisNaoExistemProdutosPendentes() throws Exception {

        // Cenário
        MockHttpServletRequestBuilder request = post("/produtos/inativar");

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        TypeFactory typeFactory = mapper.getTypeFactory();

        List<Long> ids = mapper.readValue(payloadResponse, typeFactory.constructCollectionType(
                List.class,
                Long.class
        ));

        // Asserts
        assertThat(ids).isEmpty();

    }

    @Test
    @DisplayName("Deve inativar produtos pendentes")
    void deveInativarProdutosPendentes() throws Exception {

        // Cenário
        Produto videogame = new Produto("Videogame", "Jogo", new BigDecimal("4000"));

        this.produtoRepository.save(videogame);

        MockHttpServletRequestBuilder request = post("/produtos/inativar");

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        TypeFactory typeFactory = mapper.getTypeFactory();

        List<Long> ids = mapper.readValue(payloadResponse, typeFactory.constructCollectionType(
                List.class,
                Long.class
        ));

        // Asserts
        assertThat(ids)
                .hasSize(1)
                .contains(videogame.getId());

        List<Produto> listaProdutosAtualizados = this.produtoRepository.findAllById(ids);

        listaProdutosAtualizados.forEach(
                p -> assertEquals(StatusProduto.INATIVO, p.getStatus())
        );

    }

}