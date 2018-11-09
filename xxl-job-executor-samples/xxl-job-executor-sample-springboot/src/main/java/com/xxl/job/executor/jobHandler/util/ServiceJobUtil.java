package com.xxl.job.executor.jobHandler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * Job_Service 中间表 的dao操作，dataSource
 */
@Service
public class ServiceJobUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceJobUtil.class);

    /**
     * 生成paramMap
     *
     * @param tableName      表名
     * @param executeParam   执行参数
     * @return    map{id,tableName,executeParam}
     */
    public HashMap<String, String> generateParamMap(String tableName, String executeParam) {
        HashMap<String, String> paramMap = new HashMap<>();
        String uuid = UUID.randomUUID().toString().replace("-","");
        paramMap.put("id", uuid);
        paramMap.put("params", executeParam);
        paramMap.put("tableName", tableName);
        return paramMap;
    }
    /**
     * 插入中间表
     * @param tableName
     * @param uuid
     * @param jobId
     * @param serviceId
     */
    public void insertJobAndService(String tableName, String uuid, int jobId, String serviceId) {
        JdbcTemplate jdbcTemplate = generateJdbcTemplate();
        Date date = new Date();
        String sql = "insert into " + tableName + "(id,job_id,service_id,execute_times,flag,update_time) values(?,?,?,?,?,?)";
        LOG.debug("sql=====>" + sql);
        LOG.debug("params==>" + uuid, jobId, serviceId, 0, -1, date);
        Object[] obj = new Object[]{uuid, jobId, serviceId, 0, -1, date};
        try {
            jdbcTemplate.update(sql, obj);
        } catch (Exception e) {
            LOG.error("insert failed", e.getMessage(), e);
        }
    }
    /**
     * 获取jobId
     * @param tableName
     * @param id
     * @return
     */
    public int getJobId(String tableName, String id) {
        JdbcTemplate jdbcTemplate = generateJdbcTemplate();
        int jobId = -1;  // 根据id读取记录表中的jobId
        String sql = "select job_id from " + tableName + " where id=?";
        LOG.debug("sql=====>" + sql);
        LOG.debug("params==>" + id);
        Object[] obj = new Object[]{id};
        try {
            jobId = jdbcTemplate.queryForObject(sql, obj, Integer.class);
        } catch (Exception e) {
            LOG.error("get jobId failed", e.getMessage(), e);
        }
        return jobId;
    }

    /**
     * 更新 flag ,执行结果
     * @param tableName
     * @param id
     * @param flag
     */
    public void updateFlag(String tableName, String id, int flag) {
        JdbcTemplate jdbcTemplate = generateJdbcTemplate();
        String sql = "update " + tableName + " set flag = ? where id = ?";
        LOG.debug("sql=====>" + sql);
        LOG.debug("params==>" + id, flag);
        try {
            jdbcTemplate.update(sql, flag, id);
        } catch (Exception e) {
            LOG.error("update flag failed", e.getMessage(), e);
        }
    }
    /**
     * 执行次数+1
     * @param tableName
     * @param id
     */
    public void updateExecuteTimes(String tableName, String id) {
        JdbcTemplate jdbcTemplate = generateJdbcTemplate();
        String sql = "update " + tableName + " A set A.execute_times = A.execute_times + 1 where id=?";
        LOG.debug("sql=====>" + sql);
        LOG.debug("params==>" + id);
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            LOG.error("update execute_times failed", e.getMessage(), e);
        }
    }

    /**
     * 获取补偿结果，是(1),否(0)
     * @param tableName
     * @param id
     * @return
     */
    public int getCompensated(String tableName, String id) {
        JdbcTemplate jdbcTemplate = generateJdbcTemplate();
        int compensated = -1;  // 根据id读取记录表中的compensated
        String sql = "select compensated from " + tableName + " where id=?";
        LOG.debug("sql=====>" + sql);
        LOG.debug("params==>" + id);
        Object[] obj = new Object[]{id};
        try {
            compensated = jdbcTemplate.queryForObject(sql, obj, Integer.class);
        } catch (Exception e) {
            LOG.error("get compensated failed", e.getMessage(), e);
        }
        return compensated;
    }
    public int getExecuteTimes(String tableName, String id) {
        JdbcTemplate jdbcTemplate = generateJdbcTemplate();
        int execute_times = -1;  // 根据id读取记录表中的execute_times
        String sql = "select execute_times from " + tableName + " where id=?";
        LOG.debug("sql=====>" + sql);
        LOG.debug("params==>" + id);
        Object[] obj = new Object[]{id};
        try {
            execute_times = jdbcTemplate.queryForObject(sql, obj, Integer.class);
        } catch (Exception e) {
            LOG.error("get execute_times failed", e.getMessage(), e);
        }
        return execute_times;
    }
    /**
     * 更新补偿结果
     * @param tableName
     * @param id
     * @param compensated
     */
    public void updateCompensated(String tableName, String id, int compensated) {
        JdbcTemplate jdbcTemplate = generateJdbcTemplate();
        String sql = "update " + tableName + " set COMPENSATED = ? where id=?";
        LOG.debug("sql=====>" + sql);
        LOG.debug("params==>" + id);
        try {
            jdbcTemplate.update(sql, compensated, id);
        } catch (Exception e) {
            LOG.error("update compensated failed", e.getMessage(), e);
        }
    }

    public JdbcTemplate generateJdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ServiceJobUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}

