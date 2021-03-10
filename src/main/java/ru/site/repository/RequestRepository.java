package ru.site.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.site.model.Request;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {
}
