package learn.microservices.multiplicator.controller;

import learn.microservices.multiplicator.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.entity.ChallengeAttempt;
import learn.microservices.multiplicator.entity.User;
import learn.microservices.multiplicator.service.ChallengeService;
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

import java.util.Calendar;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(ChallengeAttemptController.class)
public class ChallengeAttemptControllerTest {

    @MockBean
    private ChallengeService challengeService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ChallengeAttemptDto> jsonRequestAttempt;

    @Autowired
    private JacksonTester<ChallengeAttempt> jsonResultAttempt;

    @Test
    void postValidResult() throws Exception {
        // given
        User user = new User("john_doe_testing_12121212");
        ChallengeAttemptDto requestDto = new ChallengeAttemptDto(20, 30, user.getAlias(), 600);
        ChallengeAttempt expectedResponse = new ChallengeAttempt(user, 20, 30, 600, true, Calendar.getInstance().getTimeInMillis());
        given(challengeService.verifyAttempt(eq(requestDto))).willReturn(expectedResponse);

        // when
        MockHttpServletResponse actualResponse = mvc.perform(
                post("/attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestAttempt.write(requestDto).getJson())
        ).andReturn().getResponse();

        // then
        then(actualResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(actualResponse.getContentAsString()).isEqualTo(jsonResultAttempt.write(expectedResponse).getJson());
    }

    @Test
    void postInvalidResult() throws Exception {
        // given
        ChallengeAttemptDto requestDto = new ChallengeAttemptDto(200, -1, "john_doe_testing_12121212", 1);

        // when
        MockHttpServletResponse actualResponse = mvc.perform(
                post("/attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestAttempt.write(requestDto).getJson())
        ).andReturn().getResponse();

        // then
        then(actualResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}