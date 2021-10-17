package br.com.saleback.service;

import br.com.saleback.dao.ItemSolicitationDAO;
import br.com.saleback.model.ItemSolicitation;
import br.com.saleback.repository.ItemSolicitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemSolicitationService {

    @Autowired
    private ItemSolicitationDAO itemSolicitationDAO;

    @Autowired
    private ItemSolicitationRepository itemSolicitationRepository;

    public ItemSolicitation save (ItemSolicitation itemSolicitation) {
        return this.itemSolicitationRepository.save(itemSolicitation);
    }

    public String deleteById (UUID idItemOrder) { return this.itemSolicitationDAO.deleteById(idItemOrder); }

    public List<ItemSolicitation> findAll () {
        return this.itemSolicitationRepository.findAll();
    }

    public ItemSolicitation findById (UUID id) {
        return this.itemSolicitationRepository.findById(id);
    }

}
