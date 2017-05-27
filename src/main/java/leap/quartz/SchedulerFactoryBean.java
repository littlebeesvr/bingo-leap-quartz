package leap.quartz;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.StdSchedulerFactory;

import leap.core.BeanFactory;
import leap.core.annotation.Inject;
import leap.core.annotation.M;
import leap.core.ioc.PostCreateBean;
import leap.lang.logging.Log;
import leap.lang.logging.LogFactory;

public class SchedulerFactoryBean implements PostCreateBean {

	private static final Log log = LogFactory.get(SchedulerFactoryBean.class);

	protected @Inject @M SchedulerConfig config;

	protected List<TaskBean> tasks = new ArrayList<>();

	public void setTasks(List<TaskBean> tasks) {
		this.tasks = tasks;
	}

	@Override
	public void postCreate(BeanFactory beanFactory) throws Throwable {
		if (!config.isEnabled()) {
			log.debug("---------- quartz scheduer状态禁用 ----------");
			return;
		}
		log.debug("---------- quartz scheduer 开始启用 ----------");

		if (tasks != null || tasks.size() > 0) {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Properties properties = new Properties();
			properties.setProperty(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, config.getThreadPoolClass());
			properties.setProperty(StdSchedulerFactory.PROP_THREAD_POOL_PREFIX + ".threadCount", Integer.toString(config.getThreadPoolThreadCount()));
			properties.setProperty(StdSchedulerFactory.PROP_THREAD_POOL_PREFIX + ".threadPriority",
					Integer.toString(config.getThreadPoolThreadPriority()));
			properties.setProperty(StdSchedulerFactory.PROP_JOB_STORE_CLASS, config.getJobStoreClass());

			((StdSchedulerFactory) schedulerFactory).initialize(properties);

			Scheduler scheduler = this.createScheduler(schedulerFactory, config.getSchedulerInstanceName());

			for (TaskBean taskBean : tasks) {
				scheduler.scheduleJob(taskBean.getJobDetail(), taskBean.getTrigger());
			}
			scheduler.startDelayed(2);
			log.info("---------- quartz scheduler 启动完成 ----------");
		}else{
			log.info("---------- quartz scheduler 未配置job，放弃启动 ----------");
		}
	}

	protected Scheduler createScheduler(SchedulerFactory schedulerFactory, String schedulerName) throws SchedulerException {
		try {
			SchedulerRepository repository = SchedulerRepository.getInstance();
			synchronized (repository) {
				Scheduler existingScheduler = (schedulerName != null ? repository.lookup(schedulerName) : null);
				Scheduler newScheduler = schedulerFactory.getScheduler();
				if (newScheduler == existingScheduler) {
					throw new IllegalStateException("Active Scheduler of name '" + schedulerName + "' already registered "
							+ "in Quartz SchedulerRepository. Cannot create a new Spring-managed Scheduler of the same name!");
				}
				SchedulerRepository.getInstance().remove(newScheduler.getSchedulerName());
				return newScheduler;
			}
		} finally {
		}
	}
}
