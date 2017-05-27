package leap.quartz;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import leap.core.BeanFactory;
import leap.core.ioc.PostCreateBean;

public class CronTaskBean implements TaskBean, PostCreateBean {

	private String name;

	private String group;
	
	private Object targetObject;
	
	private String targetMethod;

	private JobDetail jobDetail;

	private Date startTime;

	private long startDelay;

	private String cronExpression;

	private int priority = Trigger.DEFAULT_PRIORITY;

	private String beanName;

	private CronTrigger cronTrigger;

	@Override
	public JobDetail getJobDetail() {
		return jobDetail;
	}

	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setStartDelay(long startDelay) {
		this.startDelay = startDelay;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}

	@Override
	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public Trigger getTrigger() {
		return cronTrigger;
	}

	@Override
	public void postCreate(BeanFactory factory) throws Throwable {
		if (this.name == null) {
			this.name = this.beanName;
		}
		if (this.group == null) {
			this.group = Scheduler.DEFAULT_GROUP;
		}
		if (this.startDelay > 0) {
			this.startTime = new Date(System.currentTimeMillis() + this.startDelay);
		} else if (this.startTime == null) {
			this.startTime = new Date();
		}

		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("targetObject", this.targetObject);
		jobDataMap.put("targetMethod", this.targetMethod);

		this.jobDetail = JobBuilder.newJob(MethodInvokeJobBean.class).withIdentity(this.name, this.group).setJobData(jobDataMap).build();

		this.cronTrigger = TriggerBuilder.newTrigger().withIdentity(this.name + "_cronTrigger", this.group + "_triggerGroup")
				.withSchedule(CronScheduleBuilder.cronSchedule(this.cronExpression)).startAt(this.startTime).withPriority(this.priority).build();
	}

}
