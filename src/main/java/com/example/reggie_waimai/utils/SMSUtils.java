package com.example.reggie_waimai.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.jetbrains.annotations.TestOnly;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.beans.factory.annotation.Value;

/**
 * 短信发送工具类
 */
public class SMSUtils {

	/**
	 * 发送短信
	 * @param signName 签名
	 * @param templateCode 模板
	 * @param phoneNumbers 手机号
	 * @param param 参数
	 *
	 *
	 */
	@Value("${aliyun.oss.accessKeyId}")
	private String accessKeyId="LTAI5tQR123CUiXd6VmYpGVx";
	@Value("${aliyun.oss.accessKeySecret}")
	private String secret="Hh2y9Re2i79ihM6l21oivX4fA76axy";
    @Value("${aliyun.oss.templateCode}")
	private String templateCode="SMS_154950909";
	//public void sendMessage(String signName,String phoneNumbers, String param){
	public  void sendMessage(String signName, String phoneNumbers, String param){
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

	public void tets(){
		this.sendMessage("阿里云短信测试","17856796238","1234");}

}
