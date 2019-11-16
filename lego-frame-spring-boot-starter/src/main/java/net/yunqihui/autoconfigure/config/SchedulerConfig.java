package net.yunqihui.autoconfigure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulerConfig {
	/**
	 * 并行任务使用策略：多线程处理
	 *
	 * @return ThreadPoolTaskScheduler 线程池
	 */
	@Bean(destroyMethod = "shutdown", name = "taskScheduler")
	public ThreadPoolTaskScheduler taskScheduler() {
		// 创建一个线程池调度器
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		// 设置线程池容量
		scheduler.setPoolSize(20);
		// 线程名前缀
		scheduler.setThreadNamePrefix("task-");
		// 等待时常
		scheduler.setAwaitTerminationSeconds(60);
		// 当调度器shutdown被调用时等待当前被调度的任务完成
		scheduler.setWaitForTasksToCompleteOnShutdown(true);
		// 设置当任务被取消的同时从当前调度器移除的策略
		scheduler.setRemoveOnCancelPolicy(true);
		return scheduler;
	}

}
