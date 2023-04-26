package ooo.github.io.datacheck.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author kaiqin
 */
public class DataSourceConfig {

    @Primary
    @Bean
    public DataSource dataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

}
