package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author muhammad.khadafi
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> getAllStudentsWithCourses() {
        List<Student> students = studentRepository.findAll();

        Map<Long, Student> studentMap = new HashMap<>();
        for (Student s : students) {
            studentMap.put(s.getId(), s);
        }

        List<StudentCourse> allStudentCourses = studentCourseRepository.findAll();

        List<StudentCourse> result = new ArrayList<>();
        for (StudentCourse sc : allStudentCourses) {
            StudentCourse newSc = new StudentCourse();
            Long studentId = sc.getStudent().getId();
            newSc.setStudent(studentMap.get(studentId));
            newSc.setCourse(sc.getCourse());
            result.add(newSc);
        }

        return result;
    }

    public Optional<Student> findStudentWithHighestGpa() {
        return studentRepository.findStudentWithHighestGpa();
    }

    public String joinStudentNames() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .collect(Collectors.joining(", "));
    }
}

