package com.journalbuddy.DataFilter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.RecursiveTask;

import com.journalbuddy.JournalDatabase.JournalData;

public class ListTask extends RecursiveTask<List<JournalData>> {


	private JournalData data[];
	private int start, end, size;
	private List<FilterData> filters;
	private TaskManager manager;

	public ListTask(JournalData data[], int start, int end, TaskManager manager, int size, List<FilterData> filters) {
		this.data = data;
		this.start = start;
		this.end = end;
		this.size = size;
		this.filters = filters;
		this.manager = manager;
	}

	@Override
	protected List<JournalData> compute() {
		List<JournalData> ret = new ArrayList<JournalData>();
		List<JournalData> tmp;
		if (end - start <= size) {
			for (int i = start; i < end; i++) {
				JournalData journalData = data[i];
				try {
					if (Filter.filter(journalData, filters)) {
						ret.add(journalData);
					}
				} catch (ParseException e) {
					System.out.println("Parse Exception in ");
					e.printStackTrace();
				}
			}
		} else {
			int mid = (start + end) / 2;
			ListTask task1 = new ListTask(data, start, mid, manager, size, filters);
			ListTask task2 = new ListTask(data, mid, end, manager, size, filters);
			manager.addTask(task1);
			manager.addTask(task2);
			manager.deleteTask(this);
			task1.fork();
			task2.fork();
			task2.quietlyJoin();
			task1.quietlyJoin();
			manager.deleteTask(task1);
			manager.deleteTask(task2);

			try {
				tmp = task1.join();
				if (tmp != null)
					ret.addAll(tmp);
				manager.deleteTask(task1);
			} catch (CancellationException ex) {
			}
			try {
				tmp = task2.join();
				if (tmp != null)
					ret.addAll(tmp);
				manager.deleteTask(task2);
			} catch (CancellationException ex) {
			}
		}

		return ret;
	}

}
