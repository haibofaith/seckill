package org.seckill.web;

import java.util.Date;
import java.util.List;

import org.seckill.dto.Exporter;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillService seckillService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String List(Model model) {
		// 获取列表页
		List<Seckill> list = seckillService.getSeckillList();
		model.addAttribute("list", list);
		return "list";
	}

	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
		if (seckillId == null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getById(seckillId);
		if (seckill == null) {
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}

	// ajax json
	@RequestMapping(value = "/{seckillId}/exporter", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<Exporter> exporter(@PathVariable("seckillId") Long seckillId) {
		SeckillResult<Exporter> result;
		try {
			//获取秒杀地址
			Exporter exporter = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<>(true, exporter);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = new SeckillResult<>(false, e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") long seckillId,
			@CookieValue(value = "killPhone", required = false) long userPhone, @PathVariable("md5") String md5) {
		SeckillResult<SeckillExecution> result;
		try {
			SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
			result = new SeckillResult<>(true, execution);
		}catch (RepeatKillException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
			result = new SeckillResult<>(false, execution);
		}catch (SeckillCloseException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
			result = new SeckillResult<>(false, execution);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
			result = new SeckillResult<>(false, execution);
		}
		return result;
	}
	
	@RequestMapping(value="/time/now",method = RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> time(){
		Date date = new Date();
		return new SeckillResult<>(true, date.getTime());
	}
}
