package jpaBasic;

import javax.persistence.*;

@Entity
public class Member2 {

    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    private String name;

    //객체 참조를 위하여 아래 코드 주석 처리
    //@Column(name="TEAM_ID")
    //private Long teamId;



    //객체 참조
    //member 입장에서 team과의 다양성은 다대일
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team2 team;

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

    /*
    public Long getTeamId() {
        return teamId;
    }
    */
    /*
    public void setTeamId(Long teamId) {
        this.teamId = teamId;

    }
    */

    public Team2 getTeam() {
        return team;
    }

    public void setTeam(Team2 team) {
        this.team = team;
    }
}
