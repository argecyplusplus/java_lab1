package ru.chernyukai.projects.lab1jokebot.repository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.chernyukai.projects.lab1jokebot.model.UserRole;

@Transactional
public interface UserRolesRepository extends CrudRepository<UserRole, Long> {


    @Modifying
    @Query(value = "delete from user_roles where user_authority=1 and user_id = :userId", nativeQuery = true)
    void deleteModerById (@Param("userId") Long userId);


    //void deleteAllByUserAndUserAuthorityEquals1();

}
