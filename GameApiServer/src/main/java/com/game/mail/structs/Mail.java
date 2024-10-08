package com.game.mail.structs;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮件
 *
 * @author zhangzhen
 * @time 2023年4月27日
 */
@Document(collection = "mail")
public class Mail {

    /**
     * 邮件id
     */
    @Id
    private Long mailId;
    /**
     * 给web前端使用，在游戏服务器不用赋值，也不处理，只有web后端给web前端数据的时候才赋值
     */
    @Transient
    private transient String mailIds;
    /**
     * 邮件配置id
     */
    private Integer configId = 0;
    /**
     * 后台邮件id
     */
    private Long backId;
    /**
     * 邮件类型 MailType
     */
    private int type;
    /**
     * 邮件领取类型 MailGetType
     */
    private int getType;
    /**
     * 角色id
     */
    private long playerId;
    /**
     * 标题
     */
    private String title = "";
    /**
     * 发件人
     */
    private String sender = "";
    /**
     * 内容
     */
    private String content = "";
    /**
     * 是否已读 0未读 1已读
     */
    private int mailRead;
    /**
     * 是否领取 0未领取 1已领取
     */
    private int mailGet;
    /**
     * 发送邮件时间
     */
    private long createTime;
    /**
     * 邮件过期时间
     */
    private long delTime;
    /**
     * 邮件内容替换参数
     */
    private List<String> paramList = new ArrayList<>();
    /**
     * 奖励物品
     */
    private List<Integer> goodsList = new ArrayList<>();

    public Long getMailId() {
        return mailId;
    }

    public void setMailId(Long mailId) {
        this.mailId = mailId;
    }

    public String getMailIds() {
        return mailIds;
    }

    public void setMailIds(String mailIds) {
        this.mailIds = mailIds;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Long getBackId() {
        return backId;
    }

    public void setBackId(Long backId) {
        this.backId = backId;
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

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMailRead() {
        return mailRead;
    }

    public void setMailRead(int mailRead) {
        this.mailRead = mailRead;
    }

    public int getMailGet() {
        return mailGet;
    }

    public void setMailGet(int mailGet) {
        this.mailGet = mailGet;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getDelTime() {
        return delTime;
    }

    public void setDelTime(long goodsLimitTime) {
        this.delTime = goodsLimitTime;
    }

    public List<String> getParamList() {
        return paramList;
    }

    public void setParamList(List<String> paramList) {
        this.paramList = paramList;
    }

    public List<Integer> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Integer> goodsList) {
        this.goodsList = goodsList;
    }

}
