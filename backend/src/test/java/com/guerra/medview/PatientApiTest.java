package com.guerra.medview;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Patient endpoints now require authentication; the tests authenticate with a mock JWT.
 * The real login flow is covered by AuthApiTest.
 */
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:medview-api;DB_CLOSE_DELAY=-1")
@AutoConfigureMockMvc
class PatientApiTest {

    private static final String PATIENT_JSON =
            "{\"name\":\"Maria Silva\",\"address\":{\"street\":\"Rua A\",\"city\":\"Recife\",\"state\":\"PE\"}}";

    private static final String UPDATED_JSON =
            "{\"name\":\"Maria Souza\",\"address\":{\"street\":\"Rua B\",\"city\":\"Olinda\",\"state\":\"PE\"}}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void withoutTokenReturns401() throws Exception {
        mockMvc.perform(post("/api/v1/patients").contentType(MediaType.APPLICATION_JSON).content(PATIENT_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void registerReturns201WithLocationAndBody() throws Exception {
        mockMvc.perform(post("/api/v1/patients").with(jwt()).contentType(MediaType.APPLICATION_JSON).content(PATIENT_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Maria Silva"))
                .andExpect(jsonPath("$.address.city").value("Recife"));
    }

    @Test
    void registerWithInvalidBodyReturns400() throws Exception {
        mockMvc.perform(post("/api/v1/patients").with(jwt()).contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findUnknownPatientReturns404() throws Exception {
        mockMvc.perform(get("/api/v1/patients/999999").with(jwt()))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUnknownPatientReturns404() throws Exception {
        mockMvc.perform(put("/api/v1/patients/999999").with(jwt()).contentType(MediaType.APPLICATION_JSON).content(UPDATED_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUnknownPatientReturns404() throws Exception {
        mockMvc.perform(delete("/api/v1/patients/999999").with(jwt()))
                .andExpect(status().isNotFound());
    }

    @Test
    void fullCrudFlow() throws Exception {
        String body = mockMvc.perform(post("/api/v1/patients").with(jwt()).contentType(MediaType.APPLICATION_JSON).content(PATIENT_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Number id = JsonPath.read(body, "$.id");

        mockMvc.perform(get("/api/v1/patients/" + id).with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maria Silva"));

        mockMvc.perform(put("/api/v1/patients/" + id).with(jwt()).contentType(MediaType.APPLICATION_JSON).content(UPDATED_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/patients/" + id).with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maria Souza"))
                .andExpect(jsonPath("$.address.city").value("Olinda"));

        mockMvc.perform(delete("/api/v1/patients/" + id).with(jwt()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/patients/" + id).with(jwt()))
                .andExpect(status().isNotFound());
    }
}
