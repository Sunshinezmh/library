
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmsdbj.library.service;

import com.dmsdbj.library.app.util.UUID.UuidUtils;
import com.dmsdbj.library.entity.TDonate;
import com.dmsdbj.library.repository.TDonateRepository;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author 何新生
 */
public class DonateService {

    @Inject
    private TDonateRepository tDonateRepository;

    /**
     * 1,模糊查询所有捐赠书目(分页):  getDonate-何新生-2017-9-2
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<TDonate> getDonate(String userId){
        if(userId==null||userId.isEmpty()){
            return Collections.emptyList();
        }else {
            return tDonateRepository.getDonate(userId);
      }
    }

    /**
     * 2,捐赠书:  addDonate-何新生-2017-9-2
     * @param tDonate
     * @return
     * @throws Exception
     */
    public void addDonate(TDonate tDonate){
         tDonate.setId(UuidUtils.base58Uuid());
 //       tDonate.setId(uuid.randomUUID().toString().replace("-", ""));
//        tDonate.setDonaterType(donate.getDonateType().toString());
//        tDonate.setName(donate.getName());
//        tDonate.setDonCount(donate.getCount().toString());
//        tDonate.setDonateName(donate.getDonateName());
//        tDonate.setAuditReason(donate.getReason());

        tDonateRepository.create(tDonate);
    }

    /**
     * 3,根据状态查询捐赠的书:  findByStatus-何新生-2017-9-2
     *
     * @param status
     * @return
     * @throws Exception
     */
    public List<TDonate> findByStatus(String status){
        if((status==null || status.isEmpty())){
            return Collections.emptyList();
        }else {
            return tDonateRepository.findByStatus(status);
        }
    }
}
