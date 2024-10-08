package com.game.server;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 保存线程
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/14 17:35
 */
@Log4j2
public class SaveServer extends Thread {

    /**
     * 插入
     */
    public static final int INSERT = 1;
    /**
     * 保存
     */
    public static final int UPDATE = 2;
    /**
     * 插入或保存(有批量，放弃掉)
     */
//	public static final int MERGE = 3;
    /**
     * 删除
     */
    public static final int DELETE = 4;
    // 失败日志
    private final Logger failedlog = LogManager.getLogger("SAVEFAILED");
    // 命令执行队列
    private final LinkedBlockingQueue<SaveBean> beans = new LinkedBlockingQueue<SaveBean>();
    // 运行标志
    private boolean stop;
    // 线程名称
    protected String threadName;
    // 插入数据库
    boolean insertDB = true;
    // 最大缓存数量
    private static final int MAX_SIZE = 100000;
    // 最大错误次数
//	private static int MAX_ERRORTIMES = 5;
    // 数据库
    private final MongoTemplate mongoTemplate;

    public SaveServer(String threadName, MongoTemplate mongoTemplate) {
        super();
        this.threadName = threadName;
        this.mongoTemplate = mongoTemplate;
    }

    public SaveServer(ThreadGroup threadGroup, String threadName, MongoTemplate mongoTemplate) {
        super(threadGroup, threadName);
        this.threadName = threadName;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run() {
        try {
            this.stop = false;
            while (!stop || !beans.isEmpty()) {
                SaveBean bean = beans.poll();
                while (bean != null) {
                    deal(bean);
                    bean = beans.poll();
                }
                // 保存完毕暂停
                synchronized (this) {
                    wait();
                }
            }
        } catch (Exception e) {
            log.error("Save Thread " + threadName + " Wait Exception:", e);
        }
    }

    /**
     * 停止保存处理
     *
     * @param flag
     */
    public void stop(boolean flag) {
        stop = flag;
        try {
            synchronized (this) {
                notify();
            }
        } catch (Exception e) {
            log.error("Save Thread " + threadName + " Notify Exception:", e);
        }
    }

    /**
     * 放入待处理数据
     *
     * @param bean
     * @param dealType 0-insert 1-update 2-delete
     */
    public void deal(Object bean, int dealType) {
        try {
            beans.add(new SaveBean(bean, dealType));
            synchronized (this) {
                notify();
            }
        } catch (Exception e) {
            log.error("放入待处理数据 Thread " + threadName + " Notify Exception:", e);
        }
    }

    /**
     * 处理数据库数据操作
     *
     * @param saveBean
     * @throws Exception
     */
    private void deal(SaveBean saveBean) {
        try {
            Object bean = saveBean.getBean();
            switch (saveBean.getDealType()) {
                case INSERT:
                    mongoTemplate.insert(bean);
                    break;
                case UPDATE:
                    mongoTemplate.save(bean);
                    break;
                case DELETE:
                    mongoTemplate.remove(bean);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("处理数据库数据操作异常：", e);
        }
    }

    /**
     * 待保存的数据
     *
     * @author zhangzhen
     * @version 1.0
     * @time 2023/12/14 17:47
     */
    private class SaveBean {
        // 待保存实体
        private final Object bean;
        // 处理方式
        private final int dealType;

        public SaveBean(Object bean, int dealType) {
            this.bean = bean;
            this.dealType = dealType;
        }

        public Object getBean() {
            return bean;
        }

        public int getDealType() {
            return dealType;
        }
    }

}
