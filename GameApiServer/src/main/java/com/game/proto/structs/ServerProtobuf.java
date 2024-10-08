package com.game.proto.structs;

import com.game.bean.proto.BeanProto;
import com.game.grpc.proto.GrpcProto;
import com.game.publicchatserver.proto.PublicChatProto;
import com.game.publicclansserver.proto.PublicClansProto;
import com.game.publicmailserver.proto.PublicMailProto;
import com.game.publicmailserver.proto.PublicMailProto.*;
import com.game.room.proto.RoomProto;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;

/**
 * 服务器提前初始化好的ProtoBuf使用对象
 * <p>
 * 用于服务器都是单线程处理此消息逻辑放入此类中
 *
 * @author zhangzhen
 * @time 2022年12月9日
 */
public class ServerProtobuf {

    /**
     * 通用空的grpc返回对象，方便简化不需要返回值的grpc请求，使代码更简洁
     */
    public static final StreamObserver<Empty> EMPTY_OBSERVER = new StreamObserver<Empty>() {
        @Override
        public void onNext(Empty value) {
            // 忽略响应
        }

        @Override
        public void onError(Throwable t) {
            // 忽略报错
        }

        @Override
        public void onCompleted() {
            // 请求完成
        }
    };

    /**
     * 向邮件服发邮件消息proto
     */
    private static final PublicMailProto.ReqPublicMailSendMail.Builder reqPublicMailSendMailBuilder = PublicMailProto.ReqPublicMailSendMail.newBuilder();

    public static PublicMailProto.ReqPublicMailSendMail.Builder getReqPublicMailSendMailBuilder() {
        reqPublicMailSendMailBuilder.clear();
        return reqPublicMailSendMailBuilder;
    }

    /**
     * 请求向邮件注册服务器信息消息proto
     */
    private static final byte[] resPublicMailServerRegisterBuilder = ResPublicMailServerRegister.newBuilder().build().toByteArray();

    public static byte[] getResPublicMailServerRegisterBuilder() {
        return resPublicMailServerRegisterBuilder;
    }

    /**
     * 邮件服务器to游戏服务器之间心跳消息proto
     */
    private static final byte[] resPublicMailServer2ServerHeartbeatBuilder = ResPublicMailServer2ServerHeartbeat.newBuilder().build().toByteArray();

    public static byte[] getResPublicMailServer2ServerHeartbeatBuilder() {
        return resPublicMailServer2ServerHeartbeatBuilder;
    }

    /**
     * 返回邮件服推送邮件给玩家消息proto
     */
    private static final ResPublicMailInfo.Builder resPublicMailInfoBuilder = ResPublicMailInfo.newBuilder();

    public static ResPublicMailInfo.Builder getResPublicMailInfoBuilder() {
        resPublicMailInfoBuilder.clear();
        return resPublicMailInfoBuilder;
    }

    /**
     * 请求向邮件服打开邮件并设置已读消息proto
     */
    private static final ResPublicMailOpen.Builder resPublicMailOpenBuilder = ResPublicMailOpen.newBuilder();

    public static ResPublicMailOpen.Builder getResPublicMailOpenBuilder() {
        resPublicMailOpenBuilder.clear();
        return resPublicMailOpenBuilder;
    }

    /**
     * 请求向邮件服领取邮件奖励消息proto
     */
    private static final ResPublicMailAward.Builder resPublicMailAwardBuilder = ResPublicMailAward.newBuilder();

    public static ResPublicMailAward.Builder getResPublicMailAwardBuilder() {
        resPublicMailAwardBuilder.clear();
        return resPublicMailAwardBuilder;
    }

    /**
     * 请求向邮件服删除邮件消息proto
     */
    private static final ResPublicMailDelete.Builder resPublicMailDeleteBuilder = ResPublicMailDelete.newBuilder();

    public static ResPublicMailDelete.Builder getResPublicMailDeleteBuilder() {
        resPublicMailDeleteBuilder.clear();
        return resPublicMailDeleteBuilder;
    }

    /**
     * 玩家信息Info
     */
    private static final BeanProto.PlayerInfo.Builder playerInfoBuilder = BeanProto.PlayerInfo.newBuilder();

    public static BeanProto.PlayerInfo.Builder getPlayerInfoBuilderBuilder() {
        playerInfoBuilder.clear();
        return playerInfoBuilder;
    }

