package com.pskjeong.psk.problems.api;

import com.pskjeong.psk.problems.domain.Problems;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class BaekjoonApi extends ApiRequest{
    private String targetURL = "https://solved.ac/api/v3/search/problem?query=";

    @Override
    int getProblemNum(JSONObject res) throws JSONException {
        return (int) res.get("problemId");
    }

    @Override
    String getProblemTitle(JSONObject res) throws JSONException {
        return (String) res.get("titleKo");
    }

    @Override
    String getProblemRank(JSONObject res) throws JSONException {
        int tmpLevel = (int) res.get("level");
        int tier = tmpLevel / 5;
        int level = tmpLevel % 5;
        String rank = null;
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
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public List<Problems> baekjoonRequest() {
        JSONObject response = (JSONObject) request(targetURL);
        return toEntity(response);
    }
}
