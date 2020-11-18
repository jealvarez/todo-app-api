package org.jealvarez.todoapp.api.configuration;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.util.List;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
@KeycloakConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class KeycloakSecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

    private static final String[] AUTH_ALLOWED_LIST = List.of(
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources/**",
            "/configuration/ui/**",
            "/configuration/security/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/"
    ).toArray(String[]::new);

    public final KeycloakClientRequestFactory keycloakClientRequestFactory;

    public KeycloakSecurityConfiguration(final KeycloakClientRequestFactory keycloakClientRequestFactory) {
        this.keycloakClientRequestFactory = keycloakClientRequestFactory;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(keycloakAuthenticationProvider());
    }

    /*
     * Provide a session authentication strategy bean which should be of type
     * RegisterSessionAuthenticationStrategy for public or confidential applications
     * and NullAuthenticatedSessionStrategy for bearer-only applications.
     */
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Override
    protected KeycloakAuthenticationProvider keycloakAuthenticationProvider() {
        final KeycloakAuthenticationProvider keycloakAuthenticationProvider = super.keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper());
        return keycloakAuthenticationProvider;
    }

    @Override
    public void configure(final HttpSecurity httpSecurity) throws Exception {
        // @formatter:off
        httpSecurity
            .csrf()
                .disable()
            .authorizeRequests()
                .antMatchers(AUTH_ALLOWED_LIST).permitAll()
                .anyRequest().authenticated()
            .and()
                .oauth2ResourceServer()
                    .jwt()
                .and()
            .and()
                .exceptionHandling()
        ;
        // @formatter:on
    }

    @Bean
    public JwtDecoder jwtDecoder(@Value("${keycloak.auth-server-url}") final String authServerUrl,
                                 @Value("${keycloak.realm}") final String realm) {
        return NimbusJwtDecoder
                .withJwkSetUri(String.format("%s/realms/%s/protocol/openid-connect/certs", authServerUrl, realm))
                .build();
    }

    /*
     * Spring Security integration resolves the keycloak configuration from a keycloak.json file
     */
    @Bean
    public KeycloakConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return keycloakAuthenticationProvider();
    }

    @Bean
    public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(final KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter) {
        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(keycloakAuthenticationProcessingFilter);
        filterRegistrationBean.setEnabled(false);

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(final KeycloakPreAuthActionsFilter keycloakPreAuthActionsFilter) {
        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(keycloakPreAuthActionsFilter);
        filterRegistrationBean.setEnabled(false);

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean keycloakAuthenticatedActionsFilterBean(final KeycloakAuthenticatedActionsFilter keycloakAuthenticatedActionsFilter) {
        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(keycloakAuthenticatedActionsFilter);
        filterRegistrationBean.setEnabled(false);

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean keycloakSecurityContextRequestFilterBean(final KeycloakSecurityContextRequestFilter keycloakSecurityContextRequestFilter) {
        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(keycloakSecurityContextRequestFilter);
        filterRegistrationBean.setEnabled(false);

        return filterRegistrationBean;
    }

    @Bean
    @Override
    @ConditionalOnMissingBean(HttpSessionManager.class)
    protected HttpSessionManager httpSessionManager() {
        return new HttpSessionManager();
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public KeycloakRestTemplate keycloakRestTemplate() {
        return new KeycloakRestTemplate(keycloakClientRequestFactory);
    }

    private SimpleAuthorityMapper grantedAuthorityMapper() {
        final SimpleAuthorityMapper simpleAuthorityMapper = new SimpleAuthorityMapper();
        simpleAuthorityMapper.setConvertToLowerCase(true);
        simpleAuthorityMapper.setPrefix("role_");

        return simpleAuthorityMapper;
    }

}
