package jpaBasic;

import javax.persistence.*;

@Entity
public class Member3 {

    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    private String name;

    //객체 참조를 위하여 아래 코드 주석 처리
    //@Column(name="TEAM_ID")
    //private Long teamId;



    //객체 참조
    //member 입장에서 team과의 다양성은 다대일
    /**
     Team3와 연관관계 매핑
     연관관계의 주인: 다대일 입장에서 다(N)쪽에 연관관계 주인 설정
     외래키가 있는곳을 주인으로 정한다.
     반대편(TEAM3)은 mappedby로 주인을 타게팅 한다.
     - Team3 선언부
     @OneToMany(mappedBy = "team")
     private List<Member3> members = new ArrayList<>();
     */
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team3 team;  /** >>>>>> 연관관계 주인 **/

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

    public Team3 getTeam() {
        return team;
    }

    public void setTeam(Team3 team) {
        this.team = team;
    }
}
