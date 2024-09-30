package com.game.solana.timer;


import com.game.player.structs.GachaCardTemplate;
import com.game.solana.manager.SolanaManager;
import com.game.timer.TimerEvent;
import com.game.utils.SpringBootUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Solana创建铸造卡片任务timer
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/12 15:08
 */
@Getter
@Setter
public class SolanaAddNftMintQueueTimer extends TimerEvent {

    /**
     * 卡片模板
     */
    private GachaCardTemplate gachaCardTemplate;
    /**
     * 铸造数量
     */
    private int count;

    public SolanaAddNftMintQueueTimer(GachaCardTemplate gachaCardTemplate, int count) {
        super(1, 0);
        this.gachaCardTemplate = gachaCardTemplate;
        this.count = count;
    }

    @Override
    public void action() {
        SpringBootUtils.getBean(SolanaManager.class).addMintTask(gachaCardTemplate, count);
        super.action();
    }

}
