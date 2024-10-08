package com.game.player.structs;

import com.game.activity.proto.ActivityProto.ResActivityList;
import com.game.arena.proto.ArenaProto;
import com.game.bean.proto.BeanProto;
import com.game.bean.proto.BeanProto.PayInfo;
import com.game.bean.proto.BeanProto.PlayerSettingInfo;
import com.game.fight.proto.FightProto;
import com.game.grpc.proto.GrpcProto;
import com.game.hero.proto.HeroProto;
import com.game.mail.proto.MailProto.ResMailAward;
import com.game.mail.proto.MailProto.ResMailDelete;
import com.game.mail.proto.MailProto.ResMailList;
import com.game.mail.proto.MailProto.ResMailOpen;
import com.game.pack.proto.PackProto;
import com.game.pay.proto.PayProto.ResPaySuccess;
import com.game.player.proto.PlayerProto;
import com.game.player.proto.PlayerProto.ResPlayerAlert;
import com.game.publicmailserver.proto.PublicMailProto.*;
import com.game.quest.proto.QuestProto.ResQuestAward;
import com.game.quest.proto.QuestProto.ResQuestUpdate;
import com.game.rank.proto.RankProto;
import com.game.shop.proto.ShopProto.ResShopBuy;
import com.game.shop.proto.ShopProto.ResShopInfo;
import com.game.smelter.proto.SmelterProto;
import com.game.survivor.proto.SurvivorProto;
import com.game.wing.proto.WingProto;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家自己的ProtoBuf使用对象
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/6 10:11
 */
public class PlayerProtobuf {

    /**
     * 向登录服更新用户信息
     */
    private final transient GrpcProto.ReqPublicLoginUpUserInfo.Builder reqPublicLoginUpUserInfoBuilder = GrpcProto.ReqPublicLoginUpUserInfo.newBuilder();

    public GrpcProto.ReqPublicLoginUpUserInfo.Builder getReqPublicLoginUpUserInfoBuilder() {
        reqPublicLoginUpUserInfoBuilder.clear();
        return reqPublicLoginUpUserInfoBuilder;
    }

    /**
     * 玩家设置信息proto
     */
    private final transient PlayerSettingInfo.Builder playerSettingInfoBuilder = PlayerSettingInfo.newBuilder();

    public PlayerSettingInfo.Builder getPlayerSettingInfoBuilder() {
        playerSettingInfoBuilder.clear();
        return playerSettingInfoBuilder;
    }

    /**
     * 玩家任务proto
     */
    private final transient BeanProto.QuestInfo.Builder questInfoBuilder = BeanProto.QuestInfo.newBuilder();

    public BeanProto.QuestInfo.Builder getQuestInfoBuilder() {
        questInfoBuilder.clear();
        return questInfoBuilder;
    }

    /**
     * 玩家任务目标proto
     */
    private final transient BeanProto.QuestTargetInfo.Builder questTargetInfoBuilder = BeanProto.QuestTargetInfo.newBuilder();

    public BeanProto.QuestTargetInfo.Builder getQuestTargetInfoBuilder() {
        questTargetInfoBuilder.clear();
        return questTargetInfoBuilder;
    }

    /**
     * 返回充值成功proto
     */
    private final transient ResPaySuccess.Builder resPaySuccessBuilder = ResPaySuccess.newBuilder();

    public ResPaySuccess.Builder getResPaySuccessBuilder() {
        resPaySuccessBuilder.clear();
        return resPaySuccessBuilder;
    }

    /**
     * 请求向邮件服打开邮件设置已读消息proto
     */
    private final transient ReqPublicMailOpen.Builder reqPublicMailOpenBuilder = ReqPublicMailOpen.newBuilder();

    public ReqPublicMailOpen.Builder getReqPublicMailOpenBuilder() {
        reqPublicMailOpenBuilder.clear();
        return reqPublicMailOpenBuilder;
    }

    /**
     * 返回打开邮件设置已读消息proto
     */
    private final transient ResMailOpen.Builder resMailOpenBuilder = ResMailOpen.newBuilder();

    public ResMailOpen.Builder getResMailOpenBuilder() {
        resMailOpenBuilder.clear();
        return resMailOpenBuilder;
    }

