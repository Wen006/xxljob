package com.xxl.job.core.util;

/**
 * 手动添加,几种策略的枚举类
 */
public class JobEnumManager {


    /**
     * 失败处理策略
     */
    public enum ExecutorFailStrategyEnum {
        失败告警("FAIL_ALARM"), 失败重试("FAIL_RETRY");
        private final String title;

        private ExecutorFailStrategyEnum(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

    }

    /**
     * 阻塞处理策略
     */
    public enum ExecutorBlockStrategyEnum{

        单机串行("SERIAL_EXECUTION"),
        丢弃后续调度("SERIAL_EXECUTION"),
        覆盖之前调度("SERIAL_EXECUTION");
        private final String title;

        private ExecutorBlockStrategyEnum(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    /**
     * 路由策略
     */
    public enum ExecutorRouteStrategyEnum{

        第一个("FIRST"),
        最后一个("LAST"),
        轮询("ROUND"),
        随机("RANDOM"),
        一致性HASH("CONSISTENT_HASH"),
        最不经常使用("LEAST_FREQUENTLY_USED"),
        最近最久未使用("LEAST_RECENTLY_USED"),
        故障转移("FAILOVER"),
        忙碌转移("BUSYOVER"),
        分片广播("SHARDING_BROADCAST");

        private final String title;

        private ExecutorRouteStrategyEnum(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

    }



}
