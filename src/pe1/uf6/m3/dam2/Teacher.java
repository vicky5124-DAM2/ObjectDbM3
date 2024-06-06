package pe1.uf6.m3.dam2;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;

@Entity
@NamedQueries({
        @NamedQuery(name = "Teacher.findAll", query = "SELECT t FROM Teacher t ORDER BY t.username"),
        @NamedQuery(name = "Teacher.findById", query = "SELECT t FROM Teacher t WHERE t.id = :id"),
})
public class Teacher extends User {
    @ManyToMany(fetch = FetchType.LAZY)
    private final LinkedList<Course> courses;

    public Teacher(int id, String username, String firstname, String lastname, String email, String city, Date lastLogin) {
        super(id, username, firstname, lastname, email, city, lastLogin);

        this.courses = new LinkedList<>();
    }

    public void addCourse(Course course) {
        if (course == null) return;

        this.courses.add(course);
        if (!course.hasTeacher(this)) course.addTeacher(this);
    }

    public void removeCourse(Course course) {
        if (course == null) return;

        this.courses.remove(course);
        if (course.hasTeacher(this)) course.removeTeacher(this);
    }

    public boolean hasCourse(Course course) {
        if (course == null) return false;

        return this.courses.contains(course);
    }

    public LinkedList<Course> getCourses() {
        return courses;
    }
}
