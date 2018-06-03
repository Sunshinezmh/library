package com.dmsdbj.library.controller.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 上传文件工具类
 * Created by WGP on 2017/11/2.
 */
public class UploadFileUtil {
    /**
     * 返回文件的路径-武刚鹏-2017年11月2日21:14:14
     * @param response
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public static String upload( HttpServletResponse response,HttpServletRequest request) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //1、创建DiskFileItemFactory工厂
        FileItemFactory factory = new DiskFileItemFactory();
        //2、创建一个文件上传解析器
        ServletFileUpload su = new ServletFileUpload(factory);
        //解决上传文件名乱码
        su.setHeaderEncoding("UTF-8");
        List<FileItem> list = null;
        //获取输出流对象
        //PrintWriter out = response.getWriter();
        String path ="";
        String fileName="";
        try {
            //3、解析Request对象，以便对文件上传域对象进行处理
            list = su.parseRequest(request);
            for (FileItem fileItem : list) {
                if (fileItem.isFormField()) {//普通表单域
                    if ("username".equals(fileItem.getFieldName())) {
                        //处理表单提交字段,解决普通输入域中文乱码问题
                        String username = fileItem.getString("utf-8");
                        //out.print(username + "，");
                    }
                } else {//文件上传域
                    //得到上传文件的保存目录，将上传的文件保存到WebRoot下的upload文件夹中
                     path = request.getServletContext().getRealPath("/WEB-INF/upload");
                    //浏览器请求路径，这是一个相对路径
                    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/upload/";
                    //获取上传文件的文件名
                     fileName = fileItem.getName();
                    //得到上传文件的扩展名
                    String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
                    //服务器只支持jpg和gif两种格式的文件
                    if (!"".equals(extName)) {
                        File uFile = new File(path);

                        //如果保存的目录不存在就创建一个目录
                        if (!uFile.exists()) {
                            uFile.mkdirs();
                        }

                        //处理文件名，文件名后加上时间到毫秒的后缀
                        int dot = fileName.lastIndexOf('.');
                        if ((dot >-1) && (dot < (fileName.length()))) {
                            fileName = fileName.substring(0, dot) +  String.valueOf(System.currentTimeMillis()) +   "." + fileName.substring(fileName.lastIndexOf(".") + 1);
                        }else{
                            fileName = fileName +  String.valueOf(System.currentTimeMillis());
                        }
                        File file = new File(path, fileName);
                        //写入文件到保存的目录中
                        fileItem.write(file);
                    } else {
                        System.out.println("文件格式不支持");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  path + "//" + fileName;
    }

    /**
     * 根据上传文件接收的文件输入流获取文件名称 郑晓东 2017年11月11日16点28分
     * @param is 文件输入流
     * @return 文件名称
     * @throws IOException
     */
    public static String getFileName(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        int count = 0;
        while (true) {
            int a = is.read();
            sb.append((char) a);
            if (a == '\r') {
                count++;
            }
            if (count == 4) {
                is.read();
                break;
            }
        }
        String title = sb.toString();
        System.out.println(title);
        String[] titles = title.split("\r\n");
        String fileName = titles[1].split(";")[2].split("=")[1].replace("\"", "");
        return fileName;
    }

    /**
     * 将文件输入流 转换成 输出流 郑晓东 2017年11月11日16点32分
     * @param is 输入流
     * @param bos
     * @throws IOException
     */
    public static void getFileOutPutStream(InputStream is, OutputStream bos) throws IOException {
        byte[] buffer = new byte[3072];
        int len = 0;
        while ((len = is.read(buffer)) != -1){
            bos.write(buffer, 0, len);
        }
    }
}


