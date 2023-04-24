package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class OrderItem extends BackBoneEntity{

    @Id @GeneratedValue
    @Column(name="ORDER_ITEM_ID")
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
    @JoinColumn(name="ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ITEM_ID")
    private Item item;

    private int orderPrice;

    private int count;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }



}
