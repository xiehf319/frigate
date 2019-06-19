package cn.cici.frigate.job.service;

import cn.cici.frigate.component.exception.ServiceException;
import cn.cici.frigate.job.pojo.TaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        final String createTime = new DateTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));

        try {
            if (checkExists(jobName, jobGroup)) {
                log.info("==> AddJob failure, job already exist, jobGroup: {} , jobName: {}", jobGroup, jobName);
                throw new ServiceException("4000", String.format("Job 已经存在了, jobGroup: {%s} JobName: {%s}", jobGroup, jobName));
            }
            final TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            final CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
                    .withMisfireHandlingInstructionDoNothing();
            final CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withDescription(createTime)
                    .withSchedule(cronScheduleBuilder)
                    .build();
            final Class<? extends Job> clazz = Class.forName(jobName).asSubclass(Job.class);
            final JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            final JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey)
                    .withDescription(jobDescription)
                    .usingJobData(taskInfo.getJobDataMap())
                    .build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException | ClassNotFoundException e) {
            throw new ServiceException("4001", "类名不存在或执行表达式错误");
        }
    }


    public void editJob(final TaskInfo taskInfo) {
        final String jobName = taskInfo.jobClass();
        final String jobGroup = taskInfo.getJobGroup();
        final String cronExpression = taskInfo.getCronExpression();
        final String jobDescription = taskInfo.getJobDescription();
        final String createTime = new DateTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));

        try {
            if (!checkExists(jobGroup, jobName)) {
                log.info("===> EditJob failure, job already exist. JobGroup: {}, jobName: {}", jobGroup, jobName);
                throw new ServiceException("4002",
                        String.format("Job不存在, jobGroup: {%s} jobName: {%s}", jobGroup, jobName));
            }
            final TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            final CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
                    .withMisfireHandlingInstructionDoNothing();
            final CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                    .withDescription(createTime).withSchedule(cronScheduleBuilder).build();
            final JobKey jobKey = new JobKey(jobName, jobGroup);
            final JobBuilder jobBuilder = scheduler.getJobDetail(jobKey).getJobBuilder();
            final JobDetail jobDetail = jobBuilder.usingJobData(taskInfo.getJobDataMap())
                    .withDescription(jobDescription).build();
            final Set<Trigger> triggerSet = new HashSet<>();
            triggerSet.add(cronTrigger);

            scheduler.scheduleJob(jobDetail, triggerSet, true);
        } catch (SchedulerException e) {
            throw new ServiceException("4003", "修改定时任务失败");
        }
    }

    public void deleteJob(final String jobName, final String jobGroup) {
        final TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        try {
            if (checkExists(jobName, jobGroup)) {
                scheduler.pauseTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
                log.info("==> delete success, triggerKey: {}", triggerKey);
            }
        } catch (SchedulerException e) {
            throw new ServiceException("4004", e.getMessage());
        }
    }

    public void pauseJob(final String jobName, final String jobGroup) {
        final TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        try {
            if (checkExists(jobName, jobGroup)) {
                scheduler.pauseTrigger(triggerKey);
                log.info("==> pause success, triggerKey: {}", triggerKey);
            }
        } catch (SchedulerException e) {
            throw new ServiceException("4005", e.getMessage());
        }
    }

    public void resumeJob(final String jobName, final String jobGroup) {
        final TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        try {
            if (checkExists(jobName, jobGroup)) {
                scheduler.resumeTrigger(triggerKey);
                log.info("==> resume success, triggerKey: {}", triggerKey);
            }
        } catch (SchedulerException e) {
            throw new ServiceException("4006", e.getMessage());
        }
    }


    private boolean checkExists(String jobGroup, String jobName) throws SchedulerException{
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        return scheduler.checkExists(triggerKey);
    }
}
