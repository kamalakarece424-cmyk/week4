package com.school.edureka_student.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.edureka_student.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}