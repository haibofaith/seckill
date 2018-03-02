package org.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

public interface SeckillDao {
	/**
	 * 减少库存
	 */
	int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

	/**
	 * 根据id查询秒杀库存
	 */
	Seckill queryById(long seckillId);

	/**
	 * 根据偏移量查询所有库存
	 */
	List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
