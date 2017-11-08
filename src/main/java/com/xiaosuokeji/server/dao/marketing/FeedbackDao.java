package com.xiaosuokeji.server.dao.marketing;


import com.xiaosuokeji.server.model.marketing.Feedback;

import java.util.List;

/**
 * Created by gustinlau on 11/1/17.
 */
public interface FeedbackDao {
    
    int save(Feedback feedback);

    int remove(Feedback feedback);

    Feedback get(Feedback feedback);

    List<Feedback> list(Feedback feedback);

    Long count(Feedback feedback);

    int update(Feedback feedback);
}
