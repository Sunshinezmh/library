
package com.dmsdbj.library.facade;


import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.entity.TReservation;
import com.dmsdbj.library.viewmodel.Reservation;

import javax.ws.rs.NotFoundException;
import java.net.URISyntaxException;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-10-11T08:49:46.795+08:00")
public interface ReservationFacade {

     ItooResult deleteReservationes(String reservationIds);
     ItooResult getReservationByCondition(String condition,int pageSize,int pageNum);
     ItooResult getReservationNoCondition(int pageSize,int pageNum);
     ItooResult getReservationByuserId(String userId);
     ItooResult getReservationCount(String userId);
     ItooResult importReservation(Reservation form);
     ItooResult DownExcelGet();
     ItooResult ExportReservationGet();
     ItooResult updateReservation(Reservation body);

     ItooResult addreservetionByPC(Reservation form)throws NotFoundException,URISyntaxException;
     ItooResult addreservetionByMobile(TReservation form)throws NotFoundException,URISyntaxException;
}