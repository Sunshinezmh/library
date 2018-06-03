package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TStudent;
import com.dmsdbj.library.viewmodel.Student;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class TStudentRepository extends AbstractRepository<TStudent, String>{

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager(){
	return em;
    }

    public TStudentRepository(){
	super(TStudent.class);
    }

    /**
     * 王华伟--2017--10-15--根据ID查询学生信息
     * @param userId
     * @return
     */
    public List<TStudent> getStudent(String userId) {
        TypedQuery<TStudent> query = em.createNamedQuery("TStudent.getStudent",TStudent.class);
        query.setParameter("userId",userId);
        return query.getResultList();
    }

    public List<TStudent> getAllStudent() {
        TypedQuery<TStudent> query = em.createNamedQuery("TStudent.getAllStudent",TStudent.class);
       // query.setParameter("",);
        return query.getResultList();
    }

    public List<TStudent> getStudentBystudentCode(String studentCode) {
        TypedQuery<TStudent> query = em.createNamedQuery("TStudent.getStudentBystudentCode",TStudent.class);
        query.setParameter("studentCode",studentCode);
        return query.getResultList();
    }

    /**
     * 根据ID查询学生的信息
     * @param code
     * @return
     */
    public TStudent getStudentById(String code) {
        TypedQuery<TStudent> query = em.createQuery("select s from TStudent s where s.code = :code",TStudent.class);
        query.setParameter("code",code);
        List<TStudent> list = query.getResultList();
        if(list!= null && list.size() >0 ){
            return query.getResultList().get(0);
        }
        return null;
//        TStudent student = query.getSingleResult();
//        return student;
    }

    /**
     * 根据学号集合查询学生信息 郑晓东 2017年11月10日21点12分
     * @param codeList 学号集合
     * @return 学生信息集合
     */
    public List<TStudent> getStudentByCodeList(List<String> codeList){
        TypedQuery<TStudent> query = em.createNamedQuery("TStudent.getStudentByCodeList",TStudent.class);
        query.setParameter("codeList",codeList);
        return query.getResultList();
    }

    /**
     * 王华伟--2017--11--14--查询一级学院
     * @return
     */
    public ArrayList<Student> findParentCollege() {
        ArrayList<Student> studentList =new ArrayList<>();
        try {
            Query query=em.createQuery("select DISTINCT o.professionName,o.classesName from TStudent o where o.isDelete=0");
            Query queryProfession =em.createQuery("select DISTINCT o.professionName from TStudent o where o" +
                    ".isDelete=0");
            List listProfession = queryProfession.getResultList();
            List list= query.getResultList();
            if (listProfession!=null){
                for (int i=0;i< listProfession.size();i++){
                    Student studentModel =new Student();
                    studentModel.setId(listProfession.get(i).toString());
                    studentModel.setpId("0");
                    studentModel.setName(listProfession.get(i).toString());
                    studentList.add(studentModel);
                }
            }
            String[][] strList;
            if (list != null) {
                strList=new String[list.size()][];
                for (int i = 0; i < list.size(); i++) {
                    Student studentModel =new Student();
                    Object[] str=(Object[])list.get(i);
                    strList[i]=new String[str.length];
                    strList[i][0]=str[0].toString();
                    strList[i][1]=str[1].toString();
                    studentModel.setpId(strList[i][0]);
                    studentModel.setId(strList[i][1]);
                    studentModel.setName(strList[i][1]);
                    studentList.add(studentModel);

                }

            }else{
                strList=null;
            }
            System.out.println(strList);
            return studentList;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 模糊查询获取学生记录（模糊条件：）
     * @param conditions
     * @return
     */
    public List<TStudent> findStudentByCondition(String conditions) {
        TypedQuery<TStudent> query = em.createNamedQuery("TStudent.findStudentByCondition",TStudent.class);
        query.setParameter("conditions","%"+ conditions + "%");
        List<TStudent> list = query.getResultList();
        return list;
    }

}
