package leap.quartz;

import leap.core.AppConfig;
import leap.core.BeanFactory;
import leap.core.annotation.ConfigProperty;
import leap.core.annotation.Configurable;
import leap.core.ioc.PostConfigureBean;

@Configurable(prefix = "quartz")
public class DefaultSchedulerConfig implements SchedulerConfig, SchedulerConfigurator, PostConfigureBean {

	protected BeanFactory factory = null;
	protected boolean enabled = false;
	protected String schedulerInstanceName = "DefaultQuartzScheduler";
	protected String threadPoolClass = "org.quartz.simpl.SimpleThreadPool";
	protected int threadPoolThreadCount = 20;
	protected int threadPoolThreadPriority = Thread.NORM_PRIORITY;
	protected String jobStoreClass = "org.quartz.simpl.RAMJobStore";

	@Override
	@ConfigProperty
	public SchedulerConfigurator setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	@Override
	@ConfigProperty
	public SchedulerConfigurator setSchedulerInstanceName(String scheduler_instanceName) {
		this.schedulerInstanceName = scheduler_instanceName;
		return this;
	}

	@Override
	@ConfigProperty
	public SchedulerConfigurator setThreadPoolThreadCount(int threadPool_threadCount) {
		this.threadPoolThreadCount = threadPool_threadCount;
		return this;
	}

	@Override
	@ConfigProperty
	public SchedulerConfigurator setThreadPoolClass(String threadPool_class) {
		this.threadPoolClass = threadPool_class;
		return this;
	}

	@Override
	@ConfigProperty
	public SchedulerConfigurator setThreadPoolThreadPriority(int threadPool_threadPriority) {
		this.threadPoolThreadPriority = threadPool_threadPriority;
		return this;
	}

	@Override
	@ConfigProperty
	public SchedulerConfigurator setJobStoreClass(String jobStore_class) {
		this.jobStoreClass = jobStore_class;
		return this;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public String getSchedulerInstanceName() {
		return this.schedulerInstanceName;
	}

	@Override
	public String getThreadPoolClass() {
		return this.threadPoolClass;
	}

	@Override
	public int getThreadPoolThreadCount() {
		return this.threadPoolThreadCount;
	}

	@Override
	public int getThreadPoolThreadPriority() {
		return this.threadPoolThreadPriority;
	}

	@Override
	public String getJobStoreClass() {
		return this.jobStoreClass;
	}

	@Override
	public void postConfigure(BeanFactory factory, AppConfig config) throws Throwable {

	}

}
