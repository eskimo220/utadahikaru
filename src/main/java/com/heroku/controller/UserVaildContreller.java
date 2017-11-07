package com.heroku.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.auth0.jwt.internal.org.bouncycastle.asn1.dvcs.Data;
import com.github.pagehelper.PageHelper;
import com.heroku.entity.ASelect;
import com.heroku.entity.ASelectExample;
import com.heroku.entity.ASelectTail;
import com.heroku.entity.ASelectTailExample;
import com.heroku.entity.ATag;
import com.heroku.entity.ATag2;
import com.heroku.entity.ATagExample;
import com.heroku.entity.Alltags;
import com.heroku.entity.AlltagsExample;
import com.heroku.entity.BTag;
import com.heroku.entity.BTag2;
import com.heroku.entity.BTagExample;
import com.heroku.entity.Imgsave;
import com.heroku.entity.URole;
import com.heroku.entity.UUser;
import com.heroku.entity.UUserExample;
import com.heroku.entity.UUserRole;
import com.heroku.form.LoginForm;
import com.heroku.form.Pageform;
import com.heroku.mapper.ASelectMapper;
import com.heroku.mapper.ASelectTailMapper;
import com.heroku.mapper.ATagMapper;
import com.heroku.mapper.AlltagsMapper;
import com.heroku.mapper.BTagMapper;
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
	UUserMapper umaper;
	@Autowired
	UUserRoleMapper uuserRoleMapper;
	@Autowired
	ImgsaveMapper imgsaveMapper;
	@Autowired
	ATagMapper aTagMapper;
	@Autowired
	ASelectMapper aselectMapper;
	@Autowired
	ASelectTailMapper aSelectTailMapper;
	@Autowired
	BTagMapper bTagMapper;
	@Autowired
	AlltagsMapper alltagsMapper;

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

	@RequestMapping("Dashboard")
	String Dashboard() {
		return "Dashboard";
	}

	/**
	 * ajax登录请求
	 * 
	 * @param loginForm
	 * @return
	 */
	@RequestMapping(value = "ajaxLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitLogin(@RequestBody LoginForm loginForm) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

		/*
		 * if(vcode==null||vcode==""){ resultMap.put("status", 500);
		 * resultMap.put("message", "验证码不能为空！"); return resultMap; }
		 */

		/*
		 * Session session = SecurityUtils.getSubject().getSession(); //转化成小写字母
		 * vcode = Svcode.toLowerCase(); String v = (String)
		 * session.getAttribute("_code"); //还可以读取一次后把验证码清空，这样每次登录都必须获取验证码
		 * //session.removeAttribute("_come"); if(!vcode.equals(v)){
		 * resultMap.put("status", 500); resultMap.put("message", "验证码错误！");
		 * return resultMap; }
		 */

		try {
			UsernamePasswordToken token = new UsernamePasswordToken(loginForm.getUsername(), loginForm.getPassword(),
					loginForm.getRememberMe());

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
	 * 
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> logout() {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 退出

			SecurityUtils.getSubject().logout();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 校验是否激活邮箱
	 * 
	 * @return
	 */
	@RequestMapping(value = "Jwt")

	public String Jwt(@RequestParam String token) {
		// 首先确认token是否有效

		// 解密令牌，取出user对象
		UUser uuser = Jwt.unsign(token, UUser.class);

		UUserExample userexample = new UUserExample();

		// 根据Email，查出该user的其他信息（id），方便update
		userexample.createCriteria().andNicknameEqualTo(uuser.getNickname());
		List<UUser> users = umaper.selectByExample(userexample);

		// 获得该uuser的本身
		UUser uUser = users.get(0);
		// 将他的Status赋值为1
		uUser.setStatus((short) 1);

		if (token.equals(uUser.getTokens())) {
			// String tokenn = Jwt.sign(uUser, 60L* 1000L* 30L);
			uUser.setTokens(token + "aa");
			umaper.updateByPrimaryKeySelective(uUser);
			return "/JwtSuccess";
		} else {
			return "/JwtFail";
		}
	}

	/**
	 * 注册,用户权限默认设置为persion
	 * 
	 * @return
	 */
	@RequestMapping(value = "adduser")

	public String add(UUser user, URole urole, Model model) {
		// 权限id绑定
		UUserRole uuserRole = new UUserRole();

		// 令牌(对象是页面添加信息的user对象与半小时约定失效时间)
		String token = Jwt.sign(user, 60L * 1000L * 30L);
		// 密码加密
		String password = String.valueOf(user.getPswd());

		String pawDES = MyDES.encryptBasedDes(password);

		user.setPswd(pawDES);

		// 创建时间-获取当前的时间
		user.setCreateTime(new Date());

		// 创建token用于用户一次验证
		user.setTokens(token);
		System.out.println(user.getId());

		// 发送邮件
		try {
			SendGrid sg = new SendGrid(apiKey);
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody("{\"personalizations\":[{\"to\":[{\"email\":\"" + user.getEmail()
					+ "\"}],\"subject\":\"你中了200万!\"}],\"from\":{\"email\":\"lilu@qq.com\"},\"content\":[{\"type\":\"text/plain\",\"value\": \"路哥又来信了!还带来了一个链接http://127.0.0.1:5000/Jwt?token="
					+ token + "\"}]}");

			// long starTime=System.currentTimeMillis();
			Response response = sg.api(request);
			// System.out.println(System.currentTimeMillis() - starTime+"@#$");
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

		// 插入此数据
		if (umaper.insertSelective(user) == 1) {
			UUserExample userexample = new UUserExample();
			userexample.createCriteria().andNicknameEqualTo(user.getNickname()).andEmailEqualTo(user.getEmail());
			List<UUser> users = umaper.selectByExample(userexample);

			// 获得该uuser的本身
			UUser uUser = users.get(0);
			uuserRole.setRid(2);
			uuserRole.setUid(uUser.getId());
			;
			uuserRoleMapper.insert(uuserRole);

			return "/addsuccess";
		} else {
			return "/add";
		}
	}

	/**
	 * 保持唯一用户名
	 * 
	 * @return
	 */
	@RequestMapping(value = "oneuser")

	public @ResponseBody boolean oneuser(UUser user, Model model) {
		// Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

		UUserExample exa = new UUserExample();

		// 确认此账号是否唯一
		exa.createCriteria().andNicknameEqualTo(user.getNickname());

		return umaper.selectByExample(exa).size() == 0;

	}

	/**
	 * 忘记密码，发送邮件确认用户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "sendforgotemail")
	public String sendforgotemail(UUser user, Model model) {

		// 首先确认该邮箱的正确性
		UUserExample emailexa = new UUserExample();
		emailexa.createCriteria().andEmailEqualTo(user.getEmail());
		List<UUser> uUsers = umaper.selectByExample(emailexa);
		UUser uUser = null;
		if (uUsers.size() == 0) {
			// 提示用户，该邮箱没有注册过
			return "/forgotpwd";
		} else {
			// 密码解密后发送给用户
			uUser = uUsers.get(0);
			String password = String.valueOf(uUser.getPswd());
			String pawDES = MyDES.decryptBasedDes(password);
			uUser.setPswd(pawDES);
			try {
				SendGrid sg = new SendGrid(apiKey);
				Request request = new Request();
				request.setMethod(Method.POST);
				request.setEndpoint("mail/send");
				request.setBody("{\"personalizations\":[{\"to\":[{\"email\":\"" + user.getEmail()
						+ "\"}],\"subject\":\"已经查到您的信息!\"}],\"from\":{\"email\":\"lilu@qq.com\"},\"content\":[{\"type\":\"text/plain\",\"value\": \"路哥又来信了!您的密码="
						+ uUser.getPswd() + "用户名=" + uUser.getNickname() + "\"}]}");

				// long starTime=System.currentTimeMillis();
				Response response = sg.api(request);
				// System.out.println(System.currentTimeMillis() -
				// starTime+"@#$");
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
	 * 
	 * @return
	 */
	@RequestMapping("db")
	public String db(Model model, String email) {
		// 设置密码与邮件参数，查询返回

		UUserExample emailexa = new UUserExample();
		emailexa.createCriteria().andEmailEqualTo(email);
		List<UUser> uUsers = umaper.selectByExample(emailexa);
		UUser user = uUsers.get(0);
		String password = String.valueOf(user.getPswd());

		String pawDES = MyDES.decryptBasedDes(password);

		user.setPswd(pawDES);
		model.addAttribute("user", user);

		Imgsave imgsave = imgsaveMapper.selectByPrimaryKey(user.getNickname());

		if (imgsave != null) {
			byte[] databyte = imgsave.getLongblob();

			if (databyte != null) {
				String data = Base64.encodeBase64String(databyte);
				model.addAttribute("data", data);
			}
		}
		return "db";
	}

	/**
	 * admin管理用户查看页面
	 * 
	 * @return
	 */
	@RequestMapping("managerselectall")
	public String managerselectall(UUser user, Model model) {
		UUserExample emailexa = new UUserExample();
		List<UUser> uUserlist = umaper.selectByExample(emailexa);
		System.out.println(uUserlist);
		model.addAttribute("uUserlist", uUserlist);
		return "managerselect";
	}

	/**
	 * 保存用户信息
	 * 
	 * @return
	 */
	@RequestMapping("editsave")
	public String editsave(UUser user, Model model) {

		String password = String.valueOf(user.getPswd());
		String pawDES = MyDES.encryptBasedDes(password);
		user.setPswd(pawDES);
		UUserExample emailexa = new UUserExample();
		emailexa.createCriteria().andIdEqualTo(user.getId());
		umaper.updateByExampleSelective(user, emailexa);

		// 从session取出user信息（前面有存储），赋值到user前台可用
		model.addAttribute("user", SecurityUtils.getSubject().getPrincipal());

		Imgsave imgsave = imgsaveMapper.selectByPrimaryKey(user.getNickname());

		byte[] databyte = imgsave.getLongblob();

		String data = Base64.encodeBase64String(databyte);
		model.addAttribute("data", data);

		return "editsuccess";
	}

	/**
	 * 保存图片数据库
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("jpgsave")
	public @ResponseBody Map<String, Object> jpgsave(@RequestParam("blob") MultipartFile[] submissions, UUser user)
			throws IOException {

		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		if (submissions.length > 0) {
			MultipartFile file = submissions[0];
			if (file != null) {
				/*file.transferTo(new File("C://aaa.jpeg"));// 上传服务器
*/				// 使用转byte[]存入数据库
				byte[] images = file.getBytes();
				Imgsave imgsave = new Imgsave();
				imgsave.setUsername(user.getNickname());

				imgsaveMapper.deleteByPrimaryKey(user.getNickname());

				imgsave.setLongblob(images);

				imgsaveMapper.insert(imgsave);
			}
		} else {
			// 如果有图片就存原来的，如果是×掉的情况就得删原来的
			imgsaveMapper.deleteByPrimaryKey(user.getNickname());
		}
		resultMap.put("status", 200);
		resultMap.put("message", "保存成功");
		return resultMap;
	}

	/**
	 * online文件流显示图片（暂时不需要）
	 * 
	 * @return
	 */
	@RequestMapping("showimg")
	public void showimg(@RequestParam String nickname, HttpServletResponse response) throws IOException {
		int len = 0;
		byte[] buf = new byte[1024];

		response.setContentType("image/jpeg");
		response.setCharacterEncoding("UTF-8");

		Imgsave imgsave = imgsaveMapper.selectByPrimaryKey(nickname);

		byte[] data = imgsave.getLongblob();
		OutputStream ops = response.getOutputStream();
		InputStream in = new ByteArrayInputStream(data);

		while ((len = in.read(buf, 0, 1024)) != -1) {
			ops.write(buf, 0, len);
		}
		ops.close();
	}

	/**
	 * login跳项目组页面，带selectlist
	 * 
	 * @return
	 */
	@RequestMapping(value = "tagsall", method = RequestMethod.GET)
	public String tagsall(Model model) {
		AlltagsExample alltagsExample = new AlltagsExample();
		List<Alltags> allstaglist = alltagsMapper.selectByExample(alltagsExample);
		model.addAttribute("allstaglist", allstaglist);
		return "tagsall";
	}
	
	//A添加
	@RequestMapping(value = "tagadd", method = RequestMethod.GET)
	public String tagadd(Model model) {
		ASelectExample aSelectExample = new ASelectExample();
		List<ASelect> aselectlist = aselectMapper.selectByExample(aSelectExample);
		model.addAttribute("aselectlist", aselectlist);
		return "tagadd";
	}

	/**
	 * A 页面添加标签判断
	 * 
	 * @return
	 */
	@RequestMapping(value = "taginsert", method = RequestMethod.POST)
	public String taginsert(@ModelAttribute("ATag") @Valid ATag2 atag, BindingResult result, Model model,Alltags alltags) {

		if (result.hasErrors()) {
			// 错误类型遍历
			List<ObjectError> ls = result.getAllErrors();
			for (int i = 0; i < ls.size(); i++) {
				// 把验证后的报错信息放到前台
				model.addAttribute("errorMessage", ls.get(i).getDefaultMessage());
			}

			model.addAttribute("atag", atag.getAtag());

			ASelectExample aSelectExample = new ASelectExample();
			List<ASelect> aselectlist = aselectMapper.selectByExample(aSelectExample);
			model.addAttribute("aselectlist", aselectlist);
			return "tagadd";
		}//无错
		//alltags存储
		Alltags alltag = new Alltags();
		alltag.setAlltagsname(alltags.getAlltagsname());
		//createtime与updatetime初始两个时间的默认值
		alltag.setCreatetime(new Date());
		alltag.setUptime(new Date());
		alltagsMapper.insertSelective(alltag);
		//存储模板设置模板id
		AlltagsExample alltagsExample = new AlltagsExample();
		alltagsExample.createCriteria().andAlltagsnameEqualTo(alltags.getAlltagsname());
		List<Alltags> alltags2= alltagsMapper.selectByExample(alltagsExample);
		int alltagsid=alltags2.get(0).getAlltagsid();
		
		ATagExample aTagExample = new ATagExample();
		aTagExample.createCriteria().andAlltagsidEqualTo(alltagsid);
		aTagMapper.deleteByExample(aTagExample);
		for (ATag atag1 : atag.getAtag()) {
			//下拉框数据存储
			if (atag1.getTagvalue() != null) {
				ASelectTailExample aSelectTailExample = new ASelectTailExample();
				aSelectTailExample.createCriteria().andASelectTnameEqualTo(atag1.getTagvalue());
				List<ASelectTail> aselecttailist = aSelectTailMapper.selectByExample(aSelectTailExample);

				String[] vs = new String[aselecttailist.size()];

				for (int i = 0; i < aselecttailist.size(); i++) {
					vs[i] = aselecttailist.get(i).getaSelectTvalue();
				}
				atag1.setTagvalues(vs);
			}
			atag1.setAlltagsid(alltagsid);
			//初始详细表的更新时间
			atag1.setLastuptime(new Date());
			aTagMapper.insert(atag1);
		}
		
		return "redirect:/tagshow1?alltagsid="+alltagsid;
	}
//跳第二个值可编辑页面
	@RequestMapping(value = "tagshow1", method = RequestMethod.GET)
	public String tagshow1(Model model,@RequestParam int alltagsid) {
		// 取出a_tag ，放入list，返回前台
		 
		ATagExample aTagExample = new ATagExample();
		aTagExample.createCriteria().andAlltagsidEqualTo(alltagsid);
		List<ATag> atag2list = aTagMapper.selectByExample(aTagExample);

		model.addAttribute("atag2list", atag2list);
		return "tagshow";
	}
//ajax判断模板是否修改
	@RequestMapping("ajaxsave")
	@ResponseBody
	public Map<String, Object> ajaxsave(@RequestParam Integer alltagsid,@RequestParam String lastuptime) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		
		AlltagsExample alltagsExample = new AlltagsExample();
		alltagsExample.createCriteria().andAlltagsidEqualTo(alltagsid);
		List<Alltags> alltagslist = alltagsMapper.selectByExample(alltagsExample);
		Alltags alltag = alltagslist.get(0);
		//存储前进行判断，模板是否更改
		System.out.println(lastuptime);
		System.out.println(alltag.getUptime().toString());
		if(lastuptime.equals(alltag.getUptime().toString())){
	/*	if(lastuptime==alltag.getUptime().toString()){*/
			System.out.println("模板未被修改");
			resultMap.put("status", 200);
			resultMap.put("message", "ajax保存成功");
			return resultMap;
		}else{
			resultMap.put("status", 100);
			resultMap.put("message", "模板已被修改过，无法保存该版本数据！");
			return resultMap;
		}
	
	}
	/**
	 * B 页面保存数据atag,btag
	 * 
	 * @return
	 */
	@RequestMapping(value = "tagsave", method = RequestMethod.POST)
	public String tagsave(BTag2 btag, Model model) {
		//保存前台傳的值前先刪除
		int id = 0;
		for (BTag btag2 : btag.getBtag()) {
			id = btag2.getAlltagsid();
		}
		BTagExample dbTagExample = new BTagExample();
		dbTagExample.createCriteria().andAlltagsidEqualTo(id);
		bTagMapper.deleteByExample(dbTagExample);
        int m = 0;
		//存储
		for (BTag btag2 : btag.getBtag()) {
			m = btag2.getAlltagsid();
			bTagMapper.insert(btag2);
		}
		//返回count
		BTagExample bTagExample = new BTagExample();
		bTagExample.createCriteria().andAlltagsidEqualTo(m);
		List<BTag> btag2list = bTagMapper.selectByExample(bTagExample);
		int count = btag2list.size();
		model.addAttribute("count", count);
		model.addAttribute("id", id);
		return "tagsave";
		
	}

	@RequestMapping("selectpa")
	@ResponseBody
	public Map<String, Object> selectpa( Integer id,@RequestBody Pageform page) {

		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		BTagExample bTagExample = new BTagExample();
		bTagExample.createCriteria().andAlltagsidEqualTo(id);
		if (page.getPage()!=null && page.getCount() != null) {
			PageHelper.startPage(page.getPage(), page.getCount());
		}
		int i = bTagMapper.countByExample(bTagExample);
		if (page.getPage() != null && page.getCount() != null) {
			PageHelper.startPage(page.getPage(), page.getCount());
		}
		List<BTag> btag2list = bTagMapper.selectByExample(bTagExample);
		resultMap.put("status", 200);
		resultMap.put("btag2list", btag2list);
		resultMap.put("count", i);
		return resultMap;
	}

	/**
	 * 根据项目名查询模板
	 * 
	 * @return
	 */
	@RequestMapping(value = "selecttag", method = RequestMethod.GET)
	public String selecttag(@RequestParam String proname, Model model) {
		//根据名字查找总表id uptime 
		AlltagsExample alltagsExample = new AlltagsExample();
		alltagsExample.createCriteria().andAlltagsnameEqualTo(proname);
		List<Alltags> alltagslist = alltagsMapper.selectByExample(alltagsExample);
		Integer alltagid =alltagslist.get(0).getAlltagsid();
		Date date = alltagslist.get(0).getUptime();
        //总表的时间赋值给分表，隐藏于页面
		ATagExample aTagExample2 = new ATagExample();
        aTagExample2.createCriteria().andAlltagsidEqualTo(alltagid);
		List<ATag> ATaglist = aTagMapper.selectByExample(aTagExample2);
        ATag aTag2 = ATaglist.get(0);
        aTag2.setLastuptime(date);
        aTagMapper.updateByExample(aTag2, aTagExample2);
		//根据id查找对应项目模板
        ATagExample aTagExample3 = new ATagExample();
        aTagExample3.createCriteria().andAlltagsidEqualTo(alltagid);
		List<ATag> atagshowlist = aTagMapper.selectByExample(aTagExample3);
		model.addAttribute("atagshowlist", atagshowlist);
		model.addAttribute("proname", proname);
		return "tagselectshow";
	}
	/**
	 * 修改项目模型
	 * 
	 * @return
	 */
	@RequestMapping(value = "tagsupdate", method = RequestMethod.GET)
	public String tagsupdate(@RequestParam String proname, Model model) {
		//根据名字查找id
		AlltagsExample alltagsExample = new AlltagsExample();
		alltagsExample.createCriteria().andAlltagsnameEqualTo(proname);
		List<Alltags> alltagslist = alltagsMapper.selectByExample(alltagsExample);
		Integer alltagid =alltagslist.get(0).getAlltagsid();
		//根据id查找对应项目模板
		AlltagsExample aTagsExample = new AlltagsExample();
		aTagsExample.createCriteria().andAlltagsidEqualTo(alltagid);
		List<Alltags> alltaglist=  alltagsMapper.selectByExample(aTagsExample);
		Alltags alltags = alltaglist.get(0);
		//update 项目模型和lastupdate时间
		//Alltags alltags = new Alltags();
		alltags.setUptime(new Date());
		alltags.setAlltagsid(alltagid);
		alltagsMapper.updateByExample(alltags, aTagsExample);
		//找到项目一览
		AlltagsExample alltagsExample2 = new AlltagsExample();
		
		List<Alltags> alltagslist2 = alltagsMapper.selectByExample(alltagsExample2);
		model.addAttribute("allstaglist", alltagslist2);
		return "tagsall";
	}
	
}
