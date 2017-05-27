package leap.quartz;

import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import leap.core.BeanFactory;
import leap.core.ioc.PostCreateBean;

public class SimpleTaskBean implements TaskBean, PostCreateBean {

	private String name;

	private String group;
	
	private Object targetObject;
	
	private String targetMethod;

	private JobDetail jobDetail;

	private Date startTime;

	/**
	 * 开始延迟，单位毫秒
	 */
	private long startDelay;

	/**
	 * 间隔，单位毫秒
	 */
	private long repeatInterval;

	private int priority = Trigger.DEFAULT_PRIORITY;

	private String beanName;

	private SimpleTrigger simpleTrigger;

	@Override
	public JobDetail getJobDetail() {
		return jobDetail;
	}

	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	@Override
	public Trigger getTrigger() {
		return simpleTrigger;
	}

	public void setSimpleTrigger(SimpleTrigger simpleTrigger) {
		this.simpleTrigger = simpleTrigger;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setStartDelay(long startDelay) {
		this.startDelay = startDelay;
	}

	@Override
	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}

	@Override
	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

	public void setRepeatInterval(long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
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

		this.simpleTrigger = TriggerBuilder.newTrigger().withIdentity(this.name + "_simpleTrigger", this.group + "_triggerGroup")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(this.repeatInterval).repeatForever()).startAt(startTime)
				.withPriority(this.priority).build();

	}

}
