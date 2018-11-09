package com.xxl.job.executor.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.xxl.job.core.biz.model.XxlJobInfoCore;
import com.xxl.job.executor.jobHandler.util.ServiceJobUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * executor-api client, test
 * <p>
 * Created by xuxueli on 17/5/12.
 */
@RestController
public class ScheduleTest {

    @RequestMapping("/test")
    public String test001() {
        DataSource dataSource = new DruidDataSource();     //数据源 【需提供】
        ((DruidDataSource) dataSource).setDriverClassName("com.mysql.jdbc.Driver");
        ((DruidDataSource) dataSource).setUrl("jdbc:mysql://127.0.0.1:3306/job_test?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
        ((DruidDataSource) dataSource).setUsername("root");
        ((DruidDataSource) dataSource).setPassword("root");

        JobExecutorEntity dto = new JobExecutorEntity().getDTOFromAddTask("doc001002", "xx_service_job",
                "http://127.0.0.1:8080/xxl-job-admin",
                "executeParam","demoJobHandler",2,
                "资金任务#手动#doc001002","作者");

        //0 中间表 id
        String uuid = dto.getUuid();

        //1 设置JobInfo 参数
        XxlJobInfoCore xxlJobInfoCore = dto.getXxlJobInfoCore();

        //2 添加JobInfo ，返回jobId
        int jobId = dto.getJobUtil().addJob(xxlJobInfoCore, null);  //添加JobInfo 返回JobId

        if (jobId < 0) {
            return "job=-1,add fail";
        }

        //3 记录业务信息表,存到[本]服务,id,job_id,service_id,execute_times,flag
        new ServiceJobUtil(dataSource).insertJobAndService(dto.getTableName(), uuid, jobId, dto.getServiceId());
        // 4 触发执行一次
        dto.getJobUtil().triggerJob(jobId, null);

        return "添加任务成功";
    }

}
