package jpaBasic;

import javax.persistence.*;

@Entity
@Table(name="ADDRESS")
public class AddressEntity {

    public AddressEntity(String city, String street, String address) {
        this.address = new Address(city,street,address);
    }

    @Id @GeneratedValue
    private Long id;

    //embedded type 적용
    //• @Embeddable: 값 타입을 정의하는 곳에 표시
    //• @Embedded: 값 타입을 사용하는 곳에 표시
    @Embedded
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
