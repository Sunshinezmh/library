
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmsdbj.library.service;

import com.dmsdbj.library.entity.TMessageRecord;
import com.dmsdbj.library.repository.TMessageRecordRepository;
import com.dmsdbj.library.repository.TMessageTemplateRepository;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author 10283_000
 */
public class MessageService {
    @Inject
    private TMessageRecordRepository tMessageRecordRepository;
    @Inject
    private TMessageTemplateRepository tMessageTemplateRepository;

    /**
     * 1,查看消息:  getMessage-何新生-2017-9-2
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<TMessageRecord> getMessage(String userId){
        if(userId==null||userId.isEmpty()){
            return Collections.emptyList();
        }else {
            List<TMessageRecord> template = tMessageRecordRepository.getMessage(userId);

//            String templeId = template.get(0).getMessageTemplateId();
//            List<TMessageTemplate> contentList = tMessageTemplateRepository.queryContentBytemplateId(templeId);
//
//            List<Message> list = new ArrayList<>();
//            list.get(0).setContent(contentList.get(0).getContent());

            return  template;
        }
    }
    
}
