package br.com.saleback.service;

import br.com.saleback.dao.SolicitationDAO;
import br.com.saleback.dto.ItemSolicitationDTO;
import br.com.saleback.model.ItemSolicitation;
import br.com.saleback.model.Product;
import br.com.saleback.model.Solicitation;
import br.com.saleback.repository.ItemSolicitationRepository;
import br.com.saleback.repository.SolicitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class SolicitationService {

    @Autowired
    private SolicitationDAO solicitationDAO;

    @Autowired
    private SolicitationRepository solicitationRepository;

    @Autowired
    private ItemSolicitationRepository itemSolicitationRepository;

    public Solicitation save(Solicitation solicitation) {
        return this.solicitationRepository.save(solicitation);
    }

    public void deleteById(Long idOrder) {
        this.solicitationRepository.deleteById(idOrder);
    }

    public List<Solicitation> findAll() {
        return this.solicitationRepository.findAll();
    }

    public Solicitation findById(UUID id) {
        return this.solicitationRepository.findById(id);
    }

    public String deleteById(UUID idSolicitation) {
        return this.solicitationDAO.deleteById(idSolicitation);
    }

    public void saveItemsApplyDiscount(ItemSolicitationDTO itemSolicitationDTO) {
        if (itemSolicitationDTO != null && !CollectionUtils.isEmpty(itemSolicitationDTO.getProductList())) {

            BigDecimal value = BigDecimal.ZERO;
            BigDecimal valueItemsProduct = BigDecimal.ZERO;

            for (Product product : itemSolicitationDTO.getProductList()) {
                if (!product.getService()) {
                    valueItemsProduct = valueItemsProduct.add(product.getValor());
                } else {
                    value = value.add(product.getValor());
                }
            }

            BigDecimal valueWithDiscount = calculateDiscount(itemSolicitationDTO.getValueDiscount(), valueItemsProduct);
            valueWithDiscount = valueWithDiscount.add(value);
            value = value.add(valueItemsProduct);

            Solicitation solicitation = itemSolicitationDTO.getSolicitation();
            solicitation.setValueWithDiscount(valueWithDiscount);
            solicitation.setValue(value);

            solicitationRepository.save(solicitation);

            for (Product product : itemSolicitationDTO.getProductList()) {
                ItemSolicitation itemSolicitation = new ItemSolicitation();
                itemSolicitation.setSolicitation(solicitation);
                itemSolicitation.setProduct(product);
                itemSolicitationRepository.save(itemSolicitation);
            }
        }
    }

    protected BigDecimal calculateDiscount(BigDecimal percentage, BigDecimal valueItemsProduct) {
        return valueItemsProduct.subtract(percentage.divide(new BigDecimal(100)).multiply(valueItemsProduct));
    }

}
