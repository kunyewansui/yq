package com.xiaosuokeji.server.service.impl.system;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.system.FeedbackConsts;
import com.xiaosuokeji.server.dao.system.FeedbackDao;
import com.xiaosuokeji.server.model.system.Feedback;
import com.xiaosuokeji.server.service.intf.system.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gustinlau on 11/1/17.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackDao feedbackDao;

    @Override
    public void save(Feedback feedback) throws XSBusinessException {
        feedbackDao.save(feedback);
    }

    @Override
    public void remove(Feedback feedback) throws XSBusinessException {
        feedbackDao.remove(feedback);
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
}
