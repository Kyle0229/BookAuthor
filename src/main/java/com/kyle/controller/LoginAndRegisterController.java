package com.kyle.controller;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.kyle.config.ShiroConfig;
import com.kyle.config.TelUtils;
import com.kyle.dao.AuthorRespository;
import com.kyle.domain.Author;
import com.kyle.domain.Cdcode;
import com.kyle.service.AuthorService;
import com.kyle.service.CdcodeService;

import com.kyle.shiro.CustomizedToken;
import com.kyle.utils.Md5Utils;
import com.kyle.utils.UploadUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
public class LoginAndRegisterController {
    @Autowired
    private UploadUtils uploadUtils;
    @Autowired
    private TelUtils telUtil;
    @Resource
    private AuthorService authorService;

    @Resource
    private AuthorRespository authorRespository;
    @Autowired
    private CdcodeService cdcodeService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private Logger logger = LoggerFactory.getLogger(AuthorController.class);
    @RequestMapping("/upload")
    public String test(MultipartFile file){

        logger.debug("传入的文件参数：{}", JSON.toJSONString(file, true));
        if (Objects.isNull(file) || file.isEmpty()) {
            logger.error("文件为空");
            return "文件为空，请重新上传";
        }else {
            String path = uploadUtils.upload(file);
            return path;
        }
    }

    /*
     * 登录
     */
    @RequestMapping("/login")
    public String login(@RequestBody Author author, HttpSession session){
        //String name= author.getAname();
        String password=author.getApassword();
        String aphone = author.getAphone();
        Subject subject = SecurityUtils.getSubject();

        CustomizedToken token = new CustomizedToken(aphone, password,"");
        //UsernamePasswordToken token=new UsernamePasswordToken(name,password);
        try{
            subject.login(token);
            if (subject.isAuthenticated()){
                    Author checkauthor = authorRespository.findByAphone(aphone);
                Integer astatus = checkauthor.getAstatus();
                if (astatus==1){
                    session.setAttribute("author",checkauthor);
                    redisTemplate.opsForValue().set("author",checkauthor);
                    return "success";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "登陆失败";
    }
   @RequestMapping("/sendeTel/{tel}")
   @ResponseBody
   public String sendemail(@PathVariable("tel") String tel) {
       System.out.println(tel);
       String se = null;
       try {
           se = telUtil.telUtil(tel);
       } catch (ClientException e) {
           e.printStackTrace();
       }
       if ("success".equals(se)) {
           return "success";
       } else {
           return "";
       }
   }
   @RequestMapping("/register")
   public String register(@RequestBody Author author) {//这个user是页面用户输入的内容返回
       //判断验证是否正确

       Cdcode byName = cdcodeService.selectcdTel(author.getAphone());
      // System.out.println(byName+"/////////////////");
       //System.out.println("------------------");
       //判断用户输入的验证码与发送的验证码是否相同
       if (author.getMsgcode().equals(byName.getMsgcode())&&authorRespository.findByAname(author.getAname())==null){
           author.setAstatus(1);
           authorService.save(author);
           return "{\"aa\":\"注册成功\",\"bb\":\"login\"}";
          /* if(authorService.selectbyTel(author.getAphone())==null) {

               author.setAstatus(1);//将用户信息存入数据库
               authorService.save(author);
               return "{\"aa\":\"注册成功\",\"bb\":\"login\"}";*/
           }
       return "{\"aa\":\"用户名已被注册或验证码错误\"}";
   }
    @RequestMapping("/findRedis")
    public Author findSession(){
        Object author = redisTemplate.opsForValue().get("author");
        Author author1=(Author) author;
        return author1;
    }
    @RequestMapping("/logout")
    public String logout(){
        redisTemplate.delete("author");
        return "1";
    }
/*@RequestMapping("/test")
    public String test(@RequestBody Author author){
    String aname = author.getAname();
    String apassword = author.getApassword();
    String password1 =Md5Utils.encryptPassword(apassword,aname);
    return password1;
}*/
}
