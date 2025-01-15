package com.abin.mallchat.common.chat.service.impl;

import cn.hutool.core.thread.NamedThreadFactory;
import com.abin.mallchat.common.chat.service.WeChatMsgOperationService;
import com.abin.mallchat.common.common.handler.GlobalUncaughtExceptionHandler;
import com.abin.mallchat.common.user.domain.entity.User;
import com.abin.mallchat.common.user.service.cache.UserCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class WeChatMsgOperationServiceImpl implements WeChatMsgOperationService {

    private static final ExecutorService executor = new ThreadPoolExecutor(1, 10, 3000L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(20),
            new NamedThreadFactory("wechat-operation-thread", null, false,
                    GlobalUncaughtExceptionHandler.getInstance()));

    // at消息的微信推送模板id
    private final String atMsgPublishTemplateId = "Xd7sWPZsuWa0UmpvLaZPvaJVjNj1KjEa0zLOm5_Z7IU";

    private final String WE_CHAT_MSG_COLOR = "#A349A4";

    @Autowired
    private UserCache userCache;


    @Override
    public void publishChatMsgToWeChatUser(long senderUid, List<Long> receiverUidList, String msg) {
        User sender = userCache.getUserInfo(senderUid);
        Set uidSet = new HashSet();
        uidSet.addAll(receiverUidList);
        Map<Long, User> userMap = userCache.getUserInfoBatch(uidSet);
        userMap.values().forEach(user -> {
            if (Objects.nonNull(user.getOpenId())) {
                executor.execute(() -> {
                    //推送消息逻辑
                });
            }
        });
    }
}