    /**
     * 请求向邮件服领取邮件奖励消息proto
     */
    private final transient ReqPublicMailAward.Builder reqPublicMailAwardBuilder = ReqPublicMailAward.newBuilder();

    public ReqPublicMailAward.Builder getReqPublicMailAwardBuilder() {
        reqPublicMailAwardBuilder.clear();
        return reqPublicMailAwardBuilder;
    }

    /**
     * 返回领取邮件奖励消息proto
     */
    private final transient ResMailAward.Builder resMailAwardBuilder = ResMailAward.newBuilder();

    public ResMailAward.Builder getResMailAwardBuilder() {
        resMailAwardBuilder.clear();
        return resMailAwardBuilder;
    }

    /**
     * 请求向邮件服删除邮件消息proto
     */
    private final transient ReqPublicMailDelete.Builder reqPublicMailDeleteBuilder = ReqPublicMailDelete.newBuilder();

    public ReqPublicMailDelete.Builder getReqPublicMailDeleteBuilder() {
        reqPublicMailDeleteBuilder.clear();
        return reqPublicMailDeleteBuilder;
    }

    /**
     * 返回删除邮件消息proto
     */
    private final transient ResMailDelete.Builder resMailDeleteBuilder = ResMailDelete.newBuilder();

    public ResMailDelete.Builder getResMailDeleteBuilder() {
        resMailDeleteBuilder.clear();
        return resMailDeleteBuilder;
    }

    /**
     * 请求向邮件服获取玩家所有邮件(登录)消息proto
     */
    private final transient ReqPublicMailAll.Builder reqPublicMailAllBuilder = ReqPublicMailAll.newBuilder();

    public ReqPublicMailAll.Builder getReqPublicMailAllBuilder() {
        reqPublicMailAllBuilder.clear();
        return reqPublicMailAllBuilder;
    }

    /**
     * 返回邮件打开内容消息proto
     */
    private final transient ResPublicMailOpen.Builder resPublicMailOpenBuilder = ResPublicMailOpen.newBuilder();

    public ResPublicMailOpen.Builder getResPublicMailOpenBuilder() {
        resPublicMailOpenBuilder.clear();
        return resPublicMailOpenBuilder;
    }

    /**
     * 返回推送角色通知信息proto
     */
    private final transient ResPlayerAlert.Builder resPlayerAlertBuilder = ResPlayerAlert.newBuilder();

    public ResPlayerAlert.Builder getResPlayerAlertBuilder() {
        resPlayerAlertBuilder.clear();
        return resPlayerAlertBuilder;
    }

    /**
     * 请求商店信息消息proto
     */
    private final transient ResShopInfo.Builder resShopInfoBuilder = ResShopInfo.newBuilder();

    public ResShopInfo.Builder getResShopInfoBuilder() {
        resShopInfoBuilder.clear();
        return resShopInfoBuilder;
    }

    /**
     * 请求商店购买消息proto
     */
    private final transient ResShopBuy.Builder resShopBuyBuilder = ResShopBuy.newBuilder();

    public ResShopBuy.Builder getResShopBuyBuilder() {
        resShopBuyBuilder.clear();
        return resShopBuyBuilder;
    }

    /**
     * 请求邮件列表消息proto
     */
    private final transient ResMailList.Builder resMailListBuilder = ResMailList.newBuilder();

    public ResMailList.Builder getResMailListBuilder() {
        resMailListBuilder.clear();
        return resMailListBuilder;
    }

    /**
     * 请求活动列表信息消息proto
     */
    private final transient ResActivityList.Builder resActivityListBuilder = ResActivityList.newBuilder();

    public ResActivityList.Builder getResActivityListBuilder() {
        resActivityListBuilder.clear();
        return resActivityListBuilder;
    }

    /**
     * 请求任务领取奖励消息proto
     */
    private final transient ResQuestAward.Builder resQuestAwardBuilder = ResQuestAward.newBuilder();

    public ResQuestAward.Builder getResQuestAwardBuilder() {
        resQuestAwardBuilder.clear();
        return resQuestAwardBuilder;
    }

