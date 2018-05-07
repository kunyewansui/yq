package com.xiaosuokeji.server.service.product;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSTreeUtil;
import com.xiaosuokeji.server.constant.product.CategoryConsts;
import com.xiaosuokeji.server.dao.product.CategoryDao;
import com.xiaosuokeji.server.model.product.Category;
import com.xiaosuokeji.server.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 产品类型ServiceImpl
 * Created by kunye on 2018/05/06.
 */
@Service
public class CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	public void save(Category category) throws XSBusinessException {
		Category parent = categoryDao.get(new Category(category.getPId()));
		if(parent==null){
			category.setPId(0L);
			category.setLevel(1);
		}else {
			category.setLevel(parent.getLevel()+1);
		}
		categoryDao.save(category);
	}

	public void remove(Category category) throws XSBusinessException {
		Category existent = get(category);
		//校验该分类下是否存在子级
		Category children = new Category();
		children.setPId(existent.getId());
		Long childrenCount = categoryDao.count(children);
		if (childrenCount.compareTo(0L) > 0) {
			throw new XSBusinessException(CategoryConsts.CATEGORY_USED);
		}
		categoryDao.remove(existent);
	}

	@Transactional
	public void update(Category category) throws XSBusinessException {
		Category existent = get(category);
		//不能选择自己或自己的下级作为父级
		List<Category> list = categoryDao.listCombo(new Category());
		Map<Long, Category> map = XSTreeUtil.buildTree(list);
		List<Category> subList = XSTreeUtil.listSubTree(map.get(category.getId()));
		if(category.getPId() != null){
			boolean isSelfSub = subList.stream().anyMatch(s -> s.getId().equals(category.getPId()));
			if(isSelfSub){
				throw new XSBusinessException(CategoryConsts.CATEGORY_NOT_SELF_OR_SUB);
			}
		}
		//更新层级
		Category parent = categoryDao.get(new Category(category.getPId()));
		if(parent==null){
			category.setPId(0L);
			category.setLevel(1);
		}else {
			category.setLevel(parent.getLevel()+1);
		}
		categoryDao.update(category);
	}

	public Category get(Category category) throws XSBusinessException {
		Category existent = categoryDao.get(category);
		if (existent == null) {
			throw new XSBusinessException(CategoryConsts.CATEGORY_NOT_EXIST);
		}
		return existent;
	}

	public XSPageModel<Category> listAndCount(Category category) {
		category.setDefaultSort("id", "DESC");
		return XSPageModel.build(categoryDao.list(category), categoryDao.count(category));
	}

	public List<Category> tree(Long id) {
		Category category = new Category();
		category.setDefaultSort("seq", "DESC");
		List<Category> list = categoryDao.listCombo(category);
		XSTreeUtil.buildTree(list);
		Category c = new Category(id);
		List<Category> subTrees = XSTreeUtil.getSubTrees(list, c);
		//若不是顶级节点，则要包括自身
		Category cate = categoryDao.get(c);
		if(cate==null) return subTrees;
		cate.setChildren(subTrees);
		return CollectionUtils.toList(cate);
	}

	public List<Long> listSubTree(Long id) {
		List<Category> list = categoryDao.listCombo(new Category());
		Map<Long, Category> map = XSTreeUtil.buildTree(list);
		List<Category> c = XSTreeUtil.listSubTree(map.get(id));
		List<Long> cateList = c.stream().map(a -> a.getId()).collect(Collectors.toList());
		return cateList;
	}
}
