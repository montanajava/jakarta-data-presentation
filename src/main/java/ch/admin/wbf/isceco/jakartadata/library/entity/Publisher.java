package ch.admin.wbf.isceco.jakartadata.library.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Publisher {

    @Id
    private long id;

    @Basic(optional = false)
    private String name;

    @OneToMany(mappedBy = Book_.PUBLISHER)
    private Set<Book> books;

}
