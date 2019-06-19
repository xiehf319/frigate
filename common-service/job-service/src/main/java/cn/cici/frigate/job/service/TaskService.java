package cn.cici.frigate.job.service;

import cn.cici.frigate.component.exception.ServiceException;
import cn.cici.frigate.job.pojo.TaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @createDate:2019/6/19$16:38$
 * @author: Heyfan Xie
 */
@Service
@Slf4j
public class TaskService {

    @Autowired
    private Scheduler scheduler;


    public List<TaskInfo> getList() {

        final List<TaskInfo> list = new ArrayList<>();
        try {
            for (String jobGroupName : scheduler.getJobGroupNames()) {
                for (final JobKey jobKey : scheduler.getJobKeys(GroupMatcher.groupEquals(jobGroupName))) {
                    final List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    for (final Trigger trigger : triggers) {
                        final Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                        JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                        String cronExpression = "";
                        Date createTime = null;
                        if (trigger instanceof CronTrigger) {
                            final CronTrigger cronTrigger = (CronTrigger) trigger;
                            cronExpression = cronTrigger.getCronExpression();
                            createTime = cronTrigger.getStartTime();
                        }
                        final TaskInfo taskInfo = new TaskInfo();
                        taskInfo.setJobDataMap(new JobDataMap());
                        taskInfo.setJobName(jobKey.getName());
                        taskInfo.setJobGroup(jobGroupName);
                        taskInfo.setJobDescription(trigger.getDescription());
                        taskInfo.setJobStatus(triggerState.name());
                        taskInfo.setCronExpression(cronExpression);
                        taskInfo.setCreateTime(createTime);
                        taskInfo.setJobDataMap(jobDetail.getJobDataMap());

                        list.add(taskInfo);
                    }
                }
            }
        } catch (SchedulerException e) {
            log.error("获取任务列表异常", e);
        }
        return list;
    }


    public void addJob(final TaskInfo taskInfo) {
        final String jobName = taskInfo.jobClass();
        final String jobGroup = taskInfo.getJobGroup();
        final String cronExpression = taskInfo.getCronExpression();
        final String jobDescription = taskInfo.getJobDescription();
        final Date createTime = taskInfo.getCreateTime();

        try {
            if (checkExists(jobName, jobGroup)) {
                log.info("==> AddJob failure, job already exist, jobGroup: {} , jobName: {}", jobGroup, jobName);
                throw new ServiceException("");
            }
        } catch (SchedulerException | ClassNotFoundException e) {

        }
    }

    public boolean checkExists(String jobGroup, String jobName) {

    }
}
