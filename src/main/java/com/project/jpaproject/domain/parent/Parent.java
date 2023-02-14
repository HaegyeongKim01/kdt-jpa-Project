package com.project.jpaproject.domain.parent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@IdClass(ParentId.class)
public class Parent {
    //반드시 ParentId에서 똑같은 식별자를 가지고 와야한다.
    @Id
    private String id1;

    @Id
    private String id2;
}
