package com.project.jpaproject.domain.parent;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor   //기본 생성자
@AllArgsConstructor  //모든 인자 구현되어있는 생성자
@Embeddable
public class ParentId implements Serializable {
    private String id1;
    private String id2;
}
