package jpaBasic;

import javax.persistence.*;

@Entity
public class Member11 {

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
    

    /*******************************************************
     *• 가급적 지연 로딩만 사용(특히 실무에서) , 즉시로딩이 필요한 경우에만 별도로 설정
     * • 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
     * • 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.
     * • @ManyToOne, @OneToOne은 기본이 즉시 로딩  -> LAZY로 설정
     * • @OneToMany, @ManyToMany는 기본이 지연 로딩
     * member에게는 실제 entity ,팀에게는 proxy 객체를 줌, 사용 할 때만 db에게 쿼리 함
     *******************************************************/
    @ManyToOne(fetch = FetchType.LAZY)
    //즉시로딩 선언
    //주인, 참조 객체 모두 entity로 돌려줌 , join 쿼리 실행
    //@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEAM_ID")
    private Team8 team;  /** >>>>>> 연관관계 주인 **/

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

    public Team8 getTeam() {
        return team;
    }

    public void setTeam(Team8 team) {
        this.team = team;
        // 역방향에도 값을 세팅 해 준다.
        team.getMembers().add(this);

    }
}
