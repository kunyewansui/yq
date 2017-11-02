package com.xiaosuokeji.server.service.intf.marketing;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.model.marketing.Feedback;

/**
 * 用户反馈Service
 * Created by gustinlau on 11/1/17.
 */
public interface FeedbackService {
    void save(Feedback feedback);

    void remove(Feedback feedback) throws XSBusinessException;

    Feedback get(Feedback feedback) throws XSBusinessException;

    XSPageModel<Feedback> listAndCount(Feedback feedback);
}
