package com.singer.bean;

import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.singer.batch.DeleteFileBatch;

@Configuration
public class BatchConfig {

	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean() {
		JobDetailFactoryBean jobBean = new JobDetailFactoryBean();
		jobBean.setJobClass(DeleteFileBatch.class);
		return jobBean;
	}

	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBean() {
		CronTriggerFactoryBean cronBean = new CronTriggerFactoryBean();
		cronBean.setJobDetail(jobDetailFactoryBean().getObject());
		cronBean.setCronExpression("0 0/30 * * * ?");
		return cronBean;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean scheduleBean = new SchedulerFactoryBean();
		Trigger[] triggers = new Trigger[1];
		triggers[0] = cronTriggerFactoryBean().getObject();
		scheduleBean.setTriggers(triggers);
		return scheduleBean;
	}
}
