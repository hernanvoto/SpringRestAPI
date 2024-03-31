package com.demo.student;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Annotate the test class with @SpringBootTest to load the Spring Boot
 * application context. Annotate the test class with @AutoConfigureMockMvc to
 * automatically configure MockMvc. Autowire MockMvc and ObjectMapper into the
 * test class. Use @MockBean to mock the StudentService dependency. Use MockMvc
 * to perform HTTP requests and test the controller's behavior. In the
 * testRegisterNewStudent() method, convert the Student object to JSON using
 * ObjectMapper.
 */
@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("test") // Activate the 'test' profile
class SpringRestApiApplicationTests {

    @Test
    void contextLoads() {

    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private AtomicLong _idGenerator = new AtomicLong(1); // Starting ID value

    @Test
    public void testGetStudents() throws Exception {

        Student student = new Student("John", "john@example.com", LocalDate.of(2004, 1, 3));
        student.setId(_idGenerator.getAndIncrement());
        // Arrange
        List<Student> students = Arrays.asList(student);
        when(studentService.getStudents()).thenReturn(students);

        // Act & Assert
        mockMvc.perform(get("/api/v1/student")).andExpect(status().isOk()
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].name", is("John")))
//                .andExpect(jsonPath("$[0].email", is("john@example.com")));
        );

        // Verify that the service method was called
        // verify(studentService, times(1)).getStudents();
    }

    @Test
    public void testRegisterNewStudent() throws Exception {

        // Arrange
        Student newStudent = new Student("John", "john@example.com", LocalDate.of(2010, 2, 13));
        newStudent.setId(_idGenerator.getAndIncrement());
        newStudent.setAge(newStudent.getAge());

        String json = objectMapper.writeValueAsString(newStudent);

        // Act & Assert
        mockMvc.perform(post("/api/v1/student").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(
                status().isOk());

        // Verify that the service method was called
        // verify(studentService, times(1)).addNewStudents(newStudent);
    }

    /**
     * In the testDeleteStudent() method, we perform a DELETE request to the
     * /api/v1/student/{studentId} endpoint with the student ID path variable. We
     * then verify that the deleteStudent method of the StudentService is called
     * with the correct student ID.
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteStudent() throws Exception {

        // Arrange
        Long studentId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/v1/student/{studentId}", studentId)).andExpect(status().isOk());

        // Verify that the service method was called
        verify(studentService, times(1)).deleteStudent(studentId);
    }

    /**
     * In the testUpdateStudent() method, we perform a PUT request to the
     * /api/v1/student/{studentId} endpoint with the student ID path variable and
     * the name and email parameters. We then verify that the updateStudent method
     * of the StudentService is called with the correct student ID, name, and email
     * parameters.
     * 
     * @throws Exception
     */
    @Test
    public void testUpdateStudent() throws Exception {

        // Arrange
        Long studentId = 1L;
        String name = "UpdatedName";
        String email = "updatedemail@example.com";

        // Act & Assert
        mockMvc.perform(put("/api/v1/student/{studentId}", studentId).param("name", name).param("email", email))
                .andExpect(status().isOk());

        // Verify that the service method was called
        verify(studentService, times(1)).updateStudent(studentId, name, email);
    }
}
