package net.samagames.core;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class TasksExecutor implements Runnable {

	private LinkedBlockingQueue<PendingTask> pending = new LinkedBlockingQueue<>();

	public void addTask(PendingTask message) {
		pending.add(message);
	}

	@Override
	public void run() {
		while (true) {
			try {
				pending.take().run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static interface PendingTask {

		public void run();

	}

}
