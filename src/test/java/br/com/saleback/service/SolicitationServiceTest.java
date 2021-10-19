package br.com.saleback.service;

import br.com.saleback.dto.ItemSolicitationDTO;
import br.com.saleback.model.Product;
import br.com.saleback.model.Solicitation;
import br.com.saleback.repository.ItemSolicitationRepository;
import br.com.saleback.repository.SolicitationRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SolicitationServiceTest {

    @InjectMocks
    @Spy
    private SolicitationService solicitationService;

    @Mock
    private SolicitationRepository solicitationRepository;

    @Mock
    private ItemSolicitationRepository itemSolicitationRepository;

    @Test
    public void testCalculateDiscount () {
        BigDecimal percentage = new BigDecimal(15);
        BigDecimal valueItemsProduct = new BigDecimal(200);

        BigDecimal valueWithDiscount = solicitationService.calculateDiscount(percentage, valueItemsProduct);

        Assert.assertEquals(valueWithDiscount, new BigDecimal(170).setScale(2));
    }

    @Test
    public void testApplyDiscount () {
        ItemSolicitationDTO itemSolicitationDTO = new ItemSolicitationDTO();
        itemSolicitationDTO.setValueDiscount(new BigDecimal(50));

        List<Product> productList = new ArrayList<>();

        Product product = new Product();
        product.setName("PRODUTO A");
        product.setValor(new BigDecimal(100));
        product.setService(Boolean.FALSE);
        productList.add(product);

        Product service = new Product();
        service.setName("SERVICE B");
        service.setValor(new BigDecimal(200));
        service.setService(Boolean.TRUE);
        productList.add(service);

        Solicitation solicitation = new Solicitation();
        solicitation.setName("PEDIDO A");

        itemSolicitationDTO.setSolicitation(solicitation);
        itemSolicitationDTO.setProductList(productList);

        solicitationService.saveItemsApplyDiscount(itemSolicitationDTO);

        verify(itemSolicitationRepository, times(2)).save(anyObject());
    }

}
