package ru.geekbrains.smartkt.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.*;

// 2.3. С помощью Spring AOP посчитайте по каждому сервису суммарное время,
//      затрачиваемое на выполнение методов этих сервисов.
@Aspect
@Component
public class AOPStatistics {
    final String[] SERVICE_NAME = { "Product", "Customer", "Order" };
    final String SERVICE = "Service";
    private final List<Long> summaryTime = new ArrayList<>();

    private int getServiceCode(String serviceName) {
        final int PRODUCT_SERVICE = 1, CUSTOMER_SERVICE = 2, ORDER_SERVICE = 3;
        int i = 0;
        if (serviceName.endsWith(SERVICE)) {
            String s = serviceName.substring(0, serviceName.length()-SERVICE.length());
            if (s.equals(SERVICE_NAME[0])) i = PRODUCT_SERVICE;
            if (s.equals(SERVICE_NAME[1])) i = CUSTOMER_SERVICE;
            if (s.equals(SERVICE_NAME[2])) i = ORDER_SERVICE;
        }
        return i;
    }

    // обновить время выполения
    // любого публичного метода с любым числом аргументов любого из сервисрв
    @Around("execution(public * *.services.*(..))")
    public Object refreshExecutionTimes(ProceedingJoinPoint p) throws Throwable {
        System.out.println(p.getSignature().getName()+" is being executed");
        long started = System.nanoTime();
        Object out = p.proceed();
        long finished = System.nanoTime();
        int i = getServiceCode(p.getSignature().getDeclaringTypeName());
        if (i > 0)
            if (summaryTime.get(i) == null)
                summaryTime.add(finished - started);
            else
                summaryTime.set(i, summaryTime.get(i) + finished - started);
        return out;
    }

    // получить общее время выполнения методов сервиса
    public Long getServiceExecutionTimes(String serviceName) {
        int i = getServiceCode(serviceName);
        return i == 0 || summaryTime.size() == 0 || summaryTime.get(i) == null ? 0 : summaryTime.get(i);
    }

    // сформировать отчет о времени выполнения методов сервисов
    public String getServicesExecutionTimes() {
        StringBuilder sb = new StringBuilder();
        for (String s : SERVICE_NAME) {
            Long time = getServiceExecutionTimes(s + SERVICE);
            sb.append(s).append(SERVICE).append(": ")
              .append(time.equals(0L) ? "not executed" : time);
            if (time > 0L) sb.append("ms");
            sb.append("\n");
        }
        return sb.substring(0, sb.length()-1);
    }
}