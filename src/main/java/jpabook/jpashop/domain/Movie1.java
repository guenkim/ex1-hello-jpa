package jpabook.jpashop.domain;

import jpaBasic.Item1;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M") //  구분자 값 명시 (기본은 엔티티명) , DTYPE 컬럼에 등록 될 구분자 값
public class Movie1 extends Item {
    private String director;

    private String actor;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }


}
