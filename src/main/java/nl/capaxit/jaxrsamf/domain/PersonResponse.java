package nl.capaxit.jaxrsamf.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Insert documentation here.
 *
 * @author jcraane
 */
@XmlRootElement(name = "persons")
public class PersonResponse {
    private List<Person> persons;

    @XmlElement(name = "person")
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(final List<Person> persons) {
        this.persons = persons;
    }
}
