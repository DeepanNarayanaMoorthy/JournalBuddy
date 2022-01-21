package com.journalbuddy.DataFilter;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.RecursiveTask;

import com.journalbuddy.DataFilter.Filter;
import com.journalbuddy.DataFilter.FilterData;
import com.journalbuddy.JournalDatabase.JournalData;

public class IndividualTask extends RecursiveTask<JournalData> {

	/**
	 * 
	 */
//	private static final long serialVersionUID = -4106884399809219741L;

	private JournalData[] data;
	private int start, end, size;
	private TaskManager manager;
	private List<FilterData> filters;

	public IndividualTask(JournalData[] data, int start, int end, TaskManager manager, int size,
			List<FilterData> filters) {
		this.data = data;
		this.start = start;
		this.end = end;
		this.manager = manager;
		this.size = size;
		this.filters = filters;
	}

	@Override
	protected JournalData compute() {
		if (end - start <= size) {
			for (int i = start; i < end && !Thread.currentThread().isInterrupted(); i++) {
				JournalData journalData = data[i];
				try {
					if (Filter.filter(journalData, filters)) {
						System.out.println("Found: " + i);
						manager.cancelTasks(this);
						return journalData;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		} else {
			int mid = (start + end) / 2;
			IndividualTask task1 = new IndividualTask(data, start, mid, manager, size, filters);
			IndividualTask task2 = new IndividualTask(data, mid, end, manager, size, filters);
			manager.addTask(task1);
			manager.addTask(task2);
			manager.deleteTask(this);
			task1.fork();
			task2.fork();
			task1.quietlyJoin();
			task2.quietlyJoin();
			manager.deleteTask(task1);
			manager.deleteTask(task2);

			try {
				JournalData res = task1.join();
				if (res != null)
					return res;
				manager.deleteTask(task1);
			} catch (CancellationException ex) {
			}
			try {
				JournalData res = task2.join();
				if (res != null)
					return res;
				manager.deleteTask(task2);
			} catch (CancellationException ex) {
			}
			return null;
		}
	}

}
