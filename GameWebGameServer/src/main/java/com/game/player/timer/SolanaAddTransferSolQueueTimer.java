package com.game.player.timer;


import com.game.solana.manager.SolanaManager;
import com.game.timer.TimerEvent;
import com.game.utils.SpringBootUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Solana转移NFT timer
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/18 18:34
 */
@Getter
@Setter
public class SolanaAddTransferSolQueueTimer extends TimerEvent {

    /**
     * 充值玩家id
     */
    private long playerId;
    /**
     * 充值订单id
     */
    private String gameOrderId;
    /**
     * 充值交易id
     */
    private String transactionId;

    public SolanaAddTransferSolQueueTimer(long playerId, String gameOrderId, String transactionId) {
        super(1, 0);
        this.playerId = playerId;
        this.gameOrderId = gameOrderId;
        this.transactionId = transactionId;
    }

    @Override
    public void action() {
        SpringBootUtils.getBean(SolanaManager.class).addTransferSolTask(this.playerId, this.gameOrderId, this.transactionId);
        super.action();
    }

}
