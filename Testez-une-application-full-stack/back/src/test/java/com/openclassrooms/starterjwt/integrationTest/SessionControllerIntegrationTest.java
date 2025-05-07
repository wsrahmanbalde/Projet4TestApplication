package com.openclassrooms.starterjwt.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private Teacher savedTeacher;

    private String jwtToken;
    private User testUser;
    private Session savedSession;

    @BeforeEach
    void setup() throws Exception {
        // Assurer qu'on a un utilisateur existant
        testUser = userRepository.findByEmail("yoga2@studio.com").orElseGet(() -> {
            User user = new User();
            user.setEmail("yoga2@studio.com");
            user.setFirstName("Yoga");
            user.setLastName("Studio");
            user.setPassword("$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq"); // "test!1234" encodé bcrypt
            user.setAdmin(false);
            return userRepository.save(user);
        });

        String loginPayload = "{" +
                              "\"email\":\"yoga2@studio.com\"," +
                              "\"password\":\"test!1234\"}";

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginPayload))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        jwtToken = objectMapper.readTree(response).get("token").asText();

        // Création d’un enseignant pour les sessions
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        savedTeacher = teacherRepository.save(teacher); // Ajoute dans tes attributs : private Teacher savedTeacher;


        // Créer une session de test
        Session session = new Session();
        session.setName("Test Session");
        session.setDescription("Session de test");
        session.setDate(new Date());
        savedSession = sessionRepository.save(session);
    }

    @Test
    void testFindById_shouldReturnSession() throws Exception {
        mockMvc.perform(get("/api/session/" + savedSession.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Session"));
    }

    @Test
    void testFindAll_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/session")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testCreate_shouldReturnCreatedSession() throws Exception {
        String payload = "{" +
                         "\"name\":\"Advanced Yoga\"," +
                         "\"description\":\"An advanced yoga session\"," +
                         "\"date\":\"2025-05-01T10:00:00.000+00:00\"," +
                         "\"teacher_id\":" + savedTeacher.getId() +
                         "}";

        mockMvc.perform(post("/api/session")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Advanced Yoga"))
                .andExpect(jsonPath("$.description").value("An advanced yoga session"))
                .andExpect(jsonPath("$.teacher_id").value(savedTeacher.getId()));
    }

    @Test
    void testUpdate_shouldModifySession() throws Exception {
        // Données de la session mises à jour
        String updatedSessionDto = "{"
                                   + "\"name\":\"Updated Session\","
                                   + "\"description\":\"Updated description\","
                                   + "\"date\":\"2025-04-10T10:00:00\","
                                   + "\"teacher_id\":1,"  // Assurez-vous que cet ID existe dans votre base de données
                                   + "\"users\":[],"
                                   + "\"createdAt\":\"2025-04-10T10:00:00\","
                                   + "\"updatedAt\":\"2025-04-10T10:00:00\""
                                   + "}";

        // Effectuer la requête PUT pour mettre à jour la session
        mockMvc.perform(put("/api/session/" + savedSession.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSessionDto))
                .andExpect(status().isOk())  // Vérifie que la mise à jour a réussi (200 OK)
                .andExpect(jsonPath("$.name").value("Updated Session"))  // Vérifie la mise à jour du nom
                .andExpect(jsonPath("$.description").value("Updated description"));  // Vérifie la mise à jour de la description
    }

    @Test
    void testDelete_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/session/" + savedSession.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void testParticipate_shouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/session/" + savedSession.getId() + "/participate/" + testUser.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void testNoLongerParticipate_shouldReturnOk() throws Exception {
        // Participer d'abord
        mockMvc.perform(post("/api/session/" + savedSession.getId() + "/participate/" + testUser.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());

        // Ensuite se désinscrire
        mockMvc.perform(delete("/api/session/" + savedSession.getId() + "/participate/" + testUser.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }
}
