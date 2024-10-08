package com.game.message.struct;

/**
 * MsgId
 *
 * @author zhangzhen
 *
 * @time 2024/06/01 00:00
 */
public enum MsgId {

    /**
     * msgId:101101 请求心跳(10秒钟一次)
     */
    REQHEART(101101),
    /**
     * msgId:101201 返回心跳(10秒钟一次)
     */
    RESHEART(101201),
    /**
     * msgId:102101 请求登陆
     */
    REQLOGIN(102101),
    /**
     * msgId:102201 返回登录
     */
    RESLOGIN(102201),
    /**
     * msgId:103103 请求玩家设置
     */
    REQPLAYERSETTING(103103),
    /**
     * msgId:103107 请求玩家信息修改
     */
    REQPLAYERINFOCHANGE(103107),
    /**
     * msgId:103108 请求玩家道具战力
     */
    REQPLAYERITEMFORCE(103108),
    /**
     * msgId:103111 请求玩家所有属性
     */
    REQPLAYERALLATTRINFO(103111),
    /**
     * msgId:103201 返回推送角色报错通知信息
     */
    RESPLAYERALERT(103201),
    /**
     * msgId:103202 推送资源道具信息改变
     */
    RESPLAYERITEMCHANGE(103202),
    /**
     * msgId:103203 返回玩家设置
     */
    RESPLAYERSETTING(103203),
    /**
     * msgId:103204 返回推送玩家体力其他信息变化
     */
    RESPLAYERTLPUSH(103204),
    /**
     * msgId:103207 返回玩家信息修改
     */
    RESPLAYERINFOCHANGE(103207),
    /**
     * msgId:103208 返回玩家道具战力
     */
    RESPLAYERITEMFORCE(103208),
    /**
     * msgId:103209 返回推送玩家新开启的功能id
     */
    RESPLAYEROPENFUNCTION(103209),
    /**
     * msgId:103210 返回玩家属性变化
     */
    RESPLAYERATTRCHANGE(103210),
    /**
     * msgId:103211 返回玩家所有属性
     */
    RESPLAYERALLATTRINFO(103211),
    /**
     * msgId:106101 请求打开邮件并且设置已读
     */
    REQMAILOPEN(106101),
    /**
     * msgId:106102 请求领取邮件奖励
     */
    REQMAILAWARD(106102),
    /**
     * msgId:106103 请求删除邮件
     */
    REQMAILDELETE(106103),
    /**
     * msgId:106105 请求邮件列表
     */
    REQMAILLIST(106105),
    /**
     * msgId:106201 返回打开邮件并且设置已读
     */
    RESMAILOPEN(106201),
    /**
     * msgId:106202 返回邮件已领取成功
     */
    RESMAILAWARD(106202),
    /**
     * msgId:106203 返回删除邮件成功
     */
    RESMAILDELETE(106203),
    /**
     * msgId:106204 返回推送邮件
     */
    RESMAILINFO(106204),
    /**
     * msgId:106205 返回邮件列表
     */
    RESMAILLIST(106205),
    /**
     * msgId:107101 请求竞技场挑战列表
     */
    REQARENACHALLENGELIST(107101),
    /**
     * msgId:107201 返回竞技场挑战列表
     */
    RESARENACHALLENGELIST(107201),
    /**
     * msgId:108101 请求背包使用物品
     */
    REQPACKUSEITEM(108101),
    /**
     * msgId:108102 请求穿戴装备
     */
    REQPACKPUTONEQUIP(108102),
    /**
     * msgId:108103 请求分级装备
     */
    REQPACKDECOMPOSEEQUIP(108103),
    /**
     * msgId:108104 请求潜能激发
     */
    REQPACKPOTENTIALSTIMULATION(108104),
    /**
     * msgId:108201 返回背包使用物品
     */
    RESPACKUSEITEM(108201),
    /**
     * msgId:108202 返回穿戴装备
     */
    RESPACKPUTONEQUIP(108202),
    /**
     * msgId:108203 返回分级装备
     */
    RESPACKDECOMPOSEEQUIP(108203),
    /**
     * msgId:108204 返回潜能激发
     */
    RESPACKPOTENTIALSTIMULATION(108204),
    /**
     * msgId:110101 请求任务领取奖励
     */
    REQQUESTAWARD(110101),
    /**
     * msgId:110201 返回任务领取奖励
     */
    RESQUESTAWARD(110201),
    /**
     * msgId:110202 返回任务进度更新
     */
    RESQUESTUPDATE(110202),
    /**
     * msgId:111102 请求活动操作
     */
    REQACTIVITYOPERATION(111102),
    /**
     * msgId:111201 返回推送活动列表信息
     */
    RESACTIVITYLIST(111201),
    /**
     * msgId:111202 返回活动操作
     */
    RESACTIVITYOPERATION(111202),
    /**
     * msgId:113101 请求充值创建订单
     */
    REQPAYCREATEORDER(113101),
    /**
     * msgId:113103 请求充值订单验证
     */
    REQPAYCHECK(113103),
    /**
     * msgId:113201 返回充值订单信息
     */
    RESPAY(113201),
    /**
     * msgId:113202 充值成功返回信息
     */
    RESPAYSUCCESS(113202),
    /**
     * msgId:115101 请求战斗开始
     */
    REQFIGHTSTART(115101),
    /**
     * msgId:115201 返回战斗开始消息，通知结果
     */
    RESFIGHTSTART(115201),
    /**
     * msgId:117101 请求铲煤
     */
    REQSMELTERSHOVELCOAL(117101),
    /**
     * msgId:117102 请求升级熔炉
     */
    REQSMELTERLVUP(117102),
    /**
     * msgId:117201 返回铲煤
     */
    RESSMELTERSHOVELCOAL(117201),
    /**
     * msgId:117202 返回升级熔炉
     */
    RESSMELTERLVUP(117202),
    /**
     * msgId:117203 熔炉升级完成推送
     */
    RESSMELTERLVUPNOTIFY(117203),
    /**
     * msgId:118101 请求英雄召唤池刷新
     */
    REQHEROPOOLREFRESH(118101),
    /**
     * msgId:118102 请求英雄召唤
     */
    REQHEROSUMMON(118102),
    /**
     * msgId:118103 请求英雄成长
     */
    REQHEROUP(118103),
    /**
     * msgId:118104 请求英雄重置
     */
    REQHERORESET(118104),
    /**
     * msgId:118105 请求英雄上阵
     */
    REQHEROJOINBATTLE(118105),
    /**
     * msgId:118106 请求英雄背包格子解锁
     */
    REQHEROPACKGRIDUNLOCK(118106),
    /**
     * msgId:118108 请求选择大保底
     */
    REQHEROMAXAWARDCHOOSE(118108),
    /**
     * msgId:118109 请求英雄密码操作
     */
    REQHEROPASSWORDOPT(118109),
    /**
     * msgId:118201 返回英雄召唤池刷新
     */
    RESHEROPOOLREFRESH(118201),
    /**
     * msgId:118202 返回英雄召唤
     */
    RESHEROSUMMON(118202),
    /**
     * msgId:118203 返回英雄成长
     */
    RESHEROUP(118203),
    /**
     * msgId:118204 返回英雄重置
     */
    RESHERORESET(118204),
    /**
     * msgId:118205 返回英雄上阵
     */
    RESHEROJOINBATTLE(118205),
    /**
     * msgId:118206 返回英雄背包格子解锁
     */
    RESHEROPACKGRIDUNLOCK(118206),
    /**
     * msgId:118207 返回英雄羁绊信息
     */
    RESHEROFETTERINFO(118207),
    /**
     * msgId:118208 返回选择大保底
     */
    RESHEROMAXAWARDCHOOSE(118208),
    /**
     * msgId:118209 返回英雄密码操作
     */
    RESHEROPASSWORDOPT(118209),
    /**
     * msgId:119101 请求幸存者营救
     */
    REQSURVIVORRESCUE(119101),
    /**
     * msgId:119102 请求幸存者合成或升级
     */
    REQSURVIVORCRAFTORUPGRADE(119102),
    /**
     * msgId:119103 请求幸存者共鸣激活或升级
     */
    REQSURVIVORRESONANCEACTIVEORUPGRADE(119103),
    /**
     * msgId:119104 请求幸存者加入或退出战斗
     */
    REQSURVIVORJOINORREMOVEBATTLE(119104),
    /**
     * msgId:119105 请求幸存者编队更换
     */
    REQSURVIVORBATTLEGROUPCHANGE(119105),
    /**
     * msgId:119201 返回幸存者营救
     */
    RESSURVIVORRESCUE(119201),
    /**
     * msgId:119202 返回幸存者合成或升级
     */
    RESSURVIVORCRAFTORUPGRADE(119202),
    /**
     * msgId:119203 返回幸存者共鸣激活或升级
     */
    RESSURVIVORRESONANCEACTIVEORUPGRADE(119203),
    /**
     * msgId:119204 返回幸存者加入或退出战斗
     */
    RESSURVIVORJOINORREMOVEBATTLE(119204),
    /**
     * msgId:119205 返回幸存者编队更换
     */
    RESSURVIVORBATTLEGROUPCHANGE(119205),
    /**
     * msgId:120101 请求翅膀解锁
     */
    REQWINGUNLOCK(120101),
    /**
     * msgId:120102 请求翅膀升级
     */
    REQWINGUPGRADE(120102),
    /**
     * msgId:120103 请求翅膀穿戴
     */
    REQWINGWEAR(120103),
    /**
     * msgId:120201 返回翅膀解锁
     */
    RESWINGUNLOCK(120201),
    /**
     * msgId:120202 返回翅膀升级
     */
    RESWINGUPGRADE(120202),
    /**
     * msgId:120203 返回翅膀穿戴
     */
    RESWINGWEAR(120203),
    /**
     * msgId:121101 请求排行榜信息
     */
    REQRANKINFO(121101),
    /**
     * msgId:121201 返回排行榜信息
     */
    RESRANKINFO(121201),
    /**
     * msgId:122102 请求buff选择
     */
    REQTOWERBUFFCHOOSE(122102),
    /**
     * msgId:122103 请求层数奖励
     */
    REQTOWERSTAGEREWARD(122103),
    /**
     * msgId:122202 返回buff选择
     */
    RESTOWERBUFFCHOOSE(122202),
    /**
     * msgId:122203 返回层数奖励
     */
    RESTOWERSTAGEREWARD(122203),
    /**
     * msgId:202101 请求向公会服务器注册
     */
    REQPUBLICCLANSSERVERREGISTER(202101),
    /**
     * msgId:202201 返回向公会服务器注册
     */
    RESPUBLICCLANSSERVERREGISTER(202201),
    /**
     * msgId:202202 公会服务器to游戏服务器之间心跳
     */
    RESPUBLICCLANSSERVER2SERVERHEARTBEAT(202202),
    /**
     * msgId:202203 返回跨服操作失败
     */
    RESPUBLICCLANSFAILURE(202203),
    /**
     * msgId:206101 请求向邮件注册服务器信息
     */
    REQPUBLICMAILSERVERREGISTER(206101),
    /**
     * msgId:206103 请求向邮件服获取玩家所有邮件(登录)
     */
    REQPUBLICMAILALL(206103),
    /**
     * msgId:206104 请求向邮件服发送玩家邮件
     */
    REQPUBLICMAILSENDMAIL(206104),
    /**
     * msgId:206105 请求向邮件服打开邮件并设置已读
     */
    REQPUBLICMAILOPEN(206105),
    /**
     * msgId:206106 请求向邮件服领取邮件奖励
     */
    REQPUBLICMAILAWARD(206106),
    /**
     * msgId:206107 请求向邮件服删除邮件
     */
    REQPUBLICMAILDELETE(206107),
    /**
     * msgId:206201 返回服务器连接邮件服务器状态
     */
    RESPUBLICMAILSERVERREGISTER(206201),
    /**
     * msgId:206202 邮件服务器to服务器之间心跳
     */
    RESPUBLICMAILSERVER2SERVERHEARTBEAT(206202),
    /**
     * msgId:206205 返回邮件打开内容
     */
    RESPUBLICMAILOPEN(206205),
    /**
     * msgId:206206 返回邮件服邮件已领取成功
     */
    RESPUBLICMAILAWARD(206206),
    /**
     * msgId:206207 返回邮件服删除邮件成功
     */
    RESPUBLICMAILDELETE(206207),
    /**
     * msgId:206208 返回邮件服推送邮件给玩家
     */
    RESPUBLICMAILINFO(206208),
    ;

    private int msgId;

    private MsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getMsgId() {
        return msgId;
    }
}