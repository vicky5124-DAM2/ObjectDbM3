package pe1.uf6.m3.dam2;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s ORDER BY s.username"),
        @NamedQuery(name = "Student.findById", query = "SELECT s FROM Student s WHERE s.id = :id"),
})
public class Student extends User {
    public Student(int id, String username, String firstname, String lastname, String email, String city, Date lastlogin) {
        super(id, username, firstname, lastname, email, city, lastlogin);
    }
}
