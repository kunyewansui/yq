package com.xiaosuokeji.server.service.merchant;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.merchant.MerchantConsts;
import com.xiaosuokeji.server.dao.merchant.MerchantDao;
import com.xiaosuokeji.server.model.merchant.Merchant;
import com.xiaosuokeji.server.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商户ServiceImpl
 * Created by kunye on 2018/05/06.
 */
@Service
public class MerchantService {

	@Autowired
	private MerchantDao merchantDao;

	public void save(Merchant merchant) throws XSBusinessException {
		merchantDao.save(merchant);
	}

	public void remove(Merchant merchant) throws XSBusinessException {
		Merchant existent = get(merchant);
		merchantDao.remove(existent);
	}

	public void update(Merchant merchant) throws XSBusinessException {
		Merchant existent = get(merchant);
		merchantDao.update(merchant);
	}

	public Merchant get(Merchant merchant) throws XSBusinessException {
		Merchant existent = merchantDao.get(merchant);
		if (existent == null) {
			throw new XSBusinessException(MerchantConsts.MERCHANT_NOT_EXIST);
		}
		return existent;
	}

	public XSPageModel<Merchant> listAndCount(Merchant merchant) {
		merchant.setDefaultSort("id", "DESC");
		return XSPageModel.build(merchantDao.list(merchant), merchantDao.count(merchant));
	}

	public List<Merchant> debtList() {
		Merchant merchant = new Merchant();
		merchant.setDynamic(CollectionUtils.toMap("debt", "1"));
		merchant.setLimit(null);
		return merchantDao.list(merchant);
	}

    public void updateDebt(Long merchantId, BigDecimal debt) throws XSBusinessException {
		Merchant merchant = new Merchant();
		merchant.setId(merchantId);
		merchant.setDebt(debt);
		Merchant existent = merchantDao.get(merchant);
		if(existent.getDebt().add(debt).compareTo(BigDecimal.ZERO)<0){
			throw new XSBusinessException(MerchantConsts.MERCHANT_DEBT_FLOW);
		}
		merchantDao.updateDebt(merchant);
	}
}
