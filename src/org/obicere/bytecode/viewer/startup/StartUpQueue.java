package org.obicere.bytecode.viewer.startup;

import org.obicere.bytecode.viewer.context.Domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author Obicere
 */
public class StartUpQueue {

    private final Map<Priority, List<StartUpTask>> priorityMap = new HashMap<>();

    public void runTasks(final Domain domain) {
        final Priority[] priorities = Priority.values();

        for (final Priority priority : priorities) {
            final List<StartUpTask> tasks = priorityMap.get(priority);
            if (tasks == null) {
                continue;
            }
            tasks.forEach(task -> {
                try {
                    task.call(domain);
                } catch (final Throwable e) {
                    domain.getLogger().log(Level.SEVERE, e.getMessage(), e);
                }
            });
            tasks.clear();
            priorityMap.remove(priority);
        }
    }

    public void provide(final StartUpTask task) {
        if (task == null) {
            throw new NullPointerException("task must be non-null.");
        }
        final Priority priority = task.getPriority();
        if (priority == null) {
            throw new NullPointerException("tasks must have a non-null priority.");
        }
        final List<StartUpTask> tasks = priorityMap.get(priority);
        // if the bucket hasn't been created yet
        if (tasks == null) {
            // initialize the bucket for this priority
            final List<StartUpTask> newTasks = new LinkedList<>();

            newTasks.add(task);
            priorityMap.put(priority, newTasks);
        } else {
            tasks.add(task);
        }
    }
}
