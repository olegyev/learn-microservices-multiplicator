package learn.microservices.multiplicator.user.controller;

import learn.microservices.multiplicator.user.entity.User;
import learn.microservices.multiplicator.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<List<User>> jsonResponse;

    @Test
    void whenGetByCorrectIdList_thenReturnsCorrectResults() throws Exception {
        // given
        User user1 = new User("1", "first_user");
        User user2 = new User("2", "second_user");
        given(userService.findAllByIdIn(List.of("1", "2"))).willReturn(List.of(user1, user2));

        // when
        MockHttpServletResponse actualResponse = mvc.perform(
                get("/users/{idList}", "1, 2")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // then
        then(actualResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(actualResponse.getContentAsString()).isEqualTo(jsonResponse.write(List.of(user1, user2)).getJson());
    }

}