package com.openclassrooms.starterjwt.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;  // Assurez-vous d'importer cette annotation
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc  // Cette annotation est cruciale pour configurer MockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String jwtToken;

    @BeforeEach
    void setup() {
        // Assurer qu'un utilisateur de test existe avec un mot de passe encodé
        if (!userRepository.existsByEmail("testuser@email.com")) {
            // Encoder le mot de passe
            String encodedPassword = passwordEncoder.encode("password");

            // Sauvegarder l'utilisateur avec le mot de passe encodé
            userRepository.save(new User("testuser@email.com", "Test", "User", encodedPassword, false));
        }
    }

   /* @Test
    void testRegisterUser_shouldReturnUserCreatedMessage() throws Exception {
        // Créer une nouvelle demande d'inscription
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("newuser@email.com");
        signupRequest.setFirstName("New");
        signupRequest.setLastName("User");
        signupRequest.setPassword("newpassword");

        // Effectuer une requête POST pour enregistrer un nouvel utilisateur
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }*/

    @Test
    void testLoginUser_shouldReturnJwtToken() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("testuser@email.com");
        loginRequest.setPassword("password");  // Assurez-vous que ce mot de passe est correct et existe dans la base

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username").value("testuser@email.com"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = objectMapper.readTree(response).get("token").asText();
        assertNotNull(token);  // Vérifie que le token n'est pas nul
    }

    @Test
    void testLogin_withWrongCredentials_shouldReturnUnauthorized() throws Exception {
        // Créer une demande de connexion avec des identifiants incorrects
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("testuser@email.com");
        loginRequest.setPassword("wrongpassword");

        // Vérifier que la connexion échoue et retourne une erreur 401
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLogin_withNonExistentUser_shouldReturnUnauthorized() throws Exception {
        // Créer une demande de connexion pour un utilisateur inexistant
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("nonexistent@email.com");
        loginRequest.setPassword("password");

        // Vérifier que la connexion échoue et retourne une erreur 401
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
}