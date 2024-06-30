package id.bangkit.facetrack.facetrack.dto.response;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class APIResponse {
    public static Map<String, Object> generateResponse(boolean status, String message, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", message);
        map.put("data", responseObj);
        return map;

    }
}
