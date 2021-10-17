package br.com.saleback.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name="product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    @NotEmpty
    @Length(max = 50)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Boolean service;

    @NotNull
    @Column(nullable = false)
    private BigDecimal valor;

}
