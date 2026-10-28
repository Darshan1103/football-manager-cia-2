package football_manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FootballManagerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void applicationStarts() {
    }

    @Test
    void getAllPlayersReturnsSuccess() throws Exception {
        mockMvc.perform(get("/api/players"))
                .andExpect(status().isOk());
    }

    @Test
    void addPlayerReturnsCreated() throws Exception {
        mockMvc.perform(post("/api/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "Lionel Messi",
                        "position": "Forward",
                        "jerseyNumber": 10,
                        "team": "Inter Miami"
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Lionel Messi"));
    }

    @Test
    void missingPlayerReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/players/99999"))
                .andExpect(status().isNotFound());
    }
}
