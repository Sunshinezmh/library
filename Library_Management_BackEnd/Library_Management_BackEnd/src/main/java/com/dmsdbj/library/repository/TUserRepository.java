package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TUser;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TUserRepository extends AbstractRepository<TUser, String> {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TUserRepository() {
        super(TUser.class);
    }


    /**
     * 登录一,通过email---findByIdAndPWD---何新生-2017-9-16
     * @param  Email
     * @return
     * @throws Exception
     */
    public List<TUser> queryUserByEmail(String Email){
        TypedQuery<TUser>  query=em.createNamedQuery("TUser.queryUserByEmail",TUser.class);
        query.setParameter("Email",Email);
        List<TUser> userlist=query.getResultList();
        em.close();
        return userlist;
    }

    /**
     * 登录二,通过 qqNumber---queryUserByQQ---何新生-2017-9-16
     * @param  QQ
     * @return
     * @throws Exception
     */
    public List<TUser> queryUserByQQ(String QQ){
        TypedQuery<TUser>  query=em.createNamedQuery("TUser.queryUserByQQ",TUser.class);
        query.setParameter("QQ",QQ);
        List<TUser> userlist=query.getResultList();
        em.close();
        return userlist;
    }

    /**
     * 登录三,通过 WeChat---queryUserByWeChat---何新生-2017-9-16
     * @param  WeChat
     * @return
     * @throws Exception
     */
    public List<TUser> queryUserByWeChat(String WeChat){
        TypedQuery<TUser>  query=em.createNamedQuery("TUser.queryUserByWeChat",TUser.class);
        query.setParameter("WeChat",WeChat);
        List<TUser> userlist=query.getResultList();
        em.close();
        return userlist;
    }



    /**
     * 查找用户原来密码-findPWDByUserId---何新生-2017-9-16
     * @param userId
     * @return
     * @throws Exception
     */
    public List<TUser> findPWDByUserId(String userId){
        TypedQuery<TUser> query=em.createNamedQuery("TUser.findPWDByUserId",TUser.class);
        query.setParameter("userId",userId);
        return query.getResultList();
    }


//    /**
//     * 修改密码--- changPWD ---何新生-2017-9-16
//     * @param
//     * @return
//     * @throws Exception
//     */
//    public int  changPWD(String userId,String newPWD){
//        boolean flag=false;
//        Query query=em.createNamedQuery("TUser.changePWD");
//        query.setParameter("userId",userId);
//        query.setParameter("newPWD",newPWD);
//        int pwd=query.getFirstResult();
//        em.close();
//        return  pwd;
//    }

    /**
     * 查询用户信息--- findUserById ---何新生-2017-9-16
     * @param
     * @return
     * @throws Exception
     */
    public List<TUser>  queryUserById(String userId){
        TypedQuery<TUser> query=em.createNamedQuery("TUser.queryUserById",TUser.class);
        query.setParameter("userId",userId);
        return  query.getResultList();
    }

}
