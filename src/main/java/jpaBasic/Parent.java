package jpaBasic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    /**
     CASCADE의 종류
     *   ALL: 모두 적용 > 사용 해 볼만함
     *   PERSIST: 영속 > 사용 해 볼만함
     * • REMOVE: 삭제
     * • MERGE: 병합
     * • REFRESH: REFRESH
     * • DETACH: DETACH
     * -- 고아 객체 제거: 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제 (가능하면 쓰지말자)
     *  orphanRemoval = true (가능하면 쓰지말자)
     **/
    @OneToMany(mappedBy = "parent" , cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Child> childList = new ArrayList<>();

    /**************************
     * 연관관꼐 편의 메소드
     * 아래 코드와 같이 양방향 관계 존재시 객체를 삽입한다.
     */
    public void addChild(Child child){
        childList.add(child);
        child.setParent(this);
    }

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

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
