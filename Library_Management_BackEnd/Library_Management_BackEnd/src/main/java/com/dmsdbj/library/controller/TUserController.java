package com.dmsdbj.library.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.entity.TUser;
import com.dmsdbj.library.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * REST controller for managing TUser.
 */
@Api(value = "user", description = "T User Controller")
@Path("user")
public class TUserController {

    @Inject
    private Logger log;

    @Inject
    private UserService userService;
    @POST
    @Path("/addTuser")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "注册", notes = "", response = void.class, tags = "andorid")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),
            @ApiResponse(code = 404, message = "not found", response = void.class),
            @ApiResponse(code = 405, message = "Validation exception", response = void.class)})
    public ItooResult addTuser(TUser tUser) throws NotFoundException {
        boolean flag = false;
        flag = userService.addUser(tUser);
        if(flag == true){
            return ItooResult.build("0000","注册成功",flag);
        }
        return ItooResult.build("1111","注册失败",flag);
    }

    @POST
    @Path("/updateTuser/{oldPwd}/{newPwd}/{Id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "修改密码", notes = "", response = void.class, tags = "andorid")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
            ,

            @ApiResponse(code = 404, message = "not found", response = void.class)
            ,

            @ApiResponse(code = 405, message = "Validation exception", response = void.class)})
    public ItooResult updateTuser(@PathParam("oldPwd") String oldPwd,@PathParam("newPwd")String newPwd ,@PathParam("Id") String Id ) throws NotFoundException {

        boolean flag =false;
        try{
            //Id=Id.replace(" ","");
            flag= userService.updateTuser(oldPwd,newPwd,Id);
            if(flag==true){
                return ItooResult.build("0000","修改密码成功",flag);
            }
            return ItooResult.build("1111","修改密码失败",flag);
        }catch (Exception e){
            log.error("修改密码失败",e);
            return ItooResult.build("1111","修改密码失败",flag);
        }

    }

    @POST
    @Path("/login/{code}/{pwd}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "登录", notes = "", response = void.class, tags = "andorid")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),
            @ApiResponse(code = 404, message = "not found", response = void.class),
            @ApiResponse(code = 405, message = "Validation exception", response = void.class)})
    public ItooResult login(@PathParam("code") String code, @PathParam("pwd") String  pwd) throws NotFoundException {
        TUser tUser;
        try{
            tUser = userService.login(code,pwd);
            if(tUser != null){
                return ItooResult.build("0000","登录成功",tUser);
            }
        }catch (Exception e){
            log.error("用户登录失败",e);
            return ItooResult.build("1111","登录失败");

        }
           return ItooResult.build("1111","登录失败");
    }
    @POST
    @Path("/findUserInfoByCode/{code}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "查询", notes = "", response = void.class, tags = "andorid")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),
            @ApiResponse(code = 404, message = "not found", response = void.class),
            @ApiResponse(code = 405, message = "Validation exception", response = void.class)})
    public ItooResult findUserInfoByCode(@PathParam("code") String code) throws NotFoundException {
        TUser tUser;
        try{
            tUser = userService.findUserInfoByCode(code);
            if(tUser != null){
                return ItooResult.build("0000","查询学生信息成功",tUser);
            }
        }catch (Exception e){
            log.error("查询学生信息失败",e);
            return ItooResult.build("1111","查询学生信息失败");

        }
        return ItooResult.build("1111","查询学生信息失败");
    }

    @POST
    @Path("/logOut/{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "退出", notes = "", response = void.class, tags = "andorid")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),
            @ApiResponse(code = 404, message = "not found", response = void.class),
            @ApiResponse(code = 405, message = "Validation exception", response = void.class)})
    public ItooResult logout(@PathParam("id") String id) throws NotFoundException {
        boolean flag = userService.logOut(id);
        if(flag == true){

            return ItooResult.build("0000","退出成功",flag);
        }
        return ItooResult.build("1111","退出失败",flag);
    }



}
