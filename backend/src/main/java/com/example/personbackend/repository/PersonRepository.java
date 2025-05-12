package com.example.personbackend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.personbackend.model.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.personbackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Page<Person> findByGender(Gender gender, Pageable pageable);

    @Query("SELECT p FROM Person p WHERE (:gender IS NULL OR p.gender = :gender) AND (LOWER(p.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Person> searchByFields(@Param("gender") Gender gender, @Param("search") String search, Pageable pageable);
}
