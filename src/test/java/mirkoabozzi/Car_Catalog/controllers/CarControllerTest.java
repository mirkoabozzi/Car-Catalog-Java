package mirkoabozzi.Car_Catalog.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Value("${jwt.test.admin.token}")
    private String adminToken;
    @Value("${jwt.test.user.token}")
    private String userToken;

    @Test
    void save() throws Exception {

        String newCar = """
                {
                 "brand" : "New",
                 "model" : "Car",
                 "price" : 10000.00,
                 "productionYear" : 2025,
                 "vehicleStatus" : "AVAILABLE"
                }""";

        mockMvc.perform(post("/api/cars")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCar))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("New"))
                .andExpect(jsonPath("$.model").value("Car"))
                .andExpect(jsonPath("$.price").value(10000.00))
                .andExpect(jsonPath("$.productionYear").value(2025))
                .andExpect(jsonPath("$.vehicleStatus").value("AVAILABLE"));
    }

    @Test
    void saveUnauthorized() throws Exception {

        String newCar = """
                {
                 "brand" : "New",
                 "model" : "Car",
                 "price" : 10000.00,
                 "productionYear" : 2025,
                 "vehicleStatus" : "AVAILABLE"
                }""";

        mockMvc.perform(post("/api/cars")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCar))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findAllOk() throws Exception {

        mockMvc.perform(get("/api/cars")
                        .header("Authorization", adminToken))
                .andExpect(status().isOk());

    }

    @Test
    void findAllUnauthorized() throws Exception {

        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findByBrand() throws Exception {

        mockMvc.perform(get("/api/cars/brand")
                        .header("Authorization", userToken)
                        .param("brand", "New")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "productionYear"))
                .andExpect(status().isOk());
    }
}