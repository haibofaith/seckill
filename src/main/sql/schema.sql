CREATE TABLE `seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` timestamp NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '秒杀开启时间',
  `end_time` timestamp NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

CREATE TABLE `success_killed` (
  `seckill_id` bigint(20) NOT NULL COMMENT '秒杀商品ID',
  `user_phone` bigint(20) NOT NULL COMMENT '用户手机号',
  `state` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '状态：-1:无效，0:成功，1:付款',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`,`user_phone`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表'

insert into seckill.seckill(name,number,start_time,end_time)
values
('1000元秒杀iphonex',100,'2017-11-01 00:00:00','2017-11-01 00:00:00'),
('800元秒杀小米电视',200,'2017-11-01 00:00:00','2017-11-01 00:00:00'),
('200元秒杀小米6',300,'2017-11-01 00:00:00','2017-11-01 00:00:00'),
('10元秒杀红米',400,'2017-11-01 00:00:00','2017-11-01 00:00:00');