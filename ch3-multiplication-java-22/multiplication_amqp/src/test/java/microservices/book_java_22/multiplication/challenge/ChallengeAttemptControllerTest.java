package microservices.book_java_22.multiplication.challenge;

import microservices.book_java_22.multiplication.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(ChallengeAttemptController.class)
@Slf4j
class ChallengeAttemptControllerTest {
    @MockBean
    private ChallengeService challengeService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<ChallengeAttemptRequest> requestAttemptJson;
    @Autowired
    private JacksonTester<ChallengeAttempt> challengeAttemptJacksonTester;
    @Autowired
    private JacksonTester<List<ChallengeAttempt>> challengeAttemptsJacksonTester;
    @Mock
    private ChallengeAttempt challengeAttempt;
    private List<ChallengeAttempt> expected = Collections.singletonList(challengeAttempt);


    @Test
    void postValidResultTest() throws Exception {
// given
        User user = new User(1L, "john");
        long attemptId = 5L;
        ChallengeAttemptRequest challengeAttemptRequest = new ChallengeAttemptRequest(50, 70, "john", 3500);
        ChallengeAttempt checkedAttempt = new ChallengeAttempt(attemptId, user, 50, 70, 3500, true);
        given(challengeService
                .verifyAttempt(eq(challengeAttemptRequest))) // when this method is called with this argument
                .willReturn(checkedAttempt);  // return this response
// when
        String json = requestAttemptJson.write(challengeAttemptRequest).getJson();
        //log.info("json: {}", json);
        System.out.println("json = " + json);
        MockHttpServletResponse response = mvc.perform(
                        post("/attempts").contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andReturn().getResponse();     // given the response from the service
// then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                challengeAttemptJacksonTester.write(
                        checkedAttempt        // check that the controller returns this json response
                ).getJson());
    }

    @Test
    void postInvalidResultTest() throws Exception {
// given an attempt with invalid input data
        User user = new User(1L, "john");
        long attemptId = 5L;
        ChallengeAttemptRequest challengeAttemptRequest = new ChallengeAttemptRequest(2000, -70, "john", 1);
        ChallengeAttempt checkedAttempt = new ChallengeAttempt(attemptId, user, 2000, -70, 1, false);
        given(challengeService
                .verifyAttempt(eq(challengeAttemptRequest))) // when this method is called with this argument
                .willReturn(checkedAttempt);  // return this response

// when
        MockHttpServletResponse response = mvc.perform(
                        post("/attempts").contentType(MediaType.APPLICATION_JSON)
                                .content(requestAttemptJson.write(challengeAttemptRequest).getJson()))
                .andReturn().getResponse();
// then
        then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void retrieveLastAttemptsTest() throws Exception {
        // given
        String userAlias = "Max Bygraves";
        given(challengeService.getLastAttempts(userAlias))
                .willReturn(expected);
        // when
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                        .get("/attempts?userAlias=Max Bygraves").contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
        // then
        then(response.getContentAsString()).isEqualTo(challengeAttemptsJacksonTester.write(expected).getJson());
    }

    @Test
    void retrieveUserStatsTest() throws Exception {
        // given
        String userAlias = "Max Bygraves";
        given(challengeService.getStatsForUser(userAlias))
                .willReturn(expected);
        // when
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                        .get("/attempts/stats?userAlias=Max Bygraves"))
                        .andReturn().getResponse();
        // then
        then(response.getContentAsString()).isEqualTo(challengeAttemptsJacksonTester.write(expected).getJson());
    }
}
