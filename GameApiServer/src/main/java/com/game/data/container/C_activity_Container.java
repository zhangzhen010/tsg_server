package com.game.data.container;

import com.alibaba.fastjson2.JSON;
import com.game.data.bean.B_activity_Bean;
import com.game.data.myenum.MyEnumActivityOpenType;
import com.game.data.myenum.MyEnumActivityType;
import com.game.data.structs.DataCacheActivityData;
import com.game.data.structs.IContainer;
import com.game.server.structs.ServerData;
import com.game.utils.TimeUtil;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangzhen
 * @version 1.0.0
 * B_activity_Bean数据容器
 */
@Component
@Getter
@Log4j2
public class C_activity_Container implements IContainer {

    private List<B_activity_Bean> list;
//    活动涉及到多个逻辑服，不开放此map，免得误用
//    private final HashMap<Integer, B_activity_Bean> map = new HashMap<>();
    /**
     * 当前服务器id（逻辑服使用）
     */
    private @Value("${server.serverId}") int serverId;
    /**
     * 本服活动配置map key=活动id value=B_Activity_Bean（B_Activity_Bean表）
     */
    private Map<Integer, B_activity_Bean> activityMap;
    /**
     * 本服活动配置list value=B_Activity_Bean（B_Activity_Bean表）
     */
    private List<B_activity_Bean> activityList;
    /**
     * 活动预加载开始时间(仅包含按开服天数活动的开启时间)map key=活动id（B_Activity_Bean表）
     */
    private Map<Integer, Long> activityLoadBeginTimeMap;
    /**
     * 活动预加载结束时间(仅包含按开服天数活动的开启时间)map key=活动id（B_Activity_Bean表）
     */
    private Map<Integer, Long> activityLoadEndTimeMap;
    /**
     * 活动开始时间(仅包含按开服天数活动的开启时间)map key=活动id（B_Activity_Bean表）
     */
    private Map<Integer, Long> activityBeginTimeMap;
    /**
     * 活动结束时间(仅包含按开服天数活动的开启时间)map key=活动id（B_Activity_Bean表）
     */
    private Map<Integer, Long> activityEndTimeMap;
    /**
     * 活动data数据map，list下标0对应data1 key=活动id value=data数据列表
     */
    private Map<Integer, List<DataCacheActivityData>> activityDataMap;
    /**
     * 活动数据 key=活动类型(MyDefineActivityType) value=活动配置
     */
    Map<Integer, List<B_activity_Bean>> activityTypeBeanMap;

    private @Resource
    @Qualifier("dataMongo") MongoTemplate mongoTemplate;

    public void load() {
        list = mongoTemplate.findAll(B_activity_Bean.class);
//        for (B_activity_Bean bean : list) {
//            map.put(bean.getId(), bean);
//        }

        init();
    }

    /**
     * 配置是否为空
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 初始化(实现加载完成后数据处理)
     */
    public void init() {
        updateActivityConfig();
    }

