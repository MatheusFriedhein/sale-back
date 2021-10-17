package br.com.saleback.model;

import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name="item_solicitation")
public class ItemSolicitation implements Serializable {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    @ManyToOne
    @Nullable
    @JoinColumn(name="id_product")
    private Product product;

    @ManyToOne
    @Nullable
    @JoinColumn(name="id_solicitation")
    private Solicitation solicitation;

    public ItemSolicitation() { }
}
