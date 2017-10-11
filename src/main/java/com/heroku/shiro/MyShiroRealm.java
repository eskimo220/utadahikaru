package com.heroku.shiro;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.heroku.entity.URolePermission;
import com.heroku.entity.URolePermissionExample;
import com.heroku.entity.UUser;
import com.heroku.entity.UUserExample;
import com.heroku.entity.UUserRole;
import com.heroku.entity.UUserRoleExample;
import com.heroku.mapper.URolePermissionMapper;
import com.heroku.mapper.UUserMapper;
import com.heroku.mapper.UUserRoleMapper;
import com.heroku.util.MyDES;

public class MyShiroRealm extends AuthorizingRealm {

    /**
     *
     */
    @Autowired
    private UUserMapper uuserMapper;
    @Autowired
    private UUserRoleMapper uuserRoleMapper;
    @Autowired
    private URolePermissionMapper urolePermissionMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限认证方法：MyShiroRealm.doGetAuthenticationInfo()");

        UUser token = (UUser) SecurityUtils.getSubject().getPrincipal();

		Integer userId = token.getId();
	
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 根据用户ID查询角色（role），放入到Authorization里。
	
		UUserRoleExample uUserRoleExample = new UUserRoleExample();
	
		uUserRoleExample.createCriteria().andUidEqualTo(userId);
		// 根据登录用户id查询该用户的角色（role）id

		List<UUserRole> uUserRolelist = uuserRoleMapper.selectByExample(uUserRoleExample);
	
		Set<String> roleSet = new HashSet<String>();

		for (UUserRole uUserRole : uUserRolelist) {
			// logger.info(uUserRole.getRid().toString());
			roleSet.add(uUserRole.getRid().toString());
		}

		info.setRoles(roleSet);
		
		// 根据用户ID查询权限（permission），放入到Authorization里。
		
		List<Integer> roleList = new ArrayList<Integer>();

		for (String role : roleSet) {
			roleList.add(Integer.parseInt(role));
		}

		URolePermissionExample uRolePermissionExample = new URolePermissionExample();
		uRolePermissionExample.createCriteria().andRidIn(roleList);
		List<URolePermission> uRolePermissionlist = urolePermissionMapper.selectByExample(uRolePermissionExample);

		Set<String> permissionSet = new HashSet<String>();
		for (URolePermission uRolePermission : uRolePermissionlist) {
			permissionSet.add(uRolePermission.getPid().toString());
		}
		info.setStringPermissions(permissionSet);
	
		return info;
    }
   
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("身份认证方法：MyShiroRealm.doGetAuthenticationInfo()");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String password = String.valueOf(token.getPassword());
		
	    //密码进行加密处理  
		String pawDES = MyDES.encryptBasedDes(password);
		
		token.setPassword(pawDES.toCharArray());
			
        UUserExample exa = new UUserExample();
        exa.createCriteria().andEmailEqualTo(token.getUsername())
                .andPswdEqualTo(String.valueOf(token.getPassword()));
       
		// 从数据库获取对应用户名密码的用户
        List<UUser> uUsers = uuserMapper.selectByExample(exa);
        UUser uUser = null;

        if(uUsers.size()!=0){
            uUser = uUsers.get(0);
        }
        if (null == uUser) {
            throw new AccountException("帐号或密码不正确！");
        }else if(uUser.getStatus()==0){
            /**
             * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
             */
            throw new DisabledAccountException("帐号已经禁止登录！");
        }else{
            //更新登录时间 last login time
        	/*exa.createCriteria().andLastLoginTimeEqualTo(new Date());
            uuserMapper.updateByExampleSelective(uUser, exa);*/
            
            uUser.setLastLoginTime(new Date());
            uuserMapper.updateByPrimaryKey(uUser);
        	
        }
        return new SimpleAuthenticationInfo(uUser, uUser.getPswd(), getName());

    }
}
