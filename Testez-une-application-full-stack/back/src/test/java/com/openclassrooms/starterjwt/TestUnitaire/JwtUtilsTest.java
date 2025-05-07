package com.openclassrooms.starterjwt.TestUnitaire;

import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.UnsupportedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtUtils jwtUtils;

    private static final String JWT_SECRET = "testSecret";
    private static final int JWT_EXPIRATION_MS = 3600000; // 1 hour
    private static final String USERNAME = "testuser@example.com";
    private static final String JWT_TOKEN = "mockJwtToken";

    @BeforeEach
    void setUp() {
        // Simuler l'injection des propriétés dans le test via ReflectionTestUtils
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", JWT_SECRET);
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", JWT_EXPIRATION_MS);

        // Créer un mock de l'objet Authentication et de UserDetailsImpl
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

        // Si vous ne testez pas cette méthode dans certains tests, vous pouvez supprimer ce stub
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(USERNAME);
    }

    @Test
    void testGenerateJwtToken() {
        // Arrange
        String token = jwtUtils.generateJwtToken(authentication);

        // Assert: Vérifier que le token généré n'est pas null et qu'il commence par "eyJ"
        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"), "JWT token should start with 'eyJ'");
    }

    @Test
    void testGetUserNameFromJwtToken() {
        // Arrange
        String token = jwtUtils.generateJwtToken(authentication);

        // Act: Extraire le username du token généré
        String username = jwtUtils.getUserNameFromJwtToken(token);

        // Assert: Vérifier que le username extrait correspond à celui attendu
        assertEquals(USERNAME, username);
    }

    @Test
    void testValidateJwtToken_Success() {
        // Arrange
        String token = jwtUtils.generateJwtToken(authentication);

        // Act: Valider le JWT
        boolean isValid = jwtUtils.validateJwtToken(token);

        // Assert: Vérifier que le JWT est valide
        assertTrue(isValid);
    }
}