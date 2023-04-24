package jpaBasic;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") //  구분자 값 명시 (기본은 엔티티명) , DTYPE 컬럼에 등록 될 구분자 값
public class Book extends Item1 {


    private String author;
    private String isbn;
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
