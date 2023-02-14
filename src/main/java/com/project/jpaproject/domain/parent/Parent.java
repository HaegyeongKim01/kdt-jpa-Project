package com.project.jpaproject.domain.parent;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Parent {
    //반드시 ParentId에서 똑같은 식별자를 가지고 와야한다.
    @EmbeddedId   //ParentId로 바로 mapping가능
    private ParentId id;
}
