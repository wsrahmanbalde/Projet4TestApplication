package com.openclassrooms.starterjwt.TestUnitaire;

import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignupRequestTest {

    private SignupRequest request;

    @BeforeEach
    void setUp() {
        // Initialisation de l'objet SignupRequest
        request = new SignupRequest();
        request.setEmail("user@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("secure123");
    }

    @Test
    void testGettersAndSetters() {
        // Test des getters et setters
        assertEquals("user@example.com", request.getEmail());
        assertEquals("John", request.getFirstName());
        assertEquals("Doe", request.getLastName());
        assertEquals("secure123", request.getPassword());

        // Test de la modification des valeurs via les setters
        request.setEmail("newemail@example.com");
        request.setFirstName("Jane");
        request.setLastName("Smith");
        request.setPassword("newpassword123");

        assertEquals("newemail@example.com", request.getEmail());
        assertEquals("Jane", request.getFirstName());
        assertEquals("Smith", request.getLastName());
        assertEquals("newpassword123", request.getPassword());
    }

    @Test
    void testToString() {
        // Test de la méthode toString() générée par Lombok
        String toString = request.toString();
        assertTrue(toString.contains("email=" + request.getEmail()));
        assertTrue(toString.contains("firstName=" + request.getFirstName()));
        assertTrue(toString.contains("lastName=" + request.getLastName()));
        assertTrue(toString.contains("password=" + request.getPassword()));
    }

    @Test
    void testEqualsAndHashCode() {
        // Créer un autre objet avec les mêmes données
        SignupRequest otherRequest = new SignupRequest();
        otherRequest.setEmail("user@example.com");
        otherRequest.setFirstName("John");
        otherRequest.setLastName("Doe");
        otherRequest.setPassword("secure123");

        // Vérifier que les objets sont égaux
        assertEquals(request, otherRequest);
        assertEquals(request.hashCode(), otherRequest.hashCode());

        // Modifier une valeur et vérifier que les objets ne sont plus égaux
        otherRequest.setEmail("different@example.com");
        assertNotEquals(request, otherRequest);
    }

    @Test
    void testEqualsWithNull() {
        // Comparer avec null (doit être false)
        assertNotEquals(request, null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        // Comparer avec une classe différente (doit être false)
        assertNotEquals(request, new String("some string"));
    }

    @Test
    void testEqualsWithDifferentValues() {
        // Vérifier que les objets avec des valeurs différentes ne sont pas égaux
        SignupRequest otherRequest = new SignupRequest();
        otherRequest.setEmail("different@example.com");
        otherRequest.setFirstName("Jane");
        otherRequest.setLastName("Doe");
        otherRequest.setPassword("newpassword123");

        assertNotEquals(request, otherRequest);
    }
}