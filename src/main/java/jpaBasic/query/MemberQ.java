package jpaBasic.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.BackBoneEntity;
import jpabook.jpashop.domain.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
/********************************************************
 JPQL - Named 쿼리
 Named 쿼리 - 정적 쿼리
 • 미리 정의해서 이름을 부여해두고 사용하는 JPQL
 • 정적 쿼리
 • 어노테이션, XML에 정의
 • 애플리케이션 로딩 시점에 초기화 후 재사용
 • 애플리케이션 로딩 시점에 쿼리를 검증
 ********************************************************/
@NamedQuery(
        name="MemberQ.findUser",
        query = "select m from MemberQ m where m.name = :name"
)
public class MemberQ{

    public MemberQ() {
    }

    public MemberQ(String name, int age, TeamQ team) {
        this.name = name;
        this.age = age;
        this.team = team;
    }

    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    private String name;
    private int age;

    /** 열거형 매핑
     * EnumType.ORDINAL: enum 순서를 데이터베이스에 저장
     * EnumType.STRING: enum 이름을 데이터베이스에 저장
     * Enumerate type의 경우 String으로 사용
     */
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TEAM_ID")
    private TeamQ team;


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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "MemberQ{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                 // ", team=" + team + >>>>>>>>>> 양방향 참조 시에 프린트하면 무한 루프 발생 주의
                '}';
    }

    public TeamQ getTeam() {
        return team;
    }

    public void setTeam(TeamQ team) {
        this.team = team;
        //양방향 연관관계 편의 메소드 (member에 team을 삽입했으면 team에게도 현행화 된 member를 추가해 준다.)
        team.getMemberList().add(this);
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }
}
