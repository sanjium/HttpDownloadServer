package com.download.aop;

import com.alibaba.fastjson.JSON;
import com.download.entity.domain.Log;
import com.download.server.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


@Component
@Aspect
@Slf4j
public class LogAspect {

    final LogService logService;

    @Autowired
    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    @Pointcut("@annotation(com.download.aop.LogAnnotation)")
    public void pt() {
    }

    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Log myLog = new Log();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result;
        try {
            result = joinPoint.proceed();
            stopWatch.stop();
            long runTime = stopWatch.getTotalTimeMillis();
            myLog.setType("info");
            recordLog(joinPoint, myLog, runTime);
        } catch (Throwable e) {
            myLog.setType("error");
            myLog.setErrMsg(e.getMessage());
            stopWatch.stop();
            long runTime = stopWatch.getTotalTimeMillis();
            recordLog(joinPoint, myLog, runTime);
            throw e;
        } finally {
            // 将保存日志的操作异步执行
            CompletableFuture.runAsync(() -> {
                logService.save(myLog);
            });
        }
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, Log myLog, long runTime) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);

        log.info("===============log start===============");
        log.info("module:{}", logAnnotation.module());
        myLog.setModule(logAnnotation.module());
        log.info("operation:{}", logAnnotation.operation());
        myLog.setOperation(logAnnotation.operation());

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("请求接口:{}", className + "." + methodName + "()");
        myLog.setMethod(className + "." + methodName + "()");

        //请求的方法和路径
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String requestMethod = request.getMethod();
        log.info("请求方法：{}", requestMethod);
        myLog.setRequestMethod(requestMethod);
        String requestURI = request.getRequestURI();
        log.info("请求地址:{}", requestURI);
        myLog.setPath(requestURI);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params;
        if (args.length == 0) {
            params = "无参数";
        } else {
            params = JSON.toJSONString(args[0]);
        }
        log.info("请求参数:{}", params);
        myLog.setParams(params);

        //获取ip地址

        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.warn(e.getMessage(), e);
                }
                if (inet != null) {
                    ipAddress = inet.getHostAddress();
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }

        log.info("ip:{}", ipAddress);
        myLog.setIp(ipAddress);
        log.info("执行时间: {} ms", runTime);
        myLog.setRunTime(runTime + "ms");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info("请求完成时间:{}", LocalDateTime.now().format(dateTimeFormatter));
        myLog.setCreateTime(LocalDateTime.now().format(dateTimeFormatter));
        log.info("==============log end===============");
    }


}
