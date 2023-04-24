package jpaBasic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team6 {
    @Id @GeneratedValue
    @Column(name="TEAM_ID")
    private Long id;
    private String name;


    /*********************************
     * 양방향 연관관계
     * mappedby의 설정은 반대편의 변수명을 아래와 같이 설정 한다.
     * -- Member3 선언부 : * private Team3 team;
     * @OneToMany(mappedBy = "team")
     *****************************/
    @OneToMany
    @JoinColumn(name="TEAM_ID")
    private List<Member6> members = new ArrayList<>();


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


    public List<Member6> getMembers() {
        return members;
    }

    public void setMembers(List<Member6> members) {
        this.members = members;
    }
}
