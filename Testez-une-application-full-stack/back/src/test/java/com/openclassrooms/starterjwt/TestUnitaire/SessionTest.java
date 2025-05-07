package com.openclassrooms.starterjwt.TestUnitaire;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SessionTest {

    private Teacher teacher;
    private User user;
    private Session session;

    @BeforeEach
    void setUp() {
        // Initialisation des dépendances mockées
        teacher = mock(Teacher.class);
        user = mock(User.class);

        // Création de l'objet Session via le builder
        session = Session.builder()
                .id(1L)
                .name("Yoga Class")
                .date(new java.util.Date())
                .description("A relaxing yoga session")
                .teacher(teacher)
                .users(Arrays.asList(user))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testSessionBuilder() {
        // Vérifie que le builder crée un objet session correctement
        assertNotNull(session);
        assertEquals("Yoga Class", session.getName());
        assertEquals("A relaxing yoga session", session.getDescription());
        assertNotNull(session.getDate());
        assertNotNull(session.getTeacher());
        assertEquals(1, session.getUsers().size());
        assertNotNull(session.getCreatedAt());
        assertNotNull(session.getUpdatedAt());
    }

    @Test
    void testSessionGettersAndSetters() {
        // Test des setters et getters
        session.setName("Advanced Yoga");
        session.setDescription("An advanced yoga session");
        session.setDate(new java.util.Date());
        session.setTeacher(teacher);
        session.setUsers(Arrays.asList(user));

        // Vérifie que les valeurs sont correctement affectées et récupérées via les getters
        assertEquals("Advanced Yoga", session.getName());
        assertEquals("An advanced yoga session", session.getDescription());
        assertNotNull(session.getDate());
        assertEquals(teacher, session.getTeacher());
        assertEquals(1, session.getUsers().size());

        // Test des autres propriétés
        session.setCreatedAt(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());
        assertNotNull(session.getCreatedAt());
        assertNotNull(session.getUpdatedAt());
    }

    @Test
    void testSessionBuilderWithNullValues() {
        // Vérifie si le builder fonctionne même avec des valeurs null
        session = Session.builder()
                .id(2L)
                .name(null)
                .description(null)
                .date(null)
                .teacher(null)
                .users(null)
                .createdAt(null)
                .updatedAt(null)
                .build();

        assertNotNull(session);
        assertNull(session.getName());
        assertNull(session.getDescription());
        assertNull(session.getDate());
        assertNull(session.getTeacher());
        assertNull(session.getUsers());
        assertNull(session.getCreatedAt());
        assertNull(session.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange: Créer deux objets de type Session avec les mêmes attributs
        Session session1 = Session.builder()
                .id(1L)
                .name("Yoga Class")
                .date(new java.util.Date())
                .description("A relaxing yoga session")
                .teacher(teacher)
                .users(Arrays.asList(user))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Session session2 = Session.builder()
                .id(1L)
                .name("Yoga Class")
                .date(new java.util.Date())
                .description("A relaxing yoga session")
                .teacher(teacher)
                .users(Arrays.asList(user))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Act & Assert: Vérifier que les deux objets sont égaux et ont le même hashCode
        assertEquals(session1, session2, "Sessions with the same id should be equal.");
        assertEquals(session1.hashCode(), session2.hashCode(), "Sessions with the same id should have the same hashCode.");
    }

    @Test
    void testToString() {
        // Arrange
        session = Session.builder()
                .id(1L)
                .name("Yoga Class")
                .description("A relaxing yoga session")
                .date(new java.util.Date())
                .teacher(teacher)
                .users(Arrays.asList(user))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Act
        String toString = session.toString();

        // Assert
        assertTrue(toString.contains("Yoga Class"));
        assertTrue(toString.contains("A relaxing yoga session"));
        assertTrue(toString.contains("1"));
        // Vous pouvez ajouter d'autres assertions pour valider le format et les valeurs dans toString()
    }
}