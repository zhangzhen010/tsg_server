package com.game.solana.timer;


import com.game.solana.manager.SolanaManager;
import com.game.timer.TimerEvent;
import com.game.utils.SpringBootUtils;
import com.game.utils.TimeUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Solana transfer 卡片 timer
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/12 15:08
 */
@Component
@Getter
@Setter
public class SolanaNftTransferTimer extends TimerEvent {


    public SolanaNftTransferTimer() {
        super(-1, TimeUtil.TWO_MILLIS);
    }

    @Override
    public void action() {
        SpringBootUtils.getBean(SolanaManager.class).transfer();
        super.action();
    }

}
