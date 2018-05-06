package com.xiaosuokeji.server.dao.order;

import com.xiaosuokeji.server.model.order.Payment;
import java.util.List;

/**
 * 还款Dao
 * Created by kunye on 2018/05/06.
 */
public interface PaymentDao {

	int save(Payment payment);

	int remove(Payment payment);

	int update(Payment payment);

	Payment get(Payment payment);

	List<Payment> list(Payment payment);

	Long count(Payment payment);
}
