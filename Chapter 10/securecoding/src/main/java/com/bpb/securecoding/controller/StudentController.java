package com.bpb.securecoding.controller;

import com.bpb.securecoding.error.StudentNotFoundException;
import com.bpb.securecoding.error.StudentUnSupportedFieldPatchException;
import com.bpb.securecoding.model.Student;
import com.bpb.securecoding.repository.StudentRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@Validated
public class StudentController {
    Logger logger = Logger.getLogger(StudentController.class.getName());

    @Autowired
    private StudentRepository repository;

    // Find
    @GetMapping("/students")
    List<Student> findAll() {
        return repository.findAll();
    }

    // Save
    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    Student newStudent(@Valid @RequestBody Student newStudent, HttpServletRequest request) {
        logger.info("Input :"+request.getParameter("input"));
        return repository.save(newStudent);
    }

    // Find
    @GetMapping("/students/{id}")
    Student findOne(@PathVariable @Min(1) Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    // Save or update
    @PutMapping("/students/{id}")
    Student saveOrUpdate(@RequestBody Student newStudent, @PathVariable Long id) {

        return repository.findById(id)
                .map(existingStrudent -> {
                    existingStrudent.setName(newStudent.getName());
                    existingStrudent.setGrade(newStudent.getGrade());
                    existingStrudent.setGpa(newStudent.getGpa());
                    return repository.save(existingStrudent);
                })
                .orElseGet(() -> {
                    newStudent.setId(id);
                    return repository.save(newStudent);
                });
    }

    // update only grade
    @PatchMapping("/students/{id}")
    Student patch(@RequestBody Map<String, String> update, @PathVariable Long id) {
        return repository.findById(id)
                .map(existingStudent -> {
                    String grade = update.get("grade");
                    if (!StringUtils.isEmpty(grade)) {
                        existingStudent.setGrade(grade);

                        // better create a custom method to update a value = :newValue where id = :id
                        return repository.save(existingStudent);
                    } else {
                        throw new StudentUnSupportedFieldPatchException(update.keySet());
                    }

                })
                .orElseGet(() -> {
                    throw new StudentNotFoundException(id);
                });

    }

    @DeleteMapping("/students/{id}")
    void deleteStudent(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
