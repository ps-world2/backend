package com.pskjeong.psk.problems.service;

import com.pskjeong.psk.problems.domain.Problems;
import com.pskjeong.psk.problems.domain.ProblemsRepository;
import com.pskjeong.psk.problems.dto.ProblemDto;
import com.pskjeong.psk.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProblemService {
    private final ProblemsRepository problemsRepository;

    @Transactional
    public ProblemDto.Response getProblems(int offset, int limit) {
        // pagenation으로 문제들을 불러온다.
        Page<Problems> results = problemsRepository.findAll(PageRequest.of(offset, limit));

        // entity to dto
        List<ProblemDto.Info> infoList = results.getContent().stream()
                .map(problems -> ModelMapperUtils.getInstance().map(problems, ProblemDto.Info.class))
                .collect(Collectors.toList());

        return ProblemDto.Response.builder().infos(infoList).cnt(infoList.size()).totalCnt(results.getTotalElements()).build();
    }
}
