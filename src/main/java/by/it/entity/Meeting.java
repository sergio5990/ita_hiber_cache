package by.it.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@NoArgsConstructor
@Entity
@Cacheable
@Table(name = "MEETING")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Meeting implements Serializable {
    private static final long serialVersionUID = 8L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEETING_ID")
    private Long meetingId;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "DATE")
    private Date startDate;

    @ManyToMany(mappedBy = "meetings")
    private Set<Employee> employees = new HashSet<Employee>();

    public Meeting(String subject) {
        this.subject = subject;
        this.startDate = new Date();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;

        Meeting meeting = (Meeting) o;

        if (startDate != null ? !startDate.equals(meeting.startDate) : meeting.startDate != null) return false;
        if (meetingId != null ? !meetingId.equals(meeting.meetingId) : meeting.meetingId != null) return false;
        if (subject != null ? !subject.equals(meeting.subject) : meeting.subject != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = meetingId != null ? meetingId.hashCode() : 0;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        return result;
    }
}
