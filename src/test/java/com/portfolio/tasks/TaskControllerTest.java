
package com.portfolio.tasks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void createsTaskAndReturns201WithContractFields() throws Exception {
        mvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Review PR\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Review PR"))
            .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void rejectsBlankTitle() throws Exception {
        mvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"   \"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsTitleLongerThan120Characters() throws Exception {
        String title = "x".repeat(121);
        String json = "{\"title\":\"" + title + "\"}";

        mvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void returns404ForUnknownTask() throws Exception {
        mvc.perform(get("/api/tasks/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void listsTasksAsJsonArray() throws Exception {
        mvc.perform(get("/api/tasks"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void rejectsMalformedJson() throws Exception {
        mvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{not-json"))
            .andExpect(status().isBadRequest());
    }
}
