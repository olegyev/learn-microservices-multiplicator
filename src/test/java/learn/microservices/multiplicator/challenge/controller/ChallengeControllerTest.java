package learn.microservices.multiplicator.challenge.controller;

import learn.microservices.multiplicator.challenge.entity.Challenge;
import learn.microservices.multiplicator.challenge.service.ChallengeGeneratorService;
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

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(ChallengeController.class)
class ChallengeControllerTest {

    @MockBean
    ChallengeGeneratorService challengeGeneratorService;

    @Autowired
    MockMvc mvc;

    @Autowired
    JacksonTester<Challenge> jsonResponse;

    @Test
    void whenGetRandomChallenge_thenResponseIsCorrect() throws Exception {
        // given
        Challenge generatedChallenge = new Challenge(30, 40);
        given(challengeGeneratorService.generateRandomChallenge()).willReturn(generatedChallenge);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/challenges/random")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        then(response.getContentAsString()).isEqualTo(jsonResponse.write(generatedChallenge).getJson());
    }

}