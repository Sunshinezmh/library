package com.dmsdbj.library.controller;

//import com.codahale.metrics.annotation.Timed;
import com.dmsdbj.library.app.config.Constants;
import com.dmsdbj.library.app.security.AuthenticationException;
import com.dmsdbj.library.app.security.UserAuthenticationToken;
import com.dmsdbj.library.app.security.jwt.JWTToken;
import com.dmsdbj.library.app.security.jwt.TokenProvider;
import com.dmsdbj.library.app.service.UserService;
import com.dmsdbj.library.controller.dto.LoginDTO;
import com.dmsdbj.library.entity.User;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Api(value = "/api")
@Path("/api")
public class UserJWTController {

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private UserService userService;

    /**
     * POST /authenticate : authenticate the credential.
     * <p>
     * Authenticate the user login and password.
     * </p>
     *
     * @param loginDTO the login details to authenticate
     * @return the Response with status 200 (OK) and with body the new jwt
     * token, or with status 401 (Unauthorized) if the authentication fails
     */
//    @Timed
    @ApiOperation(value = "authenticate the credential")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 401, message = "Unauthorized")})
    @Path("/authenticate")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginDTO loginDTO) throws ServletException {

        UserAuthenticationToken authenticationToken = new UserAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        try {
            User user = userService.authenticate(authenticationToken);
            boolean rememberMe = (loginDTO.isRememberMe() == null) ? false : loginDTO.isRememberMe();
            String jwt = tokenProvider.createToken(user, rememberMe);
            return Response.ok(new JWTToken(jwt)).header(Constants.AUTHORIZATION_HEADER, "Bearer " + jwt).build();
        } catch (AuthenticationException exception) {
            return Response.status(Status.UNAUTHORIZED).header("AuthenticationException", exception.getLocalizedMessage()).build();
        }
    }

}
