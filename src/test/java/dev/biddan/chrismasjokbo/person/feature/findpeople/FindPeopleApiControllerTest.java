package dev.biddan.chrismasjokbo.person.feature.findpeople;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.biddan.chrismasjokbo.person.domain.Person;
import dev.biddan.chrismasjokbo.person.domain.Person.Birthdate;
import dev.biddan.chrismasjokbo.person.domain.SexType;
import dev.biddan.chrismasjokbo.person.feature.PersonInfo;
import dev.biddan.chrismasjokbo.person.feature.createperson.CreatePersonService;
import dev.biddan.chrismasjokbo.person.feature.findpeople.FindPeopleService.FindPeopleInfo;
import dev.biddan.chrismasjokbo.person.feature.findpeople.FindPeopleService.FindPeopleQuery;
import java.util.ArrayList;
import java.util.List;
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
class FindPeopleApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FindPeopleService findPeopleService;

    @DisplayName("이름과 생년월일과 일치하는 사람들을 찾는다")
    @Test
    void findPeople() throws Exception {
        FindPeopleQuery query = FindPeopleQuery.builder()
                .firstName("유")
                .lastName("원우")
                .birthdate("2000-01-01")
                .build();

        PersonInfo personInfo = PersonInfo.builder()
                .firstName(query.firstName())
                .lastName(query.lastName())
                .birthdate(query.birthdate())
                .sex("남성")
                .features(new ArrayList<>())
                .build();
        FindPeopleInfo peopleInfo = new FindPeopleInfo(List.of(personInfo));

        given(findPeopleService.findByNameAndBirthdate(any(FindPeopleQuery.class)))
                .willReturn(peopleInfo);

        mockMvc.perform(get("/people")
                        .param("firstName", query.firstName())
                        .param("lastName", query.lastName())
                        .param("birthdate", query.birthdate()))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(peopleInfo)))
                .andDo(print());
    }

}
