package ooo.github.io.datacheck.config;

import lombok.Data;
import ooo.github.io.datacheck.dto.CheckConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author kaiqin
 */
@Data
@Configuration
@PropertySource(value = {"classpath:config.yml"}, factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "data-check")
public class ApplicationConfigSql {

    private CheckConfig[] list;

}


