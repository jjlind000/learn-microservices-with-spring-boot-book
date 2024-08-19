package microservices.book_java_22.multiplication.challenge;

import microservices.book_java_22.multiplication.user.User;
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

import lombok.extern.slf4j.Slf4j;

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
    private JacksonTester<ChallengeAttemptDTOValue> requestAttemptJson;
    @Autowired
    private JacksonTester<ChallengeAttempt> jsonResultAttempt;

    @Test
    void postValidResult() throws Exception {
// given
        User user = new User(1L, "john");
        long attemptId = 5L;
        ChallengeAttemptDTOValue challengeAttemptDTO = new ChallengeAttemptDTOValue(50, 70, "john", 3500);
        ChallengeAttempt checkedAttempt = new ChallengeAttempt(attemptId, user.getId(), 50, 70, 3500, true);
        given(challengeService
                .verifyAttempt(eq(challengeAttemptDTO))) // when this method is called with this argument
                .willReturn(checkedAttempt);  // return this response
// when
        String json = requestAttemptJson.write(challengeAttemptDTO).getJson();
        //log.info("json: {}", json);
        System.out.println("json = " + json);
        MockHttpServletResponse response = mvc.perform(
                        post("/attempts").contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andReturn().getResponse();     // given the response from the service
// then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonResultAttempt.write(
                        checkedAttempt        // check that the controller returns this json response
                ).getJson());
    }

    @Test
    void postInvalidResult() throws Exception {
// given an attempt with invalid input data
        ChallengeAttemptDTOValue attemptDTO = new ChallengeAttemptDTOValue(2000, -70, "john", 1);
// when
        MockHttpServletResponse response = mvc.perform(
                        post("/attempts").contentType(MediaType.APPLICATION_JSON)
                                .content(requestAttemptJson.write(attemptDTO).getJson()))
                .andReturn().getResponse();
// then
        then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
