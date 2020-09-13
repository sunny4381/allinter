/*******************************************************************************
 * Copyright (c) 2007, 2008 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Kentarou FUKUDA - initial API and implementation
 *******************************************************************************/

package org.eclipse.actf.visualization.internal.engines.blind;

import java.io.File;
import java.io.IOException;

import compat.FileUtils;

/**
 * The activator class controls the plug-in life cycle
 */
public class BlindVizEnginePlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.actf.visualization.engines.blind"; //$NON-NLS-1$

//	// The shared instance
//	private static BlindVizEnginePlugin plugin;

	private static File tmpDir = null;

//	/**
//	 * The constructor
//	 */
//	public BlindVizEnginePlugin() {
//		plugin = this;
//	}

//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
//	 */
//	public void start(BundleContext context) throws Exception {
//		super.start(context);
//		if (!getPreferenceStore().getBoolean(
//				IBlindPreferenceConstants.NOT_FIRST_TIME)) {
//			TextChecker.getInstance();
//		}
//
//		createTempDirectory();
//		String tmpS;
//		if (tmpDir != null) {
//			tmpS = tmpDir.getAbsolutePath() + File.separator + "img"; //$NON-NLS-1$
//			if (FileUtils.isAvailableDirectory(tmpS)) {
//				String tmpS2 = tmpS + File.separator;
//				BlindVizResourceUtil.saveImages(tmpS2);
//				BlindVizResourceUtil.saveScripts(tmpS2);
//			}
//		}
//
//	}

//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
//	 */
//	public void stop(BundleContext context) throws Exception {
//		getPreferenceStore().setValue(
//				IBlindPreferenceConstants.NOT_FIRST_TIME, true);
//		plugin = null;
//		super.stop(context);
//		deleteFiles(tmpDir);
//	}

//	/**
//	 * Returns the shared instance
//	 *
//	 * @return the shared instance
//	 */
//	public static BlindVizEnginePlugin getDefault() {
//		return plugin;
//	}

//	public String getConfigDir() {
//		try {
//			URL url = plugin.getBundle().getEntry("config"); //$NON-NLS-1$
//			url = FileLocator.resolve(url);
//			return new Path(url.getPath()).makeAbsolute().toOSString();
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//			return ""; //$NON-NLS-1$
//		}
//	}

	private static void createTempDirectory() throws IOException {
		if (tmpDir == null) {
			tmpDir = FileUtils.newTempDirectory();
		}
	}

	public static File createTempFile(String prefix, String suffix) throws Exception {
		createTempDirectory();
		return (File.createTempFile(prefix, suffix, tmpDir));
	}

	public static File getTempDirectory() throws IOException {
		if (tmpDir == null) {
			createTempDirectory();
		}
		return tmpDir;
	}
	
//	private void deleteFiles(File rootDir) {
//		if (rootDir != null) {
//			File[] fileList = rootDir.listFiles();
//
//			for (int i = 0; i < fileList.length; i++) {
//				if (fileList[i].isDirectory()) {
//					deleteFiles(fileList[i]);
//				}
//				fileList[i].delete();
//			}
//		}
//	}
		
}
