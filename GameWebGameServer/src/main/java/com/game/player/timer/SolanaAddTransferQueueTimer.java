package com.game.player.timer;


import com.game.player.structs.GachaCard;
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
public class SolanaAddTransferQueueTimer extends TimerEvent {

    /**
     * 转移的卡片信息
     */
    private GachaCard gachaCard;
    /**
     * 目标钱包地址
     */
    private String targetWalletAddress;

    public SolanaAddTransferQueueTimer(GachaCard gachaCard, String targetWalletAddress) {
        super(1, 0);
        this.gachaCard = gachaCard;
        this.targetWalletAddress = targetWalletAddress;
    }

    @Override
    public void action() {
        SpringBootUtils.getBean(SolanaManager.class).addTransferNftTask(this.gachaCard, this.targetWalletAddress);
        super.action();
    }

}
