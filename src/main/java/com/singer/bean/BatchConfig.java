package com.singer.bean;

import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections4.map.HashedMap;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.singer.batch.DeleteFileBatch;
import com.singer.util.PropertyUtil;

@Configuration
public class BatchConfig {

	@Inject
	private PropertyUtil propertyUtil;

	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean() {
		JobDetailFactoryBean jobBean = new JobDetailFactoryBean();
		jobBean.setJobClass(DeleteFileBatch.class);
		jobBean.setDurability(true);
		Map<String, Object> jobDataAsMap = new HashedMap<>();
		jobDataAsMap.put("propertyUtil", propertyUtil);
		jobBean.setJobDataAsMap(jobDataAsMap);
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
