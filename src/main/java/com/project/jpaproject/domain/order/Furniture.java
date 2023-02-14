package com.project.jpaproject.domain.order;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("Furniture")
@Entity
public class Furniture extends Item{
    private int width;
    private int height;
}
