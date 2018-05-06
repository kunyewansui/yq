package com.xiaosuokeji.server.service.order;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.order.PaymentConsts;
import com.xiaosuokeji.server.dao.order.PaymentDao;
import com.xiaosuokeji.server.model.order.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 还款ServiceImpl
 * Created by kunye on 2018/05/06.
 */
@Service
public class PaymentService {

	@Autowired
	private PaymentDao paymentDao;

	public void save(Payment payment) throws XSBusinessException {
		paymentDao.save(payment);
	}

	public void remove(Payment payment) throws XSBusinessException {
		Payment existent = get(payment);
		paymentDao.remove(existent);
	}

	public void update(Payment payment) throws XSBusinessException {
		Payment existent = get(payment);
		paymentDao.update(payment);
	}

	public Payment get(Payment payment) throws XSBusinessException {
		Payment existent = paymentDao.get(payment);
		if (existent == null) {
			throw new XSBusinessException(PaymentConsts.PAYMENT_NOT_EXIST);
		}
		return existent;
	}

	public XSPageModel<Payment> listAndCount(Payment payment) {
		payment.setDefaultSort("id", "DESC");
		return XSPageModel.build(paymentDao.list(payment), paymentDao.count(payment));
	}
}
