package ch.admin.wbf.isceco.jakartadata.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {

  @Id
  private String id;

}
