package com.game.mail.structs;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台共享邮件
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/6 11:56
 */
@Document(collection = "backMail")
public class BackMail {

    /**
     * 后台邮件id
     */
    @Id
    private long backId;
    /**
     * 给web前端使用，在游戏服务器不用赋值，也不处理，只有web后端给web前端数据的时候才赋值
     */
    @Transient
    private transient String backIds;
    /**
     * 邮件类型 MailType
     */
    private int type;
    /**
     * 邮件领取类型 MailGetType
     */
    private int getType;
    /**
     * 发件人
     */
    private String sender = "";
    /**
     * 收件人玩家唯一id列表，空表示全服邮件
     */
    private List<Long> receivePlayerIdList = new ArrayList<>();
    /**
     * 标题
     */
    private String title = "";
    /**
     * 内容
     */
    private String content = "";
    /**
     * 自定义邮件扩展信息
     */
    private byte[] extInfo;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 发送时间（在web后台添加的邮件，创建和发送时间不一样，是先创建，然后再确定是否发送）
     */
    private long sendTime;
    /**
     * 邮件状态（0=未发送 1=已发送）（当此邮件为web后台新增，需要通过这个状态来表示是否已发送出去，如果是系统邮件默认为已发送）
     */
    private int mailState;
    /**
     * 奖励物品
     */
    private List<Integer> goodsList = new ArrayList<>();

    public long getBackId() {
        return backId;
    }

    public void setBackId(long backId) {
        this.backId = backId;
    }

    public String getBackIds() {
        return backIds;
    }

    public void setBackIds(String backIds) {
        this.backIds = backIds;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGetType() {
        return getType;
    }

    public void setGetType(int getType) {
        this.getType = getType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<Long> getReceivePlayerIdList() {
        return receivePlayerIdList;
    }

    public void setReceivePlayerIdList(List<Long> receivePlayerIdList) {
        this.receivePlayerIdList = receivePlayerIdList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(byte[] extInfo) {
        this.extInfo = extInfo;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public int getMailState() {
        return mailState;
    }

    public void setMailState(int mailState) {
        this.mailState = mailState;
    }

    public List<Integer> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Integer> goodsList) {
        this.goodsList = goodsList;
    }
}
