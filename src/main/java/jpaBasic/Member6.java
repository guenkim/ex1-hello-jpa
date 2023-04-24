package jpaBasic;

import javax.persistence.*;

@Entity
public class Member6 {

    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
