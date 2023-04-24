package jpaBasic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team7 {
    @Id @GeneratedValue
    @Column(name="TEAM_ID")
    private Long id;
    private String name;


    /*********************************
     * 양방향 연관관계
     * mappedby의 설정은 반대편의 변수명을 아래와 같이 설정 한다.
     * -- Member10 선언부 : * private Team7 team;
     * @OneToMany(mappedBy = "team")
     *****************************/
    @OneToMany(mappedBy = "team")
    private List<Member10> members = new ArrayList<>();


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


    public List<Member10> getMembers() {
        return members;
    }

    public void setMembers(List<Member10> members) {
        this.members = members;
    }
}
