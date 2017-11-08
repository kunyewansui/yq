package com.xiaosuokeji.server.service.impl.marketing;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.system.FeedbackConsts;
import com.xiaosuokeji.server.dao.marketing.FeedbackDao;
import com.xiaosuokeji.server.model.marketing.Feedback;
import com.xiaosuokeji.server.service.intf.marketing.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户反馈ServiceImpl
 * Created by gustinlau on 11/1/17.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackDao feedbackDao;

    @Override
    public void save(Feedback feedback) {
        feedbackDao.save(feedback);
    }

    @Override
    public void remove(Feedback feedback) throws XSBusinessException {
        Feedback existent = get(feedback);
        feedbackDao.remove(existent);
    }

    @Override
    public Feedback get(Feedback feedback) throws XSBusinessException {
        Feedback existent = feedbackDao.get(feedback);
        if (existent == null) {
            throw new XSBusinessException(FeedbackConsts.FEEDBACK_NOT_EXIST);
        }
        return existent;
    }

    @Override
    public XSPageModel<Feedback> listAndCount(Feedback feedback) {
        feedback.setDefaultSort("create_time", "DESC");
        return XSPageModel.build(feedbackDao.list(feedback), feedbackDao.count(feedback));
    }

    @Override
    public void solve(Feedback feedback) throws XSBusinessException {
        Feedback existent = get(feedback);
        feedbackDao.update(existent);
    }
}
