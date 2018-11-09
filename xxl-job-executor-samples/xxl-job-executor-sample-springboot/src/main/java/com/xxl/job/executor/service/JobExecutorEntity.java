package com.xxl.job.executor.service;

import com.alibaba.fastjson.JSON;
import com.xxl.job.core.biz.model.XxlJobInfoCore;
import com.xxl.job.core.util.JobEnumManager;
import com.xxl.job.core.util.JobUtil;
import com.xxl.job.executor.jobHandler.util.ServiceJobUtil;

import java.util.HashMap;

public class JobExecutorEntity {

    private String uuid;

    private String serviceId;

    private String tableName;

    private JobUtil jobUtil;

    private String jobAdminAddress;

    private int groupId;

    private String jobHandlerName;

    private String jobDesc;

    private String author;

    private HashMap<String, String> paramMap;

    private String executeParam;

    private XxlJobInfoCore xxlJobInfoCore;


    /**
     * 代码添加任务时使用
     * @param serviceId
     * @param tableName
     * @param jobAdminAddress
     * @param executeParam
     * @param jobHandlerName
     * @param groupId
     * @param jobDesc
     * @param author
     * @return
     */
    public JobExecutorEntity getDTOFromAddTask(String serviceId, String tableName, String jobAdminAddress, String executeParam,
                                               String jobHandlerName, int groupId, String jobDesc, String author) {
        JobExecutorEntity dto = new JobExecutorEntity();
        dto.setServiceId(serviceId);
        dto.setTableName(tableName);
        dto.setJobUtil(new JobUtil(jobAdminAddress));
        HashMap<String, String> paramMap = new ServiceJobUtil(null).generateParamMap(tableName, executeParam);
        dto.setUuid(paramMap.get("id"));
        dto.setXxlJobInfoCore(generateDTO(paramMap, groupId,
                jobHandlerName, jobDesc, author));
        return dto;
    }

    /**
     * handler内使用
     * @param param
     * @param jobAdminAddress
     * @return
     */
    public JobExecutorEntity getDTOFromHandler(String param, String jobAdminAddress) {
        JobExecutorEntity dto = new JobExecutorEntity();
        dto.setJobUtil(new JobUtil(jobAdminAddress));
        HashMap<String, String> paramMap = JSON.parseObject(param, HashMap.class);    // 解析map参数
        String executeParam = paramMap.get("params");  // 执行参数
        dto.setTableName(paramMap.get("tableName"));  //中间表名
        dto.setUuid(paramMap.get("id"));
        dto.setExecuteParam(executeParam);
        return dto;
    }

    /**
     * 生成jobInfo
     *
     * @param paramMap
     * @param jobGroupId
     * @param jobHandlerName
     * @param jobDesc
     * @param author
     * @return
     */
    private XxlJobInfoCore generateDTO(HashMap<String, String> paramMap,
                                       int jobGroupId,
                                       String jobHandlerName,
                                       String jobDesc,
                                       String author
    ) {
        XxlJobInfoCore xxlJobInfoCore = new XxlJobInfoCore();        //默认为BEAN方式
        xxlJobInfoCore.setJobGroup(jobGroupId);                               //设置执行器id
        xxlJobInfoCore.setExecutorParam(JSON.toJSONString(paramMap));   //{params:''}
        xxlJobInfoCore.setExecutorHandler(jobHandlerName);
        xxlJobInfoCore.setJobDesc(jobDesc);
        xxlJobInfoCore.setAuthor(author);
        xxlJobInfoCore.setExecutorFailStrategy(JobEnumManager.ExecutorFailStrategyEnum.失败告警.getTitle());   //设置失败策略
        xxlJobInfoCore.setExecutorBlockStrategy(JobEnumManager.ExecutorBlockStrategyEnum.单机串行.getTitle()); //设置阻塞策略
        xxlJobInfoCore.setExecutorRouteStrategy(JobEnumManager.ExecutorRouteStrategyEnum.第一个.getTitle());   //设置路由策略
        return xxlJobInfoCore;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public JobUtil getJobUtil() {
        return jobUtil;
    }

    public void setJobUtil(JobUtil jobUtil) {
        this.jobUtil = jobUtil;
    }

    public String getJobAdminAddress() {
        return jobAdminAddress;
    }

    public void setJobAdminAddress(String jobAdminAddress) {
        this.jobAdminAddress = jobAdminAddress;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getJobHandlerName() {
        return jobHandlerName;
    }

    public void setJobHandlerName(String jobHandlerName) {
        this.jobHandlerName = jobHandlerName;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public HashMap<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(HashMap<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public String getExecuteParam() {
        return executeParam;
    }

    public void setExecuteParam(String executeParam) {
        this.executeParam = executeParam;
    }

    public XxlJobInfoCore getXxlJobInfoCore() {
        return xxlJobInfoCore;
    }

    public void setXxlJobInfoCore(XxlJobInfoCore xxlJobInfoCore) {
        this.xxlJobInfoCore = xxlJobInfoCore;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
