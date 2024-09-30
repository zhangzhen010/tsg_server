package com.game.discord.listener;

import com.game.discord.manager.DiscordManager;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * discord监听事件
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/19 14:11
 */
@Component
@Log4j2
public class DiscordListener extends ListenerAdapter {

    @Lazy
    private @Resource DiscordManager discordManager;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        discordManager.onMessageReceived(event);
    }
}
