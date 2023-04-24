package jpaBasic.query;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class AddressQ {
    public AddressQ() {
    }

    public AddressQ(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    private String city;
    private String street;
    private String zipcode;

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressQ addressQ = (AddressQ) o;
        return Objects.equals(getCity(), addressQ.getCity()) && Objects.equals(getStreet(), addressQ.getStreet()) && Objects.equals(getZipcode(), addressQ.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipcode());
    }
}
