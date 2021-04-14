package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.AdminExample;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseException;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.atguigu.crowd.exception.LoginFailedException;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.utils.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void saveAdmin(Admin admin) {
        String userPswd = admin.getUserPswd();
        // userPswd = CrowdUtil.md5(userPswd);
        userPswd = bCryptPasswordEncoder.encode(userPswd);
        admin.setUserPswd(userPswd);
        // 生成创建时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(date);
        admin.setCreateTime(createTime);
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        //1.根据登录账号查询Admin对象
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        //2.判断Admin对象是否为null
        if (adminList == null || adminList.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (adminList.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        Admin admin = adminList.get(0);
        //3.如果Admin对象为null则抛出异常
        if (admin == null || adminList.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        //4.如果Admin对象不为null则将数据库密码从Admin对象中取出
        String userPswdDB = admin.getUserPswd();
        //5.将表单提交的明文密码进行加密
        String userPswdForm = CrowdUtil.md5(userPswd);
        //6.对密码进行比较
        if (!Objects.equals(userPswdForm, userPswdDB)) {
            //7.如果比较结果是不一致则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        //8.如果一致则返回Admin对象
        return admin;
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public PageInfo<Admin> getAdminPage(String keyword, Integer pageNum, Integer pageSize) {
        // 1.开启分页功能
        PageHelper.startPage(pageNum, pageSize);
        // 2.查询 Admin 数据
        List<Admin> adminList = adminMapper.selectAdminListByKeyword(keyword);
        // ※辅助代码：打印 adminList 的全类名
/*        Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
        logger.debug("adminList 的全类名是：" + adminList.getClass().getName());*/
        // 3.为了方便页面使用将 adminList 封装为 PageInfo
        PageInfo<Admin> pageInfo = new PageInfo<>(adminList);
        return pageInfo;
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void update(Admin admin) {
        // Selective表示有选择性的更新，为null的字段不更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public void saveAdminRelationship(Integer adminId, List<Integer> roleIdList) {
        // 为了简化操作:先根据adminId删除旧的数据，再根据roleIdList保存全部新的数据
        // 1.根据adminId删除旧的关联关系数据
        adminMapper.deleteRelationship(adminId);
        // 2.根据roleIdList和adminId保存新的关联关系
        if (roleIdList != null) {
            adminMapper.insertNewRelationship(adminId, roleIdList);
        }
    }

    @Override
    public Admin getAdminByLoginAcct(String username) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(username);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        Admin admin = adminList.get(0);
        return admin;
    }
}
