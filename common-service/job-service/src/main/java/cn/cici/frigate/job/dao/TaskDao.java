package cn.cici.frigate.job.dao;

import cn.cici.frigate.job.pojo.TaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @description:
 * @createDate:2019/6/20$11:11$
 * @author: Heyfan Xie
 */
@Repository
@Slf4j
public class TaskDao {


    @PersistenceContext
    private EntityManager entityManager;

    public Page<TaskInfo> findPageList(final Pageable pageable, final String jobGroup) {
        String conditionJobGroup = "";
        if (!StringUtils.isEmpty(jobGroup)) {
            conditionJobGroup = " AND d.JOB_GROUP=\"" + jobGroup + "\"";
        }

        final String sql = "SELECT "
                + " d.JOB_GROUP as jobGroup,"
                + " d.JOB_NAME as jobName,"
                + " d.DESCRIPTION as jobDescription,"
                + " c.CRON_EXPRESSION as cronExpression"
                + " FROM "
                + " QRTZ_JOB_DETAILS d, QRTZ_TRIGGERS t, QRTZ_CRON_TRIGGERS c "
                + " WHERE "
                + " d.SCHED_NAME = t.SCHED_NAME "
                + " AND d.JOB_GROUP = t.JOB_GROUP "
                + " AND d.JOB_NAME = t.JOB_NAME "
                + " AND t.SCHED_NAME = c.SCHED_NAME "
                + " AND t.TRIGGER_GROUP = c.TRIGGER_GROUP "
                + " AND t.TRIGGER_NAME = c.TRIGGER_NAME "
                + conditionJobGroup
                + " ORDER BY t.START_TIME DESC";
        final String sqlCount = "SELECT COUNT(1) AS countQuery FROM QRTZ_JOB_DETAILS d WHERE 1 = 1 " + conditionJobGroup;
        log.info("sql: {}", sql);
        final Query countQuery = entityManager.createNativeQuery(sqlCount);
        countQuery.unwrap(NativeQueryImpl.class).addScalar("countQuery", StandardBasicTypes.LONG);
        final Long count = (Long) countQuery.getSingleResult();

        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(TaskInfo.class));
        query.setFirstResult(pageable.getPageNumber());
        query.setMaxResults(pageable.getPageSize());
        return new PageImpl<TaskInfo>(query.getResultList(), pageable, count);
    }
}