    /**
     * 返回任务进度更新proto
     */
    private final transient ResQuestUpdate.Builder resQuestUpdateBuilder = ResQuestUpdate.newBuilder();

    public ResQuestUpdate.Builder getResQuestUpdateBuilder() {
        resQuestUpdateBuilder.clear();
        return resQuestUpdateBuilder;
    }

    /**
     * 充值档位信息Bean
     */
    private final transient PayInfo.Builder payInfoBuilder = PayInfo.newBuilder();

    public PayInfo.Builder getPayInfoBuilder() {
        payInfoBuilder.clear();
        return payInfoBuilder;
    }

    /**
     * 物品信息proto列表
     */
    private final transient List<BeanProto.ItemInfo> itemInfoList = new ArrayList<>();

    public List<BeanProto.ItemInfo> getItemInfoList() {
        itemInfoList.clear();
        return itemInfoList;
    }

    /**
     * 物品proto
     */
    private final transient BeanProto.ItemInfo.Builder itemInfoBuilder = BeanProto.ItemInfo.newBuilder();

    public BeanProto.ItemInfo.Builder getItemInfoBuilder() {
        itemInfoBuilder.clear();
        return itemInfoBuilder;
    }

    /**
     * 物品proto
     */
    private final transient PlayerProto.ResPlayerItemChange.Builder resPlayerItemChangeBuilder = PlayerProto.ResPlayerItemChange.newBuilder();

    public PlayerProto.ResPlayerItemChange.Builder getResPlayerItemChangeBuilder() {
        resPlayerItemChangeBuilder.clear();
        return resPlayerItemChangeBuilder;
    }

    /**
     * 请求玩家设置消息proto
     */
    private final transient PlayerProto.ResPlayerSetting.Builder resPlayerSettingBuilder = PlayerProto.ResPlayerSetting.newBuilder();

    public PlayerProto.ResPlayerSetting.Builder getResPlayerSettingBuilder() {
        resPlayerSettingBuilder.clear();
        return resPlayerSettingBuilder;
    }

    /**
     * 房间服务器信息proto
     */
    private final transient BeanProto.ServerRoomInfo.Builder serverRoomBuilder = BeanProto.ServerRoomInfo.newBuilder();

    public BeanProto.ServerRoomInfo.Builder getServerRoomInfoBuilder() {
        serverRoomBuilder.clear();
        return serverRoomBuilder;
    }

    /**
     * 玩家连接地址信息proto
     */
    private final BeanProto.PlayerAddressInfo.Builder playerAddressInfoBuilder = BeanProto.PlayerAddressInfo.newBuilder();

    public BeanProto.PlayerAddressInfo.Builder getPlayerAddressInfoBuilder() {
        playerAddressInfoBuilder.clear();
        return playerAddressInfoBuilder;
    }

    /**
     * 请求背包使用物品消息proto
     */
    private transient final PackProto.ResPackUseItem.Builder resPackUseItemBuilder = PackProto.ResPackUseItem.newBuilder();

    public PackProto.ResPackUseItem.Builder getResPackUseItemBuilder() {
        resPackUseItemBuilder.clear();
        return resPackUseItemBuilder;
    }

    /**
     * 抽卡数据proto
     */
    private final transient BeanProto.LuckyDrawInfo.Builder luckyDrawInfoBuilder = BeanProto.LuckyDrawInfo.newBuilder();

    public BeanProto.LuckyDrawInfo.Builder getLuckyDrawInfoBuilderBuilder() {
        luckyDrawInfoBuilder.clear();
        return luckyDrawInfoBuilder;
    }

    /**
     * 请求玩家信息修改消息proto
     */
    private transient final PlayerProto.ResPlayerInfoChange.Builder resPlayerInfoChangeBuilder = PlayerProto.ResPlayerInfoChange.newBuilder();

    public PlayerProto.ResPlayerInfoChange.Builder getResPlayerInfoChangeBuilder() {
        resPlayerInfoChangeBuilder.clear();
        return resPlayerInfoChangeBuilder;
    }

