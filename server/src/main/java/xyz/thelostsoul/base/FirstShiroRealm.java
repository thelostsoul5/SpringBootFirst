package xyz.thelostsoul.base;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.thelostsoul.bean.User;
import xyz.thelostsoul.service.inter.IUserService;

public class FirstShiroRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String userId = (String) principals.getPrimaryPrincipal();

        User user = userService.getUserById(Integer.valueOf(userId));

        if (user != null) {
            //TODO 鉴权完善
            authorizationInfo.addRole("user");
            authorizationInfo.addStringPermission("all");
        }

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userId = (String) token.getPrincipal();
        User user = userService.getUserById(Integer.valueOf(userId));

        if (user == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(String.valueOf(user.getId()), user.getPassword(),
                ByteSource.Util.bytes("first"), getName());
    }
}
