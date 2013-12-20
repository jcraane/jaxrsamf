package nl.capaxit.jaxrsamf.domain.mapper;

import nl.capaxit.jaxrsamf.domain.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jcraane on 23-11-13.
 */
@Component("inMemoryPersonMapper")
public class InMemoryPersonMapper implements PersonMapper {
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private static final Map<Long, Person> STORE = new HashMap();

    {
        long id = ID_GENERATOR.incrementAndGet();
        STORE.put(id, Person.create(id, "John", "Smith", new Date()));
        id = ID_GENERATOR.incrementAndGet();
        STORE.put(id, Person.create(id, "John", "Stuart", new Date()));
    }

    @Override
    public Person retrieve(Long id) {
        return STORE.get(id);
    }

    @Override
    public List<Person> retrievePersons() {
        return new ArrayList(STORE.values());
    }

    @Override
    public void create(final Person person) {
        person.setId(ID_GENERATOR.incrementAndGet());
        STORE.put(person.getId(), person);
    }

    @Override
    public Person update(final Person update, final Long id) {
        Person person = STORE.get(id);
        if (person != null) {
            person.merge(update);
        }

        return person;
    }

    @Override
    public void delete(final Long id) {
        STORE.remove(id);
    }
}
