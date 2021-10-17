package br.com.saleback.controller;

import br.com.saleback.SaleBackApplication;
import br.com.saleback.model.ItemSolicitation;
import br.com.saleback.model.Product;
import br.com.saleback.model.Solicitation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SaleBackApplication.class)
public class ItemSolicitationControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    public void persistItemSolicitation() throws Exception {
        MvcResult result = mvc.perform(get("/product/")).andReturn();
        String content = result.getResponse().getContentAsString();
        List<Product> products = objectMapper.readValue(content, new TypeReference<List<Product>>() {});

        result = mvc.perform(get("/solicitation/")).andReturn();
        content = result.getResponse().getContentAsString();
        List<Solicitation> solicitations = objectMapper.readValue(content, new TypeReference<List<Solicitation>>() {});

        Object itemSolicitation = new Object() {
            public final Product product = products.get(0);
            public final Solicitation solicitation = solicitations.get(0);
        };
        String json = objectMapper.writeValueAsString(itemSolicitation);

        mvc.perform(post("/item-solicitation/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    @Order(2)
    public void persistItemSolicitationWithDeduction() throws Exception {
        BigDecimal percentageDiscount = new BigDecimal(10);

        MvcResult result = mvc.perform(get("/product/")).andReturn();
        String content = result.getResponse().getContentAsString();
        List<Product> products = objectMapper.readValue(content, new TypeReference<List<Product>>() {});

        result = mvc.perform(get("/solicitation/")).andReturn();
        content = result.getResponse().getContentAsString();
        List<Solicitation> solicitations = objectMapper.readValue(content, new TypeReference<List<Solicitation>>() {});

        Object itemSolicitationWithDeduction = new Object() {
            public final BigDecimal valueDiscount = percentageDiscount;
            public final List<Product> productList = products;
            public final Solicitation solicitation = solicitations.get(0);
        };
        String json = objectMapper.writeValueAsString(itemSolicitationWithDeduction);

        mvc.perform(put("/solicitation/apply-discount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    @Order(3)
    public void findAllWithDeleteItemSolicitation() throws Exception {
        MvcResult result = mvc.perform(get("/item-solicitation/")).andReturn();
        String content = result.getResponse().getContentAsString();
        List<ItemSolicitation> itemSolicitations = objectMapper.readValue(content, new TypeReference<List<ItemSolicitation>>() {});

        if (!CollectionUtils.isEmpty(itemSolicitations) && itemSolicitations.size() > 0) {
            ItemSolicitation itemSolicitationToDelete = itemSolicitations.get(0);
            System.out.println("Deletando o itens solicitacao: UUID:" + itemSolicitationToDelete.getId());

            String json = objectMapper.writeValueAsString(itemSolicitationToDelete);
            mvc.perform(delete("/item-solicitation/deleteById/{id}", itemSolicitationToDelete.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .characterEncoding("utf-8"))
                    .andExpect(status().isOk())
                    .andReturn();
        }
    }

}
