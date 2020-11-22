### tiny_mybatis
一个简单mybatis框架,拥有mapper等基本功能.

### 关于
tiny_mybatis是为了学习mybatis源码的而开发的，可以认为是一个精简版的mybatis。

### 功能
1、支持接口mapper映射，也支持指定namespace等查询。
</br>
2、可从xml中读取配置。


### TODO
1、JavaBean与数据库字段映射
</br>
2、引入一级缓存
### 使用端 
    相关SQL见tiny_mybatis.sql
    
    @Test
    public void testSelect() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> all = userDao.findAll();
        System.out.println("all = " + all);
    }
    /*
    all = [User{id=1, username='lucy', password='1111111', birthday='2019-12-12'}, 
    User{id=2, username='tom', password='333333', birthday='2019-12-12'}, 
    User{id=4, username='liaowei', password='123456', birthday='null'}, 
    User{id=5, username='tom', password='5456456', birthday='null'}, 
    User{id=6, username='liaowei', password='123456', birthday='null'}]
    */
    
    
    
    
    @Test
    public void testSelectOne() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(1);
        user.setUsername("tom");
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        System.out.println("userDao.findByCondition(user) = " + userDao.findByCondition(user));
    }
    //java.lang.RuntimeException: Expected one result (or null) to be returned by selectOne(), but found: 2

