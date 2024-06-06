package pe1.uf6.m3.dam2;

import javax.jdo.annotations.Index;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public abstract class User {

    @Index(unique = "true")
    private int id;
    @Index(unique = "true")
    @Basic(optional = false)
    private String username;
    @Basic(optional = false)
    private String firstname;
    @Basic(optional = false)
    private String lastname;
    @Index(unique = "true")
    @Basic(optional = false)
    private String email;
    private String city;
    private Date lastlogin;

    public User(int id, String username, String firstname, String lastname, String email, String city, Date lastlogin) {
        super();
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.city = city;
        this.lastlogin = lastlogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    @Override
    public int hashCode() {
        return id % 33;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return id == other.id;
    }

}
