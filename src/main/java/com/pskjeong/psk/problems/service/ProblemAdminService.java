package com.pskjeong.psk.problems.service;

import com.pskjeong.psk.problems.api.BaekjoonApi;
import com.pskjeong.psk.problems.domain.Problems;
import com.pskjeong.psk.problems.domain.ProblemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProblemAdminService {

    private final ProblemsRepository problemsRepository;

    public int postBaekjoonProblems() {
        List<Problems> problems = new BaekjoonApi().baekjoonRequest();
        problemsRepository.saveAll(problems);
        return problems.size();
    }
}
