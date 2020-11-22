package com.lzb.easytest;


import com.lzb.bean.User;
import com.lzb.dao.UserDao;
import com.lzb.io.Resources;
import com.lzb.sqlSession.SqlSession;
import com.lzb.sqlSession.SqlSessionFactory;
import com.lzb.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author : LZB
 * @Date : 2020/10/11
 * @Description :
 */
public class EasyTest {


    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

    }


    @Test
    public void testSelect() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> all = userDao.findAll();
        System.out.println("all = " + all);
    }

    @Test
    public void testSelectOne() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(1);
        user.setUsername("tom");
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        System.out.println("userDao.findByCondition(user) = " + userDao.findByCondition(user));
    }


    @Test
    public void testUpdate() throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId(2);
        user.setPassword("333333");
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        System.out.println("userDao.updatePasswordById(user) = " + userDao.updatePasswordById(user));

    }

    @Test
    public void test1() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(2);
        user.setUsername("tom");
        List<User> userList = sqlSession.selectOne("com.lzb.dao.UserDao.findByCondition", user);
        System.out.println("userList = " + userList);

    }

    @Test
    public void testInsert() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setUsername("liaowei");
        user.setPassword("123456");
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        System.out.println("userDao.insertUser(user) = " + userDao.insertUser(user));
    }

    @Test
    public void testDelete() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setUsername("liaowei");
        user.setPassword("123456");
        user.setId(3);
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        System.out.println("userDao.deleteUser(user) = " + userDao.deleteUser(user));
    }


}
