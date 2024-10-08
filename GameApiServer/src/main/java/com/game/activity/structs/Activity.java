package com.game.activity.structs;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 公共活动数据，用于存放公共活动数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/14 18:08
 */
@Document(collection = "activity")
public class Activity {

    /**
     * 活动唯一id
     */
    @Id
    private int id;
    /**
     * 活动类型id
     */
    private int activityType;
    /**
     * 本轮活动是否已执行过关闭处理结算状态，true=已处理过关闭结算，只有当version发生变化才会重新设置为true
     */
    private boolean close;
    /**
     * 活动版本号
     */
    private int version;
    /**
     * 是否保存
     */
    @Transient
    private transient boolean save;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

}
