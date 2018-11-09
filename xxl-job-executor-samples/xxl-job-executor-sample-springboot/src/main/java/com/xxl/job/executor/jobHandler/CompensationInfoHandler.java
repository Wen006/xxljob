package com.xxl.job.executor.jobHandler;


import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.core.util.JobUtil;
import org.springframework.stereotype.Component;

/**
 * 任务Handler示例（Bean模式）
 *
 * 开发步骤：
 * 1、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”；
 * 2、注册到Spring容器：添加“@Component”注解，被Spring容器扫描为Bean实例；
 * 3、注册到执行器工厂：添加“@JobHandler(value="自定义jobhandler名称")”注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 */
@JobHandler(value="compensationInfoHandler")
@Component
public class CompensationInfoHandler extends IJobHandler {
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        // 构造JobUtil，设置调度中心地址
        JobUtil jobUtil = new JobUtil("http://127.0.0.1:8080/xxl-job-admin");
        XxlJobLogger.log("XXL-JOB, 失败任务补偿执行.");

        //1 扫描业务中间表，获取执行失败的任务，带上JobId返回

        int[] idArr = new int[]{52,53};  //模拟返回数据
        //2 调用
        try {
            for(int id : idArr){
                jobUtil.triggerJob(id,null);
            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnT.FAIL;
        }
    }
}
