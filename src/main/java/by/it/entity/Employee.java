package by.it.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name="EMPLOYEE")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "EMPLOYEE_ID", unique = true)
    private Long employeeId;
    @Column(name = "FIRST_NAME")
    private String firstname;
    @Column(name = "LAST_NAME")
    private String lastname;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @OneToOne(mappedBy = "employee", cascade = CascadeType.PERSIST)
    private EmployeeDetail employeeDetail;
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "EMPLOYEE_MEETING", joinColumns = {@JoinColumn(name = "EMPLOYEE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "MEETING_ID")}
    )
    private Set<Meeting> meetings = new HashSet<>(0);
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (employeeId != null ? !employeeId.equals(employee.employeeId) : employee.employeeId != null) return false;
        if (firstname != null ? !firstname.equals(employee.firstname) : employee.firstname != null) return false;
        if (lastname != null ? !lastname.equals(employee.lastname) : employee.lastname != null) return false;
        return date != null ? date.equals(employee.date) : employee.date == null;
    }
    @Override
    public int hashCode() {
        int result = employeeId != null ? employeeId.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}

