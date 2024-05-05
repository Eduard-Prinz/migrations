package hw.prince.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import hw.prince.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> getByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}