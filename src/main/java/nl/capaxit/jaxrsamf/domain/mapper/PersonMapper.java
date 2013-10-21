package nl.capaxit.jaxrsamf.domain.mapper;

import nl.capaxit.jaxrsamf.domain.Person;

import java.util.List;

/**
 * @author Jamie Craane
 */
public interface PersonMapper {
    List<Person> retrievePersons();
}
