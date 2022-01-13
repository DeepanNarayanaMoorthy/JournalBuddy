package com.journalbuddy.invertedindexing;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class InvertedIndexingMain {

	public static void GenerateInvertedIndex(String ConvertedPath, String OutputPath) {

		int numCores=Runtime.getRuntime().availableProcessors();
		ThreadPoolExecutor executor=(ThreadPoolExecutor)Executors.newFixedThreadPool(Math.max(numCores-1, 1));
		ExecutorCompletionService<List<Vocab>> completionService=new ExecutorCompletionService<>(executor);
		ConcurrentHashMap<String, StringBuffer> invertedIndex=new ConcurrentHashMap<String,StringBuffer> ();

//		"E:\\Research Papers\\New folder"
		File source = new File(ConvertedPath);
		File[] files = source.listFiles();
		System.out.print("\n");
		System.out.print(numCores);
		System.out.print("\n");
		System.out.print(files.length);
		System.out.print("\n");
		System.out.print(files.length/numCores);
		System.out.print("\n");
		
		final int NUMBER_OF_DOCUMENTS;
		if (files.length/numCores>1) {
			NUMBER_OF_DOCUMENTS=files.length/numCores;
		} else {
			NUMBER_OF_DOCUMENTS=2;
		}
		
		Date start, end;
		
		start=new Date();
		
		JournalInvertedIndexing invertedIndexTask=new JournalInvertedIndexing(completionService,invertedIndex);
		Thread thread1=new Thread(invertedIndexTask);
		thread1.start();
		JournalInvertedIndexing invertedIndexTask2=new JournalInvertedIndexing(completionService,invertedIndex);
		Thread thread2=new Thread(invertedIndexTask2);
		thread2.start();
		
		List<File> taskFiles=new ArrayList<>();
		for (File file : files) {
			taskFiles.add(file);

			if (taskFiles.size()==NUMBER_OF_DOCUMENTS) {
				JournalIndexing task=new JournalIndexing(taskFiles);
				completionService.submit(task);
				taskFiles=new ArrayList<>();
			}
			
			if (executor.getQueue().size()>10) {
				do {
					try {
						TimeUnit.MILLISECONDS.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} while (executor.getQueue().size()>10);
			}
		}
		
		if (taskFiles.size()>0) {
			JournalIndexing task=new JournalIndexing(taskFiles);
			completionService.submit(task);
		}

		
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
			thread1.interrupt();
			thread2.interrupt();
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		end=new Date();
		System.out.println("Execution Time: "+(end.getTime()-start.getTime()));
		System.out.println("invertedIndex: "+invertedIndex.size());
		System.out.println(invertedIndex.get("book").length());
		thread1.interrupt();
		thread2.interrupt();
//		"E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\op.txt"
		String outputFilePath=OutputPath;
		File file = new File(outputFilePath);
		BufferedWriter bf = null;
		try {
			bf = new BufferedWriter(new FileWriter(file));
			for (Entry<String, StringBuffer> entry :
				invertedIndex.entrySet()) {
				bf.write(entry.getKey() + ":"
						+ entry.getValue());
				bf.newLine();
			}

			bf.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {

			try {
				bf.close();
			}
			catch (Exception e) {
			}
		}


	}

}



