package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*******************************************************
 @MappedSuperclass : 예제 BaseEntity class
 • 공통 매핑 정보가 필요할 때 사용(id, name)
 상속관계 매핑X
 • 엔티티X, 테이블과 매핑X
 • 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공
 • 조회, 검색 불가(em.find(BaseEntity) 불가)
 • 직접 생성해서 사용할 일이 없으므로 추상 클래스 권장
 ******************************************************/
@Entity
//@Inheritance(strategy = InheritanceType.JOINED) // 조인 테이블 전략 : 테이블 분리
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 싱글 테이블 전략 : 단일 테이블
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 구현 클래스마다 테이블 전략
@DiscriminatorColumn(name="DTYPE")
public abstract class Item extends BackBoneEntity {
    @Id @GeneratedValue
    @Column(name="ITEM_ID")
    private Long Id;

    private String name;
    private int price;

    private int stockQuantity;


    /*******************************************************
     *• 가급적 지연 로딩만 사용(특히 실무에서) , 즉시로딩이 필요한 경우에만 별도로 설정
     * • 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
     * • 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.
     * • @ManyToOne, @OneToOne은 기본이 즉시 로딩  -> LAZY로 설정
     * • @OneToMany, @ManyToMany는 기본이 지연 로딩
     * member에게는 실제 entity ,팀에게는 proxy 객체를 줌, 사용 할 때만 db에게 쿼리 함
     *******************************************************/
    @ManyToMany(mappedBy = "items",fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }


    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
