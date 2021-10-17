package br.com.saleback.controller;

import br.com.saleback.SaleBackApplication;
import br.com.saleback.model.Solicitation;
import br.com.saleback.model.SolicitationStatus;
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

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SaleBackApplication.class)
public class SolicitationControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    public void persistSolicitation() throws Exception {
        Object order = new Object() {
            public final String name = "PEDIDO " + new Date().getTime();
            public final SolicitationStatus status = SolicitationStatus.OPEN;
        };

        String json = objectMapper.writeValueAsString(order);

        mvc.perform(post("/solicitation/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Order(2)
    public void findAllWithEditSolicitation() throws Exception {
        MvcResult result = mvc.perform(get("/solicitation/")).andReturn();
        String content = result.getResponse().getContentAsString();
        List<Solicitation> solicitations = objectMapper.readValue(content, new TypeReference<List<Solicitation>>() {});

        if (!CollectionUtils.isEmpty(solicitations) && solicitations.size() > 0) {
            Solicitation solicitationToEdit = solicitations.get(0);
            System.out.println("Editando o pedido: " + solicitationToEdit.getName());

            String nameEdit = solicitationToEdit.getName();
            solicitationToEdit.setName(nameEdit + " - EDITADO");
            solicitationToEdit.setStatus(SolicitationStatus.CLOSE);

            String json = objectMapper.writeValueAsString(solicitationToEdit);

            mvc.perform(put("/solicitation/edit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .characterEncoding("utf-8"))
                    .andExpect(status().isOk())
                    .andReturn();
        }
    }

    @Test
    @Order(3)
    public void findAllWithDeleteSolicitation() throws Exception {
        MvcResult result = mvc.perform(get("/solicitation/")).andReturn();
        String content = result.getResponse().getContentAsString();
        List<Solicitation> solicitations = objectMapper.readValue(content, new TypeReference<List<Solicitation>>() {});

        if (!CollectionUtils.isEmpty(solicitations) && solicitations.size() > 0) {
            Solicitation solicitationToDelete = solicitations.get(0);
            System.out.println("Deletando o pedido: " + solicitationToDelete.getName() + " UUID:" + solicitationToDelete.getId());

            String json = objectMapper.writeValueAsString(solicitationToDelete);
            mvc.perform(delete("/solicitation/deleteById/{id}", solicitationToDelete.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .characterEncoding("utf-8"))
                    .andExpect(status().isOk())
                    .andReturn();
        }
    }

}
