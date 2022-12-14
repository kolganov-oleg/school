package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Collection<Student> findByAge(int age);

    public Collection<Student> findByAgeBetween(int min, int max);

    public Collection<Student> findByFacultyId(long facultyId);

    @Query(value = "SELECT count(*) FROM student", nativeQuery = true)
    Integer getCountOfStudents();

    @Query(value = "SELECT avg(age) FROM student", nativeQuery = true)
    Float getStudentsAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLast5Students();
}


