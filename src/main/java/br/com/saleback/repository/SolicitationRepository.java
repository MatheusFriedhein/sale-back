package br.com.saleback.repository;

import br.com.saleback.model.Solicitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SolicitationRepository extends JpaRepository<Solicitation, Long> {

    @Query(value = "SELECT * FROM public.order WHERE id = :id ", nativeQuery = true)
    Solicitation findById(@Param("id") UUID id);

}
