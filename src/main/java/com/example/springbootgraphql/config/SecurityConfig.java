package com.example.springbootgraphql.config;

import com.example.springbootgraphql.filter.CorsFilter;
import com.example.springbootgraphql.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${jwt.secret:}")
    private String jwtSecret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        configureExpressionInterceptUrl(http);
        http.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtSecret));
        http.addFilterBefore(new CorsFilter(), BasicAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint((request, response, e) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format("{\"message\": \"%s\"}", "Unauthorized"));
        });
    }

    private void configureExpressionInterceptUrl(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authenticatedRegistry =
                http.authorizeRequests();

        authenticatedRegistry
                .antMatchers("/").permitAll()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/graphiql").permitAll()
                .antMatchers("/graphql").permitAll()
                .antMatchers("/user/authenticate").permitAll();

        authenticatedRegistry.anyRequest().authenticated().and();
    }
}
