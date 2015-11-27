/**
 * This package should only contain ways to start up the application. The
 * most standard way is the {@link org.obicere.bcviewer.Boot} class, which
 * is a standard boot.
 *
 * The description and usage for all entry points is as follows:
 * <ul>
 * <li>{@link org.obicere.bcviewer.Boot}
 * <p>The main entry point for the
 * application. Unless otherwise needed, this should be the primary way to
 * boot the application</p>
 * </ul>
 *
 * The standard way a boot should perform is as follows:
 * <ul>
 * <li> Setting up logger and domain
 * <li> Running startup tasks
 * <li> Settings are loaded
 * <li> Window is initialized and started
 * <li> All files passed in as arguments are loaded
 * </ul>
 *
 * Unless otherwise specified, each of these steps should be taken.
 *
 * @author Obicere
 * @since 0.0
 */
package org.obicere.bcviewer;