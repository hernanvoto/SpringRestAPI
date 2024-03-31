package com.demo.student;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService _studentService;

    /**
     * 
     * @param studentService injected '@Autowired' is optional for constructor
     *                       injection when there's only one constructor. So,
     *                       whether to use it or not is more of a stylistic
     *                       preference.
     */
    public StudentController(StudentService studentService) {

        _studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {

        return _studentService.getStudents();
    }

    @PostMapping
    public void registerNewStudents(@RequestBody Student student) {

        _studentService.addNewStudents(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id) {

        _studentService.deleteStudent(id);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ) {

        _studentService.updateStudent(studentId, name, email);
    }
}