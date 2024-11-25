package com.farm.test.json;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.farm.wcp.api.util.HttpUtils;

public class HttpTest {
	public static void main(String[] args) {
		JSONObject obj = HttpUtils.httpGet("http://192.168.9.44:8080/wcp/api/get/organization.do?id=402888a84fcb6d88014fcb70eb4d000e");
		System.out.println(obj);
		Map<String, String> map = new HashMap<>();
		map.put("id", "402888a84fcb6d88014fcb70eb4d000e");
		JSONObject obj2 = HttpUtils.httpPost("http://192.168.9.44:8080/wcp/api/get/organization.do", map);
		System.out.println(obj2);
	}
}
