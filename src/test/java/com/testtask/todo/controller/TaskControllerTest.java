package com.testtask.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.todo.dto.TaskDto;
import com.testtask.todo.entity.Priority;
import com.testtask.todo.entity.Status;
import com.testtask.todo.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:init_task.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskController taskController;

    private TaskDto taskDto;
    private TaskDto expected;

    @BeforeEach
    void setUp() {
        taskDto = TaskDto.builder()
                .name("task")
                .description("description")
                .status(Status.IN_PROGRESS)
                .priority(Priority.HIGH)
                .build();

        expected = TaskDto.builder()
                .id(1L)
                .name("Task1")
                .description("This is a task #1")
                .status(Status.IN_PROGRESS)
                .priority(Priority.MEDIUM)
                .build();
    }

    @Container
    public static PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
            new PostgreSQLContainer<>("postgres:13.6");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        POSTGRESQL_CONTAINER.start();

        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void createTask() throws Exception {
        String json = objectMapper.writeValueAsString(taskDto);
        MvcResult mvcResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();
        TaskDto actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TaskDto.class);
        assertEquals(6, actual.getId());
    }

    @Test
    void getTask() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/tasks/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();
        TaskDto actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TaskDto.class);
        assertEquals(expected, actual);
    }

    @Test
    void getTaskByName() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/tasks/name")
                        .param("name", "Task1"))
                .andExpect(status().isOk())
                .andReturn();
        TaskDto actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TaskDto.class);
        assertEquals(expected, actual);
    }

    @Test
    void updateTask() throws Exception {
        expected.setName("qwerty");
        expected.setId(2L);
        String json = objectMapper.writeValueAsString(expected);
        mockMvc.perform(post("/tasks/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        TaskDto actual = taskController.getTaskByName("qwerty");
        assertEquals("qwerty", actual.getName());
    }

    @Test
    void deleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/{id}", 5L))
                .andExpect(status().isOk());

        assertThrows(EntityNotFoundException.class, () -> taskController.getTask(5L));
    }

    @Test
    void getAllTasks() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/tasks/page/{offset}/limit/{limit}", 0, 3))
                .andExpect(status().isOk())
                .andReturn();
        TaskDto[] taskDtos = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TaskDto[].class);

        assertEquals(3, taskDtos.length);
    }
}