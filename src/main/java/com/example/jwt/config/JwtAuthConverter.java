package com.example.jwt.config;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";

    @Value("${jwt.auth.converter.principal-attribute}")
    private String principalAttribute;

    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    @Nullable
    public AbstractAuthenticationToken convert(@SuppressWarnings("null") Jwt jwt) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()).collect(Collectors.toSet());

        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipal(jwt));
    }

    private String getPrincipal(Jwt jwt) {
        return jwt.getClaim(principalAttribute);
    }

    @SuppressWarnings("unchecked")
    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourcesRoles;
        if (jwt.getClaim("resource_access") == null) {
            return Set.of();
        }

        resourceAccess = jwt.getClaim("resource_access");

        if (resourceAccess.get(resourceId) == null) {
            return Set.of();
        }

        resource = (Map<String, Object>) resourceAccess.get(resourceId);

        resourcesRoles = (Collection<String>) resource.get(ROLES_CLAIM);

        return resourcesRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

}
