package xyz.fengqing11.springbootquartz.config;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.SimpleTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.quartz.*;
import xyz.fengqing11.springbootquartz.job.MySecondJob;

@Configuration
public class QuartzConfig {
    @Bean
    MethodInvokingJobDetailFactoryBean jobDetailFactoryBean1(){
        MethodInvokingJobDetailFactoryBean bean =
                new MethodInvokingJobDetailFactoryBean();
        bean.setTargetBeanName("myFirstJob");
        bean.setTargetMethod("sayHello");
        return bean;
    }
    @Bean
    JobDetailFactoryBean jobDetailFactoryBean2(){
        JobDetailFactoryBean bean =
                new JobDetailFactoryBean();
        bean.setJobClass(MySecondJob.class);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("name","sang");
        bean.setJobDataMap(jobDataMap);
        return bean;
    }

    @Bean
    SimpleTriggerFactoryBean simpleTrigger(){
        SimpleTriggerFactoryBean bean =
                new SimpleTriggerFactoryBean();
        bean.setJobDetail(jobDetailFactoryBean1().getObject());
        bean.setRepeatCount(3);
        bean.setStartDelay(1000);
        bean.setRepeatInterval(2000);
        return bean;
    }

    @Bean
    CronTriggerFactoryBean cronTrigger(){
        CronTriggerFactoryBean bean =
                new CronTriggerFactoryBean();
        bean.setJobDetail(jobDetailFactoryBean2().getObject());
        bean.setCronExpression("* * * * * ?");
        return bean;
    }

    @Bean
    SchedulerFactoryBean schedulerFactory(){
        SchedulerFactoryBean bean =
                new SchedulerFactoryBean();
        SimpleTrigger simpleTrigger = simpleTrigger().getObject();
        CronTrigger cronTrigger = cronTrigger().getObject();
        bean.setTriggers(simpleTrigger,cronTrigger);
        return bean;
    }

}
