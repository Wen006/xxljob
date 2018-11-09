/*==============================================================*/
/* Table: XX_SERVICE_JOB                                               */
/*==============================================================*/
CREATE TABLE XX_SERVICE_JOB (
   ID varchar(40) NOT NULL  COMMENT '主键',
   JOB_ID int(11) ,
   SERVICE_ID varchar(255) ,
   EXECUTE_TIMES int(4) COMMENT '执行次数',
   FLAG int(2) COMMENT '执行结果，未执行(-1),失败(0),成功(1)',
   COMPENSATED int(2)  default 0 COMMENT '已补偿，是(1),否(0)',
   UPDATE_TIME TIMESTAMP,
   constraint PK_XX_SERVICE_JOB primary key (ID)
 );