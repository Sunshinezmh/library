package com.dmsdbj.library.controller;

//import com.codahale.metrics.annotation.Timed;
import com.dmsdbj.library.app.config.MailConfig;
import com.dmsdbj.library.app.security.Secured;
import com.dmsdbj.library.app.security.SecurityUtils;
import com.dmsdbj.library.app.service.UserService;
import com.dmsdbj.library.app.service.mail.MailService;
import com.dmsdbj.library.controller.dto.KeyAndPasswordDTO;
import com.dmsdbj.library.controller.dto.ManagedUserDTO;
import com.dmsdbj.library.controller.dto.UserDTO;
import com.dmsdbj.library.controller.util.HeaderUtil;
import com.dmsdbj.library.entity.User;
import com.dmsdbj.library.repository.UserRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.*;

/**
 * REST controller for managing the current user's account.
 */
@Api(value = "/api")
@Path("/api")
public class AccountController {

    @Inject
    private Logger log;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private MailService mailService;

    @Inject
    private SecurityUtils securityUtils;

    @Inject
    private MailConfig mailConfig;

    @Context
    private HttpServletRequest request;

    /**
     * POST /register : register the user.
     *
     * @param managedUserDTO the managed user DTO
     * @return the Response with status 201 (Created) if the user is registered
     * or 400 (Bad Request) if the login or e-mail is already in use
     */
//    @Timed
    @ApiOperation(value = "register the user")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created")
        ,
        @ApiResponse(code = 400, message = "Bad Request")})
    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response registerAccount(@Valid ManagedUserDTO managedUserDTO) {

        return userRepository.findOneByLogin(managedUserDTO.getLogin().toLowerCase())
                .map(user -> Response.status(BAD_REQUEST).type(TEXT_PLAIN).entity("login already in use").build())
                .orElseGet(() -> userRepository.findOneByEmail(managedUserDTO.getEmail())
                .map(user -> Response.status(BAD_REQUEST).type(TEXT_PLAIN).entity("e-mail address already in use").build())
                .orElseGet(() -> {
                    User user = userService.createUser(managedUserDTO.getLogin(), managedUserDTO.getPassword(),
                            managedUserDTO.getFirstName(), managedUserDTO.getLastName(), managedUserDTO.getEmail().toLowerCase(),
                            managedUserDTO.getLangKey());
                    String baseUrl = request.getScheme()
                            + // "http"
                            "://"
                            + // "://"
                            request.getServerName()
                            + // "myhost"
                            ":"
                            + // ":"
                            request.getServerPort()
                            + // "80"
                            request.getContextPath();
                    // "/myContextPath" or "" if deployed in root context

                    if (mailConfig.isEnable()) {
                        mailService.sendActivationEmail(user, baseUrl);
                    }

                    return Response.status(CREATED).build();
                })
                );
    }

    /**
     * GET /activate : activate the registered user.
     *
     * @param key the activation key
     * @return the Response with status 200 (OK) and the activated user in body,
     * or status 500 (Internal Server Error) if the user couldn't be activated
     */
//    @Timed
    @ApiOperation(value = "activate the registered user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Path("/activate")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response activateAccount(@QueryParam("key") String key) {
        return userService.activateRegistration(key)
                .map(user -> Response.ok().build())
                .orElse(Response.status(INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /authenticate : check if the user is authenticated, and return its
     * login.
     *
     * @return the login if the user is authenticated
     */
//    @Timed
    @ApiOperation(value = "check if the user is authenticated")
    @Path("/authenticate")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @Secured
    public String isAuthenticated() {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET /account : get the current user.
     *
     * @return the Response with status 200 (OK) and the current user in body,
     * or status 500 (Internal Server Error) if the user couldn't be returned
     */
//    @Timed
    @ApiOperation(value = "get the current user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Path("/account")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Secured
    public Response getAccount() {
        return Optional.ofNullable(userService.getUserWithAuthorities())
                .map(user -> Response.ok(new UserDTO(user)).build())
                .orElse(Response.status(INTERNAL_SERVER_ERROR).build());
    }

    /**
     * POST /account : update the current user information.
     *
     * @param userDTO the current user information
     * @return the Response with status 200 (OK), or status 400 (Bad Request) or
     * 500 (Internal Server Error) if the user couldn't be updated
     */
//    @Timed
    @ApiOperation(value = "update the current user information")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 400, message = "Bad Request")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Path("/account")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Secured
    public Response saveAccount(@Valid UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findOneByEmail(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userDTO.getLogin()))) {
            return HeaderUtil.createFailureAlert(Response.status(BAD_REQUEST), "user-management", "emailexists", "Email already in use").build();
        }
        return userRepository
                .findOneByLogin(securityUtils.getCurrentUserLogin())
                .map(u -> {
                    userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                            userDTO.getLangKey());
                    return Response.ok().build();
                })
                .orElseGet(() -> Response.status(INTERNAL_SERVER_ERROR).build());
    }

    /**
     * POST /account/change_password : changes the current user's password
     *
     * @param password the new password
     * @return the Response with status 200 (OK), or status 400 (Bad Request) if
     * the new password is not strong enough
     */
//    @Timed
    @ApiOperation(value = "changes the current user's password")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 400, message = "Bad Request")})
    @Path("/account/change_password")
    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Secured
    public Response changePassword(String password) {
        if (!checkPasswordLength(password)) {
            return Response.status(BAD_REQUEST).entity("Incorrect password").build();
        }
        userService.changePassword(password);
        return Response.ok().build();
    }

    /**
     * POST /account/reset_password/init : Send an e-mail to reset the password
     * of the user
     *
     * @param mail the mail of the user
     * @return the Response with status 200 (OK) if the e-mail was sent, or
     * status 400 (Bad Request) if the e-mail address is not registred
     */
//    @Timed
    @ApiOperation(value = "Send an e-mail to reset the password")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 400, message = "Bad Request")})
    @Path("/account/reset_password/init")
    @POST
    @Produces({MediaType.TEXT_PLAIN})
    public Response requestPasswordReset(String mail) {
        return userService.requestPasswordReset(mail)
                .map(user -> {
                    String baseUrl = request.getScheme()
                            + "://"
                            + request.getServerName()
                            + ":"
                            + request.getServerPort()
                            + request.getContextPath();
                    mailService.sendPasswordResetMail(user, baseUrl);
                    return Response.ok("e-mail was sent").build();
                }).orElse(Response.status(BAD_REQUEST).entity("e-mail address not registered").build());
    }

    /**
     * POST /account/reset_password/finish : Finish to reset the password of the
     * user
     *
     * @param keyAndPassword the generated key and the new password
     * @return the Response with status 200 (OK) if the password has been reset,
     * or status 400 (Bad Request) or 500 (Internal Server Error) if the
     * password could not be reset
     */
//    @Timed
    @ApiOperation(value = "reset the password")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 400, message = "Bad Request")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Path("/account/reset_password/finish")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public Response finishPasswordReset(KeyAndPasswordDTO keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            return Response.status(BAD_REQUEST).entity("Incorrect password").build();
        }
        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
                .map(user -> Response.ok().build())
                .orElse(Response.status(INTERNAL_SERVER_ERROR).build());
    }

    private boolean checkPasswordLength(String password) {
        return (!StringUtils.isEmpty(password)
                && password.length() >= ManagedUserDTO.PASSWORD_MIN_LENGTH
                && password.length() <= ManagedUserDTO.PASSWORD_MAX_LENGTH);
    }
}
