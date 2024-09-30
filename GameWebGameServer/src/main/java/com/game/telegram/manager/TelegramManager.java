package com.game.telegram.manager;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;

/**
 * Telegram管理器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/26 16:44
 */
@Component
@Log4j2
public class TelegramManager {

    private @Value("${server.telegram.botToken}") String botToken;
    private @Value("${server.telegram.botName}") String botName;

    /**
     * telegram初始化
     */
    @PostConstruct
    public void init() {
        try {
            new TelegramBot(botToken, botName);
            log.info("Telegram初始化成功！");
        } catch (Exception e) {
            log.error("异常：", e);
        }
    }

    class TelegramBot extends TelegramLongPollingBot {

        private final String botToken;

        private final String botName;

        public TelegramBot(String token, String botName) {
            super(token);
            this.botToken = token;
            this.botName = botName;
        }

        @Override
        public void onUpdateReceived(Update update) {
            // 检查是否有 Callback Query
            if (update.hasMessage()) {
                Message message = update.getMessage();
                String text = message.getText();
                long chatId = message.getChatId();
                log.info("收到telegram消息：text=" + text);
            } else if (update.hasCallbackQuery()) {
                handleCallbackQuery(update.getCallbackQuery());
            } else if (update.hasInlineQuery()) {
                InlineQuery inlineQuery = update.getInlineQuery();
                String query = inlineQuery.getQuery();
                long fromUserId = inlineQuery.getFrom().getId();
                log.info("收到telegram命令消息：" + query);
            }
        }

        private void handleCallbackQuery(CallbackQuery callbackQuery) {
            // 处理 Callback Query，例如从中获取用户 ID 等信息
            long userId = callbackQuery.getFrom().getId();
            String data = callbackQuery.getData();
            log.info("telegram收到回调处理userId=" + userId + " data=" + data);
            log.info("user=" + callbackQuery.getFrom().toString());
            log.info("telegramMessage=" + callbackQuery.getMessage());
            // 假设 data 是用户选择的登录选项
            if ("login".equals(data)) {
                // 这里可以记录用户 ID 或者其他信息
                // 例如：保存到数据库
                // ...
            }
        }

        @Override
        public String getBotUsername() {
            return this.botName;
        }

    }

}