    /**
     * 更新活动配置
     */
    public void updateActivityConfig() {
        int id = 0;
        try {
            long currentTime = System.currentTimeMillis();
            // 服务器首次开服时间（逻辑服才有，其他服务器启动不处理）
            long serverOpenTime = 0;
            // 读取本服配置
            ServerData serverData = mongoTemplate.findOne(Query.query(Criteria.where("serverId").is(serverId)), ServerData.class);
            if (serverData != null) {
                serverOpenTime = serverData.getOpenTime();
            } else {
                // 如果为逻辑服，找不到为空，表示刚开服，因为这里执行顺序高于GameServer执行顺序
                serverOpenTime = currentTime;
            }
            // 获取当天0点时间，用于判断活动是否开启
            serverOpenTime = TimeUtil.getTimeInMillisByHour(serverOpenTime, 0);
            // 本服活动配置map key=活动id value=B_Activity_Bean（B_Activity_Bean表）
            Map<Integer, B_activity_Bean> tempActivityMap = new HashMap<>();
            // 本服活动配置list value=B_Activity_Bean（B_Activity_Bean表）
            List<B_activity_Bean> tempActivityList = new ArrayList<>();
            // 活动预加载开始时间(仅包含按开服天数活动的开启时间)map key=活动id（B_Activity_Bean表）
            Map<Integer, Long> tempActivityLoadBeginTimeMap = new HashMap<>();
            // 活动预加载结束时间(仅包含按开服天数活动的开启时间)map key=活动id（B_Activity_Bean表）
            Map<Integer, Long> tempActivityLoadEndTimeMap = new HashMap<>();
            // 活动开始时间(仅包含按开服天数活动的开启时间)map key=活动id（B_Activity_Bean表）
            Map<Integer, Long> tempActivityBeginTimeMap = new HashMap<>();
            // 活动结束时间(仅包含按开服天数活动的开启时间)map key=活动id（B_Activity_Bean表）
            Map<Integer, Long> tempActivityEndTimeMap = new HashMap<>();
            // 活动data数据map key=活动id value=data数据列表
            Map<Integer, List<DataCacheActivityData>> tempActivityDataMap = new HashMap<>();
            // 活动数据 key=活动类型(MyDefineActivityType) value=活动id
            Map<Integer, List<B_activity_Bean>> tempActivityTypeBeanMap = new HashMap<>();
            // 遍历
            for (B_activity_Bean bean : list) {
                id = bean.getId();
                // 属于本服活动
                if (bean.getServerIdBegin() <= serverId && serverId <= bean.getServerIdEnd()) {
                    boolean isOpen = false;
                    if (bean.getOpenType() == MyEnumActivityOpenType.FOREVER.getType().intValue()) {
                        isOpen = true;
                    } else if (bean.getOpenType() == MyEnumActivityOpenType.TIME.getType().intValue() && bean.getLoadBeginTime() <= currentTime && currentTime < bean.getLoadEndTime()) {
                        isOpen = true;
                    } else if (bean.getOpenType() == MyEnumActivityOpenType.DAY.getType().intValue() && (TimeUtil.getActivityTimeByServerOpenDayArray(bean.getLoadBeginDayList(), serverOpenTime) <= currentTime && currentTime < TimeUtil.getActivityTimeByServerOpenDayArray(bean.getLoadEndDayList(), serverOpenTime))) {
                        isOpen = true;
                        // 属于本服活动并且属于开服天数活动提前计算活动开关时间
                        tempActivityLoadBeginTimeMap.put(bean.getId(), TimeUtil.getActivityTimeByServerOpenDayArray(bean.getLoadBeginDayList(), serverOpenTime));
                        tempActivityLoadEndTimeMap.put(bean.getId(), TimeUtil.getActivityTimeByServerOpenDayArray(bean.getLoadEndDayList(), serverOpenTime));
                        tempActivityBeginTimeMap.put(bean.getId(), TimeUtil.getActivityTimeByServerOpenDayArray(bean.getBeginDayList(), serverOpenTime));
                        tempActivityEndTimeMap.put(bean.getId(), TimeUtil.getActivityTimeByServerOpenDayArray(bean.getEndDayList(), serverOpenTime));
                    }
                    // 属于预加载时间内的活动
                    if (isOpen) {
                        if (tempActivityMap.containsKey(bean.getId())) {
                            log.error("更新活动Activity配置异常，同一时间段活动重复，重复不更新，activity=" + JSON.toJSONString(bean));
                            continue;
                        }
                        tempActivityTypeBeanMap.computeIfAbsent(bean.getActivityType(), k -> new ArrayList<>()).add(bean);
                        // 添加本服活动
                        tempActivityMap.put(bean.getId(), bean);
                        tempActivityList.add(bean);
                        // 活动data数据
                        List<DataCacheActivityData> dataList = new ArrayList<>();
                        //注意不同的活动可能解析不一样的，可能需要判断活动类型(目前还不需要特殊拆分解析配置)
                        // 补充内容
                        tempActivityDataMap.put(bean.getId(), dataList);
                        // 解析配置
                        // 补充内容
                    }
                }
            }
            // 本服活动配置列表（B_Activity_Bean表）
            activityMap = tempActivityMap;
            activityList = tempActivityList;
            // 活动预加载开始时间(仅包含按开服天数活动的开启时间)map key=活动id（B_Activity_Bean表）
            activityLoadBeginTimeMap = tempActivityLoadBeginTimeMap;
            // 活动预加载结束时间(仅包含按开服天数活动的开启时间)map key=活动id（B_Activity_Bean表）
            activityLoadEndTimeMap = tempActivityLoadEndTimeMap;
            // 活动开始时间(仅包含按开服天数活动的开启时间)map key=活动id（B_Activity_Bean表）
            activityBeginTimeMap = tempActivityBeginTimeMap;
            // 活动结束时间(仅包含按开服天数活动的开启时间)map key=活动id（B_Activity_Bean表）
            activityEndTimeMap = tempActivityEndTimeMap;
            // 活动data数据map，list下标0对应data1 key=活动id value=data数据列表
            activityDataMap = tempActivityDataMap;
            activityTypeBeanMap = tempActivityTypeBeanMap;
            // 打印活动状态日志
            for (B_activity_Bean bean : activityMap.values()) {
                // 打印活动信息
                StringBuilder sb = new StringBuilder();
                sb.append("加载活动配置：activityId=");
                sb.append(bean.getId());
                sb.append(" activityType=");
                sb.append(MyEnumActivityType.getMyEnumActivityType(bean.getActivityType()).getName());
                sb.append(" startId=");
                sb.append(bean.getServerIdBegin());
                sb.append(" endId=");
                sb.append(bean.getServerIdEnd());
                sb.append(" version=");
                sb.append(bean.getVersion());
                sb.append(" 是否加载：");
                sb.append(isActivityLoad(bean));
                sb.append(" 是否开启：");
                sb.append(isActivityOpen(bean));
                log.info(sb.toString());
            }
        } catch (Exception e) {
            log.error("更新活动配置,配置id=" + id, e);
        }
    }

