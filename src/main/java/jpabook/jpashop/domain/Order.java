package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ORDERS")
public class Order extends BackBoneEntity{

    @Id @GeneratedValue
    @Column(name="ORDER_ID")
    private Long Id;

    /*******************************************************
     *• 가급적 지연 로딩만 사용(특히 실무에서) , 즉시로딩이 필요한 경우에만 별도로 설정
     * • 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
     * • 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.
     * • @ManyToOne, @OneToOne은 기본이 즉시 로딩  -> LAZY로 설정
     * • @OneToMany, @ManyToMany는 기본이 지연 로딩
     * member에게는 실제 entity ,팀에게는 proxy 객체를 줌, 사용 할 때만 db에게 쿼리 함
     *******************************************************/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;


/******************************************
 * -- 영속성 전이
 *      • 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속
 *          상태로 만들도 싶을 때
 *      • 예: 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장.
 *      Parent객체에 아래와 같이 선언
 *      (cascade = CascadeType.ALL)
 *
 *      CASCADE의 종류
 *      • ALL: 모두 적용 > 사용 해 볼만함
 *      • PERSIST: 영속 > 사용 해 볼만함
 *      • REMOVE: 삭제
 *      • MERGE: 병합
 *      • REFRESH: REFRESH
 *      • DETACH: DETACH
 ************************************************************/
    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name="DELIVER_ID")
    private Delivery delivery;



    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<OrderItem> orderItem = new ArrayList<>();


    //LocalDateTime은 DB와 자동 매핑 된다.
    private LocalDateTime orderDate;

    //enum type은 반드시 EnumType.STRING 타입으로
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }


    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public List<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }
}
