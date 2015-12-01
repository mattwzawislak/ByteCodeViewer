package org.obicere.bytecode.viewer.startup;

import org.obicere.bytecode.viewer.context.Domain;

/**
 * Represents a single task to be ran at start up. It should be assumed
 * that any and all tasks are optional. This means that by the user's
 * discretion, certain tasks may not be ran. Anything crucial to the
 * execution of the program should be part of the boot sequence, which is
 * not currently modifiable without providing an entirely separate boot.
 * <p>
 * The task itself is rather simple. Each task is given access to the
 * global domain. The domain will be non-<code>null</code> and be fully
 * initialized as of calling the task. Therefore everything within the
 * domain should be fully accessible.
 * <p>
 * Tasks generally should not:
 * <ul>
 * <li> Start, stop or otherwise interfere with other threads
 * <li> Initialize windows, dialogs or other applications
 * <li> Start a secondary boot sequence
 * <li> Cancel the execution of other tasks
 * </ul>
 * <p>
 * Should the results of one task be unneeded, this should be specified
 * beforehand or overwritten with a new task. To ensure the first task
 * runs first, the overwriting task must have a lower priority if
 * possible.
 * <p>
 * Each task has a specified priority. Modification of these values allow
 * certain tasks to run before other tasks, should they still need to be
 * independent. Often, if one task being disabled results in another task
 * needing to be disabled, then they should be merged. This allows the set
 * of available priorities to be fairly limited.
 * <p>
 * The list of accepted priorities are:
 * <ul>
 * <li> {@link Priority#LOWEST}
 * <li> {@link Priority#LOW}
 * <li> {@link Priority#NORMAL}
 * <li> {@link Priority#HIGH}
 * <li> {@link Priority#HIGHEST}
 * </ul>
 * <p>
 * Unless otherwise needed, every task should run at
 * {@link Priority#NORMAL}. This will
 * therefore provide consistent results regardless of the number or
 * validity of the tasks.
 *
 * @author Obicere
 * @version 0.0
 * @see StartUpQueue
 * @see StartUpTaskLoader
 * @since 0.0
 */
public interface StartUpTask {

    default public Priority getPriority() {
        return Priority.NORMAL;
    }

    public void call(final Domain domain);

}
