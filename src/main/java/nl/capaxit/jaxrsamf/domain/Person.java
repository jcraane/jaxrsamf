package nl.capaxit.jaxrsamf.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Jamie Craane
 */
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {
    private Long id;

    @XmlElement(required = true)
    private String firstName;

    @XmlElement(required = true)
    private String lastName;

    public static Person create(final Long id, final String firstName, final String lastName) {
        Person person = new Person();
        person.setId(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }

    public void merge(final Person person) {
        this.firstName = person.firstName;
        this.lastName = person.lastName;
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
}
