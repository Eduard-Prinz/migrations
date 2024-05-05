package hwtests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import hw.prince.controller.StudentController;
import hw.prince.model.Faculty;
import hw.prince.model.Student;
import hw.prince.repository.StudentRepository;
import hw.prince.service.StudentService;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class SchoolApplicationStudentControllerWithMVCTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void testCreateStudent() throws Exception {
        Long id = 10L;
        String name = "Bob";
        int age = 15;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student/createStudent") //send
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        verify(studentRepository, times(1)).save(argThat(savedStudent -> savedStudent.getName().equals(name)
                && savedStudent.getAge() == age));

    }

    @Test
    public void testFindStudentById() throws Exception {
        Long id = 10L;
        String name = "Bob";
        int age = 15;

        Student student1 = new Student(id, name, age, null);

        when(studentRepository.findById(id)).thenReturn(Optional.of(student1));
    }


    @Test
    public void testDeleteStudent() throws Exception {
        long id = 1L;
        String name = "Bob";
        int age = 37;
        Student student = new Student(id, name, age, null);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/deleteStudent/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindFacultyOfStudent() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(6L);
        faculty.setName("test");
        List<Student> students1 = List.of(
                new Student(1L, "name", 4, faculty));
        List<Student> student2 = List.of(
                new Student(1L, "name", 4, faculty));
        when(studentRepository.findAll()).thenReturn(students1);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/student")
                .accept(MediaType.APPLICATION_JSON));

        assertEquals(students1.size(), student2.size());
        for (int i = 0; i < students1.size(); i++) {
            assertEquals(student2.get(i).getName(), students1.get(i).getName());
        }
    }
}
