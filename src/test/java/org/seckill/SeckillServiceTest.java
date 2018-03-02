package org.seckill;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exporter;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//真心不知道为什么5.0.4版本的spring依赖找不到这个包。哭晕
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit Spring配置文件
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class SeckillServiceTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillService seckillService;

	@Test
	public void getSeckillList() throws Exception {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}", list);
	}

	@Test
	public void getById() throws Exception {
		int seckillId = 1;
		Seckill seckill = seckillService.getById(seckillId);
		logger.info("seckill={}", seckill);
	}

	@Test
	public void exportSeckillUrl() throws Exception {
		int seckillId = 1;
		Exporter exporter = seckillService.exportSeckillUrl(seckillId);
		logger.info("exporter={}", exporter);
	}

	@Test
	public void executeSeckill() throws Exception {
		long id = 1L;
		long phone = 18511068608L;
		String md5="65b0474df07d9f76b8cb9e8806ed7625";
		try {
			SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
			logger.info("seckillExecution={}", seckillExecution);
		} catch (RepeatKillException e) {
			logger.error(e.getMessage());
		}catch (SeckillCloseException e) {
			logger.error(e.getMessage());
		}
		
	}
}
