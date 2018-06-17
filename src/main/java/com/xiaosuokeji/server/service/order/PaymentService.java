package com.xiaosuokeji.server.service.order;

import com.xiaosuokeji.framework.annotation.XSTransactional;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.order.PaymentConsts;
import com.xiaosuokeji.server.dao.order.PaymentDao;
import com.xiaosuokeji.server.model.merchant.Merchant;
import com.xiaosuokeji.server.model.order.Payment;
import com.xiaosuokeji.server.service.merchant.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 还款ServiceImpl
 * Created by kunye on 2018/05/06.
 */
@Service
public class PaymentService {

	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private MerchantService merchantService;

	@Transactional
	public void save(Payment payment) throws XSBusinessException {
		paymentDao.save(payment);
		// 客户的欠款更新
		merchantService.updateDebt(payment.getMerchantId(), payment.getAmount().multiply(BigDecimal.valueOf(-1)));
	}

	@Transactional
	public void remove(Payment payment) throws XSBusinessException {
		Payment existent = get(payment);
		paymentDao.remove(existent);
		// 客户的欠款更新
		merchantService.updateDebt(existent.getMerchantId(), existent.getAmount());
	}

	@Transactional
	public void update(Payment payment) throws XSBusinessException {
		Payment existent = get(payment);
		paymentDao.update(payment);
		if(payment.getAmount()!=null){
			// 客户的欠款相应减少
			merchantService.updateDebt(payment.getMerchantId(), payment.getAmount().subtract(existent.getAmount()));
		}
	}

	public Payment get(Payment payment) throws XSBusinessException {
		Payment existent = paymentDao.get(payment);
		if (existent == null) {
			throw new XSBusinessException(PaymentConsts.PAYMENT_NOT_EXIST);
		}
		return existent;
	}

	public XSPageModel<Payment> listAndCount(Payment payment) {
		payment.setDefaultSort("a.id", "DESC");
		return XSPageModel.build(paymentDao.list(payment), paymentDao.count(payment));
	}
}
