package cn.cici.frigate.fescar.filter;

import cn.cici.frigate.fescar.config.FescarAutoConfiguration;
import com.alibaba.fescar.core.context.RootContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/16 13:58
 * @author: Heyfan Xie
 */
public class FescarRMRequestFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(FescarRMRequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String currentXID = request.getHeader(FescarAutoConfiguration.FESCAR_XID);
        if (!StringUtils.isEmpty(currentXID)) {
            RootContext.bind(currentXID);
            log.info("current request bind xid: {}", currentXID);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            String unbindXID = RootContext.unbind();
            if (unbindXID != null) {
                log.info("current request unbind XID: {}", unbindXID);
                if (!currentXID.equals(unbindXID)) {
                    log.info("XID is changed when request execute, check if it meets expectations please.");
                }
            }
            if (currentXID != null) {
                log.info("XID is changed when request execute, check if it meets expectations please");
            }
        }
    }
}
