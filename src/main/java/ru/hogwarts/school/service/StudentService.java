package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private Integer syncCount = 0;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        logger.debug("Calling constructor StudentService");
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.debug("Calling method createStudent");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.debug("Calling method findStudent (studentId = {})", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        logger.debug("Calling method editStudent (studentId = {})", student.getId());
        if (studentRepository.findById(student.getId()).orElse(null) == null) {
            return null;
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.debug("Calling method deleteStudent (studentId = {})", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        logger.debug("Calling method getStudentsByAge (age = {})", age);
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.debug("Calling method findByAgeBetween (minAge = {}, maxAge = {})", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getStudentFaculty(long id) {
        logger.debug("Calling method getStudentFaculty (studentId = {})", id);
        Student student = findStudent(id);
        if (student == null) {
            return null;
        }
        return student.getFaculty();
    }

    public Integer getCountOfStudents() {
        logger.debug("Calling method getCountOfStudents");
        return studentRepository.getCountOfStudents();
    }

    public Float getStudentsAverageAge() {
        logger.debug("Calling method getStudentsAverageAge");
        return studentRepository.getStudentsAverageAge();
    }

    public List<Student> getLast5Students() {
        logger.debug("Calling method getLast5Students");
        return studentRepository.getLast5Students();
    }

    public List<String> getStudentsByNameStartsWith(String letter) {
        return studentRepository.findAll().stream()
                .map(user -> user.getName())
                .filter(s -> s.startsWith(letter))
                .sorted((s1, s2) -> s1.compareTo(s2))
                .map(s -> s.toUpperCase())
                .collect(Collectors.toList());
    }

    public Double getAverageAgeWithStream() {
        return studentRepository.findAll().stream()
                .mapToDouble(user -> user.getAge())
                .average()
                .orElse(Double.NaN);
    }

    public void printStudentsInThread() {
        List <Student> students = studentRepository.findAll();

        printToConsole(students.get(0).getName());
        printToConsole(students.get(1).getName());

        new Thread(() -> {
            printToConsole(students.get(2).getName());
            printToConsole(students.get(3).getName());
        }
        ).start();

        new Thread(() -> {
            printToConsole(students.get(4).getName());

        }
        ).start();
    }


    private void printToConsole(String str) {
        System.out.println(str);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    public void printToConsoleSyncMode() {
        List <Student> students = studentRepository.findAll();


        printToConsoleSyncMode(students);
        printToConsoleSyncMode(students);

        new Thread(()-> {
            printToConsoleSyncMode(students);
            printToConsoleSyncMode(students);
        }
        ).start();

        new Thread(()-> {
            printToConsoleSyncMode(students);

        }
        ).start();

    }

    private void printToConsoleSyncMode(List<Student> students) {

        synchronized (syncCount) {
            System.out.println(students.get(syncCount).getName());
            syncCount++;
        }
    }
}
