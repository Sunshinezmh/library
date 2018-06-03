package com.dmsdbj.library.service;

import com.dmsdbj.library.app.util.UUID.UuidUtils;
import com.dmsdbj.library.entity.TStudent;
import com.dmsdbj.library.entity.TTeacher;
import com.dmsdbj.library.entity.TUser;
import com.dmsdbj.library.repository.TStudentRepository;
import com.dmsdbj.library.repository.TTeacherRepository;
import com.dmsdbj.library.repository.TUserRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-31T16:21:12.109+08:00")
public  class UserService {
    @Inject
    private TUserRepository tUserRepository;

    @Inject
    private TTeacherRepository tTeacherRepository;

    @Inject
    private TStudentRepository tStudentRepository;

    public boolean addUser(String Id,String name){
        TUser tUser = tUserRepository.find(Id);
        if(tUser !=null){
            return  false;
        }
        TUser user = new TUser();
        user.setId(Id);
        user.setName(name);
        user.setPwd("123");

        tUserRepository.create(user);
        return true;
    }

    /**
     * 修改密码--郭晶晶--2017年11月13日14:03:51
     * @param pwd
     * @return
     */
    public boolean updateTuser(String oldPwd,String newPwd ,String Id) {

        List<String> listId = new ArrayList<String>();
        //参数id指的是code(查询这个学号是否存在)
        TStudent student = tStudentRepository.getStudentById(Id);
        if(student != null){
            String TeacherID = student.getId();
            listId.add(TeacherID);
        }

        //参数id指的是code(查询这个教师是否存在)
        TTeacher teacher = tTeacherRepository.findTeacherByCode(Id);
        if(teacher != null){
            String StudentID = teacher.getId();
            listId.add(StudentID);
        }
//        System.out.println(teacher);
        //判断是否有该用户
        TUser user = tUserRepository.find(listId.get(0));

        if (user !=null){
            //密码不一致，返回false
           if(oldPwd.equals(user.getPwd())){
               user.setPwd(newPwd);
               Date date = new Date();
               user.setUpdateTime(date);
               tUserRepository.edit(user);
               return true;
           }else{
               return false;
           }
        }else{
            return false;

        }

    }

    /**
     * 注册--郭晶晶--2017年11月13日16:58:46
     * @param tUser
     * @return
     */
    public boolean addUser(TUser tUser) {

            tUser.setId(UuidUtils.base58Uuid());

            tUserRepository.create(tUser);
            return true;

    }

    /**
     * 登录--郭晶晶--2017-11-14 08:47:27
     * @param tUser
     * @return
     */
    public TUser login( String code,String pwd) {
        //判断是否有该用户
         //学生表里面查
        List<TStudent> studentsList =tStudentRepository.getStudentBystudentCode(code);
        if(studentsList !=null && studentsList.size()>0){
            String Id = studentsList.get(0).getId();
            //在t_user表中查找是否有该用户
            TUser tUser = tUserRepository.find(Id);
            String tUserPwd = tUser.getPwd();
            //判断密码是否对应
            if(pwd.equals(tUserPwd)){
                return tUser;
            }
        }

        //教师表里面查
        TTeacher teacher =tTeacherRepository.findTeacherByCode(code);
        if(teacher != null){
            String teacherId = teacher.getId();
            TUser user =tUserRepository.find(teacherId);
            String teacherPwd = user.getPwd();
            if (pwd .equals(teacherPwd)){
                return user;
            }
        }

        return null;
    }

    public TUser findUserInfoByCode( String code) {
        //判断是否有该用户
        //学生表里面查
        List<TStudent> studentsList =tStudentRepository.getStudentBystudentCode(code);
        if(studentsList !=null && studentsList.size()>0){
            String Id = studentsList.get(0).getId();
            //在t_user表中查找是否有该用户
            TUser tUser = tUserRepository.find(Id);
                return tUser;
        }

        //教师表里面查
        TTeacher teacher =tTeacherRepository.findTeacherByCode(code);
        if(teacher != null){
            String teacherId = teacher.getId();
            TUser user =tUserRepository.find(teacherId);
                return user;
        }


        return null;
    }

    /**
     * 退出--郭晶晶--2017年11月14日10:36:38---该用itoo的登录
     * @param id
     * @return
     */
    public  boolean logOut(String  id){
        TUser tUser = tUserRepository.find(id);
    if (tUser != null){
        Short online = 0;

//        tUser.setOnLine(online);
        Date date = new Date();
        tUser.setUpdateTime(date);
        tUserRepository.edit(tUser);
        return true;
    }
        return false;

    }
}
