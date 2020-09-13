/*******************************************************************************
 * Copyright (c) 2004, 2008 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 *    Kentarou FUKUDA - initial API and implementation
 *******************************************************************************/
package compat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static java.nio.file.Files.*;

public final class FileUtils {
	public static final String LINE_SEP = System.getProperty("line.separator"); //$NON-NLS-1$

	/**
	 * delete files or directories under the specified root directory
	 *
	 * @param rootDir -
	 *            root directory from which to start - must be existing
	 *            directory
	 */
	public static void deleteFiles(File rootDir) {
		if (rootDir != null) {
			File[] fileList = rootDir.listFiles();

			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory()) {
					deleteFiles(fileList[i]);
				}
				fileList[i].delete();
			}
		}
	}

	private static Object lock = new Object();
	private static Path workDirectory = null;
	private static int sequence = 0;

    public static Path newTempPath() throws IOException {
		synchronized (lock) {
			if (workDirectory == null) {
				workDirectory = createTempDirectory("compat");
			}

			String unique = "dir-" + String.valueOf(sequence);
			sequence++;

			Path tmpDir = Paths.get(workDirectory.toString(), unique);
			createDirectories(tmpDir);

			return tmpDir;
		}
	}

    public static File newTempDirectory() throws IOException {
		return newTempPath().toFile();
    }

    public static void cleanupWorkDirectory() throws IOException {
        synchronized (lock) {
            if (workDirectory == null) {
                return;
            }

            walk(workDirectory)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            workDirectory = null;
        }
    }
}
