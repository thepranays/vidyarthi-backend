package com.vidyarthi.APIGateway.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity // as api gateway is based on spring webflux project not spring mvc
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity){
           serverHttpSecurity
                   .csrf((csrfSpec -> csrfSpec.disable())).
                   authorizeExchange(( //Disable authentication only for /eureka/**
                           authorizeExchangeSpec -> authorizeExchangeSpec.pathMatchers("/ws/vidyarthi-ws-chat/**"
                                           ,"/api/product/get/**", //To allow non-authenticated user can see products but can't proceed for deal or edit or delete
                                           "/api/notification/event-product-created" //To allow non-authenticated user also sees newly listed products notification
                                           ,"/eureka/**" ).permitAll() //Discovery server monitoring web interface
                                   .anyExchange().authenticated()))
                   //^anyExchange(). authenticated() – anyRequest() defines a rule chain for any request which did not match the previous rules. In our case, such requests will be passed as long as they are authenticated. Please note that there can be only one default rule in the configuration, and it needs to be at the end.
                   .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(Customizer.withDefaults()));

           return serverHttpSecurity.build();

    }

}
