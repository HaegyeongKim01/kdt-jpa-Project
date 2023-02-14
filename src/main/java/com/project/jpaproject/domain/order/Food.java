package com.project.jpaproject.domain.order;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("FOOD")
public class Food extends Item {
    private String chef;

}
