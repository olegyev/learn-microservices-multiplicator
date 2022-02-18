package learn.microservices.multiplicator.challenge.controller;

import learn.microservices.multiplicator.challenge.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.challenge.entity.ChallengeAttempt;
import learn.microservices.multiplicator.challenge.service.ChallengeService;
import learn.microservices.multiplicator.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    private JacksonTester<List<ChallengeAttempt>> jsonResultMultipleAttempts;

    @Test
    public void whenPostValidResult_thenStatusIsOk() throws Exception {
        // given
        User user = new User("user_testing_12121212");
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
    public void whenPostInvalidResult_thenStatusIsBadRequest() throws Exception {
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

    @Test
    public void whenGetAllByCorrectAlias_thenStatusIsOk() throws Exception {
        // given
        User user = new User("1", "user_testing_12121212");
        ChallengeAttempt expectedResponseOne = new ChallengeAttempt("1", user, 20, 30, 600, true, Calendar.getInstance().getTimeInMillis());
        ChallengeAttempt expectedResponseTwo = new ChallengeAttempt("2", user, 20, 30, 600, true, Calendar.getInstance().getTimeInMillis());
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by("timestamp").descending());
        given(challengeService.findByUserAlias(anyString(), eq(pageRequest)))
                .willReturn(List.of(expectedResponseOne, expectedResponseTwo));

        // when
        MockHttpServletResponse actualResponse = mvc.perform(
                get("/attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("alias", "user_testing_12121212")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(2))
                        .param("sort", "timestamp,desc")
        ).andReturn().getResponse();

        // then
        then(actualResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(actualResponse.getContentAsString()).isEqualTo(jsonResultMultipleAttempts.write(List.of(expectedResponseOne, expectedResponseTwo)).getJson());
    }

    @Test
    public void whenGetAllByNoAlias_thenStatusIsBadRequest() throws Exception {
        // given
        // No required parameter 'alias' is sent.

        // when
        MockHttpServletResponse actualResponse = mvc.perform(
                get("/attempts")
        ).andReturn().getResponse();

        // then
        then(actualResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}