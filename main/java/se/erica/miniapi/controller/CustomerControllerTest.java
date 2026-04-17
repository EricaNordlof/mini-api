package se.erica.miniapi.controller; 

import com.fasterxml.jackson.databind.ObjectMapper; 
import org.junit.jupiter.api.Test; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.boot.test.web.client.TestRestTemplate; 
import org.springframework.boot.test.web.server.LocalServerPort; 
import org.springframework.http.*; 
import org.springframework.test.context.ActiveProfiles; 
import org.springframework.test.context.DynamicPropertyRegistry; 
import org.springframework.test.context.DynamicPropertySource; 
import org.testcontainers.containers.PostgreSQLContainer; 
import org.testcontainers.junit.jupiter.Container; 
import org.testcontainers.junit.jupiter.Testcontainers; 
import se.erica.miniapi.dto.AuthRequest; 
import se.erica.miniapi.dto.AuthResponse; 
import se.erica.miniapi.dto.CreateCustomerRequest; 

import static org.assertj.core.api.Assertions.assertThat; 

@Testcontainers 
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) 
@ActiveProfiles("test") 
class CustomerControllerTest { 

    @Container 
    static PostgreSQLContainer<?> postgres = 
            new PostgreSQLContainer<>("postgres:16") 
                    .withDatabaseName("miniapi_test") 
                    .withUsername("testuser") 
                    .withPassword("testpassword"); 

    @DynamicPropertySource 
    static void configureProperties(DynamicPropertyRegistry registry) { 
        registry.add("spring.datasource.url", postgres::getJdbcUrl); 
        registry.add("spring.datasource.username", postgres::getUsername); 
        registry.add("spring.datasource.password", postgres::getPassword); 
    } 

    @LocalServerPort 
    int port; 

    @Autowired 
    TestRestTemplate restTemplate; 

    @Autowired 
    ObjectMapper objectMapper; 

    @Test 
    void shouldGetAllCustomers() { 
        ResponseEntity<String> response = 
                restTemplate.getForEntity("http://localhost:" + port + "/api/customers", String.class); 

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); 
        assertThat(response.getBody()).contains("Erica"); 
    } 

    @Test 
    void shouldRejectCreateWithoutToken() throws Exception { 
        CreateCustomerRequest request = new CreateCustomerRequest(); 
        request.setName("Lisa"); 
        request.setEmail("lisa@example.com"); 

        HttpHeaders headers = new HttpHeaders(); 
        headers.setContentType(MediaType.APPLICATION_JSON); 

        HttpEntity<String> entity = new HttpEntity<>( 
                objectMapper.writeValueAsString(request), 
                headers 
        ); 

        ResponseEntity<String> response = restTemplate.exchange( 
                "http://localhost:" + port + "/api/customers", 
                HttpMethod.POST, 
                entity, 
                String.class 
        ); 

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN); 
    } 

    @Test 
    void shouldLoginAndCreateCustomer() throws Exception { 
        AuthRequest authRequest = new AuthRequest(); 
        authRequest.setUsername("erica"); 
        authRequest.setPassword("hemligt123"); 

        HttpHeaders loginHeaders = new HttpHeaders(); 
        loginHeaders.setContentType(MediaType.APPLICATION_JSON); 

        HttpEntity<String> loginEntity = new HttpEntity<>( 
                objectMapper.writeValueAsString(authRequest), 
                loginHeaders 
        ); 

        ResponseEntity<AuthResponse> loginResponse = restTemplate.exchange( 
                "http://localhost:" + port + "/auth/login", 
                HttpMethod.POST, 
                loginEntity, 
                AuthResponse.class 
        ); 

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK); 
        assertThat(loginResponse.getBody()).isNotNull(); 
        assertThat(loginResponse.getBody().getToken()).isNotBlank(); 

        CreateCustomerRequest createRequest = new CreateCustomerRequest(); 
        createRequest.setName("Lisa"); 
        createRequest.setEmail("lisa@example.com"); 

        HttpHeaders createHeaders = new HttpHeaders(); 
        createHeaders.setContentType(MediaType.APPLICATION_JSON); 
        createHeaders.setBearerAuth(loginResponse.getBody().getToken()); 

        HttpEntity<String> createEntity = new HttpEntity<>( 
                objectMapper.writeValueAsString(createRequest), 
                createHeaders 
        ); 

        ResponseEntity<String> createResponse = restTemplate.exchange( 
                "http://localhost:" + port + "/api/customers", 
                HttpMethod.POST, 
                createEntity, 
                String.class 
        ); 

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED); 
        assertThat(createResponse.getBody()).contains("Lisa"); 
    } 
} 
