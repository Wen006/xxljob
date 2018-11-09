package com.xxl.job.executor.test;

import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.biz.model.TriggerParam;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import com.xxl.job.core.rpc.netcom.NetComClientProxy;
import org.junit.Test;

/**
 * executor-api client, test
 *
 * Created by xuxueli on 17/5/12.
 */
public class DemoJobHandlerTest {

    @Test
    public void name() {
    }

    public static void main(String[] args) throws Exception {
        // param
        String jobHandler = "demoJobHandler";
        String params = "example:业务单据号..";   //业务传参

        // trigger data
        TriggerParam triggerParam = new TriggerParam();
        triggerParam.setJobId(10);    //数据库Id 主键Integer自增
        triggerParam.setExecutorHandler(jobHandler);
        triggerParam.setExecutorParams(params);
        triggerParam.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.COVER_EARLY.name());
        triggerParam.setGlueType(GlueTypeEnum.BEAN.name());
        triggerParam.setGlueSource(null);
        triggerParam.setGlueUpdatetime(System.currentTimeMillis());
        triggerParam.setLogId(1991);
        triggerParam.setLogDateTim(System.currentTimeMillis());

        // do remote trigger
        String accessToken = null;
        ExecutorBiz executorBiz = (ExecutorBiz) new NetComClientProxy(ExecutorBiz.class, "127.0.0.1:9999", null).getObject();
        ReturnT<String> runResult = executorBiz.run(triggerParam);
        System.out.println(runResult);

    }

}
