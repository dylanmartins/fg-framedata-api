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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MoveControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private Long createGame() throws Exception {
        String response = mockMvc.perform(post("/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Street Fighter 6\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return Long.parseLong(response.split("\"id\":")[1].split(",")[0]);
    }

    private Long createCharacter(Long gameId) throws Exception {
        String response = mockMvc.perform(post("/characters/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Ryu\", \"gameId\": " + gameId + ", \"patchVersion\": \"20250222\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return Long.parseLong(response.split("\"id\":")[1].split(",")[0]);
    }

    @Test
    void shouldCreateAndGetAllMoves() throws Exception {
        Long gameId = createGame();
        Long characterId = createCharacter(gameId);

        mockMvc.perform(post("/characters/" + characterId + "/moves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Hadoken\", \"input\": \"236P\", \"moveType\": \"SPECIAL\", \"startupFrames\": 13, \"hasArmor\": false}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Hadoken"))
                .andExpect(jsonPath("$.input").value("236P"))
                .andExpect(jsonPath("$.moveType").value("SPECIAL"));

        mockMvc.perform(get("/characters/" + characterId + "/moves"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moves").isArray())
                .andExpect(jsonPath("$.moves[0].name").value("Hadoken"));
    }

    @Test
    void shouldReturnNotFound_whenCharacterDoesNotExist() throws Exception {
        mockMvc.perform(get("/characters/999/moves"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetMoveById() throws Exception {
        Long gameId = createGame();
        Long characterId = createCharacter(gameId);

        String response = mockMvc.perform(post("/characters/" + characterId + "/moves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Shoryuken\", \"input\": \"623P\", \"moveType\": \"SPECIAL\", \"hasArmor\": true, \"armorHits\": 1}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long moveId = Long.parseLong(response.split("\"id\":")[1].split(",")[0]);

        mockMvc.perform(get("/characters/" + characterId + "/moves/" + moveId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Shoryuken"))
                .andExpect(jsonPath("$.hasArmor").value(true));
    }

    @Test
    void shouldUpdateMove() throws Exception {
        Long gameId = createGame();
        Long characterId = createCharacter(gameId);

        String response = mockMvc.perform(post("/characters/" + characterId + "/moves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Hadoken\", \"input\": \"236P\", \"moveType\": \"SPECIAL\", \"startupFrames\": 13, \"hasArmor\": false}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long moveId = Long.parseLong(response.split("\"id\":")[1].split(",")[0]);

        mockMvc.perform(put("/characters/" + characterId + "/moves/" + moveId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startupFrames\": 12}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startupFrames").value(12));
    }

    @Test
    void shouldDeleteMove() throws Exception {
        Long gameId = createGame();
        Long characterId = createCharacter(gameId);

        String response = mockMvc.perform(post("/characters/" + characterId + "/moves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Hadoken\", \"input\": \"236P\", \"moveType\": \"SPECIAL\", \"hasArmor\": false}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long moveId = Long.parseLong(response.split("\"id\":")[1].split(",")[0]);

        mockMvc.perform(delete("/characters/" + characterId + "/moves/" + moveId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/characters/" + characterId + "/moves/" + moveId))
                .andExpect(status().isNotFound());
    }
}
