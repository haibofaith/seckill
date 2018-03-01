package org.seckill.dao;

import java.util.Date;
import java.util.List;

import org.seckill.entity.Seckill;

public interface SeckillDao {
	int reduceNumber(long seckillId,Date killTime);
	Seckill queryById(long seckillId);
	List<Seckill> queryAll(int offset,int limit);
}
