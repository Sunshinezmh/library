package com.dmsdbj.library.service;


import com.dmsdbj.library.entity.TUser;
import com.dmsdbj.library.repository.TUserRepository;
import com.dmsdbj.library.viewmodel.User;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-31T16:21:12.109+08:00")
public  class LoginService {

    @Inject
    private org.slf4j.Logger log;
    @Inject
    private TUserRepository tUserRepository;

    /**
     * 登录---Longin---何新生-2017-9-16
     * @param
     * @return
     * @throws Exception
     */
    public boolean Login(User user) {

        //通过userId登录
        String userId = user.getUserId();
        String password = user.getPwd();

        if (user==null||  user.getUserId().isEmpty() && user.getPwd().isEmpty()&&user.getUserId()==null&&user.getPwd()==null) {
            log.error("请输入账号或密码");
            return false;
        } else {
            List<TUser> userList = tUserRepository.queryUserById(userId);
            if (user.getPwd().equals(userList.get(0).getPwd())) {
                return true;
            } else {
                log.error("密码不正确,请重新输入!");
            }
        }
        return false;
    }


    public  List<TUser> login(User user){
        String userId=user.getUserId();
        List<TUser> userList=tUserRepository.queryUserById(userId);

        return userList;
    }

    public  List<TUser>queryUserById(String userId, String pwd){
        List<TUser> userList=tUserRepository.queryUserById(userId);
        String a = userList.get(0).getPwd();
        if(pwd.equals(a)){
            return userList;
        }
        log.error("密码错误,请重新输入!");
        return Collections.emptyList();
    }

}
