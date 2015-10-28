package org.obicere.bcviewer.startup;

import org.obicere.bcviewer.context.Domain;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.logging.Level;

/**
 * @author Obicere
 */
public class StartUpQueue {

    public static final int HIGHEST_PRIORITY = Integer.MIN_VALUE;

    public static final int HIGH_PRIORITY = Short.MIN_VALUE;

    public static final int STANDARD_PRIORITY = 0;

    public static final int LOW_PRIORITY = Short.MAX_VALUE;

    public static final int LOWEST_PRIORITY = Integer.MAX_VALUE;

    private final PriorityQueue<StartUpTask> queue = new PriorityQueue<>(new TaskComparator());

    public void runTasks(final Domain domain) {
        queue.forEach(task -> {
            try {
                task.call(domain);
            } catch (final Throwable e) {
                domain.getLogger().log(Level.SEVERE, e.getMessage(), e);
            }
        });
        queue.clear();
    }

    public void provide(final StartUpTask task) {
        if (task == null) {
            throw new NullPointerException("Cannot run a null task.");
        }
        queue.offer(task);
    }

    private class TaskComparator implements Comparator<StartUpTask> {

        @Override
        public int compare(final StartUpTask o1, final StartUpTask o2) {
            if (o1 == null) {
                if (o2 == null) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if (o2 == null) {
                    return -1;
                } else {
                    return Integer.compare(o1.getPriority(), o2.getPriority());
                }
            }
        }
    }

}
