package com.dmsdbj.library.controller;


import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.service.LoginService;
import com.dmsdbj.library.viewmodel.User;
import com.wordnik.swagger.annotations.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Path("/login")


@Api(value="/login",description = "the login API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-31T16:21:12.109+08:00")
public class LoginController  {
    // private final LoginService delegate ;


    @Inject
    private LoginService loginService;

    //登录--郭晶晶--2017年11月13日17:39:44
    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "login", notes = "", response = void.class, tags= "login" )
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input", response = void.class) })
    public ItooResult longin(@ApiParam(value = "" ,required=true) User user, @Context SecurityContext securityContext) throws NotFoundException {

       boolean flag =loginService.Login(user);
       if(flag == true){
           return ItooResult.build("0000","登录成功",flag);
       }
       return ItooResult.build("1111","登录失败",flag);
    }

}