    /**
     * 请求战斗服进行战斗grpc proto
     */
    private transient final GrpcProto.ReqPublicFightFight.Builder reqPublicFightFightBuilder = GrpcProto.ReqPublicFightFight.newBuilder();

    public GrpcProto.ReqPublicFightFight.Builder getReqPublicFightFightBuilder() {
        reqPublicFightFightBuilder.clear();
        return reqPublicFightFightBuilder;
    }

    /**
     * 请求铲煤消息proto
     */
    private transient final SmelterProto.ResSmelterShovelCoal.Builder resSmelterShovelCoalBuilder = SmelterProto.ResSmelterShovelCoal.newBuilder();

    public SmelterProto.ResSmelterShovelCoal.Builder getResSmelterShovelCoalBuilder() {
        resSmelterShovelCoalBuilder.clear();
        return resSmelterShovelCoalBuilder;
    }


    /**
     * 请求升级熔炉消息proto
     */
    private transient final SmelterProto.ResSmelterLvUp.Builder resSmelterLvUpBuilder = SmelterProto.ResSmelterLvUp.newBuilder();

    public SmelterProto.ResSmelterLvUp.Builder getResSmelterLvUpBuilder() {
        resSmelterLvUpBuilder.clear();
        return resSmelterLvUpBuilder;
    }

    /**
     * 请求穿戴装备消息proto
     */
    private transient final PackProto.ResPackPutOnEquip.Builder resPackPutOnEquipBuilder = PackProto.ResPackPutOnEquip.newBuilder();

    public PackProto.ResPackPutOnEquip.Builder getResPackPutOnEquipBuilder() {
        resPackPutOnEquipBuilder.clear();
        return resPackPutOnEquipBuilder;
    }

    /**
     * 请求分级装备消息proto
     */
    private transient final PackProto.ResPackDecomposeEquip.Builder resPackDecomposeEquipBuilder = PackProto.ResPackDecomposeEquip.newBuilder();

    public PackProto.ResPackDecomposeEquip.Builder getResPackDecomposeEquipBuilder() {
        resPackDecomposeEquipBuilder.clear();
        return resPackDecomposeEquipBuilder;
    }

    /**
     * 装备信息proto
     */
    private transient final BeanProto.EquipInfo.Builder equipInfoBuilder = BeanProto.EquipInfo.newBuilder();

    public BeanProto.EquipInfo.Builder getEquipInfoBuilder() {
        equipInfoBuilder.clear();
        return equipInfoBuilder;
    }

    /**
     * 熔炉升级推送proto
     */
    private transient final SmelterProto.ResSmelterLvUpNotify.Builder smelterLvUpNotifyBuilder = SmelterProto.ResSmelterLvUpNotify.newBuilder();

    public SmelterProto.ResSmelterLvUpNotify.Builder getSmelterLvUpNotifyBuilder() {
        smelterLvUpNotifyBuilder.clear();
        return smelterLvUpNotifyBuilder;
    }

    /**
     * 玩家信息proto
     */
    private transient final BeanProto.PlayerInfo.Builder playerInfoBuilder = BeanProto.PlayerInfo.newBuilder();

    public BeanProto.PlayerInfo.Builder getPlayerInfoBuilder() {
        playerInfoBuilder.clear();
        return playerInfoBuilder;
    }

    /**
     * 请求玩家道具战力消息proto
     */
    private transient final PlayerProto.ResPlayerItemForce.Builder resPlayerItemForceBuilder = PlayerProto.ResPlayerItemForce.newBuilder();

    public PlayerProto.ResPlayerItemForce.Builder getResPlayerItemForceBuilder() {
        resPlayerItemForceBuilder.clear();
        return resPlayerItemForceBuilder;
    }

    /**
     * 请求玩家所有属性消息proto
     */
    private transient final PlayerProto.ResPlayerAllAttrInfo.Builder resPlayerAllAttrInfoBuilder = PlayerProto.ResPlayerAllAttrInfo.newBuilder();

    public PlayerProto.ResPlayerAllAttrInfo.Builder getResPlayerAllAttrInfoBuilder() {
        resPlayerAllAttrInfoBuilder.clear();
        return resPlayerAllAttrInfoBuilder;
    }

