package com.game.datagroup.structs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 所有服务器离散存储数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/14 17:59
 */
@Getter
@Setter
@Document(collection = "dataGroup")
public class DataGroup {

    @Id
    private int id;

    /***************************** ↓排行榜公共离散数据↓ *****************************/

    /**
     * 完成排行榜任务的玩家信息 key=RankType value=RankQuestData
     */
    private ConcurrentHashMap<Integer, RankQuestData> rankQuestMap = new ConcurrentHashMap<>();

    /***************************** ↑排行榜公共离散数据↑ *****************************/


    /***************************** ↓solana相关离散数据↓ *****************************/

    /**
     * Solana NFT collection mint keypair私钥（DataGroupType.2）
     */
    private String collectionMintPrivateKey = "";
    /**
     * Solana NFT mint队列数据（DataGroupType.3）
     */
    private SolanaMintQueueData solanaMintQueueData = new SolanaMintQueueData();
    /**
     * Solana NFT transfer队列数据（DataGroupType.4）
     */
    private LinkedList<SolanaTransferQueueData> solanaTransferQueueDataList = new LinkedList<>();
    /**
     * Solana NFT burn队列数据（DataGroupType.5）
     */
    private LinkedList<SolanaBurnQueueData> solanaBurnQueueDataList  = new LinkedList<>();

    /***************************** ↑solana相关离散数据↑ *****************************/

    /***************************** ↓卡池相关离散数据↓ *****************************/

    /**
     * 卡池背景图片或者背景视频的地址
     */
    private List<String> gachaPoolImageUrlList = new ArrayList<>();
    /**
     * 卡池抽卡动画地址
     */
    private List<GachaPoolAnimationData> gachaPoolAnimationDataList = new ArrayList<>();

    /***************************** ↑卡池相关离散数据↑ *****************************/
}
