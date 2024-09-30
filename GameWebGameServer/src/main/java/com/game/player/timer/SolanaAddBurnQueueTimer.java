package com.game.player.timer;


import com.game.player.structs.GachaCardRefund;
import com.game.solana.manager.SolanaManager;
import com.game.timer.TimerEvent;
import com.game.utils.SpringBootUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Solana销毁NFT timer
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/18 18:34
 */
@Getter
@Setter
public class SolanaAddBurnQueueTimer extends TimerEvent {

    /**
     * 销毁卡片交易id
     */
    private GachaCardRefund reqData;

    public SolanaAddBurnQueueTimer(GachaCardRefund reqData) {
        super(1, 0);
        this.reqData = reqData;
    }

    @Override
    public void action() {
        SpringBootUtils.getBean(SolanaManager.class).addBurnNftTask(reqData);
        super.action();
    }

}
