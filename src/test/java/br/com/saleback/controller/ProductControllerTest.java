package br.com.saleback.controller;

import br.com.saleback.SaleBackApplication;
import br.com.saleback.model.Product;
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
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SaleBackApplication.class)
public class ProductControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    public void persistProduct() throws Exception {
        Object order = new Object() {
            public final String name = "PRODUTO " + new Date().getTime();
            public final Boolean service = Boolean.FALSE;
            public final BigDecimal valor = new BigDecimal(100);
        };

        String json = objectMapper.writeValueAsString(order);

        mvc.perform(post("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Order(2)
    public void findAllWithEditProduct() throws Exception {
        MvcResult result = mvc.perform(get("/product/")).andReturn();
        String content = result.getResponse().getContentAsString();
        List<Product> products = objectMapper.readValue(content, new TypeReference<List<Product>>() {});

        if (!CollectionUtils.isEmpty(products) && products.size() > 0) {
            Product productToEdit = products.get(0);
            System.out.println("Editando o produto: " + productToEdit.getName());

            String nameEdit = productToEdit.getName();
            productToEdit.setName(nameEdit + " - EDITADO");

            String json = objectMapper.writeValueAsString(productToEdit);

            mvc.perform(put("/product/edit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .characterEncoding("utf-8"))
                    .andExpect(status().isOk())
                    .andReturn();
        }
    }

    @Test
    @Order(3)
    public void findAllWithDeleteProduct() throws Exception {
        MvcResult result = mvc.perform(get("/product/")).andReturn();
        String content = result.getResponse().getContentAsString();
        List<Product> products = objectMapper.readValue(content, new TypeReference<List<Product>>() {});

        if (!CollectionUtils.isEmpty(products) && products.size() > 0) {
            Product productToDelete = products.get(0);
            System.out.println("Deletando o produto: " + productToDelete.getName() + " UUID:" + productToDelete.getId());

            String json = objectMapper.writeValueAsString(productToDelete);
            mvc.perform(delete("/product/deleteById/{id}", productToDelete.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .characterEncoding("utf-8"))
                    .andExpect(status().isOk())
                    .andReturn();
        }
    }

}