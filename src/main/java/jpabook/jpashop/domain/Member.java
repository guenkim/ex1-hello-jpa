package jpabook.jpashop.domain;

import javax.persistence.*;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BackBoneEntity{

    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    private String name;


    /******************************************
     - 임베디드 타입(embedded type, 복합 값 타입)
     임베디드 타입 사용법
     • @Embeddable: 값 타입을 정의하는 곳에 표시
     • @Embedded: 값 타입을 사용하는 곳에 표시
     • 기본 생성자 필수
     • 불변객체화 시켜야 됨
     불변 객체
     • 객체 타입을 수정할 수 없게 만들면 부작용을 원천 차단
     • 값 타입은 불변 객체(immutable object)로 설계해야함
     • 불변 객체: 생성 시점 이후 절대 값을 변경할 수 없는 객체
     • 생성자로만 값을 설정하고 수정자(Setter)를 만들지 않으면 됨
     • 참고: Integer, String은 자바가 제공하는 대표적인 불변 객체
     *****************************************/
// • @Embeddable: 값 타입을 정의하는 곳에 표시
// • @Embedded: 값 타입을 사용하는 곳에 표시
    @Embedded
    private Address address;

    /*******************************************************
     *• 가급적 지연 로딩만 사용(특히 실무에서) , 즉시로딩이 필요한 경우에만 별도로 설정
     * • 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
     * • 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.
     * • @ManyToOne, @OneToOne은 기본이 즉시 로딩  -> LAZY로 설정
     * • @OneToMany, @ManyToMany는 기본이 지연 로딩
     * member에게는 실제 entity ,팀에게는 proxy 객체를 줌, 사용 할 때만 db에게 쿼리 함
     *******************************************************/
    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
