package testproject.cryptoexchanger.config.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.lang.Nullable
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.web.authentication.AuthenticationConverter
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


class OAuth2PasswordGrantAuthenticationConverter: AuthenticationConverter {

    companion object {
        val PASSWORD_GRANT_TYPE = AuthorizationGrantType("password")
    }

    @Nullable
    override fun convert(request: HttpServletRequest): Authentication? {
        // grant_type (REQUIRED)
        val grantType: String = request.getParameter(OAuth2ParameterNames.GRANT_TYPE)
        if (PASSWORD_GRANT_TYPE.value != grantType) {
            return null
        }
        val clientPrincipal: Authentication = SecurityContextHolder.getContext().authentication
        val parameters: MultiValueMap<String, String> = getParameters(request)
        val scope: String = parameters.getFirst(OAuth2ParameterNames.SCOPE)!!
        val scopes = setOf(*scope.split(" ".toRegex()).toTypedArray())
        return OAuth2PasswordGrantAuthenticationToken(
            parameters.getFirst(OAuth2ParameterNames.USERNAME)!!,
            parameters.getFirst(OAuth2ParameterNames.PASSWORD)!!, clientPrincipal, scopes
        )
    }

    // from https://docs.spring.io/spring-authorization-server/docs/current/reference/html/guides/how-to-ext-grant-type.html
    private fun getParameters(request: HttpServletRequest): MultiValueMap<String, String> {
        val parameterMap: Map<String, Array<String>> = request.parameterMap
        val parameters: MultiValueMap<String, String> = LinkedMultiValueMap(parameterMap.size)
        parameterMap.forEach { (key: String?, values: Array<String>) ->
            if (values.isNotEmpty()) {
                for (value in values) {
                    parameters.add(key, value)
                }
            }
        }
        return parameters
    }

}