package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * @author 杨鹏炜
 * @data 2021/4/10  19:08:35
 * 考虑到 User 对象中仅仅包含账号和密码，为了能够获取到原始的Admin对象，专门创建这个类对User类进行扩展
 */
public class SecurityAdmin extends User {
    // 原始的Admin对象，包含Admin对象的全部属性
    private Admin originalAdmin;
    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities) {
        super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), authorities);
        this.originalAdmin = originalAdmin;
        // 将原始Admin对象中的密码擦除
        this.originalAdmin.setUserPswd(null);
    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }

    public void setOriginalAdmin(Admin originalAdmin) {
        this.originalAdmin = originalAdmin;
    }
}
