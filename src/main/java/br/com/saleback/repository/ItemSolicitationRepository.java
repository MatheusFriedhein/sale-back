package br.com.saleback.repository;

import br.com.saleback.model.ItemSolicitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemSolicitationRepository extends JpaRepository<ItemSolicitation, Long>  {

    @Query(value = "SELECT * FROM public.item_order WHERE id = :id ", nativeQuery = true)
    ItemSolicitation findById(@Param("id") UUID id);

}
