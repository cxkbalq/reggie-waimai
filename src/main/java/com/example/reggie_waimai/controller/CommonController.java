package com.example.reggie_waimai.controller;

import com.aliyuncs.utils.MapUtils;
import com.example.reggie_waimai.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.imgepath}")
    private String imgepath;
    /**
     上传图片
     MapUtils 本质还是 Apache Commons Collections 提供的工具类，
     file变量名必须要与浏览器提交的文件名相同
     且大多使用post方法
     * **/

    @PostMapping("/upload")
    public R<String> uplod(MultipartFile file){

        //file 是tomcat临时产生的文件
        log.info("当前获得文件信息为：{}",file);

        //获取文件的后缀格式
        String imgename=file.getOriginalFilename();
        String ingeend=imgename.substring(imgename.lastIndexOf("."));

        //使用uuid生成新的名称
        String imge= UUID.randomUUID().toString()+ingeend;

        //判断文件目录是否存在
        File dir=new File(imgepath);
        if(dir.exists()){
            log.info("文件夹无需再次创建");
        }
        else {
            log.info("文件夹不存在已创建");
            dir.mkdirs();
        }

        log.info("当前新的文件为：{}",imge);

        try {
            file.transferTo(new File(imgepath+imge));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(imge);
    }




    /**
    下载图片
     * **/
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        try {
            //创建输入流，读取传入的图片
            FileInputStream fileInputStream=new FileInputStream(imgepath+name);
            //创建输出流，向浏览器发生读取的数据
            ServletOutputStream outputStream=response.getOutputStream();
            response.setContentType("image/jpeg");
            int len=1;
            //定义一次传入可以的最大字节
            byte [] bytes=new byte[2048];
            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                //刷新输出流，确保所有数据都被写入到输出目标中。
                outputStream.flush();
            }
            fileInputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
