package com.heroku.controller;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.heroku.entity.Imgsave;
import com.heroku.entity.URole;
import com.heroku.entity.UUser;
import com.heroku.entity.UUserExample;
import com.heroku.entity.UUserRole;
import com.heroku.form.LoginForm;
import com.heroku.mapper.ImgsaveMapper;
import com.heroku.mapper.UUserMapper;
import com.heroku.mapper.UUserRoleMapper;
import com.heroku.util.Jwt;
import com.heroku.util.MyDES;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;


@Controller
public class UserVaildContreller {
	
	@Value("${spring.sendgrid.api-key}")
    private String apiKey;
	
	@Autowired
	UUserMapper umaper ;
	@Autowired
	UUserRoleMapper uuserRoleMapper;
	@Autowired
	ImgsaveMapper imgsaveMapper;

    @RequestMapping("/")
    String index() {
        return "index";
    }

    @RequestMapping("login")
    String login() {
        return "login";
    }

    @RequestMapping("add")
    String add() {
        return "add";
    }
    @RequestMapping("addsuccess")
    String addsuccess() {
        return "addsuccess";
    }
 
    @RequestMapping("JwtSuccess")
    String JwtSuccess() {
        return "JwtSuccess";
    }
    @RequestMapping("JwtFail")
    String JwtFail() {
        return "JwtFail";
    }
    @RequestMapping("forgotpwd")
    String forgotpwd() {
        return "forgotpwd";
    }
    
