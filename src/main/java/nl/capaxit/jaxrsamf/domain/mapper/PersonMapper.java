package nl.capaxit.jaxrsamf.domain.mapper;

import nl.capaxit.jaxrsamf.domain.Person;

import java.util.List;

/**
 * @author Jamie Craane
 */
public interface PersonMapper {
    Person retrieve(Long id);

    List<Person> retrievePersons();

    void create(Person person);

    Person update(Person person, Long id);

    void delete(Long id);
}
