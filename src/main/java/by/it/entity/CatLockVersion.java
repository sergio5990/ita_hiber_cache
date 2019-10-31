package by.it.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class CatLockVersion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String owner;
    @Version
    private Integer version;
}