    /**
     * 活动是否加载
     * <p>
     * 此方法仅仅用作于日志打印
     *
     * @param bean
     * @return
     */
    public boolean isActivityLoad(B_activity_Bean bean) {
        try {
            long currentTime = System.currentTimeMillis();
            if (bean.getOpenType() == MyEnumActivityOpenType.FOREVER.getType().intValue()) {
                return true;
            } else if (bean.getOpenType() == MyEnumActivityOpenType.TIME.getType().intValue()) {
                return bean.getLoadBeginTime() <= currentTime && currentTime < bean.getLoadEndTime();
            } else {
                return getActivityLoadBeginTimeMap().get(bean.getId()) <= currentTime && currentTime < getActivityLoadEndTimeMap().get(bean.getId());
            }
        } catch (Exception e) {
            log.error("活动是否已开启", e);
            return false;
        }
    }

    /**
     * 活动是否开启
     * <p>
     * 此方法仅仅用作于日志打印
     *
     * @param bean
     * @return
     */
    public boolean isActivityOpen(B_activity_Bean bean) {
        try {
            long currentTime = System.currentTimeMillis();
            if (bean.getOpenType() == MyEnumActivityOpenType.FOREVER.getType().intValue()) {
                return true;
            } else if (bean.getOpenType() == MyEnumActivityOpenType.TIME.getType().intValue()) {
                return bean.getBeginTime() <= currentTime && currentTime < bean.getEndTime();
            } else {
                return getActivityBeginTimeMap().get(bean.getId()) <= currentTime && currentTime < getActivityEndTimeMap().get(bean.getId());
            }
        } catch (Exception e) {
            log.error("活动是否已开启", e);
            return false;
        }
    }

}