    /**
     * 推送变化得属性及战力消息proto
     */
    private transient final PlayerProto.ResPlayerAttrChange.Builder resPlayerAttrChangeBuilder = PlayerProto.ResPlayerAttrChange.newBuilder();

    public PlayerProto.ResPlayerAttrChange.Builder getResPlayerAttrChangeBuilder() {
        resPlayerAttrChangeBuilder.clear();
        return resPlayerAttrChangeBuilder;
    }

    /**
     * 返回玩家新开启的功能消息proto
     */
    private transient PlayerProto.ResPlayerOpenFunction.Builder resPlayerOpenFunctionBuilder = PlayerProto.ResPlayerOpenFunction.newBuilder();

    public PlayerProto.ResPlayerOpenFunction.Builder getResPlayerOpenFunctionBuilder() {
        resPlayerOpenFunctionBuilder.clear();
        return resPlayerOpenFunctionBuilder;
    }

    /**
     * 请求战斗开始消息proto
     */
    private transient final FightProto.ResFightStart.Builder resFightStartBuilder = FightProto.ResFightStart.newBuilder();

    public FightProto.ResFightStart.Builder getResFightStartBuilder() {
        resFightStartBuilder.clear();
        return resFightStartBuilder;
    }

    /**
     * 请求英雄召唤池刷新消息proto
     */
    private transient final HeroProto.ResHeroPoolRefresh.Builder resHeroPoolRefreshBuilder = HeroProto.ResHeroPoolRefresh.newBuilder();

    public HeroProto.ResHeroPoolRefresh.Builder getResHeroPoolRefreshBuilder() {
        resHeroPoolRefreshBuilder.clear();
        return resHeroPoolRefreshBuilder;
    }

    /**
     * 请求英雄召唤消息proto
     */
    private transient final HeroProto.ResHeroSummon.Builder resHeroSummonBuilder = HeroProto.ResHeroSummon.newBuilder();

    public HeroProto.ResHeroSummon.Builder getResHeroSummonBuilder() {
        resHeroSummonBuilder.clear();
        return resHeroSummonBuilder;
    }

    /**
     * 请求英雄成长消息proto
     */
    private transient final HeroProto.ResHeroUp.Builder resHeroUpBuilder = HeroProto.ResHeroUp.newBuilder();

    public HeroProto.ResHeroUp.Builder getResHeroUpBuilder() {
        resHeroUpBuilder.clear();
        return resHeroUpBuilder;
    }

    /**
     * 请求英雄重置消息proto
     */
    private transient final HeroProto.ResHeroReset.Builder resHeroResetBuilder = HeroProto.ResHeroReset.newBuilder();

    public HeroProto.ResHeroReset.Builder getResHeroResetBuilder() {
        resHeroResetBuilder.clear();
        return resHeroResetBuilder;
    }

    /**
     * 请求英雄上阵消息proto
     */
    private transient final HeroProto.ResHeroJoinBattle.Builder resHeroJoinBattleBuilder = HeroProto.ResHeroJoinBattle.newBuilder();

    public HeroProto.ResHeroJoinBattle.Builder getResHeroJoinBattleBuilder() {
        resHeroJoinBattleBuilder.clear();
        return resHeroJoinBattleBuilder;
    }

    /**
     * 请求英雄背包格子解锁消息proto
     */
    private transient final HeroProto.ResHeroPackGridUnlock.Builder resHeroPackGridUnlockBuilder = HeroProto.ResHeroPackGridUnlock.newBuilder();

    public HeroProto.ResHeroPackGridUnlock.Builder getResHeroPackGridUnlockBuilder() {
        resHeroPackGridUnlockBuilder.clear();
        return resHeroPackGridUnlockBuilder;
    }

    /**
     * 玩家英雄信息proto
     */
    private transient final BeanProto.PlayerHeroInfo.Builder playerHeroInfoBuilder = BeanProto.PlayerHeroInfo.newBuilder();

    public BeanProto.PlayerHeroInfo.Builder getPlayerHeroInfoBuilder() {
        playerHeroInfoBuilder.clear();
        return playerHeroInfoBuilder;
    }

