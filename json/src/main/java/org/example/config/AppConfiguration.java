package org.example.config;

import org.example.repository.ManagerRepository;
import org.example.service.security.ManagerAuthService;
import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.client.RestClient;


@Configuration
public class AppConfiguration {

    @Bean
    public UserDetailsService userDetailsService(ManagerRepository repository) {
        return new ManagerAuthService(repository);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public JtsModule jtsModule() {
        return new JtsModule();
    }

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}
