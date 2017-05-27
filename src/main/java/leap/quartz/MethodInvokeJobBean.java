package leap.quartz;

import org.apache.commons.beanutils.MethodUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MethodInvokeJobBean implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		Object targetObject = jobDataMap.get("targetObject");
		String targetMethod = jobDataMap.getString("targetMethod");
		try {
			MethodUtils.invokeMethod(targetObject, targetMethod, null);
		} catch (Exception ex) {
			throw new JobExecutionException("quartz task调起任务失败", ex);
		}
	}

}
