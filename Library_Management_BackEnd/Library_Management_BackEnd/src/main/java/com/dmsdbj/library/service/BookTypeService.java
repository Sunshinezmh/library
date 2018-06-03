package com.dmsdbj.library.service;
import com.dmsdbj.library.app.util.UUID.UuidUtils;
import com.dmsdbj.library.entity.TBook;
import com.dmsdbj.library.entity.TBookAndType;
import com.dmsdbj.library.repository.TBookAndTypeRepository;
import com.dmsdbj.library.viewmodel.Book;
import com.dmsdbj.library.viewmodel.BookViewModel;
import com.dmsdbj.library.repository.TBookRepository;
import scala.collection.parallel.ParSeqLike;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-09-24T17:35:42.344+08:00")
public  class BookTypeService {

    @Inject
    private TBookAndTypeRepository TbookAndTypeRespository;
    @Inject
    private TBookRepository TBookRepository;
    /**
     * 得到所有的图书分类-申明霜-2017-9-28
     * @return
     */
    public List<TBookAndType> findAllbookType(){
        return TbookAndTypeRespository.findAllbookType();
    }

    /**
     * 模糊查询根据类别名查询--刘雅雯--2017年10月7日21:41:58
     * @return
     */
    public List<TBookAndType> findBookTypeByName(String bookName){
        return TbookAndTypeRespository.findBookTypeByName(bookName);
    }

    /**
     * 查询一级图书类别--刘雅雯--2017年10月10日19:17:58
     * @return
     */
    public List<TBookAndType> findParentBookType(){
        return TbookAndTypeRespository.findParentBookType();
    }

    /**
     * 查询所有二级图书类别--田成荣--2018年5月2日10:40:10
     * @return
     */
    public List<BookViewModel> findShelfBookType(String location){
        List<TBookAndType> listType = TbookAndTypeRespository.findShelfBookType();
        List<BookViewModel> Lists = new ArrayList<BookViewModel>();

        for(int i=0 ;i<listType.size();i++)
        {

            List<BookViewModel> BookList = TBookRepository.getAllBookByTypeId(listType.get(i).getId(),location);
            BookViewModel List = new BookViewModel();
            List.setBookNum(BookList.size());  //此类下有多少书.
            List.setTypeId(listType.get(i).getId());//类别ID.
            List.settypeName(listType.get(i).getName());//类别名.
            List.setpicture(listType.get(i).getRemark());//图片（类别）.
            Lists.add(i,List);
        }
        return Lists;
    }
    /**
     * 查询书，根据二级分类和location--田成荣--2018年5月2日10:40:10
     * @return
     */
    public List<BookViewModel> findBookbyShelfandLocation(String Location, String typeID, Integer pageNum, Integer pageSize){
        return TbookAndTypeRespository.findBookbyShelfandLocation(Location,typeID,pageNum,pageSize);
    }

    /**
     * 根据一级类别查询子类别--刘雅雯--2017年10月10日19:51:30
     * @param pId
     * @return
     */
    public List<TBookAndType> findBookTypeBypId(String pId){
        return TbookAndTypeRespository.findBookTypeBypId(pId);
    }

    /**
     * 根据id查询pId返回实体--刘雅雯--2017年10月31日14:06:19
     * @param typeId
     * @return
     */
    public TBookAndType findBookTypepIdById(String typeId){
        return TbookAndTypeRespository.findBookTypepIdById(typeId);
    }
    /**
     * 根据booktypeId删除图书分类-申明霜-2017-9-28
     * @param bookTypeId 图书类型ID
     */
    public void deleteBookType(String bookTypeId){
        //将父类的id状态标记为删除
        TBookAndType tBookAndType = TbookAndTypeRespository.find(bookTypeId);
        tBookAndType.setIsDelete(1);
        TbookAndTypeRespository.edit(tBookAndType);

    }


    /**
     * 根据图书的id，找pId递归删除图书类别--刘雅雯--2017年10月31日16:47:26
     * @param bookTypeId
     * @return
     */
    public boolean getChildBypId(String bookTypeId){
        TBookAndType tBookAndType = TbookAndTypeRespository.find(bookTypeId);
        //存在父类id
        if (tBookAndType!=null){
            //寻找子类
            List<TBookAndType>  tBookAndTypeChil =TbookAndTypeRespository.findBookTypeBypId(bookTypeId);
            if (tBookAndTypeChil!=null){
                for (int i=0;i<tBookAndTypeChil.size();i++){
                    TBookAndType tBookAndTypeChilEntity = tBookAndTypeChil.get(i);
                    tBookAndTypeChilEntity.setIsDelete(1);
                    //修改子类的状态
                    TbookAndTypeRespository.edit(tBookAndTypeChilEntity);
                    getChildBypId(tBookAndTypeChil.get(i).getId());
                }
            }
            //父类状态标记为删除
            tBookAndType.setIsDelete(1);
            TbookAndTypeRespository.edit(tBookAndType);
            return true;
        }
            return false;
    }

    /**
     * 根据图书类别名称或者类别ID查询图书类别id -武刚鹏 -2017年12月2日12:16:48
     * 根据图书类别名称查询图书类别id--刘雅雯--2017年10月31日10:48:51
     * @return
     */
    public List<TBookAndType> findTypeIdByTypeName(String typeName){
        return TbookAndTypeRespository.findBookTypeIdByName(typeName);
    }

    /**
     * 新增一条图书类型-申明霜-2017-9-28
     * @param tBookAndType 图书类别实体
     */
    public boolean addBookType(TBookAndType tBookAndType){

        try {
            // TODO  应该从前端传，或者从缓存取
            tBookAndType.setShelfId(UuidUtils.base58Uuid());

            tBookAndType.setoperator("123");
            TbookAndTypeRespository.create(tBookAndType);
           return  true;
        } catch (Exception e) {
            return false;
        }
            
        

    }

    /**
     * 更新图书类型-申明霜-2017-9-29
     * @param tBookAndType 图书类别实体
     */
    public boolean updateBookType(TBookAndType tBookAndType){

        //查询要改为的内容是否存在库中--刘雅雯--2017年11月6日20:13:22
        try{
            List<TBookAndType> tBookAndTypeList =findBookTypeByName(tBookAndType.getName());


            //库中没有这样的记录，可以编辑
            if (tBookAndTypeList ==null || tBookAndTypeList.size()==0){

                TbookAndTypeRespository.edit(tBookAndType);
                return true;
            }
            else
            {
                if ((tBookAndTypeList.get(0).getId()).equals(tBookAndType.getId())){
                    TbookAndTypeRespository.edit(tBookAndType);
                    return true;
                }
                else{
                    return false;
                }
            }

        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * 无条件查询出所有的子类别，包括二级分类以及二级分类一下
     * @return
     */
    public List<TBookAndType> findAllSonType(){
        List<TBookAndType> list = TbookAndTypeRespository.findAllSonType();
        return list;
    }


}
