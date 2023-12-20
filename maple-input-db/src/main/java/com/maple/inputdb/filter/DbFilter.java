package com.maple.inputdb.filter;

import com.maple.inputdb.config.DbStatusSingleton;
import com.maple.inputdb.config.DynamicDatasourceConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/3/23
 */
@WebFilter(filterName = "dbFilter", urlPatterns = "/*")
@Order(1)
@Slf4j
@AllArgsConstructor
public class DbFilter implements Filter {

    private final DynamicDatasourceConfig datasourceConfig;

    private final List<String> excludedUrlList;

    @Override
    public void init(FilterConfig filterConfig) {
        excludedUrlList.addAll(Collections.singletonList(
                "/init/initData"
        ));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String url = ((HttpServletRequest) request).getRequestURI();
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        boolean isMatch = false;
        for (String excludedUrl : excludedUrlList) {
            if (Pattern.matches(excludedUrl.replace("*", ".*"), url)) {
                isMatch = true;
                break;
            }
        }
        if (isMatch) {
            chain.doFilter(request, response);
        } else {
            boolean isOk = DbStatusSingleton.getInstance().getDbStatus() || datasourceConfig.checkDataSource();
            if (isOk) {
                chain.doFilter(request, response);
            } else {
                log.error("初始化系统失败，请先进行系统配置");
                writeRsp(httpServletResponse);
            }
        }
    }

    private void writeRsp(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setHeader("content-type", "application/json;charset=UTF-8");
        try {
            response.getWriter().println("初始化系统失败，请先进行系统配置");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
