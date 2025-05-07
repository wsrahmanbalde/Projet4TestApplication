package com.openclassrooms.starterjwt.TestUnitaire;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.mapper.SessionMapperImpl;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SessionMapperTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SessionMapper sessionMapper = new SessionMapperImpl();  // Remplacez par la classe concrète générée par MapStruct

    private SessionDto sessionDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialiser un SessionDto
        sessionDto = new SessionDto();
        sessionDto.setDescription("Test Description");
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(List.of(1L, 2L));
    }

    @Test
    void testToEntity() {
        // Mocking the dependencies
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("User1");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("User2");

        when(userService.findById(1L)).thenReturn(user1);
        when(userService.findById(2L)).thenReturn(user2);

        // Mock the TeacherService
        when(teacherService.findById(1L)).thenReturn(new Teacher());

        // Mapper
        Session session = sessionMapper.toEntity(sessionDto);

        // Verifying
        assertNotNull(session);
        assertEquals("Test Description", session.getDescription());
        assertEquals(2, session.getUsers().size());
        assertNotNull(session.getTeacher());
        verify(userService, times(1)).findById(1L);
        verify(userService, times(1)).findById(2L);
    }

    @Test
    void testToDto() {
        // Create sample session
        Session session = new Session();
        session.setDescription("Test Description");
        session.setUsers(Collections.emptyList());
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        session.setTeacher(teacher);

        // Call the mapper method
        SessionDto sessionDto = sessionMapper.toDto(session);

        // Verifying the output
        assertNotNull(sessionDto);
        assertEquals("Test Description", sessionDto.getDescription());
        assertEquals(0, sessionDto.getUsers().size());
        assertEquals(1L, sessionDto.getTeacher_id());
    }
}