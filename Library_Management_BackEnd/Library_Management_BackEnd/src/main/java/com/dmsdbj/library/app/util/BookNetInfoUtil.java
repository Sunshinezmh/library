package com.dmsdbj.library.app.util;

import com.dmsdbj.itoo.tool.fastdfs.FastDfsUtil;
import com.dmsdbj.itoo.tool.tojson.JacksonJsonUntil;
import com.dmsdbj.library.app.util.UUID.UuidUtils;
import com.dmsdbj.library.entity.TBookBasic;
import com.dmsdbj.library.viewmodel.etc.BookNetInfo;
import org.slf4j.Logger;
import scala.Int;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Objects;


/*
 * 图书信息抓取处理类
 * @author 郑晓东
 * 2017年10月27日12点10分
 */
public class BookNetInfoUtil {

    private static final int DATELENGTH = 8;//接收的日期长度
    private static final String FASTDFS="config/fast_client.conf";

    private static Logger log;

    /**
     * 根据url查询数据:查询结果是JSON数据的  郑晓东 2017年10月13日12点17分    ---张明慧完善--2018年1月13日20:58:12
     * @param urlStr 查询路径
     * @return 返回图书信息
     */
    @SuppressWarnings("finally")
    private static BookNetInfo getBookInfo(String urlStr) throws IOException{
        BookNetInfo book = null;
        URL url = null;
        //http连接
        HttpURLConnection httpConn  = null;
        //输入流
        BufferedReader in  = null;
        StringBuffer sb  = new StringBuffer();
        try {
            url = new URL(urlStr);
            httpConn = (HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            InputStreamReader inputStreamReader  = new InputStreamReader(httpConn.getInputStream(),"UTF-8");
            in = new BufferedReader(inputStreamReader);
            String str  = null;
            while ((str = in.readLine())!= null){
                sb.append(str);
            }
            book = JacksonJsonUntil.jsonToPojo(sb.toString(),BookNetInfo.class);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("抓取图书信息异常，"+urlStr+"获未找到图书");
        }
        finally{
            try{
                if(in!=null) {
                    in.close();
                }
                httpConn.disconnect();
            }catch(IOException e) {
                e.printStackTrace();
            }
            return book;
        }
    }

    /**
     * 根据isbn从豆瓣查询图书信息，返回图书信息 郑晓东 2017年10月13日12点18分
     * @param isbn ISBN值
     * @return 通过isbn访问得url
     * @throws MalformedURLException 网络连接异常
     * @throws UnsupportedEncodingException 内容编码异常
     * @throws IOException 输出异常
     */
    private static String setURLByISBN(String isbn) throws IOException {
        return "https://api.douban.com/v2/book/isbn/:" + isbn;//设置url格式
    }

    /**
     * 图书基本数据转换 郑晓东 郑晓东 2017年10月27日10点48分   --张明慧完善--2018年1月13日20:58:32
     * @param bookInfo 图书网络查询格式
     * @return
     */
    private static TBookBasic setBookBasic(BookNetInfo bookInfo) throws Exception {
        //如果返回错误码，证明没有这本图书
        if (bookInfo == null) {
            return null;
        }
        int code = bookInfo.getCode();
        if (code==6000 ||code==6002||code==6004||code==6006||code==6007||code==6008 ||code==6009||code==6010||code==6011||code==6012|| code ==6013 ) {
            return null;
        }
        TBookBasic bookBasic = new TBookBasic();

        //将从网上查询的图书信息传递给数据库图书详情信息数据
        bookBasic.setId(UuidUtils.base58Uuid());//设置ID

        bookBasic.setIsbn(bookInfo.getIsbn13()==null?bookInfo.getIsbn10():bookInfo.getIsbn13());//设置图书ISBN 13 （先保存ISBN13为主）
        bookBasic.setName(bookInfo.getTitle());//设置图书名称

        bookBasic.setPublishPlace(bookInfo.getPublisher());//设置图书出版地址
        if (!bookInfo.getPages().trim().isEmpty()) {
            String regEx = bookInfo.getPages().trim();
            regEx = regEx.replaceAll("[^0-9]", "");

            bookBasic.setTotalPage(Integer.parseInt(regEx));//设置总页码数
        }
        if (!bookInfo.getSummary().trim().isEmpty()) {
            bookBasic.setsummary(bookInfo.getSummary());//设置图书摘要
        }
        if (!bookInfo.getCatalog().trim().isEmpty()) {
            bookBasic.setContent(bookInfo.getCatalog());//设置图书目录信息
        }
        String result = "";
        if(Objects.equals(bookInfo.getPrice(), "")){
            result=(String.valueOf(0));
        }else
        {
            char[] chars =  bookInfo.getPrice().toCharArray();

            for (int i = 0; i < chars.length; i++)
            {
                if (("0123456789.").indexOf(chars[i] + "") != -1)
                {
                    result += chars[i];
                }
            }
        }

        //设置图书原价
        bookBasic.setPrimCost(new Double(result));


        bookBasic.setRank((int)Math.round(Double.parseDouble(bookInfo.getRating().getAverage())));//设置图书评分

        //多条内容如何分隔，分隔符待考虑 郑晓东 2017年10月14日15点47分
        StringBuilder authors=new StringBuilder();
        for (int i = 0; i < bookInfo.getAuthor().size(); i++) {
            authors.append(bookInfo.getAuthor().get(i)+";");//以分隔符“;”分隔作者
        }
        bookBasic.setAuthor(authors.toString());//设置作者

        //以下图书封面地址设置和出版日期设置可存在转换异常
        String url = null;
        try {
            url = setImageUrl(bookInfo.getImage()); //fastdfs崩了
        } catch (Exception e) {
            e.printStackTrace();
        }
        bookBasic.setPicture( FastDfsUtils.getFastDfsIP() + url);//设置图书封面地址
        if(bookInfo.getPubdate().length()<DATELENGTH){//如果返回的出版日期小于8位，即类型是yyyy-MM型的
            //出版日期返回yyyy-MM类型，具体处理待考虑
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
           // bookBasic.setPublishTime(simpleDateFormat.parse(bookInfo.getPubdate()));//设置出版日期时间
        }else{//返回的日期类型是yyyy-MM-dd类型的
            bookBasic.setPublishTime(bookInfo.getPubdate());
        }
        return bookBasic;
    }





    /**
     * 对外图书基本信息接口  郑晓东 2017年10月27日10点49分
     * @param isbn ISBN值
     * @return 图书基本信息
     * @throws MalformedURLException 网络连接异常
     * @throws UnsupportedEncodingException 内容编码异常
     * @throws IOException 输出异常
     */
    public static TBookBasic getBookBasicByISBN(String isbn) throws Exception {
        return setBookBasic(getBookInfo(setURLByISBN(isbn)));
    }

    //TODO 图片下载 待考虑 郑晓东 2017年10月27日11点52分
    private static String setImageUrl(String imageUrl) throws Exception {
        String fileName=imageUrl.substring(imageUrl.lastIndexOf(".")+1);//获取文件名，带后缀
        return saveImageToFastDfs(getImageBytes(imageUrl),fileName);
    }

    //region 图片读取及保存到fastdfs服务器步骤 郑晓东 2017年10月28日14点53分
    /**
     * 通过url获取文件字节流  郑晓东 2017年10月28日14点39分
     * @param imageUrl 文件url
     * @return 文件字节流
     * @throws Exception
     */
    public static byte[] getImageBytes(String imageUrl) throws Exception {
        //设置URL
        URL url = new URL(imageUrl);
        // 打开连接
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        // 通过输入流获取图片数据
        InputStream is = conn.getInputStream();
        byte[] imageBytes = readInputStream(is);
        return imageBytes;
    }

    /**
     * 读取输入流为字节流 郑晓东 2017年10月28日14点37分
     * @param inStream 输入流
     * @return 字节流
     * @throws Exception
     */
    private static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 保存文件字节流到fastdfs服务器（带文件名称） 郑晓东 2017年10月28日14点38分
     * @param imageBytes 文件字节流
     * @param fileName 文件名称（不带 .）
     * @return 文件路径
     * @throws IOException
     */
    public static String saveImageToFastDfs(byte[] imageBytes,String fileName) throws Exception {
        FastDfsUtil client = new FastDfsUtil(FASTDFS);
        return client.uploadFile(imageBytes,fileName);
    }
	
	/**
     * 保存文件字节流到fastdfs服务器（带文件名称） 郑晓东 2017年10月28日14点38分
     * @param imageBytes 文件字节流
     * @return 文件路径
     * @throws IOException
     */
    public static String saveImageToFastDfs(byte[] imageBytes) throws Exception {
        FastDfsUtil client = new FastDfsUtil(FASTDFS);
        return client.uploadFile(imageBytes);
    }
	
    //endregion
//测试文件转字节流
     public static byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);
            ByteArrayOutputStream bos;
            try (FileInputStream fis = new FileInputStream(file)) {
                bos = new ByteArrayOutputStream(1000);
                byte[] b = new byte[1000];
                int n;
                while ((n = fis.read(b)) != -1) {
                    bos.write(b, 0, n);
                }
                fis.close();
            }
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (IOException ignored) {
        }  
        return buffer;  
    }

}
