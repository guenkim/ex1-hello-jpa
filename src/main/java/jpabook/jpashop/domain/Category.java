package jpabook.jpashop.domain;

import net.bytebuddy.agent.builder.AgentBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category  extends BackBoneEntity{

    @Id @GeneratedValue
    private Long Id;

    private String name;

    /*******************************************************
     *• 가급적 지연 로딩만 사용(특히 실무에서) , 즉시로딩이 필요한 경우에만 별도로 설정
     * • 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
     * • 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.
     * • @ManyToOne, @OneToOne은 기본이 즉시 로딩  -> LAZY로 설정
     * • @OneToMany, @ManyToMany는 기본이 지연 로딩
     * member에게는 실제 entity ,팀에게는 proxy 객체를 줌, 사용 할 때만 db에게 쿼리 함
     *******************************************************/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent" , fetch = FetchType.LAZY)
    private List<Category> child = new ArrayList<>();



    @ManyToMany(fetch = FetchType.LAZY)
    //다대다관계 조인시 설정
    @JoinTable(name="CATEGORY_ITEM" ,joinColumns = @JoinColumn(name="CATEGORY_ID")
            ,inverseJoinColumns = @JoinColumn(name="ITEM_ID")
    )
    private List<Item> items = new ArrayList<>();

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getChild() {
        return child;
    }

    public void setChild(List<Category> child) {
        this.child = child;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
