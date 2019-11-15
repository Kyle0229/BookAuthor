package com.kyle.config;

import java.util.Random;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.kyle.dao.AuthorRespository;
import com.kyle.domain.Author;
import com.kyle.domain.Cdcode;
import com.kyle.service.AuthorService;
import com.kyle.service.CdcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Title AliDayunSms
 * @Description 阿里大鱼短信接口开发
 * @author calvin
 * @date: 2018/8/9 下午1:28
 */
@Component
public class TelUtils {
    @Autowired
    private AuthorRespository authorRespository;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private CdcodeService cdcodeService;
    public String telUtil(String tel) throws com.aliyuncs.exceptions.ClientException {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = "LTAI4FnU9vLCT8w1bubv8AL9";//你的accessKeyId
        final String accessKeySecret = "hE0lYSalo4KZdpLzzPsQDNj5esC6uJ";//你的accessKeySecret
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象

        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);

        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
        request.setPhoneNumbers(tel);
        System.out.println(tel);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("益书城登陆");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_177242842");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //request.setTemplateParam("{\"code\":\"988756\"}");
        String msgCode = getMsgCode();
        request.setTemplateParam("{\"code\":\"" + msgCode + "\"}");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
//            请求成功
//            通过email查询验证码，若用户已存在，更新验证码，不存在则增加
            Author author = new Author();
            Cdcode cdcode = new Cdcode();
            Author byname = authorService.selectbyTel(tel);
            if (byname != null) {
//                byname.setAstatus(1);
//                byname.setMsgcode(msgCode);
//                authorService.save(byname);
                return "用户已存在";
            }else {
                cdcode.setMsgcode(msgCode);
                cdcode.setAphone(tel);
//           System.out.println("usercode"+usercode.toString());
                cdcodeService.savecode(cdcode);
            }
            System.out.println("=====success====");
            return "success";
        } else {
            System.out.println("=====fail=======");
            return "fail";
        }
    }

    /**
     * 生成随机的6位数，短信验证码
     *
     * @return
     */
    private static String getMsgCode() {
        int n = 6;
        StringBuilder code = new StringBuilder();
        Random ran = new Random();
        for (int i = 0; i < n; i++) {
            code.append(Integer.valueOf(ran.nextInt(10)).toString());
        }
        return code.toString();
    }
}