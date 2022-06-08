package com.pskjeong.psk.problems.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ProblemDto {

    @Getter
    @Setter
    public static class Info {
        private int platform;
        private String title;
        private int problemNum;
        private String rank;

        // ModelMapper를 사용하기 위해 기본 생성자 생성
        public Info() {
        }
    }
    @Getter
    @Builder
    public static class Response {
        private int cnt;
        private Long totalCnt;
        private List<ProblemDto.Info> infos;
    }
}
