package mirkoabozzi.Car_Catalog.services;

import mirkoabozzi.Car_Catalog.dto.request.UserRegistrationDTO;
import mirkoabozzi.Car_Catalog.entities.User;
import mirkoabozzi.Car_Catalog.enums.UserRole;
import mirkoabozzi.Car_Catalog.mappers.UserMapper;
import mirkoabozzi.Car_Catalog.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserRegistrationDTO userRegistrationDTO;
    private User user;
    private UUID id;

    @BeforeEach
    void setUp() {
        this.userRegistrationDTO = new UserRegistrationDTO("User", "Test", "user@test.com", "password");
        this.user = User.builder()
                .name("User")
                .surname("test")
                .email("user@test.com")
                .password("password")
                .userRole(UserRole.ADMIN)
                .isEnabled(true)
                .build();
        this.id = UUID.randomUUID();
    }


    @Test
    void findByEmail() {
        when(this.userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));

        User userFound = this.userService.findByEmail("user@test.com");

        assertNotNull(userFound);
        assertEquals("user@test.com", userFound.getEmail());
    }

    @Test
    void findById() {
        when(this.userRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(user));

        User userFound = this.userService.findById(id);

        assertNotNull(userFound);
        assertEquals(user.getEmail(), userFound.getEmail());
    }

    @Test
    void saveUser() {
        when(this.userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userMapper.createUser(userRegistrationDTO)).thenReturn(user);
        when(this.userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = this.userService.saveUser(userRegistrationDTO);

        assertNotNull(savedUser);
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getName(), savedUser.getName());
        verify(userMapper).createUser(userRegistrationDTO);
    }

    @Test
    void deleteUser() {
        when(this.userRepository.findById(id)).thenReturn(Optional.of(user));

        this.userService.deleteUser(id);

        verify(this.userRepository).delete(user);
    }
}