package id.bangkit.facetrack.facetrack.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Component
@Data
public class JWTProperties {
    private String key;
    private Long accessTokenExpiration;
}
