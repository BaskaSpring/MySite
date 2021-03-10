package ru.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.site.model.Event;

import java.util.Date;
import java.util.List;

@Repository
public interface EventCustomRepository extends JpaRepository<Event,Long> {
    @Query("Select e from Event as e where e.date = :date")
    List<Event> FindByDate (@Param("date")Date date);

}
