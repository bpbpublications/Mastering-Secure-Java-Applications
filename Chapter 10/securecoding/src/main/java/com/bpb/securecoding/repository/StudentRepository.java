package com.bpb.securecoding.repository;

import com.bpb.securecoding.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
