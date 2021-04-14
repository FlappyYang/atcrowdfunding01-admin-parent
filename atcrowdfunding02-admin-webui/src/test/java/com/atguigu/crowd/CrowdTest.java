package com.atguigu.crowd;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.mapper.RoleMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.service.api.RoleService;
import com.atguigu.crowd.service.impl.AdminServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void test() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void test2() {
        Admin admin = new Admin(null, "tom", "123123", "tom", "tom@qq.com", null);

        int insert = adminMapper.insert(admin);
        System.out.println(insert);
    }

    @Test
    public void testLog() {
        // 获取日志记录对象
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        // 按照 Debug 级别打印日志
        logger.debug("debug debug debug debug debug debug debug debug");
        logger.debug("debug debug debug debug debug debug debug debug");
        logger.debug("debug debug debug debug debug debug debug debug");

        logger.info("info info info info info info info info");
        logger.info("info info info info info info info info");
        logger.info("info info info info info info info info");

        logger.warn("warn warn warn warn warn warn warn warn");
        logger.warn("warn warn warn warn warn warn warn warn");
        logger.warn("warn warn warn warn warn warn warn warn");

        logger.error("error error error error error error error");
        logger.error("error error error error error error error");
        logger.error("error error error error error error error");
    }

    @Test
    public void testTX() {
        Admin admin = new Admin(null, "tom", "123456", "tom", "tom@qq.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testxx() {
        for (int i = 0; i < 238; i++) {
            int insert = adminMapper.insert(new Admin(null, "loginAcct" + i, "userPswd" + i, "userNmae" + i, "email" + i, null));
        }
    }

    @Test
    public void testxx2() {
        for (int i = 0; i < 100; i++) {
            int insert = roleMapper.insert(new Role(null, "role" + i));
        }
    }
}
