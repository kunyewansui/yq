package com.xiaosuokeji.server.dao.merchant;

import com.xiaosuokeji.server.model.merchant.Merchant;
import java.util.List;

/**
 * 商户Dao
 * Created by kunye on 2018/05/06.
 */
public interface MerchantDao {

	int save(Merchant merchant);

	int remove(Merchant merchant);

	int update(Merchant merchant);
	int updateDebt(Merchant merchant);

	Merchant get(Merchant merchant);

	List<Merchant> list(Merchant merchant);

	Long count(Merchant merchant);

}
