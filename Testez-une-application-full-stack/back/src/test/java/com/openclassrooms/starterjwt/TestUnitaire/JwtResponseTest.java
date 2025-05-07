package com.openclassrooms.starterjwt.TestUnitaire;

import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtResponseTest {

    @Test
    void shouldSetAndGetAllFieldsCorrectly() {
        // Arrange
        JwtResponse response = new JwtResponse(
                "initial-token",
                10L,
                "initial@example.com",
                "InitialFirst",
                "InitialLast",
                false
        );

        // Assert - vérification des valeurs passées au constructeur
        assertThat(response.getToken()).isEqualTo("initial-token");
        assertThat(response.getId()).isEqualTo(10L);
        assertThat(response.getUsername()).isEqualTo("initial@example.com");
        assertThat(response.getFirstName()).isEqualTo("InitialFirst");
        assertThat(response.getLastName()).isEqualTo("InitialLast");
        assertThat(response.getAdmin()).isFalse();
        assertThat(response.getType()).isEqualTo("Bearer"); // valeur par défaut

        // Act - modification des valeurs avec les setters
        response.setToken("new-token");
        response.setId(42L);
        response.setUsername("updated@example.com");
        response.setFirstName("John");
        response.setLastName("Doe");
        response.setAdmin(true);
        response.setType("Custom");

        // Assert - vérification des getters après setters
        assertThat(response.getToken()).isEqualTo("new-token");
        assertThat(response.getId()).isEqualTo(42L);
        assertThat(response.getUsername()).isEqualTo("updated@example.com");
        assertThat(response.getFirstName()).isEqualTo("John");
        assertThat(response.getLastName()).isEqualTo("Doe");
        assertThat(response.getAdmin()).isTrue();
        assertThat(response.getType()).isEqualTo("Custom");
    }
}