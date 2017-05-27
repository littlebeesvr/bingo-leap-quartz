package leap.quartz;

public interface SchedulerConfig {

	/**
	 * 是否启用调试
	 * @return
	 */
	boolean isEnabled();
	
	/**
	 * 设置调度器名称
	 * @return
	 */
	String getSchedulerInstanceName();
	
	/**
	 * 线程池的实现类（一般使用SimpleThreadPool即可满足几乎所有用户的需求） 
	 * @return
	 */
	String getThreadPoolClass();
	
	/**
	 * 指定线程数，至少为1（无默认值）(一般设置为1-100直接的整数合适)  
	 * @return
	 */
	int getThreadPoolThreadCount();
	
	/**
	 * 设置线程的优先级（最大为java.lang.Thread.MAX_PRIORITY 10，最小为Thread.MIN_PRIORITY 1，默认为5）
	 * @return
	 */
	int getThreadPoolThreadPriority();
	
	/**
	 * 保存job和Trigger的状态信息到内存中的类 
	 * @return
	 */
	String getJobStoreClass();
}
