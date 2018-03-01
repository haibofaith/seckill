package org.seckill;


import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//真心不知道为什么5.0.4版本的spring依赖找不到这个包。哭晕
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit Spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	// 注入Dao实现类依赖
	@Resource
	private SeckillDao seckillDao;
	/*
	 * 1\java.lang.Exception: No tests found matching
	 * [{ExactMatcher:fDisplayName=queryByIdtest],
	 * 算了，这个问题，如果junit 和 Spring版本没问题的话，试着运行整行看看日志。
	 * 
	 * 2\Caused by: java.lang.ClassNotFoundException: org.apache.ibatis.session.SqlSe
	 * Mybatis.jar和Mybatis-spring的版本不兼容。
	 **/
	@Test
	public void queryByIdtest() throws Exception{
		long id = 1;
		Seckill seckil = seckillDao.queryById(id);
		System.out.println(seckil.getName());
		System.out.println(seckil);
	}

}
