package jpaBasic.query;

import javax.persistence.*;

@Entity
@Table(name="ORDERQ")
public class OrderQ {

    @Id @GeneratedValue
    @Column(name="ORDER_ID")
    private Long id;
    private int orderAmount;
    @Embedded
    private AddressQ addressQ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PRODUCT_ID")
    private ProductQ productQ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public AddressQ getAddressQ() {
        return addressQ;
    }

    public void setAddressQ(AddressQ addressQ) {
        this.addressQ = addressQ;
    }

    public ProductQ getProductQ() {
        return productQ;
    }

    public void setProductQ(ProductQ productQ) {
        this.productQ = productQ;
    }
}
