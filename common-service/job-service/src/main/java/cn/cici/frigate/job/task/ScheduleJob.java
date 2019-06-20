package cn.cici.frigate.job.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @description:
 * @createDate:2019/6/20$11:02$
 * @author: Heyfan Xie
 */
@Slf4j
public class ScheduleJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("jobName: {}", context.getJobDetail().getKey().getName());
    }
}
