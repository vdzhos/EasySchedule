package com.easyschedule.repositories;

import com.easyschedule.models.Lesson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends CrudRepository<Lesson,Long> {

}
