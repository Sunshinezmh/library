package com.dmsdbj.library.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.entity.TBorrowingModel1;
import com.dmsdbj.library.entity.TSetting;
import com.dmsdbj.library.service.BasicSettingService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing TSetting.
 */
@Api(value = "/basicSetting", description = "T Setting Controller")
@Path("/basicSetting")
public class TSettingController {

    @Inject
    private Logger log;

    @Inject
    private BasicSettingService basicSettingService;



    /**
     * PUT : Updates an existing TSetting.
     *
     * @return the Response with status 200 (OK) and with body the updated
     * TSetting, or with status 400 (Bad Request) if the TSetting is not valid,
     * or with status 500 (Internal Server Error) if the TSetting couldn't be
     * updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Path("/updateSetting")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "update TSetting", notes = "Updates an existing TSetting")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK")
        ,
        @ApiResponse(code = 400, message = "Bad Request")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @POST

    public ItooResult updateSetting(TSetting setting) throws URISyntaxException {
        log.debug("REST request to update TSetting : {}", setting);
        boolean flag = basicSettingService.updateSetting(setting);
        if(flag == true){
            return  ItooResult.build("0000","更新成功",flag);
        }else {
            return ItooResult.build("1111","更新失败",flag);
        }
    }
	
    /**
     * PUT : Updates an existing TSetting.
     *
     * @return the Response with status 200 (OK) and with body the updated
     * TSetting, or with status 400 (Bad Request) if the TSetting is not valid,
     * or with status 500 (Internal Server Error) if the TSetting couldn't be
     * updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @Path("/addSetting")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "create TSetting", notes = "Updates an existing TSetting")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
            ,
            @ApiResponse(code = 400, message = "Bad Request")
            ,
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @POST
    public ItooResult addSetting(TSetting setting) throws URISyntaxException {

        log.debug("REST request to update TSetting : {}", setting);
        boolean flag = basicSettingService.addSetting(setting);
        if(flag == true){
            return  ItooResult.build("0000","添加成功",flag);
        }else {
            return ItooResult.build("1111","添加失败",flag);
        }
    }




    @GET
    @Path("/getAlltSettings")

    @Produces({"application/json"})
    @ApiOperation(value = "根据借阅状态查找借阅记录", notes = "", response = TBorrowingModel1.class, responseContainer = "List", tags = "User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok", response = TBorrowingModel1.class),
            @ApiResponse(code = 400, message = "not found", response = TBorrowingModel1.class)})
    public ItooResult getAlltSettings() {
        log.debug("REST request to get all tSettings");
        List<TSetting> list = basicSettingService.getAlltSettings();
        if(list != null && list.size()>0){
            return  ItooResult.build("0000","查询全部设置成功",list);
        }else {
            return ItooResult.build("1111","查询全部设置失败或没有数据");
        }

    }


}
