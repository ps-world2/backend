package com.pskjeong.psk.problems.controller;

import com.pskjeong.psk.problems.service.ProblemAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class ProblemAdminController {
    private final ProblemAdminService problemAdminService;

    // TODO 어드민만 가능하게끔 수정
    @PostMapping("/baekjoon")
    public ResponseEntity<Integer> postBaekjoonProblems() {
        return ResponseEntity.ok().body(problemAdminService.postBaekjoonProblems());
    }
}
