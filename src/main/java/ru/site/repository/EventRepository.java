package ru.site.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.site.model.Event;

@Repository
public interface EventRepository extends CrudRepository<Event,Long> {
}
