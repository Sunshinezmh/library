package com.dmsdbj.library.app.config;

import org.apache.deltaspike.core.api.config.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SecurityConfig {

    @Inject
    @ConfigProperty(name = "security.authentication.jwt.secret")
    private String secret;

    @Inject
    @ConfigProperty(name = "security.authentication.jwt.tokenValidityInSeconds")
    private long tokenValidityInSeconds;

    @Inject
    @ConfigProperty(name = "security.authentication.jwt.tokenValidityInSecondsForRememberMe")
    private long tokenValidityInSecondsForRememberMe;

    /**
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @return the tokenValidityInSeconds
     */
    public long getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }

    /**
     * @return the tokenValidityInSecondsForRememberMe
     */
    public long getTokenValidityInSecondsForRememberMe() {
        return tokenValidityInSecondsForRememberMe;
    }

}