    /**
     * ajax登录请求
     * @param loginForm
     * @return
     */
    @RequestMapping(value="ajaxLogin",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> submitLogin(@RequestBody LoginForm loginForm) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

/*        if(vcode==null||vcode==""){
            resultMap.put("status", 500);
            resultMap.put("message", "验证码不能为空！");
            return resultMap;
        }*/

/*        Session session = SecurityUtils.getSubject().getSession();
        //转化成小写字母
        vcode = Svcode.toLowerCase();
        String v = (String) session.getAttribute("_code");
        //还可以读取一次后把验证码清空，这样每次登录都必须获取验证码
        //session.removeAttribute("_come");
        if(!vcode.equals(v)){
            resultMap.put("status", 500);
            resultMap.put("message", "验证码错误！"); 
            return resultMap;
        }*/

        try {
            UsernamePasswordToken token = new UsernamePasswordToken(loginForm.getUsername(), loginForm.getPassword(),loginForm.getRememberMe());
           
            SecurityUtils.getSubject().login(token);
            resultMap.put("status", 200);
            resultMap.put("message", "登录成功");

        } catch (Exception e) {
            resultMap.put("status", 500);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

    /**
	 * 退出
	 * @return
	 */
	@RequestMapping(value="logout",method =RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> logout(){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			//退出
			
			SecurityUtils.getSubject().logout();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 校验是否激活邮箱
	 * @return
	 */
	@RequestMapping(value = "Jwt")
	
	public String Jwt(@RequestParam String token) {
		//首先确认token是否有效

	    //解密令牌，取出user对象
		UUser uuser = Jwt.unsign(token, UUser.class);
	
		UUserExample userexample = new UUserExample();
		
		//根据Email，查出该user的其他信息（id），方便update
		userexample.createCriteria().andNicknameEqualTo(uuser.getNickname());
		List<UUser> users=umaper.selectByExample(userexample);
		
		//获得该uuser的本身
		UUser uUser=users.get(0);
		//将他的Status赋值为1
		uUser.setStatus((short) 1);
		
      if(token.equals(uUser.getTokens())){
		//String tokenn = Jwt.sign(uUser, 60L* 1000L* 30L);
		uUser.setTokens(token+"aa");
		umaper.updateByPrimaryKeySelective(uUser);
		return "/JwtSuccess";
      }else{
	    return "/JwtFail";
      }
	}
	
	/**
	 *  注册,用户权限默认设置为persion
	 * @return
	 */
		@RequestMapping(value = "adduser")
	
		public String add(UUser user,URole urole,Model model) {
		//权限id绑定
		UUserRole uuserRole = new UUserRole();
		
	    //令牌(对象是页面添加信息的user对象与半小时约定失效时间)
	    String token = Jwt.sign(user, 60L* 1000L* 30L);
	    //密码加密
		String password = String.valueOf(user.getPswd());	
	
		String pawDES = MyDES.encryptBasedDes(password);
		
		user.setPswd(pawDES);
		
		//创建时间-获取当前的时间
	    user.setCreateTime(new Date());
	    
	    //创建token用于用户一次验证
	    user.setTokens(token);
	    System.out.println(user.getId());

	    //发送邮件
	    try {
  	      SendGrid sg = new SendGrid(apiKey);
  	      Request request = new Request();
  	      request.setMethod(Method.POST);
  	      request.setEndpoint("mail/send");
  	      request.setBody("{\"personalizations\":[{\"to\":[{\"email\":\"" + user.getEmail() + "\"}],\"subject\":\"你中了200万!\"}],\"from\":{\"email\":\"lilu@qq.com\"},\"content\":[{\"type\":\"text/plain\",\"value\": \"路哥又来信了!还带来了一个链接http://127.0.0.1:5000/Jwt?token=" + token + "\"}]}");
  	  
  	      //long starTime=System.currentTimeMillis();
  	      Response response = sg.api(request);
  	      //System.out.println(System.currentTimeMillis() - starTime+"@#$");
  	      System.out.println(response.getStatusCode());
  	      System.out.println(response.getBody());
  	      System.out.println(response.getHeaders());
  	    } catch (IOException ex) {
  	      try {
				throw ex;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  	    }
	    
	    //插入此数据
			if (umaper.insertSelective(user)==1) {
				UUserExample userexample = new UUserExample();
				userexample.createCriteria().andNicknameEqualTo(user.getNickname()).andEmailEqualTo(user.getEmail());
				List<UUser> users=umaper.selectByExample(userexample);
				
				//获得该uuser的本身
				UUser uUser=users.get(0);
				uuserRole.setRid(2);
				uuserRole.setUid(uUser.getId());;
				uuserRoleMapper.insert(uuserRole);
				
				return "/addsuccess";
			} else {
				return "/add";
			}
		
		}

	/**
	  * 保持唯一用户名
	  * @return
	  */
		@RequestMapping(value = "oneuser")
		
		public @ResponseBody boolean oneuser(UUser user,Model model) {
//		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();	
				
		UUserExample exa = new UUserExample();
			
		//确认此账号是否唯一
		exa.createCriteria().andNicknameEqualTo(user.getNickname());

		return umaper.selectByExample(exa).size()==0;
		    
		}	
			
	/**
	  * 忘记密码，发送邮件确认用户信息
	  * @return
	 */	
		@RequestMapping(value = "sendforgotemail")	
		public String sendforgotemail(UUser user,Model model) {
		
		//首先确认该邮箱的正确性	
		UUserExample emailexa = new UUserExample();
		emailexa.createCriteria().andEmailEqualTo(user.getEmail());
		List<UUser> uUsers=umaper.selectByExample(emailexa);
		UUser uUser = null;
		if(uUsers.size()==0){
            //提示用户，该邮箱没有注册过
			 return "/forgotpwd";
		}else{
        //密码解密后发送给用户
			uUser = uUsers.get(0);
			String password = String.valueOf(uUser.getPswd());	
			String pawDES = MyDES.decryptBasedDes(password);
			uUser.setPswd(pawDES);
		  try {
			  SendGrid sg = new SendGrid(apiKey);
			  Request request = new Request();
			  request.setMethod(Method.POST);
			  request.setEndpoint("mail/send");
			  request.setBody("{\"personalizations\":[{\"to\":[{\"email\":\"" + user.getEmail() + "\"}],\"subject\":\"已经查到您的信息!\"}],\"from\":{\"email\":\"lilu@qq.com\"},\"content\":[{\"type\":\"text/plain\",\"value\": \"路哥又来信了!您的密码=" + uUser.getPswd() + "用户名=" + uUser.getNickname() + "\"}]}");
			  	  
			  //long starTime=System.currentTimeMillis();
			  Response response = sg.api(request);
			  //System.out.println(System.currentTimeMillis() - starTime+"@#$");
			  System.out.println(response.getStatusCode());
			  System.out.println(response.getBody());
			  System.out.println(response.getHeaders());
	       } catch (IOException ex) {
			try {
			   throw ex;
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		  }
			return "login";
		}
		
	}
			

	/**
	  * 根据用户提供的邮箱，查找返回该用户的信息(用于修改)
	  * @return
	  */	
	@RequestMapping("db")
	public String db(Model model,String email) {
	    //设置密码与邮件参数，查询返回

		UUserExample emailexa = new UUserExample();
		emailexa.createCriteria().andEmailEqualTo(email);
		List<UUser> uUsers=umaper.selectByExample(emailexa);
		UUser user =uUsers.get(0);
		String password = String.valueOf(user.getPswd());	
		
		String pawDES = MyDES.decryptBasedDes(password);
		
		user.setPswd(pawDES);
		model.addAttribute("user", user);
	    return "db";
}

	
	/**
	  * admin管理用户查看页面
	  * @return
	  */	
	@RequestMapping("managerselectall")
	public String managerselectall(UUser user,Model model){
		UUserExample emailexa = new UUserExample();
		List<UUser> uUserlist = umaper.selectByExample(emailexa);
		System.out.println(uUserlist);
		model.addAttribute("uUserlist", uUserlist);
		return "managerselect";
	}
	

	/**
	  * 保存用户信息
	  * @return
	  */
	@RequestMapping("editsave")		
	public String editsave(UUser user,Model model){
		
		String password = String.valueOf(user.getPswd());	
		String pawDES = MyDES.encryptBasedDes(password);
		user.setPswd(pawDES);
		UUserExample emailexa = new UUserExample();
		emailexa.createCriteria().andIdEqualTo(user.getId());
		umaper.updateByExampleSelective(user, emailexa);
		
		//从session取出user信息（前面有存储），赋值到user前台可用
		model.addAttribute("user", SecurityUtils.getSubject().getPrincipal());
		
		Imgsave imgsave = imgsaveMapper.selectByPrimaryKey(user.getNickname());
        
        byte[] databyte = imgsave.getLongblob();
		
        String data = Base64.encodeBase64String(databyte); 
        System.out.println("@@"+data);
        model.addAttribute("data",data);
		
		return "editsuccess";
	}
	
	/**
	  * 保存图片数据库
	  * @return
	 * @throws IOException 
	  */
	@RequestMapping("jpgsave")		
	public @ResponseBody Map<String,Object> jpgsave(@RequestParam("blob") MultipartFile[] submissions,UUser user) throws IOException{
		
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
	       
		if(submissions.length > 0)
		{
			MultipartFile file = submissions[0];
			if (file != null) {  
                file.transferTo(new File("C://aaa.jpeg"));// 上传服务器
                //使用转byte[]存入数据库
                byte[] images = file.getBytes();                     
                Imgsave imgsave = new Imgsave();
                imgsave.setUsername(user.getNickname());
                imgsave.setLongblob(images);
                imgsaveMapper.insert(imgsave);
                
                //使用base64转码     
                /*// 通过base64来转化图片  
                String data = Base64.encodeBase64String(file.getBytes()); 
                Imgsave imgsave = new Imgsave();
                imgsave.setUsername(user.getNickname());
                imgsave.setLongblob(data);*/
            }  
		}
	        return resultMap;
	}
	
	/**
	  * online文件流显示图片
	  * @return
	  */
	@RequestMapping("showimg")		
	public void showimg(@RequestParam String nickname,HttpServletResponse response)throws IOException{
  	    int len = 0;  
	    byte[] buf = new byte[1024]; 
		
        response.setContentType("image/jpeg");  
        response.setCharacterEncoding("UTF-8");
        
        Imgsave imgsave = imgsaveMapper.selectByPrimaryKey(nickname);
        
        byte[] data = imgsave.getLongblob();
        OutputStream  ops = response.getOutputStream(); 
        InputStream in = new ByteArrayInputStream(data);
        
        while ((len = in.read(buf, 0, 1024)) != -1) {  
        	ops.write(buf, 0, len);  
        }  
        ops.close();  
	}	
	
	
}
