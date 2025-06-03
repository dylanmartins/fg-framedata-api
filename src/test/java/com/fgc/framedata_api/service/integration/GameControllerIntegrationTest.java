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
public class GameControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateAndGetAllGames() throws Exception {
        // Create a game
        mockMvc.perform(post("/games/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Game\", \"releaseDate\": \"2023-01-01\", \"developer\": \"Test Developer\", \"publisher\": \"Test Publisher\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Game"));
        // Get all games
        mockMvc.perform(get("/games/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.games").isArray())
                .andExpect(jsonPath("$.games[0].name").value("Test Game"));
    }

    @Test
    void shouldGetGameById() throws Exception {
        // Create a game
        String response = mockMvc.perform(post("/games/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Game\", \"releaseDate\": \"2023-01-01\", \"developer\": \"Test Developer\", \"publisher\": \"Test Publisher\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Extract the game ID from the response
        Long gameId = Long.parseLong(response.split("\"id\":")[1].split(",")[0]);

        // Get the game by ID
        mockMvc.perform(get("/games/" + gameId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Game"));
    }

    @Test
    void shouldDeleteGameById() throws Exception {
        // Create a game
        String response = mockMvc.perform(post("/games/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Game\", \"releaseDate\": \"2023-01-01\", \"developer\": \"Test Developer\", \"publisher\": \"Test Publisher\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Extract the game ID from the response
        Long gameId = Long.parseLong(response.split("\"id\":")[1].split(",")[0]);

        // Verify the game is created
        mockMvc.perform(get("/games/" + gameId))
                .andExpect(status().isOk());

        // Delete the game by ID
        mockMvc.perform(delete("/games/" + gameId))
                .andExpect(status().isNoContent());

        // Verify the game is deleted
        mockMvc.perform(get("/games/" + gameId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateGameById() throws Exception {
        // Create a game
        String response = mockMvc.perform(post("/games/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Game\", \"releaseDate\": \"2023-01-01\", \"developer\": \"Test Developer\", \"publisher\": \"Test Publisher\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Extract the game ID from the response
        Long gameId = Long.parseLong(response.split("\"id\":")[1].split(",")[0]);

        // Update the game
        mockMvc.perform(put("/games/" + gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Test Game\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Test Game"));

        // Verify the game is updated
        mockMvc.perform(get("/games/" + gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Test Game"));
    }
}