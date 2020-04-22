package com.diomeda.credential.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.diomeda.credential.model.Rol;
import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    List<Rol> findByName(String name);

}
