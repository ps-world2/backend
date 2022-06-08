package com.pskjeong.psk.problems.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ProblemDto {

    @Getter
    @Builder
    public static class Info {
        private int platform;
        private String title;
        private int problemNum;
        private String rank;
    }
    @Getter
    @Builder
    public static class Response {
        private int result;
        private List<ProblemDto.Info> infos;
    }
}
