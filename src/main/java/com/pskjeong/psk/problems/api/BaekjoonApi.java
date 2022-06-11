package com.pskjeong.psk.problems.api;

import com.pskjeong.psk.problems.domain.Problems;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BaekjoonApi extends ApiRequest{
    private final String targetURL = "https://solved.ac/api/v3/search/problem?query=";

    @Override
    int getProblemNum(JSONObject res) throws Exception {
        try {
            return (int) res.get("problemId");
        } catch (JSONException e) {
            throw new JSONException("getProblem() Json Exception Error");
        } catch (Exception e) {
            throw new Exception("getProblem() Error");
        }
    }

    @Override
    String getProblemTitle(JSONObject res) throws Exception {
        try {
            return (String) res.get("titleKo");
        } catch (JSONException e) {
            throw new JSONException("getProblemTitle() Json Exception Error");
        } catch (Exception e) {
            throw new Exception("getProblemTitle() Error");
        }
    }

    @Override
    String getProblemRank(JSONObject res) throws Exception {
        int tmpLevel;
        try {
            tmpLevel = (int) res.get("level");
        } catch (JSONException e) {
            throw new JSONException("getProblemRank() Json Exception Error");
        } catch (Exception e) {
            throw new Exception("getProblemRank() Error");
        }
        int tier = tmpLevel / 5;
        int level = tmpLevel % 5;
        String rank;
        switch (tier) {
            case 0:
                rank = "B" + (6 - level);
                break;
            case 1:
                rank = "S" + (6 - level);
                break;
            case 2:
                rank = "G" + (6 - level);
                break;
            case 3:
                rank = "P" + (6 - level);
                break;
            case 4:
                rank = "D" + (6 - level);
                break;
            case 5:
                rank = "R" + (6 - level);
                break;
            default:
                rank = "unknown";
                break;
        }
        return rank;
    }

    @Override
    public List<Problems> toEntity(JSONObject res) {
        try {
            JSONArray array = res.getJSONArray("items");
            List<Problems> problemsList = new ArrayList<>();
            for (int i=0; i<array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                problemsList.add(
                        Problems.builder()
                        .platform(baekjoon)
                        .problemNum(getProblemNum(jsonObject))
                        .title(getProblemTitle(jsonObject))
                        .rank(getProblemRank(jsonObject))
                        .build()
                );

            }
            return problemsList;
        } catch (JSONException e) {
            log.error("toEntity JsonError", e);
            return null;
        } catch (Exception e) {
            log.error("toEntity Error", e);
            return null;
        }
    }
    public List<Problems> baekjoonRequest() {
        List<Problems> result = new ArrayList<>();

        // query를 100개씩 불러오기 때문에 100개씩 요청
        int page = 1;
        JSONObject response;
        while (true) {
            response = (JSONObject) request(targetURL + "&page=" + page++);

            List<Problems> resEntity = toEntity(response);
            result.addAll(resEntity);
            if (resEntity.size() < 100) break;
        }

        return result;
    }
}
