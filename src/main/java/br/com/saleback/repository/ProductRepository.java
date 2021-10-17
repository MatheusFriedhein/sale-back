package br.com.saleback.repository;

import br.com.saleback.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM public.product WHERE id = :id ", nativeQuery = true)
    Product findById(@Param("id") UUID id);

}
