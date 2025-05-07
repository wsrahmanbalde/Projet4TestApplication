package com.openclassrooms.starterjwt.TestUnitaire;

import com.openclassrooms.starterjwt.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {

    @Test
    void testNotFoundException_hasCorrectStatusCode() {
        // Lancer l'exception
        NotFoundException exception = new NotFoundException();

        // Vérifier que l'exception est une instance de RuntimeException
        assertTrue(exception instanceof RuntimeException, "Exception should be an instance of RuntimeException");

        // Vérifier que l'exception a le bon code de statut HTTP
        ResponseStatus responseStatus = NotFoundException.class.getAnnotation(ResponseStatus.class);
        assertNotNull(responseStatus, "ResponseStatus annotation should not be null");
        assertEquals(HttpStatus.NOT_FOUND, responseStatus.value(), "The HTTP status code should be NOT_FOUND");
    }
}