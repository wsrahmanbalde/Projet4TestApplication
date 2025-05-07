package com.openclassrooms.starterjwt.TestUnitaire;

import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageResponseTest {

    @Test
    void constructor_shouldInitializeMessage() {
        // Arrange
        String expectedMessage = "Operation successful";

        // Act
        MessageResponse response = new MessageResponse(expectedMessage);

        // Assert
        assertNotNull(response);
        assertEquals(expectedMessage, response.getMessage());
    }

    @Test
    void setMessage_shouldUpdateMessage() {
        // Arrange
        MessageResponse response = new MessageResponse("Initial");
        String updatedMessage = "Updated message";

        // Act
        response.setMessage(updatedMessage);

        // Assert
        assertEquals(updatedMessage, response.getMessage());
    }

    @Test
    void getMessage_shouldReturnCurrentMessage() {
        // Arrange
        MessageResponse response = new MessageResponse("Hello World");

        // Act
        String actual = response.getMessage();

        // Assert
        assertEquals("Hello World", actual);
    }
}