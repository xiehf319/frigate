package cn.cici.frigate.job.controller;

import cn.cici.frigate.component.exception.ServiceException;
import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.job.pojo.TaskInfo;
import cn.cici.frigate.job.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @createDate:2019/6/20$11:07$
 * @author: Heyfan Xie
 */
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/list")
    public R<Page<TaskInfo>> getAll() {
        Pageable pageable = PageRequest.of(1, 10);
        Page<TaskInfo> page = taskService.getList(pageable, "");
        return R.success(page);
    }

    @PostMapping
    public R createJob(@RequestBody TaskInfo taskInfo) {
        taskService.addJob(taskInfo);
        return R.success();
    }

    /**
     * 测试一下国际化
     * 通过 lang=zh_CN  /   en_US 切换结果
     * @return
     */
    @GetMapping("/test2")
    public R i18nTest2() {
        throw new ServiceException("4002", new Object[]{"张三", 1233444}, "default value");
    }
}