    /**
     * 英雄信息proto
     */
    private transient final BeanProto.HeroInfo.Builder heroInfoBuilder = BeanProto.HeroInfo.newBuilder();

    public BeanProto.HeroInfo.Builder getHeroInfoBuilder() {
        heroInfoBuilder.clear();
        return heroInfoBuilder;
    }

    /**
     * 请求选择大保底消息proto
     */
    private transient final HeroProto.ResHeroMaxAwardChoose.Builder resHeroMaxAwardChooseBuilder = HeroProto.ResHeroMaxAwardChoose.newBuilder();

    public HeroProto.ResHeroMaxAwardChoose.Builder getResHeroMaxAwardChooseBuilder() {
        resHeroMaxAwardChooseBuilder.clear();
        return resHeroMaxAwardChooseBuilder;
    }

    /**
     * 请求英雄密码操作消息proto
     */
    private transient final HeroProto.ResHeroPasswordOpt.Builder resHeroPasswordOptBuilder = HeroProto.ResHeroPasswordOpt.newBuilder();

    public HeroProto.ResHeroPasswordOpt.Builder getResHeroPasswordOptBuilder() {
        resHeroPasswordOptBuilder.clear();
        return resHeroPasswordOptBuilder;
    }

    /**
     * 请求幸存者营救消息proto
     */
    private transient final SurvivorProto.ResSurvivorRescue.Builder resSurvivorRescueBuilder = SurvivorProto.ResSurvivorRescue.newBuilder();

    public SurvivorProto.ResSurvivorRescue.Builder getResSurvivorRescueBuilder() {
        resSurvivorRescueBuilder.clear();
        return resSurvivorRescueBuilder;
    }

    /**
     * 请求幸存者合成或升级消息proto
     */
    private transient final SurvivorProto.ResSurvivorCraftOrUpgrade.Builder resSurvivorCraftOrUpgradeBuilder = SurvivorProto.ResSurvivorCraftOrUpgrade.newBuilder();

    public SurvivorProto.ResSurvivorCraftOrUpgrade.Builder getResSurvivorCraftOrUpgradeBuilder() {
        resSurvivorCraftOrUpgradeBuilder.clear();
        return resSurvivorCraftOrUpgradeBuilder;
    }

    /**
     * 请求幸存者共鸣激活或升级消息proto
     */
    private transient final SurvivorProto.ResSurvivorResonanceActiveOrUpgrade.Builder resSurvivorResonanceActiveOrUpgradeBuilder = SurvivorProto.ResSurvivorResonanceActiveOrUpgrade.newBuilder();

    public SurvivorProto.ResSurvivorResonanceActiveOrUpgrade.Builder getResSurvivorResonanceActiveOrUpgradeBuilder() {
        resSurvivorResonanceActiveOrUpgradeBuilder.clear();
        return resSurvivorResonanceActiveOrUpgradeBuilder;
    }

    /**
     * 请求幸存者加入或退出战斗消息proto
     */
    private transient final SurvivorProto.ResSurvivorJoinOrRemoveBattle.Builder resSurvivorJoinOrRemoveBattleBuilder = SurvivorProto.ResSurvivorJoinOrRemoveBattle.newBuilder();

    public SurvivorProto.ResSurvivorJoinOrRemoveBattle.Builder getResSurvivorJoinOrRemoveBattleBuilder() {
        resSurvivorJoinOrRemoveBattleBuilder.clear();
        return resSurvivorJoinOrRemoveBattleBuilder;
    }

    /**
     * 请求幸存者编队更换消息proto
     */
    private transient final SurvivorProto.ResSurvivorBattleGroupChange.Builder resSurvivorBattleGroupChangeBuilder = SurvivorProto.ResSurvivorBattleGroupChange.newBuilder();

    public SurvivorProto.ResSurvivorBattleGroupChange.Builder getResSurvivorBattleGroupChangeBuilder() {
        resSurvivorBattleGroupChangeBuilder.clear();
        return resSurvivorBattleGroupChangeBuilder;
    }

    /**
     * 幸存者信息proto
     */
    private transient final BeanProto.SurvivorInfo.Builder survivorInfoBuilder = BeanProto.SurvivorInfo.newBuilder();

