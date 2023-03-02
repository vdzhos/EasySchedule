package com.easyschedule.repositories;

import com.easyschedule.models.Specialty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends CrudRepository<Specialty,Long> {

}
