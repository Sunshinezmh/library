package com.dmsdbj.library.service;
import com.dmsdbj.library.app.util.UUID.UuidUtils;
import com.dmsdbj.library.entity.TTeacher;
import com.dmsdbj.library.entity.TUser;
import com.dmsdbj.library.repository.TTeacherRepository;
import com.dmsdbj.library.repository.TUserRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author lyn
 * @date 2017-10-14 14:46:39
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017年10月31日19:22:24")
public class TeacherService {

    @Inject
    private TTeacherRepository tTeacherRepository;

    @Inject
    private UserService userService;

    @Inject
    private TUserRepository tUserRepository;

    /**
     * 模糊查询获取教师记录（模糊条件：教工号/姓名/职务/职称/权限）--刘雅雯--2017年10月31日19:27:05
     * @param condition
     * @return
     */
    public List<TTeacher> findTeacherByConditon(String condition){
        return tTeacherRepository.findTeacherByConditon(condition);
    }

    /**
     * 获取全部教师信息--刘雅雯--2017年11月1日02:18:22
     * @return
     */
    public List<TTeacher> findAllTeacher(){
        return tTeacherRepository.findAllTeacher();
    }

    /**
     * 根据teacherId删除老师--刘雅雯--2017年11月1日02:38:00
     * 同时删除user表中记录
     * @param teacherId
     */
    @Transactional(rollbackOn = Exception.class)
    public boolean deleteTeacher(String teacherId){
        TTeacher tTeacher=tTeacherRepository.findTeacherByCode(teacherId);
        if (tTeacher !=null){
            tTeacher.setIsDelete(1);
            tTeacherRepository.edit(tTeacher);
             //修改用户的状态为删除
            TUser tUser =tUserRepository.find(tTeacher.getId());// 找到这个实体
            if (tUser!=null ){
                tUser.setIsDelete(1);
                tUserRepository.edit(tUser);
            }
            return true;


        }
        return false;

    }

    /**
     * 根据教职工号获取教师实体--刘雅雯--2017年11月1日03:05:47
     * @param teacherId
     * @return
     */
    public TTeacher findTeacherByCode(String teacherId){
        return tTeacherRepository.findTeacherByCode(teacherId);
    }

    /**
     * 根据教职工号获取教师List--刘雅雯--2017年11月1日17:23:50
     * @param teacherId
     * @return
     */
    public List<TTeacher> findTeacherByCodeList(String teacherId){
        return tTeacherRepository.findTeacherByCodeList(teacherId);

    }

    /**
     * 添加一条教师记录--刘雅雯-2017年11月1日05:48:03
     * 同时更新user表，添加一条user记录
     * @param tTeacher
     */
    @Transactional(rollbackOn = Exception.class)
    public boolean addTeacher(TTeacher tTeacher){
        if (tTeacher!=null){

            //判断教职工号，不能重复--刘雅雯--2017年11月1日05:59:22
//            TTeacher  teacher =findTeacherByCode(tTeacher.getCode());

                //TODO 从前端中取出来
                tTeacher.setoperator("123");
                tTeacher.setId(UuidUtils.base58Uuid());

                //给user的id赋值
                TUser tUser =new TUser();
                tUser.setId(tTeacher.getId());
                tUser.setName(tTeacher.getName());

                tTeacherRepository.create(tTeacher);

               return userService.addUser(tUser.getId(),tUser.getName());

        }
        return false;

    }

    /**
     * 更新一条教师记录--刘雅雯--2017年11月1日05:52:12
     * @param tTeacher
     */
    public boolean updateTeacher(TTeacher tTeacher){
        if (tTeacher!=null)
        {
            tTeacherRepository.edit(tTeacher);
            TUser tUser =tUserRepository.find(tTeacher.getId());
            if (tUser !=null && tUser.getIsDelete()==0){
                tUser.setName(tTeacher.getName());
                tUserRepository.edit(tUser);
            }
            return  true;
        }else{
            return false;
        }

    }


}