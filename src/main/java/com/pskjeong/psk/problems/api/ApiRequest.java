package com.pskjeong.psk.problems.api;

import com.pskjeong.psk.problems.domain.Problems;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public abstract class ApiRequest {
    protected int baekjoon = 0;
    abstract int getProblemNum(JSONObject res) throws JSONException;
    abstract String getProblemTitle(JSONObject res) throws JSONException;
    abstract String getProblemRank(JSONObject res) throws JSONException;
    abstract List<Problems> toEntity(JSONObject res) throws JSONException;
    public Object request(String targetURL) {
        try {
            System.out.println(targetURL);
            Thread.sleep(3000);
            URL url = new URL(targetURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setUseCaches(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                JSONObject responseJson = new JSONObject(sb.toString());
                return responseJson;
            } else {
                return "response error";
            }
        } catch (Exception e) {
            return "exception";
        }
    }
}
