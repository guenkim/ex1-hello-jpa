package jpaBasic.query;

import jpabook.jpashop.domain.Member;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TeamQ {

    @Id @GeneratedValue
    private Long id;
    private String name;


    @BatchSize(size=100)
    @OneToMany(mappedBy = "team" , fetch = FetchType.LAZY)
    private List<MemberQ> memberList = new ArrayList<>();

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

    public List<MemberQ> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberQ> memberList) {
        this.memberList = memberList;
    }
}