    /**
     * 返回房间加入builder
     */
    private static final RoomProto.ResRoomJoin.Builder resRoomJoinBuilder = RoomProto.ResRoomJoin.newBuilder();

    public static RoomProto.ResRoomJoin.Builder getResRoomJoinBuilder() {
        resRoomJoinBuilder.clear();
        return resRoomJoinBuilder;
    }

    /**
     * 推送其他玩家房间加入builder
     */
    private static final RoomProto.ResRoomTellJoin.Builder resRoomTellJoinBuilder = RoomProto.ResRoomTellJoin.newBuilder();

    public static RoomProto.ResRoomTellJoin.Builder getResRoomTellJoinBuilder() {
        resRoomTellJoinBuilder.clear();
        return resRoomTellJoinBuilder;
    }

    /**
     * 返回房间心跳builder
     */
    private static final RoomProto.ResRoomHeart.Builder resRoomHeartBuilder = RoomProto.ResRoomHeart.newBuilder();

    public static RoomProto.ResRoomHeart.Builder getResRoomHeartBuilder() {
        resRoomHeartBuilder.clear();
        return resRoomHeartBuilder;
    }

    /**
     * 返回房间延迟检测Rpc(2秒钟一次)消息proto
     */
    private static final RoomProto.ResRoomRpc.Builder resRoomRpcBuilder = RoomProto.ResRoomRpc.newBuilder();

    public static RoomProto.ResRoomRpc.Builder getResRoomRpcBuilder() {
        resRoomRpcBuilder.clear();
        return resRoomRpcBuilder;
    }

    /**
     * 返回房间操作上报消息proto
     */
    private static final RoomProto.ResRoomStepUp.Builder resRoomStepUpBuilder = RoomProto.ResRoomStepUp.newBuilder();

    public static RoomProto.ResRoomStepUp.Builder getResRoomStepUpBuilder() {
        resRoomStepUpBuilder.clear();
        return resRoomStepUpBuilder;
    }

    /**
     * 返回房间一帧数据推送builder
     */
    private static final RoomProto.ResRoomFrameTell.Builder resRoomFrameTellBuilder = RoomProto.ResRoomFrameTell.newBuilder();

    public static RoomProto.ResRoomFrameTell.Builder getResRoomFrameTellBuilder() {
        resRoomFrameTellBuilder.clear();
        return resRoomFrameTellBuilder;
    }

    /**
     * 推送房间玩家离开builder
     */
    private static final RoomProto.ResRoomTellExit.Builder resRoomTellExitBuilder = RoomProto.ResRoomTellExit.newBuilder();

    public static RoomProto.ResRoomTellExit.Builder getResRoomTellExitBuilder() {
        resRoomTellExitBuilder.clear();
        return resRoomTellExitBuilder;
    }

    /**
     * 房间服务器信息proto
     */
    private static final BeanProto.ServerRoomInfo.Builder serverRoomBuilder = BeanProto.ServerRoomInfo.newBuilder();

    public static BeanProto.ServerRoomInfo.Builder getServerRoomInfoBuilder() {
        serverRoomBuilder.clear();
        return serverRoomBuilder;
    }

    /**
     * 单个帧所有信息builder
     */
    private static final BeanProto.FrameInfo.Builder frameInfoBuilder = BeanProto.FrameInfo.newBuilder();

    public static BeanProto.FrameInfo.Builder getFrameInfoBuilder() {
        frameInfoBuilder.clear();
        return frameInfoBuilder;
    }

    /**
     * 返回房间补帧消息proto
     */
    private static final RoomProto.ResRoomFrameRepair.Builder resRoomFrameRepairBuilder = RoomProto.ResRoomFrameRepair.newBuilder();

    public static RoomProto.ResRoomFrameRepair.Builder getResRoomFrameRepairBuilder() {
        resRoomFrameRepairBuilder.clear();
        return resRoomFrameRepairBuilder;
    }

    /**
     * 返回房间竞技游戏个人完成比赛推送proto
     */
    private static final RoomProto.ResRoomGamePlayerFinishTell.Builder resRoomGamePlayerFinishTellBuilder = RoomProto.ResRoomGamePlayerFinishTell.newBuilder();

    public static RoomProto.ResRoomGamePlayerFinishTell.Builder getResRoomGamePlayerFinishTellBuilder() {
        resRoomGamePlayerFinishTellBuilder.clear();
        return resRoomGamePlayerFinishTellBuilder;
    }

