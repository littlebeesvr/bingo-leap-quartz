package leap.quartz;

public interface SchedulerConfigurator {

	SchedulerConfigurator setEnabled(boolean enabled);

	SchedulerConfigurator setSchedulerInstanceName(String scheduler_instanceName);

	SchedulerConfigurator setThreadPoolThreadCount(int threadPool_threadCount);

	SchedulerConfigurator setThreadPoolClass(String threadPool_class);

	SchedulerConfigurator setThreadPoolThreadPriority(int threadPool_threadPriority);

	SchedulerConfigurator setJobStoreClass(String jobStore_class);

}
