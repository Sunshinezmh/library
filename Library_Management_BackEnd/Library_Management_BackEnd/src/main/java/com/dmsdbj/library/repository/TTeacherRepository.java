package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TTeacher;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TTeacherRepository extends AbstractRepository<TTeacher, String>{

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager(){
	return em;
    }

    public TTeacherRepository(){
	super(TTeacher.class);
    }

    /**
     * 模糊查询获取教师记录（模糊条件：教工号/姓名）--刘雅雯--2017年10月31日19:27:05
     * @param condition
     * @return
     */
    public List<TTeacher> findTeacherByConditon(String condition){
        TypedQuery<TTeacher> query=em.createNamedQuery("TTeacher.findTeacherByCondition",TTeacher.class);
        query.setParameter("condition", "%"+ condition + "%" );
        List<TTeacher> list = query.getResultList();
        return list;
    }

    /**
     * 获取所有教师记录--刘雅雯--2017年11月1日02:09:54
     * @return
     */
    public List<TTeacher> findAllTeacher(){
        TypedQuery<TTeacher> query =em.createNamedQuery("TTeacher.findAllTeacher",TTeacher.class);
        return query.getResultList();

    }

    /**
     * 根据教职工号获取教师实体--刘雅雯--2017年11月1日03:05:47
     * @param teacherId
     * @return
     */
    public TTeacher findTeacherByCode(String teacherId){
        TypedQuery<TTeacher> query =em.createNamedQuery("TTeacher.findTeacherByCode",TTeacher.class);
        query.setParameter("teachCode",teacherId);
       // TTeacher a = query.getSingleResult();
       List<TTeacher> list = query.getResultList();
       if(list!=null && list.size()>0){
           return list.get(0);
       }
       return null;
    }

    /**
     * 根据教职工号获取教师List--刘雅雯--2017年11月1日03:05:47
     * @param teacherId
     * @return
     */
    public List<TTeacher> findTeacherByCodeList(String teacherId){
        TypedQuery<TTeacher> query =em.createNamedQuery("TTeacher.findTeacherByCode",TTeacher.class);
        query.setParameter("teachCode",teacherId);
        return query.getResultList();
    }



}
