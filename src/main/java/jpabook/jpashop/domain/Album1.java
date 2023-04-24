package jpabook.jpashop.domain;

import jpaBasic.Item1;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") //  구분자 값 명시 (기본은 엔티티명) , DTYPE 컬럼에 등록 될 구분자 값
public class Album1 extends Item {
    private String artist;
}
