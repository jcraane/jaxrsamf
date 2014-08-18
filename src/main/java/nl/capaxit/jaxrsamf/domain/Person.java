package nl.capaxit.jaxrsamf.domain;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Jamie Craane
 */
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {
    private Long id;

    private Link details;

    @XmlElement(required = true)
    private String firstName;

    @XmlElement(required = true)
    private String lastName;

    private Date birthDate;

    public static Person create(final Long id, final String firstName, final String lastName, final Date birthDate) {
        Person person = new Person();
        person.id = id;
        person.firstName = firstName;
        person.lastName = lastName;
        person.birthDate = birthDate;
        return person;
    }

    public void merge(final Person person) {
        this.firstName = person.firstName;
        this.lastName = person.lastName;
    }

    public Link getDetails() {
        return details;
    }

    public void setDetails(final Link details) {
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Person)) {
            return false;
        }

        Person other = (Person) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
            .add("id", id)
            .add("firstName", firstName)
            .add("lastName", lastName)
            .add("birthDate", birthDate)
            .toString();
    }
}
