package com.apollogix.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Import(SecurityProblemSupport.class)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final SecurityProblemSupport securityProblemSupport;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvc) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions ->
                        exceptions
                                .authenticationEntryPoint(securityProblemSupport)
                                .accessDeniedHandler(securityProblemSupport)
                )
                .authorizeHttpRequests(authorize ->
                        // prettier-ignore
                        authorize
                                .requestMatchers(mvc.pattern("/")).permitAll()
                                .requestMatchers(mvc.pattern("/api/v1/auth")).permitAll()
                                .requestMatchers(mvc.pattern("/actuator/health")).permitAll()
                                .requestMatchers(mvc.pattern("/actuator/health/**")).permitAll()
                                .requestMatchers(mvc.pattern("/actuator/info")).permitAll()
                                .requestMatchers(mvc.pattern("/actuator/prometheus")).permitAll()
                                .anyRequest().authenticated()

                ).authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public MvcRequestMatcher.Builder mvc(final HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
}
