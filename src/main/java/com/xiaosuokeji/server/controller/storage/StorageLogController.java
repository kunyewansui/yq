package com.xiaosuokeji.server.controller.storage;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.order.Payment;
import com.xiaosuokeji.server.model.product.StorageLog;
import com.xiaosuokeji.server.service.product.StorageLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 库存日志Controller
 * Created by kunye on 2018/06/02.
 */
@Controller
@XSLog
@XSExceptionHandler
public class StorageLogController {

	@Autowired
	private StorageLogService storageLogService;

	@RequestMapping(value = "/admin/storage/log", method = RequestMethod.GET)
	public String index(Model model, StorageLog storageLog) {
		if(storageLog.getPage() == null) {
			storageLog.setPage(1L);
		}
		model.addAttribute("search", storageLog);
		model.addAttribute("pageModel", storageLogService.listAndCount(storageLog));
		return "admin/storage/storageLog";
	}

	@RequestMapping(value = "/admin/storage/log/list", method = RequestMethod.GET)
	@ResponseBody
	public XSServiceResult list(StorageLog storageLog) throws XSBusinessException {
		if(storageLog.getPage() == null) {
			storageLog.setPage(1L);
		}
		return XSServiceResult.build().data(storageLogService.listAndCount(storageLog));
	}

}
