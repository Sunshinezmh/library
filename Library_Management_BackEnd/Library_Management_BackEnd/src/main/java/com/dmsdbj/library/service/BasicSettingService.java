package com.dmsdbj.library.service;

import com.dmsdbj.library.entity.TSetting;
import com.dmsdbj.library.repository.TSettingRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lyn on 2017/11/8.
 */
public class BasicSettingService {


    @Inject
    private TSettingRepository tSettingRepository;


    public List<TSetting> getAlltSettings() {
        List<TSetting> list = tSettingRepository.getAlltSettings();
        return list;
    }
    
    public boolean updateSetting(TSetting setting) {

       tSettingRepository.edit(setting);
       return true;
    }
	   //添加信息-王雪芬-2018年4月5日11:51:31
    public boolean addSetting(TSetting setting) {
        tSettingRepository.create(setting);
        return true;
    }

	
}
