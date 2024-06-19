package testproject.cryptoexchanger.config.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.OAuth2ErrorCodes
import org.springframework.security.oauth2.core.OAuth2Token
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator
import testproject.cryptoexchanger.config.security.OAuth2PasswordGrantAuthenticationConverter.Companion.PASSWORD_GRANT_TYPE
import java.util.stream.Collectors


class OAuth2PasswordGrantAuthenticationProvider(
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder,
    private val authorizationService: OAuth2AuthorizationService,
    private val tokenGenerator: OAuth2TokenGenerator<out OAuth2Token>,
) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val passwordGrantAuthenticationToken = authentication as OAuth2PasswordGrantAuthenticationToken

        // Ensure the client is authenticated
        val clientPrincipal = getAuthenticatedClientElseThrowInvalidClient(passwordGrantAuthenticationToken)
        val registeredClient = clientPrincipal.registeredClient

        if (registeredClient == null || !registeredClient.authorizationGrantTypes.contains(
                passwordGrantAuthenticationToken.grantType)
        ) {
            throw OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT)
        }
        val authorizedScopes: Set<String?>
        for (requestedScope in passwordGrantAuthenticationToken.scopes) {
            if (!registeredClient.scopes.contains(requestedScope)) {
                throw OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE)
            }
        }
        authorizedScopes = LinkedHashSet(passwordGrantAuthenticationToken.scopes)

        val providedUsername = passwordGrantAuthenticationToken.username
        val providedPassword = passwordGrantAuthenticationToken.password
        val userDetails: UserDetails = this.userDetailsService.loadUserByUsername(providedUsername)
        if (!this.passwordEncoder.matches(providedPassword, userDetails.password)) {
            throw OAuth2AuthenticationException("Invalid resource owner credentials")
        }
        val userPrincipal = UsernamePasswordAuthenticationToken(userDetails, providedPassword, userDetails.authorities)

        //Generate the access token
        val tokenContext: OAuth2TokenContext = DefaultOAuth2TokenContext.builder()
            .registeredClient(registeredClient)
            .principal(userPrincipal)
            .authorizationServerContext(AuthorizationServerContextHolder.getContext())
            .authorizedScopes(authorizedScopes)
            .tokenType(OAuth2TokenType.ACCESS_TOKEN)
            .authorizationGrantType(PASSWORD_GRANT_TYPE)
            .authorizationGrant(passwordGrantAuthenticationToken)
            .put("roles", userDetails.authorities.stream().map { obj -> obj.authority }.collect(Collectors.toSet()))
            .build()
        val generatedAccessToken: OAuth2Token = tokenGenerator.generate(tokenContext)!!

        val accessToken = OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
            generatedAccessToken.tokenValue, generatedAccessToken.issuedAt,
            generatedAccessToken.expiresAt, tokenContext.authorizedScopes)

        val tokenMetadata: MutableMap<String, Any> = HashMap()
        tokenMetadata["username"] = userDetails.username
        tokenMetadata["roles"] = userDetails.authorities.stream().map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.toSet())
        if (authorizedScopes.isNotEmpty()) {
            tokenMetadata["scopes"] = authorizedScopes
        }
        val authorization = OAuth2Authorization.withRegisteredClient(registeredClient)
            .principalName(userDetails.username)
            .authorizationGrantType(PASSWORD_GRANT_TYPE)
            .token(accessToken) { metadata -> metadata[OAuth2Authorization.Token.CLAIMS_METADATA_NAME] = tokenMetadata }
            .build()

        this.authorizationService.save(authorization)
        return OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken)
    }


    override fun supports(authentication: Class<*>?): Boolean {
        return OAuth2PasswordGrantAuthenticationToken::class.java.isAssignableFrom(authentication!!)
    }

    private fun getAuthenticatedClientElseThrowInvalidClient(authentication: Authentication): OAuth2ClientAuthenticationToken {
        var clientPrincipal: OAuth2ClientAuthenticationToken? = null
        if (OAuth2ClientAuthenticationToken::class.java.isAssignableFrom(authentication.principal.javaClass)) {
            clientPrincipal = authentication.principal as OAuth2ClientAuthenticationToken
        }
        if (clientPrincipal != null && clientPrincipal.isAuthenticated) {
            return clientPrincipal
        }
        throw OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT)
    }
}