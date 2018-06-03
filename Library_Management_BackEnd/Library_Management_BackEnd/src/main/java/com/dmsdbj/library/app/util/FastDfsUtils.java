package com.dmsdbj.library.app.util;

import com.dmsdbj.itoo.tool.fastdfs.FastDfsUtil;
import org.csource.common.IniFileReader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
    关于fastdfs上传文件和下载文件的操作
    郑晓东 2017年10月31日08点09分
 */
public class FastDfsUtils {

    private static final String FASTDFS ="config/fast_client.conf";

    public static void main(String[] args) throws Exception {
        String urlPath="group1/M00/00/16/wKgW_Fn30MaAEHI2AAAnQiwQQ-E36.xlsx";
        //下载文件
       downloadFile(urlPath);
    }
    
    //region 上传文件

    //endregion

    //region 下载文件
    /**
     * 测试下载专用实例    urlPath="group1/M00/00/16/wKgW_Fn30MaAEHI2AAAnQiwQQ-E36.xlsx"
     * @throws Exception
     */
    private static void downloadFile()throws Exception{
        String urlPath="group1/M00/00/16/wKgW_Fn30MaAEHI2AAAnQiwQQ-E36.xlsx";

        //设置下载路径
        String downloadPath = System.getProperty("user.home")+"\\Downloads\\";
        //获取扩展名
        String extName = urlPath.substring(urlPath.lastIndexOf("."));
        //设置文件名称
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HHmmss");
        String fileName = "模板"+sdf.format(new Date());

        //设置文件路径
        String filePath=downloadPath+fileName+extName;//系统用户下载目录


    }

    /**
     * 下载文件（默认下载到用户Downloads文件）
     * @param urlPath 文件服务器路径
     * @throws Exception 文件下载异常
     */
    public static void downloadFile(String urlPath) throws Exception {
        //设置下载路径
        String downloadPath = System.getProperty("user.home")+"\\Downloads\\";
        //获取扩展名
        String extName = urlPath.substring(urlPath.lastIndexOf("."));
        //设置文件名称
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HHmmss");
        String fileName = "模板"+sdf.format(new Date());

        String filePath=downloadPath+fileName+extName;
        downloadFileFromService(urlPath,filePath);
    }

    /**
     * 下载文件
     * @param urlPath 文件服务器地址
     * @param fileName 文件名称
     * @throws Exception 下载异常
     */
    public static void downloadFile(String urlPath,String fileName) throws Exception {
        //设置下载路径
        String downloadPath = System.getProperty("user.home")+"\\Downloads\\";
        //获取扩展名
        String extName = urlPath.substring(urlPath.lastIndexOf("."));
        //设置文件路径
        String filePath=downloadPath+fileName+extName;
        downloadFileFromService(urlPath,filePath);
    }

    /**
     * 下载文件
     * @param urlPath 文件服务器地址
     * @param dirPath 文件保存目录
     * @param fileName 文件保存名称
     * @throws Exception 文件下载异常
     */
    public static void downloadFile(String urlPath,String dirPath,String fileName)throws Exception{
        String filePath=dirPath+fileName;
        downloadFileFromService(urlPath,filePath);
    }

    /**
     * 下载文件
     * @param urlPath 文件服务器地址
     * @param dirPath 文件下载目录
     * @param fileName 文件名称
     * @param extName 扩展名（带"."）
     * @throws Exception 文件下载异常
     */
    public static void downloadFile(String urlPath,String dirPath,String fileName,String extName) throws Exception {
        String filePath=dirPath+fileName+extName;
        downloadFileFromService(urlPath,filePath);
    }

    /**
     * 下载文件
     * @param urlPath 文件地址
     * @param filePath 文件保存路径
     * @throws Exception 文件下载异常
     */
    private static void downloadFileFromService(String urlPath,String filePath)throws Exception{
        FastDfsUtil client = new FastDfsUtil(FASTDFS);
        BufferedOutputStream bos=null;
        FileOutputStream fos = null;
        File file = null;
        File dir = new File(filePath);
        if (!dir.exists() && dir.isDirectory())
        {
            dir.mkdirs();
        }
        file = new File(filePath + File.separator);
        fos = new FileOutputStream(file);
        bos = new BufferedOutputStream(fos);
        client.downloadFile(urlPath,bos);
    }

    /**
     * x下载文件
     * @param urlPath 文件下载路径
     * @param bos 输出流
     * @throws Exception
     */
    public static void downloadFile(String urlPath,BufferedOutputStream bos) throws Exception{
        FastDfsUtil client = new FastDfsUtil(FASTDFS);
        client.downloadFile(urlPath,bos);
    }
    //endregion

    public static String getFastDfsIP() throws IOException {
        IniFileReader iniReader = new IniFileReader(FASTDFS);
        return iniReader.getStrValue("tracker_ip");
    }
}
