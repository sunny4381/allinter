/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Hisashi MIYASHITA - initial API and implementation
 *******************************************************************************/
package org.eclipse.actf.model.dom.dombycom;

import org.eclipse.actf.util.vocab.IEvalTarget;
import org.eclipse.swt.graphics.Rectangle;
import org.w3c.dom.Node;

/**
 * The INodeEx interface defines the methods to be implemented by the node
 * objects that are used in the application. These methods are basic functions
 * for alternative interface.
 */
public interface INodeEx extends Node, IEvalTarget {
	/**
	 * @return the text representation of the node.
	 */
	String extractString();

	/**
	 * @return the heading level of the node. If the node is not heading then
	 *         "0" will be returned.
	 */
	short getHeadingLevel();

	/**
	 * @return the link URI if the node is a link.
	 */
	String getLinkURI();

	/**
	 * @return the access key of the node.
	 */
	char getAccessKey();

	/**
	 * This maybe a heavy task.
	 * 
	 * @return the nth position of the node in the siblings.
	 */
	int getNth();

	/**
	 * The click operation for the element will be simulated.
	 * 
	 * @return whether the click is succeeded or not.
	 */
	boolean doClick();

	/**
	 * Highlight the node.
	 * 
	 * @return whether the highlight is succeeded or not
	 */
	boolean highlight();

	/**
	 * Unhighlight the node.
	 * 
	 * @return whether the unhighlight is succeeded or not
	 */
	boolean unhighlight();

	/**
	 * Set the Focus to the node.
	 * 
	 * @return whether the focusing is succeeded or not.
	 */
	boolean setFocus();

	/**
	 * Set the text to the node.
	 * 
	 * @param text
	 *            the text to be set.
	 */
	void setText(String text);

	/**
	 * @return the text of the form element. It is NOT the text representation
	 *         of the node.
	 */
	String getText();

	// !FN!
	/**
	 * @return the string array of the information about the image. The array
	 *         length is 3. Index 0 is the MIMETYPE of the image, Index 1 is the
	 *         src attribute of the image, Index 2 is an empty string.
	 */
	String[] getStillPictureData();

//	/**
//	 * @param ar
//	 *            the instance of the AnalyzedResult for store the nodes.
//	 * @return it is the same as <i>ar</i>
//	 */
//	AnalyzedResult analyze(AnalyzedResult ar);

	/**
	 * @return the location of the node on the client window.
	 */
	Rectangle getLocation();
}
