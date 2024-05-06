package hwtests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import hw.prince.controller.FacultyController;
import hw.prince.model.Faculty;
import hw.prince.model.Student;

import java.util.Collection;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolApplicationFacultyControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testFindFacultyById() throws Exception {
        Faculty testFaculty = new Faculty();
        facultyController.createFaculty(testFaculty);
        Assertions.assertThat(this.testRestTemplate.getForObject(
                "http://localhost:" + port + "/faculty/findById/" + testFaculty.getId(), Faculty.class)).isNotNull();
        facultyController.deleteFaculty(testFaculty.getId());
    }

    @Test
    public void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(10L);
        faculty.setName("test");

        Assertions.assertThat(this.testRestTemplate.postForObject(
                "http://localhost:" + port + "/faculty/createFaculty", faculty, Faculty.class)).isNotNull();
        Assertions.assertThat(faculty.getName()).isEqualTo("test");
        facultyController.deleteFaculty(faculty.getId());
    }

    @Test
    public void testEditFaculty() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setId(10L);
        faculty1.setName("test1");
        faculty1.setColor("pink");
        testRestTemplate.postForObject("http://localhost:" + port + "/faculty/createFaculty", faculty1, Faculty.class);
        Faculty faculty2 = new Faculty();
        faculty2.setId(20L);
        faculty2.setName("TEST2");
        faculty2.setColor("blue");

        ResponseEntity<Faculty> response = testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty/editFaculty?id=" + faculty1.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(faculty2),
                Faculty.class);

        Assertions.assertThat(response.getStatusCode().is2xxSuccessful());
        facultyController.deleteFaculty(faculty1.getId());
        facultyController.deleteFaculty(faculty2.getId());
    }

    @Test
    void testDeleteFaculty() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setId(20L);
        faculty1.setName("test");
        facultyController.createFaculty(faculty1);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:" + port + "/faculty/deleteFaculty/" + faculty1.getId());
    }

    @Test
    public void testFindFacultyByColorOrName() throws Exception {
        Faculty facultyTest = new Faculty();
        facultyTest.setId(20L);
        facultyTest.setName("TEST");
        facultyTest.setColor("blue");
        facultyController.createFaculty(facultyTest);
        Assertions.assertThat(this.testRestTemplate.getForObject(
                "http://localhost:" + port + "/faculty/findFacultyByColorOrName?name=" + facultyTest.getName(), String.class)).isNotNull();
        Assertions.assertThat(this.testRestTemplate.getForObject(
                "http://localhost:" + port + "/faculty/findFacultyByColorOrName?color=" + facultyTest.getColor(), String.class)).isNotNull();
        facultyController.deleteFaculty(facultyTest.getId());
    }

    @Test
    public void testGetAllStudentOfFaculty() throws Exception {
        Faculty facultyTest = new Faculty();
        facultyTest.setId(20L);
        facultyTest.setName("TEST");
        facultyTest.setColor("blue");
        facultyController.createFaculty(facultyTest);
        Student student1 = new Student();
        student1.setFaculty(facultyTest);
        Collection<Student> studentCollection = List.of(student1);
        Assertions.assertThat(this.testRestTemplate.getForObject(
                "http://localhost:" + port + "/faculty/getAllFacultyOfFaculty?id=" + facultyTest.getId(), String.class)).isNotNull();
        facultyController.deleteFaculty(facultyTest.getId());
    }
}