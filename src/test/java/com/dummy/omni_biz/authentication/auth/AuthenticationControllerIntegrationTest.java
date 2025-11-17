package com.dummy.omni_biz.authentication.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.dummy.omni_biz.authentication.entity.UserInfo;
import com.dummy.omni_biz.authentication.repository.UserInfoRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@Testcontainers
@Transactional
class AuthenticationControllerIntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("mydatabase")
            .withUsername("myuser")
            .withPassword("secret");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        // Clear existing data
        userInfoRepository.deleteAll();
        
        // Create test user
        UserInfo testUser = new UserInfo();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setUsername("testuser");
        testUser.setPassword(passwordEncoder.encode("testpass"));
        testUser.setRole("USER");
        userInfoRepository.save(testUser);
    }

    @Test
    void authenticate_ValidUser_ReturnsJwtToken() throws Exception {
        String requestBody = """
                {
                    "username": "testuser",
                    "password": "testpass"
                }
                """;

        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.emptyString())));
    }

    @Test
    void authenticate_InvalidCredentials_ReturnsUnauthorized() throws Exception {
        String requestBody = """
                {
                    "username": "testuser",
                    "password": "wrongpass"
                }
                """;

        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticate_NonExistentUser_ReturnsUnauthorized() throws Exception {
        String requestBody = """
                {
                    "username": "nonexistent",
                    "password": "anypass"
                }
                """;

        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticate_EmptyCredentials_ReturnsBadRequest() throws Exception {
        String requestBody = """
                {
                    "username": "",
                    "password": ""
                }
                """;

        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticate_MalformedJson_ReturnsBadRequest() throws Exception {
        String requestBody = "{ invalid json }";

        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
