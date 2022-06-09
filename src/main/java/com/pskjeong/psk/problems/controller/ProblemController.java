package com.pskjeong.psk.problems.controller;

import com.pskjeong.psk.problems.dto.ProblemDto;
import com.pskjeong.psk.problems.service.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/problems")
@RestController
public class ProblemController {

    private final ProblemService problemService;

    @Operation(summary = "get problems", description = "문제들 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ProblemDto.Response.class))),
            @ApiResponse(responseCode = "400", description = "BAD Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Parameters({
            @Parameter(name = "offset", description = "가져올 시작 위치", example = "0"),
            @Parameter(name = "limit", description = "가져올 개수", example = "12")
    })
    @GetMapping("")
    public ResponseEntity<ProblemDto.Response> getProblems(
            @RequestParam(value = "offset") int offset,
            @RequestParam(value = "limit") int limit
    ) {
        return ResponseEntity.ok().body(problemService.getProblems(offset, limit));
    }
}
