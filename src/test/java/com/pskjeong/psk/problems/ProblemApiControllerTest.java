package com.pskjeong.psk.problems;

import com.pskjeong.psk.problems.controller.ProblemController;
import com.pskjeong.psk.problems.dto.ProblemDto;
import com.pskjeong.psk.problems.service.ProblemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProblemController.class)
public class ProblemApiControllerTest {

    // ProblemController관련 빈만 로드한다.
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProblemService problemService;



    @DisplayName("[Problem Controller Test]")
    @Test
    public void 문제_불러오기_테스트() throws Exception {
        String offset = "0";
        String limit = "1";
        // given
        given(problemService.getProblems(0, 1)).willReturn(ProblemDto.Response.builder()
                        .cnt(12).build());

        // when
        final ResultActions actions = mvc.perform(get("/api/problems")
                        .param("offset", offset)
                        .param("limit", limit)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andDo(print());

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cnt").value(12))
                .andDo(print());
    }

}
