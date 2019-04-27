package com.aries.user.gaea.client.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class GaeaResponse<T> {
    private int code;
    private String type;
    private String message;
    private T data;

    public GaeaResponse() {
    }

    public GaeaResponse(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public GaeaResponse(int code, String type, String msg) {
        this.code = code;
        this.type = type;
        this.message = msg;
    }

    public static GaeaResponse ok() {
        return new GaeaResponse(0, "ok", "ok");
    }

    public static <T> GaeaResponse ok(T data) {
        GaeaResponse response = new GaeaResponse(0, "ok", "ok");
        response.setData(data);
        return response;
    }

    public static GaeaResponse success() {
        GaeaResponse response = new GaeaResponse();
        JSONObject res = new JSONObject();
        res.put("success", 0);
        response.setData(res);
        return response;
    }

    public static GaeaResponse error(int code, String msg) {
        GaeaResponse response = new GaeaResponse(code, "error", msg);
        return response;
    }

    public static JSONObject wrapData(Object obj) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", obj);
        return jsonObject;
    }

    public JSONObject wrapData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", this.data);
        return jsonObject;
    }
}
