package org.bdp.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import ch.qos.logback.classic.selector.servlet.LoggerContextFilter;

@Component
public class RequestFilter extends ZuulFilter{

	private static final Logger logger = LoggerFactory.getLogger(LoggerContextFilter.class);
	
	/**
     * 返回boolean类型。代表当前filter是否生效。
     * 默认值为false。
     * 返回true代表开启filter。
     */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// 通过zuul，获取请求上下文
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();

        logger.info("RequestFilter.....method={},url={}",
                request.getMethod(),request.getRequestURL().toString());
        // 可以记录日志、鉴权，给维护人员记录提供定位协助、统计性能
        return null;
	}

	/**
     * 过滤器的类型。可选值有：
     * pre - 前置过滤
     * route - 路由后过滤
     * error - 异常过滤
     * post - 远程服务调用后过滤
     */
	@Override
	public String filterType() {
		return "pre";
	}

	/**
     * 同种类的过滤器的执行顺序。
     * 按照返回值的自然升序执行。
     */
	@Override
	public int filterOrder() {
		return 0;
	}

}
