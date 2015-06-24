package org.obicere.bcviewer;

import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Obicere
 */
public class StartUpQueue {

    public static final int HIGHEST_PRIORITY = Integer.MIN_VALUE;

    public static final int HIGH_PRIORITY = Short.MIN_VALUE;

    public static final int STANDARD_PRIORITY = 0;

    public static final int LOW_PRIORITY = Short.MAX_VALUE;

    public static final int LOWEST_PRIORITY = Integer.MAX_VALUE;

    private final PriorityQueue<StartUpTask> queue = new PriorityQueue<>();

    void runTasks(final Logger errorLogger){
        queue.forEach(task -> {
            try {
                task.getTask().run();
            } catch (final Throwable e){
                errorLogger.log(Level.SEVERE, e.getMessage(), e);
            }
        });
    }

    void cleanUp(){
        queue.clear();
    }

    public void provide(final Runnable task){
        provide(task, STANDARD_PRIORITY);
    }

    public void provide(final Runnable task, final int priority){
        if(task == null){
            throw new NullPointerException("Cannot run a null task.");
        }
        queue.offer(new StartUpTask(task, priority));
    }

    private class StartUpTask implements Comparable<StartUpTask> {

        private final int priority;

        private final Runnable task;

        public StartUpTask(final Runnable task, final int priority){
            this.priority = priority;
            this.task = task;
        }

        public int getPriority(){
            return priority;
        }

        public Runnable getTask(){
            return task;
        }

        @Override
        public int compareTo(final StartUpTask other){
            if(other == null){
                return 1;
            }
            return Integer.compare(priority, other.getPriority());
        }

    }

}
