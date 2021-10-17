package br.com.saleback.service;

import br.com.saleback.dao.ProductDAO;
import br.com.saleback.model.Product;
import br.com.saleback.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductRepository productRepository;

    public Product save (Product product) {
        return this.productRepository.save(product);
    }

    public String deleteById (UUID idProduct) {
        return this.productDAO.deleteById(idProduct);
    }

    public List<Product> findAll () {
        return this.productRepository.findAll();
    }

    public Product findById (UUID id) {
        return this.productRepository.findById(id);
    }

}
