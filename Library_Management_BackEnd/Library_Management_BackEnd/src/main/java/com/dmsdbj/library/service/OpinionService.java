/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmsdbj.library.service;

import com.dmsdbj.library.entity.TOpinion;
import com.dmsdbj.library.repository.TOpinionRepository;
import com.dmsdbj.library.viewmodel.Opinion;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author 何红霞
 */
public class OpinionService {

    @Inject
    private TOpinionRepository TOpinionRepository;

    /**
     * 根据意见状态查询意见 ——十一期 何红霞 2017年9月3日08:56:02
     *
     * @param status 意见状态
     * @return
     */
    public List<TOpinion> findOpinionByStatus(List<String> status) {
        String state = status.get(0).trim();
        if ("全部".equals(state)) {
            return TOpinionRepository.findAll();
        } else {
            return TOpinionRepository.findOpinionByStatus(state);
        }
    }

    /**
     * 根据用户Id查询意见 ——十一期 何红霞 2017年9月3日08:55:35
     *
     * @param userId 用户id
     * @return
     */
    public List<TOpinion> searchingOpinion(String userId) {
        return TOpinionRepository.findOpinionByUserId(userId);
    }

    /**
     * 新增一条意见 ——十一期 何红霞 2017年9月3日09:37:35
     *
     * @param opinion 页面的viewmodel
     */
    public void addOpinion(Opinion opinion) {
        TOpinion tOpinion = new TOpinion();
        tOpinion.setUpdateTime(opinion.getDate());
        tOpinion.setContent(opinion.getContent());
        tOpinion.setIsDelete(0);
        tOpinion.setStatus("待解决");
        // TODO  应该从前端传，或者从缓存取 
        tOpinion.setoperator("管理员");     
        tOpinion.setUserId("2");
        tOpinion.setId(UUID.randomUUID().toString().replace("-", ""));
        TOpinionRepository.create(tOpinion);
    }
}
