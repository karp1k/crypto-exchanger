package testproject.cryptoexchanger.config.security

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken

class OAuth2PasswordGrantAuthenticationToken(
    val username: String,
    val password: String,
    val clientPrincipal: Authentication,
    val scopes: Set<String>,
) : OAuth2AuthorizationGrantAuthenticationToken(OAuth2PasswordGrantAuthenticationConverter.PASSWORD_GRANT_TYPE,
    clientPrincipal,
    null) {


}