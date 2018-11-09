package com.xxl.job.executor.jobHandler;

import com.alibaba.druid.pool.DruidDataSource;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.core.util.JobUtil;
import com.xxl.job.executor.jobHandler.util.ServiceJobUtil;
import com.xxl.job.executor.service.JobExecutorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


/**
 * 任务Handler示例（Bean模式）
 * <p>
 * 开发步骤：
 * 1、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”；
 * 2、注册到Spring容器：添加“@Component”注解，被Spring容器扫描为Bean实例；
 * 3、注册到执行器工厂：添加“@JobHandler(value="自定义jobhandler名称")”注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2015-12-19 19:43:36
 */
@JobHandler(value = "demoJobHandler")
@Component
public class DemoJobHandler extends IJobHandler {

    @Autowired
    private ServiceJobUtil serviceJobUtil ;

    @Override
    public ReturnT<String> execute(String param) throws Exception {

        DataSource dataSource = new DruidDataSource();     //数据源 【需提供,忽略此处创建方式】
        ((DruidDataSource) dataSource).setDriverClassName("com.mysql.jdbc.Driver");
        ((DruidDataSource) dataSource).setUrl("jdbc:mysql://127.0.0.1:3306/job_test?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
        ((DruidDataSource) dataSource).setUsername("root");
        ((DruidDataSource) dataSource).setPassword("root");

        serviceJobUtil.setDataSource(dataSource);

        JobExecutorEntity dto = new JobExecutorEntity().getDTOFromHandler(param, "http://127.0.0.1:8080/xxl-job-admin");
        // 构造JobUtil，设置调度中心地址
        JobUtil jobUtil = dto.getJobUtil();

        XxlJobLogger.log("XXL-JOB, 业务执行代码.");

        // 1.1 初始化业务操作记录，获取id,jobId,
        String id = dto.getUuid(); // 中间表id
        int jobId = -1;         // jobId
        String executeParam = dto.getExecuteParam(); // 执行参数
        String tableName = dto.getTableName();     //中间表名

        if (id != null) {
            jobId = serviceJobUtil.getJobId(tableName, id);
        } else {
            XxlJobLogger.log("XXL-JOB, 中间业务表id 为空，无法获取jobId.");
            FAIL.setMsg("中间业务表id 为空，无法获取jobId");
            return FAIL;
        }
        if (jobId == -1) {
            XxlJobLogger.log("XXL-JOB, JobId 为空.");
            FAIL.setMsg("JobId 为空，无法获取jobId");
            return FAIL;
        }

        // 1.2 job 执行次数 + 1
        serviceJobUtil.updateExecuteTimes(tableName, id);


        // 2 业务操作
        try {
            //service here
            int i = 1 / 1;
            // 判断是否执行成功
            XxlJobLogger.log("业务执行成功");

            // 2.1 更新业务操作记录，执行成功
            serviceJobUtil.updateFlag(tableName, id, 1);
            // 2.2 暂停该jobInfo
            jobUtil.pauseJob(jobId, null);
            return SUCCESS;
        } catch (Exception e) {
            // 更新业务操作记录执行失败
            serviceJobUtil.updateFlag(tableName, id, 0);
            XxlJobLogger.log("业务执行代码，" + e.getMessage() + ",触发补偿执行");
            // 3 触发补偿执行
            // 3.1 判断  是否已补偿 && 执行失败
            if (serviceJobUtil.getCompensated(tableName, id) == 0) {
                // 更新 已补偿
                serviceJobUtil.updateCompensated(tableName, id, 1);
                jobUtil.triggerJob(jobId, null);
            } else {
                XxlJobLogger.log("业务执行代码，已补偿执行");
                return FAIL;
            }
            return null;
        }
    }

}
