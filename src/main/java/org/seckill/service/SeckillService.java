package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exporter;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

/**
 * 三个方面：方法定义粒度，参数，返回类型（return 类型/异常）
 */
public interface SeckillService {
	/**
	 * 查询所有秒杀记录	
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * option+commad+J自动生成注释
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开启，输出秒杀地址
	 * 秒杀未开启，输出系统时间和秒杀时间
	 * @param seckillId
	 */
	Exporter exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀操作:秒杀失败，抛出异常
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5) throws SeckillException,RepeatKillException,SeckillCloseException;
}
