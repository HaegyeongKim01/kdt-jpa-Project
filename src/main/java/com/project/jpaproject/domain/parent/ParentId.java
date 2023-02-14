package com.project.jpaproject.domain.parent;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor   //기본 생성자
@AllArgsConstructor  //모든 인자 구현되어있는 생성자
public class ParentId implements Serializable {
    private String id1;
    private String id2;
}
