package com.journalbuddy.DataFilter;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

import com.journalbuddy.JournalDatabase.JournalData;

public class ConcurrentSearch {

	public static List<JournalData> findAll(JournalData[] data, List<FilterData> filters, int size) {
		List<JournalData> results;

		TaskManager manager = new TaskManager();
		ListTask task = new ListTask(data, 0, data.length, manager, size, filters);
		ForkJoinPool.commonPool().execute(task);
		try {
			results = task.join();
			return results;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;

	}

}
