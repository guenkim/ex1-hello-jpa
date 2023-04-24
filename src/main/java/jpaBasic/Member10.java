package jpaBasic;

import javax.persistence.*;

@Entity
public class Member10 {

    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    private String name;


    //객체 참조
    //member 입장에서 team과의 다양성은 다대일
    /**
     Team7와 연관관계 매핑
     연관관계의 주인: 다대일 입장에서 다(N)쪽에 연관관계 주인 설정
     외래키가 있는곳을 주인으로 정한다.
     반대편(TEAM3)은 mappedby로 주인을 타게팅 한다.
     - Team3 선언부
     @OneToMany(mappedBy = "team")
     private List<Member3> members = new ArrayList<>();
     */
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team7 team;  /** >>>>>> 연관관계 주인 **/

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

    public Team7 getTeam() {
        return team;
    }

    public void setTeam(Team7 team) {
        this.team = team;
        // 역방향에도 값을 세팅 해 준다.
        team.getMembers().add(this);

    }
}
