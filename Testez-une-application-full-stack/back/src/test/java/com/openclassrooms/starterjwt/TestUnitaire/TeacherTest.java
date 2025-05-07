package com.openclassrooms.starterjwt.TestUnitaire;

import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        // Initialisation de l'objet Teacher via le Builder
        teacher = Teacher.builder()
                .id(1L)
                .lastName("DELAHAYE")
                .firstName("Margot")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testTeacherBuilder() {
        // Vérifie que le builder crée un objet teacher correctement
        assertNotNull(teacher);
        assertEquals("DELAHAYE", teacher.getLastName());
        assertEquals("Margot", teacher.getFirstName());
        assertNotNull(teacher.getCreatedAt());
        assertNotNull(teacher.getUpdatedAt());
    }

    @Test
    void testTeacherGettersAndSetters() {
        // Test des setters et getters
        teacher.setLastName("THIERCELIN");
        teacher.setFirstName("Hélène");

        // Vérifie que les valeurs sont correctement affectées et récupérées via les getters
        assertEquals("THIERCELIN", teacher.getLastName());
        assertEquals("Hélène", teacher.getFirstName());

        // Test des autres propriétés
        teacher.setCreatedAt(LocalDateTime.now().minusDays(1));
        teacher.setUpdatedAt(LocalDateTime.now().minusDays(1));
        assertNotNull(teacher.getCreatedAt());
        assertNotNull(teacher.getUpdatedAt());
    }

    @Test
    void testTeacherBuilderWithNullValues() {
        // Vérifie si le builder fonctionne même avec des valeurs null
        teacher = Teacher.builder()
                .id(2L)
                .lastName(null)
                .firstName(null)
                .createdAt(null)
                .updatedAt(null)
                .build();

        assertNotNull(teacher);
        assertNull(teacher.getLastName());
        assertNull(teacher.getFirstName());
        assertNull(teacher.getCreatedAt());
        assertNull(teacher.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        Teacher teacher1 = Teacher.builder()
                .id(1L)
                .lastName("DELAHAYE")
                .firstName("Margot")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Teacher teacher2 = Teacher.builder()
                .id(1L)
                .lastName("DELAHAYE")
                .firstName("Margot")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Test d'égalité et de hashcode
        assertTrue(teacher1.equals(teacher2));
        assertEquals(teacher1.hashCode(), teacher2.hashCode());
    }

    @Test
    void testToString() {
        // Vérifie que la méthode toString contient les champs principaux
        String toString = teacher.toString();

        assertTrue(toString.contains("Teacher"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("lastName=DELAHAYE"));
        assertTrue(toString.contains("firstName=Margot"));
        assertTrue(toString.contains("createdAt="));
        assertTrue(toString.contains("updatedAt="));
    }
}