    public BeanProto.SurvivorInfo.Builder getSurvivorInfoBuilder() {
        survivorInfoBuilder.clear();
        return survivorInfoBuilder;
    }

    /**
     * 玩家幸存者信息proto
     */
    private transient final BeanProto.PlayerSurvivorInfo.Builder playerSurvivorInfoBuilder = BeanProto.PlayerSurvivorInfo.newBuilder();

    public BeanProto.PlayerSurvivorInfo.Builder getPlayerSurvivorInfoBuilder() {
        playerSurvivorInfoBuilder.clear();
        return playerSurvivorInfoBuilder;
    }

    /**
     * 玩家熔炉信息
     */
    private transient final BeanProto.PlayerSmelterInfo.Builder playerSmelterInfoBuilder = BeanProto.PlayerSmelterInfo.newBuilder();

    public BeanProto.PlayerSmelterInfo.Builder getPlayerSmelterInfoBuilder() {
        playerSmelterInfoBuilder.clear();
        return playerSmelterInfoBuilder;
    }

    /**
     * 请求翅膀解锁消息proto
     */
    private transient final WingProto.ResWingUnlock.Builder resWingUnlockBuilder = WingProto.ResWingUnlock.newBuilder();

    public WingProto.ResWingUnlock.Builder getResWingUnlockBuilder() {
        resWingUnlockBuilder.clear();
        return resWingUnlockBuilder;
    }

    /**
     * 请求翅膀升级消息proto
     */
    private transient final WingProto.ResWingUpgrade.Builder resWingUpgradeBuilder = WingProto.ResWingUpgrade.newBuilder();

    public WingProto.ResWingUpgrade.Builder getResWingUpgradeBuilder() {
        resWingUpgradeBuilder.clear();
        return resWingUpgradeBuilder;
    }

    /**
     * 请求翅膀穿戴消息proto
     */
    private transient final WingProto.ResWingWear.Builder resWingWearBuilder = WingProto.ResWingWear.newBuilder();

    public WingProto.ResWingWear.Builder getResWingWearBuilder() {
        resWingWearBuilder.clear();
        return resWingWearBuilder;
    }

    /**
     * 排行榜信息消息proto
     */
    private transient final RankProto.ResRankInfo.Builder resRankInfoBuilder = RankProto.ResRankInfo.newBuilder();

    public RankProto.ResRankInfo.Builder getResRankInfoBuilder() {
        resRankInfoBuilder.clear();
        return resRankInfoBuilder;
    }

    /**
     * 请求竞技场挑战列表消息proto
     */
    private transient final ArenaProto.ResArenaChallengeList.Builder resArenaChallengeListBuilder = ArenaProto.ResArenaChallengeList.newBuilder();

    public ArenaProto.ResArenaChallengeList.Builder getResArenaChallengeListBuilder() {
        resArenaChallengeListBuilder.clear();
        return resArenaChallengeListBuilder;
    }

    /**
     * 请求潜能激发消息proto
     */
    private transient final PackProto.ResPackPotentialStimulation.Builder resPackPotentialStimulationBuilder = PackProto.ResPackPotentialStimulation.newBuilder();

    public PackProto.ResPackPotentialStimulation.Builder getResPackPotentialStimulationBuilder() {
        resPackPotentialStimulationBuilder.clear();
        return resPackPotentialStimulationBuilder;
    }

    /**
     * 玩家潜能信息proto
     */
    private transient final BeanProto.PlayerPotentialInfo.Builder playerPotentialInfoBuilder = BeanProto.PlayerPotentialInfo.newBuilder();

    public BeanProto.PlayerPotentialInfo.Builder getPlayerPotentialInfoBuilder() {
        playerPotentialInfoBuilder.clear();
        return playerPotentialInfoBuilder;
    }

    /**
     * 潜能信息proto
     */
    private transient final BeanProto.PotentialInfo.Builder potentialInfoBuilder = BeanProto.PotentialInfo.newBuilder();

    public BeanProto.PotentialInfo.Builder getPotentialInfoBuilder() {
        potentialInfoBuilder.clear();
        return potentialInfoBuilder;
    }

}
