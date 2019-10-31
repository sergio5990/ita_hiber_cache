package by.it.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@NoArgsConstructor
@Entity
@Table(name = "DEPARTMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID", unique = true)
    private Long departmentId;

    @Column(name = "NAME")
    private String departmentName;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>(0);

    public Department(String name) {
        this.departmentName = name;
    }

    @Override
    public String toString() {
        return "Department{ departmentId=" + departmentId +
                ", departmentName='" + departmentName + '}';
    }

    @Override
    public int hashCode() {
        int result = departmentId != null ? departmentId.hashCode() : 0;
        result = 31 * result + (departmentName != null ? departmentName.hashCode() : 0);
        return result;
    }
}
