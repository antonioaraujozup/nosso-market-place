package br.com.zup.edu.marketplace.controller;

import br.com.zup.edu.marketplace.controller.response.ProdutoResponse;
import br.com.zup.edu.marketplace.model.Produto;
import br.com.zup.edu.marketplace.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.assertj.core.groups.Tuple;
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
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class ListarProdutosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        this.produtoRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve retornar lista com produtos cadastrados")
    void deveRetornarListaComProdutosCadastrados() throws Exception {

        // Cenário
        Produto televisao = new Produto("Televisão", "TV de 32 polegadas", new BigDecimal("2500"));
        Produto videogame = new Produto("Videogame", "250GB de armazenamento", new BigDecimal("5000"));
        Produto celular = new Produto("Celular", "Smartphone", new BigDecimal("3500"));

        produtoRepository.saveAll(List.of(televisao, videogame, celular));

        MockHttpServletRequestBuilder request = get("/produtos");

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        TypeFactory typeFactory = mapper.getTypeFactory();

        List<ProdutoResponse> produtos = mapper.readValue(payloadResponse, typeFactory.constructCollectionType(
                List.class,
                ProdutoResponse.class
        ));

        // Asserts
        assertThat(produtos)
                .hasSize(3)
                .extracting("titulo", "descricao", "preco")
                .contains(
                        new Tuple(televisao.getTitulo(), televisao.getDescricao(), televisao.getPreco().setScale(2, RoundingMode.HALF_UP)),
                        new Tuple(videogame.getTitulo(), videogame.getDescricao(), videogame.getPreco().setScale(2, RoundingMode.HALF_UP)),
                        new Tuple(celular.getTitulo(), celular.getDescricao(), celular.getPreco().setScale(2, RoundingMode.HALF_UP))
                );

    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existem produtos cadastrados")
    void deveRetornarListaVaziaQuandoNaoExistemProdutosCadastrados() throws Exception {

        // Cenário
        MockHttpServletRequestBuilder request = get("/produtos");

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        TypeFactory typeFactory = mapper.getTypeFactory();

        List<ProdutoResponse> produtos = mapper.readValue(payloadResponse, typeFactory.constructCollectionType(
                List.class,
                ProdutoResponse.class
        ));

        // Asserts
        assertThat(produtos)
                .isEmpty();

    }

}