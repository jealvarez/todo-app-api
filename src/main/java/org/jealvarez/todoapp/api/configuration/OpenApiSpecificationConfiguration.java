package org.jealvarez.todoapp.api.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.OAUTH2;

@OpenAPIDefinition(
        info = @Info(
                title = "Todo App API",
                version = "1.0",
                description = "Handle tasks to be done"
        )
)
@SecurityScheme(name = "bearer",
                type = OAUTH2,
                bearerFormat = "JWT",
                flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                        scopes = @OAuthScope(name = "openid"),
                        authorizationUrl = "${keycloak.auth-server-url}/realms/${keycloak.realm}/protocol/openid-connect/auth",
                        tokenUrl = "${keycloak.auth-server-url}/realms/${keycloak.realm}/protocol/openid-connect/token"))
)
@Configuration
public class OpenApiSpecificationConfiguration {

    @Controller
    public static class OpenApiSpecificationController {

        @GetMapping
        public String redirectToOpenApiSpecificationUi() {
            return "redirect:/swagger-ui.html";
        }

    }

}
