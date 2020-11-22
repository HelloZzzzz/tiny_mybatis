package com.lzb.sqlSession;


/**
 * @Author : LZB
 * @Date : 2020/10/12
 * @Description :
 */
public interface SqlSessionFactory {
    SqlSession openSession();
}