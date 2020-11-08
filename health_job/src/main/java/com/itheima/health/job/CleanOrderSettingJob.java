package com.itheima.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.service.OrderSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * @Author LiuFeng
 * @Date 2020/11/8 10:49
 */
@Component
public class CleanOrderSettingJob {
    private static final Logger log = LoggerFactory.getLogger(CleanOrderSettingJob.class);
    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 定期清理过期预约信息
     * @throws ParseException
     */
    /*fixedDelay 执行间隔  initialDelay  延时多久执行*/
    @Scheduled(fixedDelay = 10000, initialDelay = 3000)
    public void cleanOrderSet() throws ParseException {
        long timeMillis = System.currentTimeMillis();
        Date nowDate = new Date(timeMillis);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String parseDate = format.format(nowDate);
        Date parse = format.parse(parseDate);
        log.debug("开始清除" + parseDate + "之前的信息");
        int cleanResult = orderSettingService.cleanOrderByDate(parse);
        log.debug("已清除" + cleanResult + "条数据");
    }
}
