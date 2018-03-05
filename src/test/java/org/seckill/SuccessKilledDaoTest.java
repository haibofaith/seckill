package org.seckill;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//真心不知道为什么5.0.4版本的spring依赖找不到这个包。哭晕
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
		// 注入Dao实现类依赖
		@Resource
		private SuccessKilledDao successKilledDao;
		@Test
		public void insertSuccessKilledTest() {
			long id =2L;
			long phone = 18511068888L;
			int insertCount = successKilledDao.insertSuccessKilled(id,phone);
			System.out.println(insertCount);
		}
		@Test
		public void queryByIdWithSeckillTest() {
			long id =2L;
			long phone = 18511068888L;
			SuccessKilled successkilled = successKilledDao.queryByIdWithSeckill(id,phone);
			System.out.println(successkilled);
		}
}
