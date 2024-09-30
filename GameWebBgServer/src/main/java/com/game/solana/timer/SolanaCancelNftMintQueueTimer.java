package com.game.solana.timer;


import com.game.solana.manager.SolanaManager;
import com.game.timer.TimerEvent;
import com.game.utils.SpringBootUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Solana取消mint卡片timer
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/12 15:08
 */
@Getter
@Setter
public class SolanaCancelNftMintQueueTimer extends TimerEvent {


    public SolanaCancelNftMintQueueTimer() {
        super(1, 0);
    }

    @Override
    public void action() {
        SpringBootUtils.getBean(SolanaManager.class).mintCardCancel();
        super.action();
    }

}
