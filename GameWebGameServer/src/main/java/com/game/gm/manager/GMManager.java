package com.game.gm.manager;

import com.alibaba.fastjson2.JSON;
import com.game.data.bean.B_item_Bean;
import com.game.data.define.MyDefineConstant;
import com.game.data.define.MyDefineItemChangeReason;
import com.game.data.define.MyDefineItemType;
import com.game.data.define.MyDefineServerRunState;
import com.game.data.manager.DataManager;
import com.game.data.myenum.MyEnumResourceId;
import com.game.gm.structs.GMComm;
import com.game.grpc.proto.GrpcProto;
import com.game.pack.manager.PackManager;
import com.game.player.manager.PlayerManager;
import com.game.player.manager.PlayerOtherManager;
import com.game.player.structs.PlayerPack;
import com.game.player.structs.WebPlayer;
import com.game.server.WebServer;
import com.game.timer.TimerEvent;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * GM命令管理
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/14 16:23
 */
@Component
@Log4j2
public class GMManager {

    @Lazy
    private @Resource WebServer webServer;
    private @Resource PlayerManager playerManager;
    private @Resource PlayerOtherManager playerOtherManager;
    private @Resource PackManager packManager;
    private @Resource DataManager dataManager;

    /**
     * 执行rpc gm指令
     *
     * @param reqCommand
     * @return
     */
    public GrpcProto.GMResCommand execGMCommRpc(StreamObserver<GrpcProto.GMResCommand> responseObserver, GrpcProto.GMReqCommand reqCommand) {
        GrpcProto.GMResCommand.Builder resCommandBuilder = GrpcProto.GMResCommand.newBuilder();
        resCommandBuilder.setId(reqCommand.getId());
        resCommandBuilder.setState(0);
        resCommandBuilder.setCommand(reqCommand.getCommand());
        try {
            // 内容
            if (!reqCommand.getVer().equals(MyDefineConstant.GM_VERSION)) {
                log.error("在使用老版本GM工具发送gm命令，time=" + System.currentTimeMillis());
                resCommandBuilder.setState(1);
                resCommandBuilder.setMsg("在使用老版本GM工具发送gm命令，time=" + System.currentTimeMillis());
                return resCommandBuilder.build();
            }
            if (reqCommand.getCommand().equals(GMComm.T_SAVE.getValue())) {
                if (MyDefineConstant.serverState != MyDefineServerRunState.STOP) {
                    // 先把状态设置为关闭
                    MyDefineConstant.serverState = MyDefineServerRunState.STOP;
                    // 保存数据
                    webServer.saveAll();
                }
                log.info("webGame服关服保存数据成功！");
                resCommandBuilder.setMsg("webGame关服保存数据成功！");
                return resCommandBuilder.build();
            } else if (reqCommand.getCommand().equals(GMComm.T_BANNED.getValue())) {
                // 封号
                for (int i = 0, len = reqCommand.getPlayerIdCount(); i < len; i++) {
                    Long playerId = reqCommand.getPlayerId(i);
                    long bannedTime = Long.parseLong(reqCommand.getParameter(i));
                    WebPlayer targetPlayer = playerManager.getPlayer(playerId, true);
                    resCommandBuilder.addPlayerId(playerId);
                    if (targetPlayer == null) {
                        // msgStr = "<font color=\"red\">该编号找不到对应角色=" + cooooms[1] + "</font>";
                        resCommandBuilder.setState(1);
                        resCommandBuilder.setMsg("该编号找不到对应角色");
                    } else {
                        targetPlayer.setBannedTime(bannedTime);
//						msgStr = "<font color=\"green\">" + "封号成功" + "</font>";
                        resCommandBuilder.setMsg("封号成功,禁言封号时间=" + bannedTime);
                        // 保存数据
                        playerManager.savePlayer(targetPlayer);
                    }
                }
                return resCommandBuilder.build();
            } else if (reqCommand.getCommand().equals(GMComm.T_REBANNED.getValue())) {
                // 解封
                for (int i = 0, len = reqCommand.getPlayerIdCount(); i < len; i++) {
                    Long playerId = reqCommand.getPlayerId(i);
                    WebPlayer targetPlayer = playerManager.getPlayer(playerId, true);
                    resCommandBuilder.addPlayerId(playerId);
                    if (targetPlayer == null) {
                        // msgStr = "<font color=\"red\">该编号找不到对应角色=" + cooooms[1] + "</font>";
                        resCommandBuilder.setState(1);
                        resCommandBuilder.setMsg("该编号找不到对应角色=" + playerId);
                    } else {
                        targetPlayer.setBannedTime(System.currentTimeMillis());
//						msgStr = "<font color=\"green\">" + "解封成功" + "</font>";
                        resCommandBuilder.setMsg("解封成功！");
                        // 保存数据
                        playerManager.savePlayer(targetPlayer);
                    }
                }
                return resCommandBuilder.build();
            } else if (reqCommand.getCommand().equals(GMComm.T_GOODS_ADD.getValue())) {
                // 新增（存在添加）资源道具
                Long playerId = reqCommand.getPlayerId(0);
                WebPlayer targetPlayer = playerManager.getPlayer(playerId, true);
                resCommandBuilder.addPlayerId(playerId);
                if (targetPlayer == null) {
                    resCommandBuilder.setState(1);
                    resCommandBuilder.setMsg("该编号找不到对应角色");
                    return resCommandBuilder.build();
                }
                for (int i = 0, len = reqCommand.getParameterLongValueCount(); i < len; i += 2) {
                    int configId = (int) reqCommand.getParameterLongValue(i);
                    long num = reqCommand.getParameterLongValue(i + 1);
                    // 目前只支持添加资源
                    MyEnumResourceId myEnumResourceId = MyEnumResourceId.getMyEnumResourceId(configId);
                    if (myEnumResourceId == null) {
                        resCommandBuilder.setState(1);
                        resCommandBuilder.setMsg("该编号找不到对应资源(目前仅支持添加candy)configId" + configId);
                        return resCommandBuilder.build();
                    }
                    resCommandBuilder.setMsg("物品[" + configId + "]增加=" + num);
                    // 离线处理
                    packManager.addItemByConfigId(targetPlayer, configId, (int) num, MyDefineItemChangeReason.GM);
                }
                // 保存数据
                playerManager.savePlayer(targetPlayer);
                return resCommandBuilder.build();
            } else if (reqCommand.getCommand().equals(GMComm.T_GOODS_CHANGE.getValue())) {
                // 增减资源道具
                Long playerId = reqCommand.getPlayerId(0);
                WebPlayer targetPlayer = playerManager.getPlayer(playerId, true);
                PlayerPack playerPack = playerOtherManager.getPlayerPack(targetPlayer, true);
                resCommandBuilder.addPlayerId(playerId);
                if (targetPlayer == null) {
                    resCommandBuilder.setState(1);
                    resCommandBuilder.setMsg("该编号找不到对应角色");
                    return resCommandBuilder.build();
                }
                // 在线执行timer列表
                List<TimerEvent> timerList = new ArrayList<>();
                for (int i = 0, len = reqCommand.getParameterLongValueCount(); i < len; i += 3) {
                    long goodsId = reqCommand.getParameterLongValue(i);
                    int configId = (int) reqCommand.getParameterLongValue(i + 1);
                    long num = reqCommand.getParameterLongValue(i + 2);
                    // 获取配置
                    B_item_Bean itemBean = dataManager.c_item_Container.getMap().get(configId);
                    // 计算出改变的值，正数表示增加，负数表示减少
                    long diffNum = 0;
                    // 修改物品
                    if (itemBean.getType() == MyDefineItemType.RESOURCE.intValue()) {
                        // 如果在线，获取当前数量
                        Long currentNum = playerPack.getResourceMap().get(configId);
                        // 计算出改变的值，正数表示增加，负数表示减少
                        diffNum = num - currentNum;
                    }
                    if (diffNum > 0) {
                        // 不叠加道具最多添加10个
                        if (!itemBean.getMerge()) {
                            resCommandBuilder.setState(1);
                            resCommandBuilder.setMsg("不叠加物品不能增加数量！");
                            return resCommandBuilder.build();
                        }
                        resCommandBuilder.setMsg("物品[" + configId + "]增加=" + diffNum);
                        packManager.addItemByConfigId(targetPlayer, configId, (int) diffNum, MyDefineItemChangeReason.GM);
                    } else {
                        resCommandBuilder.setMsg("物品[" + configId + "]减少=" + diffNum);
                        if (!packManager.itemCheckAndReduceById(targetPlayer, List.of(goodsId, diffNum), MyDefineItemChangeReason.GM)) {
                            resCommandBuilder.setState(1);
                            resCommandBuilder.setMsg("gm扣除物品不足失败goodsId=" + goodsId + " configId=" + configId + " num=" + diffNum);
                            return resCommandBuilder.build();
                        }

                    }
                }
                // 保存数据
                playerManager.savePlayer(targetPlayer);
                return resCommandBuilder.build();
            } else {
                resCommandBuilder.setState(2);
                resCommandBuilder.setMsg("没有此命令command=" + reqCommand.getCommand());
                return resCommandBuilder.build();
            }
        } catch (Exception e) {
            String errJson = JSON.toJSONString(reqCommand);
            log.error("gm异常->参数:" + errJson + " err:", e);
            resCommandBuilder.setState(2);
            resCommandBuilder.setMsg("gm异常->参数:" + errJson + e.getMessage());
            return resCommandBuilder.build();
        }
    }

}
