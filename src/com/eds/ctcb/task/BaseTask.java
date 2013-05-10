package com.eds.ctcb.task;

import org.springframework.core.NestedRuntimeException;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.core.task.TaskTimeoutException;

/**
 * 
 * @author gzb5dy
 * 
 */
public abstract class BaseTask implements Runnable {

	private TaskExecutor taskExecutor;
	
	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	/**
	 * This method will be implemented by the TargetMethod which is declared in the task.xml.
	 * And when call the method named exectue() and pass the param of 'this', the method run()
	 * will be implemented.
	 */
	public void inProcess() {
		taskExecutor.execute(this);
	}
	
	/**
	 * Perform the particular business logic through the method named process() and catch the exception.
	 */
	public void run() {
		try {
			process();
		} catch (TaskTimeoutException e) {
			e.printStackTrace();
		} catch (TaskRejectedException e) {
			e.printStackTrace();
		} catch (NestedRuntimeException e) {
			e.printStackTrace();
		}

	}

	/**
	 * The abstract method named process() is waiting for implement by the subclasses.
	 */
	public abstract void process();

	

	

	
}
