
package com.dmsdbj.library.service;

import com.dmsdbj.library.app.util.UUID.UuidUtils;
import com.dmsdbj.library.entity.TStudent;
import com.dmsdbj.library.entity.TUser;
import com.dmsdbj.library.repository.TStudentRepository;
import com.dmsdbj.library.repository.TUserRepository;
import com.dmsdbj.library.viewmodel.Student;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


public class StudentService {
    @Inject
    private TStudentRepository tStudentRepository;
   @Inject
   private TUserRepository tUserRepository;
   @Inject
   private TUser tUser;
    @Inject
    private UserService userService;


    /**
     * 王华伟--2017--10-14根据ID查询学生信息
     * @param userId
     * @return
     */
    public List<TStudent> getStudent(String userId) {
        return tStudentRepository.getStudent(userId);
    }

    /**
     * 查询全部学生信息--2017--11--16--王华伟
     * @return
     */
    public List<TStudent> getAllStudent() {
        List<TStudent> listStudent = tStudentRepository.getAllStudent();
        return listStudent;
    }

    /**
     * 根据学号查找学生-郭晶晶-2017年11月1日02:40:40
     * @param studentCode
     * @return 
     */
    public List<TStudent> getStudentBystudentCode(String studentCode) {
            return tStudentRepository.getStudentBystudentCode(studentCode);
    }

    public TStudent getStudentById(String userId){
        return tStudentRepository.getStudentById(userId);
    }

    /**
     * 根据学号集合查询学生信息 郑晓东 2017年11月10日21点15分
     * @param list 学号集合
     * @return 学生信息集合
     */
    public List<TStudent> getStudentByCodeList(List<String> list){
        return getStudentByCodeList(list);
    }

    /**
     *王华伟--2017--11-13-根据学号删除学生
     * @param ID
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public boolean deleteStudentByStudentCodes(String ID) {
        TStudent tStudent = tStudentRepository.find(ID);
        TUser tUser = tUserRepository.find(ID);

        //int onLine = tUser.getOnLine();

        if (tStudent== null && tUser == null){

            return false;

        }else{
            tUser.setIsDelete(1);
            tUserRepository.edit(tUser);
            tStudent.setIsDelete(1);
            tStudentRepository.edit(tStudent);
            return true;
        }

    }

    /**
     * 王华伟--2017--11--13--添加学生
     * @param tStudent
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public boolean addStudent(TStudent tStudent) {
        //查询是否有要添加的学生
        tStudent.setId(UuidUtils.base58Uuid());
        String studentName = tStudent.getName();
        String studentCode = tStudent.getCode();

        List<TStudent> student3 = tStudentRepository.getStudentBystudentCode(studentCode);

        //给user也添加一条数据
        TStudent student2 = tStudentRepository.find(tStudent.getId());
        if (student2 != null && student3.size()==0){
            return false;
        }else{
            //tStudent.setId(ID);
            //tStudent.setId(UuidUtils.base58Uuid());
            tStudent.setId(tStudent.getId());
            tStudent.setIsDelete(0);
            tStudentRepository.create(tStudent);
            String userId = tStudent.getId();

            //向user表添加数据
            tUser.setId(userId);
            tUser.setName(studentName);
            userService.addUser(tUser.getId(),tUser.getName());
//            tUser.setIsDelete(0);
//            tUserRepository.create(tUser);

            return true;
        }


    }

    /**
     * 王华伟--2017--11-14--编辑学生信息
     * @param tStudent
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public boolean updateStudent(TStudent tStudent) {
        if (tStudent != null){
            TUser user = tUserRepository.find(tStudent.getAlluserId());
            if (user !=null){
                    tStudentRepository.edit(tStudent);
                    user.setName(tStudent.getName());
                    tUserRepository.edit(user);
                    return true;
            }
            else{
                return false;
            }

        }else{
            return false;
        }
    }

    /**
     * 王华伟--2017--11--14--查询一级学院
     * @return
     */
    public ArrayList<Student> findParentCollege() {
        return tStudentRepository.findParentCollege();
    }

    /**
     * 王华伟--2017--11-6--模糊查询
     * @param conditions
     * @return
     */
    public List<TStudent> findStudentByCondition(String conditions) {
        return tStudentRepository.findStudentByCondition(conditions);

    }

//    public List<Student> findParentCollege1(String pid) {
//        List<Student> list = tStudentRepository.findParentCollege1(pid);
//        return list;
//
//    }
}





