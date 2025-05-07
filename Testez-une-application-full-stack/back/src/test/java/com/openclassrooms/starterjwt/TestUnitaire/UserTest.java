package com.openclassrooms.starterjwt.TestUnitaire;

import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testNoArgsConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    void testRequiredArgsConstructor() {
        User user = new User("test@example.com", "Doe", "John", "password", true);

        assertEquals("test@example.com", user.getEmail());
        assertEquals("Doe", user.getLastName());
        assertEquals("John", user.getFirstName());
        assertEquals("password", user.getPassword());
        assertTrue(user.isAdmin());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();

        User user = new User(1L, "test@example.com", "Doe", "John", "password", true, now, now);

        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Doe", user.getLastName());
        assertEquals("John", user.getFirstName());
        assertEquals("password", user.getPassword());
        assertTrue(user.isAdmin());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void testBuilder() {
        LocalDateTime now = LocalDateTime.now();

        User user = User.builder()
                .id(99L)
                .email("builder@example.com")
                .firstName("Alice")
                .lastName("Wonder")
                .password("builderPass")
                .admin(false)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals(99L, user.getId());
        assertEquals("builder@example.com", user.getEmail());
        assertEquals("Alice", user.getFirstName());
        assertEquals("Wonder", user.getLastName());
        assertEquals("builderPass", user.getPassword());
        assertFalse(user.isAdmin());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void testAccessorsChainStyle() {
        User user = new User()
                .setId(5L)
                .setEmail("chained@example.com")
                .setFirstName("Chain")
                .setLastName("Style")
                .setPassword("chain123")
                .setAdmin(true);

        assertEquals(5L, user.getId());
        assertEquals("chained@example.com", user.getEmail());
        assertEquals("Chain", user.getFirstName());
        assertEquals("Style", user.getLastName());
        assertEquals("chain123", user.getPassword());
        assertTrue(user.isAdmin());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User().setId(1L);
        User user2 = new User().setId(1L);
        User user3 = new User().setId(2L);

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testToStringShouldContainFields() {
        User user = new User()
                .setId(10L)
                .setEmail("string@example.com")
                .setFirstName("To")
                .setLastName("String")
                .setPassword("securePass")
                .setAdmin(true);

        String result = user.toString();

        assertTrue(result.contains("10"));
        assertTrue(result.contains("string@example.com"));
        assertTrue(result.contains("To"));
        assertTrue(result.contains("String"));
        assertTrue(result.contains("securePass"));
        assertTrue(result.contains("true"));
    }
}