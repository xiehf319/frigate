package cn.cici.frigate.job.pojo;

import lombok.Data;
import org.quartz.JobDataMap;

import java.util.Date;

/**
 * @description:
 * @createDate:2019/6/19$16:39$
 * @author: Heyfan Xie
 */
@Data
public class TaskInfo {

    private JobDataMap jobDataMap;

    private String jobName;

    private String jobGroup;

    private String jobDescription;

    private String jobStatus;

    private String cronExpression;

    private Date createTime;

    public String jobClass() {
        return jobName == null ? null : jobName.split("#")[0];
    }


}
