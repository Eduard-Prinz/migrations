package hw.prince.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import hw.prince.model.Avatar;


import java.util.Collection;

public interface AvatarRepository extends JpaRepository <Avatar,Long> {
    Avatar findByStudentId(long id);
    @Query (value = "select *from avatar a",nativeQuery = true)
    Collection<Avatar> getAll (Integer pageNumber, Integer pageSize);
}