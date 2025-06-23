package com.fgc.framedata_api.service.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CharacterControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateAndGetAllCharacters() throws Exception {
        // Create a game
        String response = mockMvc.perform(post("/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test Game\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Extract the game ID from the response
        Long gameId = Long.parseLong(response.split("\"id\":")[1].split(",")[0]);

        // Create a character
        mockMvc.perform(post("/characters/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Character\", \"gameId\": " + gameId + "}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Character"));

        // Get all characters
        mockMvc.perform(get("/characters/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.characters").isArray())
                .andExpect(jsonPath("$.characters[0].name").value("Test Character"));
    }
}
