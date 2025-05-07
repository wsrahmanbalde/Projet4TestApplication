package com.openclassrooms.starterjwt.TestUnitaire;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

class BadRequestExceptionTest {

    @Test
    void testBadRequestException_hasCorrectStatusCode() {
        // Lancer l'exception
        BadRequestException exception = new BadRequestException();

        // Vérifier que l'exception est une instance de RuntimeException
        assertTrue(exception instanceof RuntimeException, "Exception should be an instance of RuntimeException");

        // Vérifier que l'exception a le bon code de statut HTTP
        ResponseStatus responseStatus = BadRequestException.class.getAnnotation(ResponseStatus.class);
        assertNotNull(responseStatus, "ResponseStatus annotation should not be null");
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus.value(), "The HTTP status code should be BAD_REQUEST");
    }
}