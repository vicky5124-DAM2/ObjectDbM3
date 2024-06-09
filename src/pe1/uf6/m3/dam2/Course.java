package pe1.uf6.m3.dam2;

import javax.jdo.annotations.Index;
import javax.jdo.annotations.Indices;
import javax.jdo.annotations.Unique;
import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;

@Entity
@Indices({
        @Index(members = {"id"}, unique = "true")
})
@NamedQueries({
        @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c ORDER BY c.fullname"),
        @NamedQuery(name = "Course.findById", query = "SELECT c FROM Course c WHERE c.id = :id"),
})
public class Course {
    private static final double MIN_GRADE = 0.0;
    private static final double MAX_GRADE = 10.0;

    @Index(unique = "true")
    @Unique
    private int id;
    @Basic(optional = false)
    private String fullname;
    @Basic(optional = false)
    private String shortname;
    @Basic(optional = false)
    private Date startdate;
    @Basic(optional = false)
    private String category;

    @OneToMany(cascade = CascadeType.ALL)
    private LinkedList<Enrollment> enrolled;
    @OneToMany(cascade = CascadeType.ALL)
    private LinkedList<Teacher> teachers;

    public Course(int id, String fullname, String shortname, Date startdate, String category) {
        super();
        this.id = id;
        this.fullname = fullname;
        this.shortname = shortname;
        this.startdate = startdate;
        this.category = category;
        this.enrolled = new LinkedList<>();
        this.teachers = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void addTeacher(Teacher teacher) {
        if (teacher == null) return;

        this.teachers.add(teacher);
        if (!teacher.hasCourse(this)) teacher.addCourse(this);
    }

    public void removeTeacher(Teacher teacher) {
        if (teacher == null) return;

        this.teachers.remove(teacher);

        if (teacher.hasCourse(this)) teacher.removeCourse(this);
    }

    public boolean hasTeacher(Teacher teacher) {
        if (teacher == null) return false;

        return this.teachers.contains(teacher);
    }

    public LinkedList<Teacher> getTeachers() {
        return teachers;
    }

    public void addStudent(Student student) {
        if (student == null) return;

        this.enrolled.add(new Enrollment(student, this));
    }

    public void removeStudent(Student student) {
        if (student == null) return;

        Enrollment enrollment = this.findStudentEnrollment(student);

        if (enrollment != null) this.enrolled.remove(enrollment);
    }

    public void gradeStudent(Student student, double grade) {
        if (student == null) return;
        if (grade < MIN_GRADE || grade > MAX_GRADE) return;

        Enrollment enrollment = this.findStudentEnrollment(student);

        if (enrollment != null) enrollment.setGrade(grade);
    }

    private Enrollment findStudentEnrollment(Student student) {
        if (student == null) return null;

        for (Enrollment enrollment : enrolled) {
            if (student.equals(enrollment.getStudent())) return enrollment;
        }
        return null;
    }

    public LinkedList<Student> getStudents() {
        LinkedList<Student> students = new LinkedList<>();

        for (Enrollment enrollment : enrolled) {
            students.add(enrollment.getStudent());
        }

        return students;
    }

    @Override
    public int hashCode() {
        return id % 11;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Course other = (Course) obj;

        return id == other.id;
    }

}
