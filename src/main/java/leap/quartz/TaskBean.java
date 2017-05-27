package leap.quartz;

import org.quartz.JobDetail;
import org.quartz.Trigger;

public interface TaskBean {
	
	JobDetail getJobDetail();
	
	Trigger getTrigger();
	
	void setName(String name);
	
	void setTargetObject(Object targetObject);
	
	void setTargetMethod(String targetMethod);
}
