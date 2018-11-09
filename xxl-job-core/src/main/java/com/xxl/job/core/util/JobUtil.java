package com.xxl.job.core.util;

import com.xxl.job.core.biz.AdminBiz;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.biz.model.XxlJobInfoCore;
import com.xxl.job.core.rpc.netcom.NetComClientProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Job工具类
 */
public class JobUtil {

    private static Logger logger = LoggerFactory.getLogger(JobUtil.class);
    private String addressUrl;

    public JobUtil(String adminAddress) {
        this.addressUrl = adminAddress.concat(AdminBiz.MAPPING);
    }


    /**
     * 调度中心API，触发任务单次执行服务
     *
     * @param jobId
     * @param accessToken
     * @throws Exception
     */
    public void triggerJob(int jobId,String accessToken) {
        try {
            AdminBiz adminBiz = (AdminBiz) new NetComClientProxy(AdminBiz.class, addressUrl, accessToken).getObject();
            ReturnT<String> returnT = adminBiz.triggerJob(jobId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 调度中心API：根据任务Id,暂停任务
     * @param jobId
     * @param accessToken
     */
    public void pauseJob(int jobId,String accessToken) {
        try {
            AdminBiz adminBiz = (AdminBiz) new NetComClientProxy(AdminBiz.class, addressUrl, accessToken).getObject();
            ReturnT<String> returnT = adminBiz.pause(jobId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    /**
     * 调度中心API：添加Job,返回Id,添加失败返回id 为-1
     *
     * @param xxlJobInfoCore
     * @param accessToken
     */
    public int addJob(XxlJobInfoCore xxlJobInfoCore,String accessToken) {
        int jobId = -1;
        try {
            AdminBiz adminBiz = (AdminBiz) new NetComClientProxy(AdminBiz.class, addressUrl, accessToken).getObject();
            ReturnT<String> returnT = adminBiz.add(xxlJobInfoCore);
            if (returnT.getContent() != null && ReturnT.SUCCESS.getCode() == 200) {
                String content = returnT.getContent();
                jobId =  Integer.valueOf(content);
                logger.info("add job success,jobId:["+jobId+"]");
                return jobId;
            }else{
                logger.error("add job fail");
                return jobId;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return jobId;
        }
    }

}
