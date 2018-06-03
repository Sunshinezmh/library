/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmsdbj.library.service;

import com.dmsdbj.library.entity.TAnnouncement;
import com.dmsdbj.library.repository.TAnnouncementRepository;

import javax.inject.Inject;
import java.util.List;

/**
 *
 * @author 10283_000
 */
public class OverviewService {

    @Inject
    private TAnnouncementRepository tAnnouncementRepository;

    /**
     * 根据类型查询声明内容 ——十一期 何红霞 2017年9月3日18:35:20
     *
     * @param type 1，本馆概况；2，读者须知；3，公告
     * @return
     */
    public List<TAnnouncement> searchingOverview(String type) {
        return tAnnouncementRepository.searchingOverview(type);
    }

}