    /**
     * 返回房间竞技游戏个人完成比赛推送proto
     */
    private static final RoomProto.ResRoomGameEndTell.Builder resRoomGameEndTellBuilder = RoomProto.ResRoomGameEndTell.newBuilder();

    public static RoomProto.ResRoomGameEndTell.Builder getResRoomGameEndTellBuilder() {
        resRoomGameEndTellBuilder.clear();
        return resRoomGameEndTellBuilder;
    }

    /**
     * 返回房间竞技场主动离开消息proto
     */
    private static final RoomProto.ResRoomQuit.Builder resRoomQuitBuilder = RoomProto.ResRoomQuit.newBuilder();

    public static RoomProto.ResRoomQuit.Builder getResRoomQuitBuilder() {
        resRoomQuitBuilder.clear();
        return resRoomQuitBuilder;
    }

    /**
     * 竞技场比赛排名结算奖励proto
     */
    private static final BeanProto.ArenaRankRewardInfo.Builder arenaRankRewardInfoBuilder = BeanProto.ArenaRankRewardInfo.newBuilder();

    public static BeanProto.ArenaRankRewardInfo.Builder getArenaRankRewardInfoBuilder() {
        arenaRankRewardInfoBuilder.clear();
        return arenaRankRewardInfoBuilder;
    }

    /**
     * 返回充值服创建一个充值订单消息proto
     */
    private static final GrpcProto.ResPublicPayServerCreateOrder.Builder resPublicPayServerCreateOrderBuilder = GrpcProto.ResPublicPayServerCreateOrder.newBuilder();

    public static GrpcProto.ResPublicPayServerCreateOrder.Builder getResPublicPayServerCreateOrderBuilder() {
        resPublicPayServerCreateOrderBuilder.clear();
        return resPublicPayServerCreateOrderBuilder;
    }

    /**
     * 返回向聊天服务器注册消息proto
     */
    private static final byte[] resChatServerRegisterBuilder = PublicChatProto.ResPublicChatServerRegister.newBuilder().build().toByteArray();

    public static byte[] getResChatServerRegisterBuilder() {
        return resChatServerRegisterBuilder;
    }

    /**
     * 聊天服务器to游戏服务器之间心跳消息proto
     */
    private static final byte[] resChatServer2ServerHeartbeatBuilder = PublicChatProto.ResPublicChatServer2ServerHeartbeat.newBuilder().build().toByteArray();

    public static byte[] getResChatServer2ServerHeartbeatBuilder() {
        return resChatServer2ServerHeartbeatBuilder;
    }

    /**
     * 返回战斗服战斗信息proto
     */
    private static final BeanProto.FightInfo.Builder fightInfoBuilder = BeanProto.FightInfo.newBuilder();

    public static BeanProto.FightInfo.Builder getFightInfoBuilder() {
        fightInfoBuilder.clear();
        return fightInfoBuilder;
    }

    /**
     * 战斗者信息proto
     */
    private static final BeanProto.FighterInfo.Builder fighterInfoBuilder = BeanProto.FighterInfo.newBuilder();

    public static BeanProto.FighterInfo.Builder getFighterInfoBuilder() {
        fighterInfoBuilder.clear();
        return fighterInfoBuilder;
    }

    /**
     * 请求向公会注册服务器信息消息proto
     */
    private static final byte[] resPublicClansServerRegisterBuilder = PublicClansProto.ResPublicClansServerRegister.newBuilder().build().toByteArray();

    public static byte[] getResPublicClansServerRegisterBuilder() {
        return resPublicClansServerRegisterBuilder;
    }

    /**
     * 公会服务器to游戏服务器之间心跳消息proto
     */
    private static final byte[] resPublicClansServer2ServerHeartbeatBuilder = PublicClansProto.ResPublicClansServer2ServerHeartbeat.newBuilder().build().toByteArray();

    public static byte[] getResPublicClansServer2ServerHeartbeatBuilder() {
        return resPublicClansServer2ServerHeartbeatBuilder;
    }

    /**
     * 返回公会服操作失败消息proto
     */
    private static final PublicClansProto.ResPublicClansFailure.Builder resPublicClansFailureBuilder = PublicClansProto.ResPublicClansFailure.newBuilder();

    public static PublicClansProto.ResPublicClansFailure.Builder getResPublicClansFailureBuilder() {
        resPublicClansFailureBuilder.clear();
        return resPublicClansFailureBuilder;
    }

}
