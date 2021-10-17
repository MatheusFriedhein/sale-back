package br.com.saleback.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ConfigurationProperties("spring")
@Validated
public class SaleProperties {

    private String allowed_origins = "*";
    private String allowed_headers = "*";
    @NotNull
    @Valid
    private DataSource dataSource;

    @Data
    public static class DataSource {
        @NotEmpty
        private String username;
        private String password;
        @NotEmpty
        private String url;
        @NotEmpty
        private String driver;
        private int maxPoolSize = 15;
    }
}
