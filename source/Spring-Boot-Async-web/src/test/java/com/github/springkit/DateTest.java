package com.github.springkit;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * Created by YT on 2018/5/10.
 */
public class DateTest {
    @Test
    public void go_test() throws InterruptedException {


//        System.out.println(System.currentTimeMillis());
//        System.out.println(System.currentTimeMillis());
//        System.out.println(DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS"));
        Date date = DateUtils.addMinutes(new Date(), 2);
        System.out.println(date.toString());
//        Thread.sleep(5000);
        Date date1 = new Date();
        System.out.println(date1);
        //int isExpire=date.compareTo(date1);
        //System.out.println(isExpire);
        System.out.println(DateUtils.truncatedCompareTo(date, date1, 12));

        System.out.println(UUID.randomUUID().toString());
        System.out.println(String.format("%s-%s", 2, 4));
    }
}
