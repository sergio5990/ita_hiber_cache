package by.it.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMPLOYEE_DETAILS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "one-one",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "employee"))
    @GeneratedValue(generator = "one-one")
    @Column(name = "EMPLOYEE_ID")
    private Long id;

    @Column(name = "STREET")
    private String street;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "COUNTRY")
    private String country;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Employee employee;
}
