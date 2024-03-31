package com.demo.student;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
/**
 * A service is a class responsible for encapsulating and implementing the
 * business logic of an application. Services perform operations related to
 * specific domains or functionalities, such as data processing, calculations,
 * validations, and interactions with other components. They are managed as
 * Spring beans and leverage dependency injection for loose coupling
 */
public class StudentService {

    private final StudentRepository _studentRepository;

    public StudentService(StudentRepository studentRepository) {

        _studentRepository = studentRepository;
    }

    public List<Student> getStudents() {

        return _studentRepository.findAll();
    }

    public void addNewStudents(final Student student) {

        if (_studentRepository.findStudentByEmail(student.getEmail()).isPresent()) {

            throw new IllegalStateException("email taken");
        }
        _studentRepository.save(student);
    }

    public void deleteStudent(final Long studentId) {

        if (!_studentRepository.existsById(studentId)) {

            throw new IllegalStateException("student with id: " + studentId + " doesn't exist");
        }
        _studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(final Long studentId, final String name, final String email) {

        Student student = _studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException(
                "student with id: " + studentId + " doesn't exist"));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {

            student.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {

            if (_studentRepository.findStudentByEmail(email).isPresent()) {

                throw new IllegalStateException("email is taken");
            }

            student.setEmail(email);
        }

    }
}
