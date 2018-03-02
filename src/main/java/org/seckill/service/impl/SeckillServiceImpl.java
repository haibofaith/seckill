package org.seckill.service.impl;

import java.util.Date;
import java.util.List;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exporter;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * @author user
 *
 */
@Service
public class SeckillServiceImpl implements SeckillService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//注入Service依赖 或者使用resource,inject
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	//用于混淆md5:越复杂越好
	private final String stat = "sdl,.x,.cdefonsneid1233@ll.ss.//phedkn";
	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exporter exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		if (seckill==null) {
			return new Exporter(false, seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if (nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()) {
			return new Exporter(false,seckillId,nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		String md5 = getMD5(seckillId);
		//md5 将字符串转化为md5
		return new Exporter(true, md5, seckillId);
	}

	/* (non-Javadoc)
	 * @see org.seckill.service.SeckillService#executeSeckill(long, long, java.lang.String)
	 * 执行秒杀
	 * 使用注解控制事务方法的优点
	 * 1、开发团队达成一致的约定，明确标注事务方法的编程风格
	 * 2、保证事务方法的执行时间尽可能短,不要穿插操作其他的网络操作RPC/HTTP，剥离到事务外
	 * 3、不是所有方法都需要事务，只有一条修改，只读操作等
	 */
	@Override
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		if (md5==null||!md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		try {
			//执行秒杀逻辑:减库存，记录购买行为
			int updateCount = seckillDao.reduceNumber(seckillId, new Date());
			if (updateCount<=0) {
				//没有更新到数据库, 秒杀结束
				throw new SeckillCloseException("seckill is closed");
			}else {
				//记录购买行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				//唯一的：sessionId,userPhone
				if (insertCount<=0) {
					//重复秒杀
					throw new RepeatKillException("seckill repeated");
				}else {
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					//用枚举更加优雅！！！SeckillStatEnum
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e) {
			//正常异常
			throw e;
		}catch (RepeatKillException e) {
			//正常异常
			throw e;
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			//所有编译器异常转化为运行期异常
			throw new SeckillException("seckill inner error:"+e.getMessage());
		}
	}

	
	private String getMD5(long seckillId) {
		String base =seckillId +"/" +stat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
}
