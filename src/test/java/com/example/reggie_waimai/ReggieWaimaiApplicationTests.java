package com.example.reggie_waimai;

import com.aliyun.oss.model.RoutingRule;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import java.util.List;
import com.aliyun.tea.*;
@Slf4j
@SpringBootTest
class ReggieWaimaiApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;


    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId="LTAI5tQR123CUiXd6VmYpGVx";
    @Value("${aliyun.oss.accessKeySecret}")
    private String secret="Hh2y9Re2i79ihM6l21oivX4fA76axy";
    @Value("${aliyun.oss.templateCode}")
    private String templateCode="SMS_154950909";
    //public void sendMessage(String signName,String phoneNumbers, String param){
    public void sendMessage(String signName,String phoneNumbers, String param){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", this.accessKeyId, this.secret);
        IAcsClient client = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setSysRegionId("cn-hangzhou");
        request.setPhoneNumbers(phoneNumbers);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam("{\"code\":\""+param+"\"}");
        try {
            SendSmsResponse response = client.getAcsResponse(request);
            System.out.println("短信发送成功");
        }catch (ClientException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void tets(){
        this.sendMessage("阿里云短信测试","17856796238","1234");}

    @Test
    void contextLoads() {
        Jedis jedis=new Jedis("127.0.0.1",6379);
        jedis.set("name","hahah");
        String name = jedis.get("name");
        System.out.println(name);
    }

    /*
     *
     * redis map设置与取值
     *
     * */
//    @Test
//    void test(){
//        //redis map设置与取值
//        // redisTemplate.opsForValue().set("cef","WD");
//        String cef = (String) redisTemplate.opsForValue().get("cef");
//        System.out.println(cef);
//    }
//
//
//    /*
//     *
//     * redis list设置与取值
//     *
//     * */
//    @Test
//    void test2() {
//        ListOperations listOperations = redisTemplate.opsForList();
//        //redis的单个纯值
//        listOperations.leftPush("testlist", "a");
//        listOperations.leftPush("testlist", "b");
//        listOperations.leftPush("testlist", "c");
//        //redis的多个存值并返回当前长度
//        Long aLong = listOperations.leftPushAll("test", "a", "a", "d");
//        System.out.println(aLong);
//
//        //redis的取值
//        List testlist = listOperations.range("testlist", 0, -1);
//        testlist.forEach(i-> System.out.println(i));
//    }
//
//    @Test
//    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
//        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
//                // 必填，您的 AccessKey ID
//                .setAccessKeyId(accessKeyId)
//                // 必填，您的 AccessKey Secret
//                .setAccessKeySecret(accessKeySecret);
//        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
//        config.endpoint = "dysmsapi.aliyuncs.com";
//        return new com.aliyun.dysmsapi20170525.Client(config);
//    }
//
//    @Test
//    public void main() throws Exception {
////        java.util.List<String> args = java.util.Arrays.asList(args_);
//        // 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
//        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例使用环境变量获取 AccessKey 的方式进行调用，仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
//        com.aliyun.dysmsapi20170525.Client client = this.createClient(System.getenv("LTAI5tQR123CUiXd6VmYpGVx"), System.getenv("Hh2y9Re2i79ihM6l21oivX4fA76axy"));
//        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
//                .setPhoneNumbers("17856796238")
//                .setSignName("阿里云短信测试")
//                .setTemplateCode("SMS_154950909")
//                .setTemplateParam("{\"code\":\"1314\"}");
//        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
//        try {
//            // 复制代码运行请自行打印 API 的返回值
//           // client.sendSmsWithOptions(sendSmsRequest, runtime);
//            log.info("成功");
//        } catch (TeaException error) {
//            // 如有需要，请打印 error
//            com.aliyun.teautil.Common.assertAsString(error.message);
//            log.info("错误1");
////        } catch (Exception _error) {
////            TeaException error = new TeaException(_error.getMessage(), _error);
////            // 如有需要，请打印 error
////            log.info("{}",error.toString());
////            com.aliyun.teautil.Common.assertAsString(error.message);
////            log.info("错误2");
////
////        }
//        }
//    }

}
