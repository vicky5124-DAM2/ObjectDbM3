package pe1.uf6.m3.dam2;

import javax.jdo.annotations.Index;
import javax.jdo.annotations.Indices;
import javax.persistence.*;

@Entity
@Indices({
        @Index(members = {"student", "course"}, unique = "true")
})
//@NamedQuery(name = "Enrollment.findByStudent", query = "SELECT e FROM Enrollment e WHERE e.student = :student ORDER BY e.course.fullname")
@NamedQuery(name = "Enrollment.findByStudent", query = "SELECT e FROM Enrollment e JOIN e.course c WHERE e.student = :student ORDER BY c.fullname")
public class Enrollment {
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Basic(optional = false)
    private Student student;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Basic(optional = false)
    private Course course;
    private Double grade;

    public Enrollment(Student student, Course course) {
        super();
        this.student = student;
        this.course = course;
        this.grade = null;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        if (student == null) return;

        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        if (course == null) return;

        this.course = course;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }
}
