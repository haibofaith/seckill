package org.seckill.dto;

/**
 * @author user dto中存放业务不相关的对象 暴漏秒杀地址
 */
public class Exporter {
	// 是否开启秒杀
	private boolean exported;
	// 加密算法秒杀地址
	private String md5;
	// ID
	private long seckillId;
	// 系统当前时间
	private long now;
	// 开启时间
	private long start;
	// 结束时间
	private long end;

	public Exporter(boolean exported, String md5, long seckillId) {
		super();
		this.exported = exported;
		this.md5 = md5;
		this.seckillId = seckillId;
	}

	public Exporter(boolean exported, long seckillId,long now, long start, long end) {
		super();
		this.exported = exported;
		this.now = now;
		this.start = start;
		this.end = end;
		this.seckillId = seckillId;
	}
	//查不到秒杀地址
	public Exporter(boolean exported, long seckillId) {
		super();
		this.exported = exported;
		this.seckillId = seckillId;
	}

	public boolean isExported() {
		return exported;
	}

	public void setExported(boolean exported) {
		this.exported = exported;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getNow() {
		return now;
	}

	public void setNow(long now) {
		this.now = now;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Exporter [exported=" + exported + ", md5=" + md5 + ", seckillId=" + seckillId + ", now=" + now
				+ ", start=" + start + ", end=" + end + "]";
	}
	
}
