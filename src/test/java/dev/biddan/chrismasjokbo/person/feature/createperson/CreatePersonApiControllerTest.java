package dev.biddan.chrismasjokbo.person.feature.createperson;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.biddan.chrismasjokbo.person.feature.createperson.CreatePersonService.CreatePersonCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class CreatePersonApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreatePersonService createPersonService;

    @DisplayName("개인을 등록한다")
    @Test
    void create_success() throws Exception {
        String requestJson = """
                {
                    "firstName": "유",
                    "lastName": "원우",
                    "createPersonFeatures": [
                        {
                            "name": "취미",
                            "description": "축구"
                        },
                        {
                            "name": "거주지",
                            "description": "산본"
                        }
                    ],
                    "sex": "남성",
                    "phoneNumber": "010-3333-5555"
                }
                """;
        Long expectedNewPersonId = 123L;

        given(createPersonService.create(any(CreatePersonCommand.class)))
                .willReturn(expectedNewPersonId);

        String expectedResponseJson = """
                {
                    "newPersonId": %d
                }
                """.formatted(expectedNewPersonId);

        mockMvc.perform(post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResponseJson))
                .andDo(print());
    }
}
