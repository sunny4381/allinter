/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Daisuke SATO - initial API and implementation
 *    Kentarou FUKUDA - initial API and implementation
 *******************************************************************************/

package org.eclipse.actf.model.dom.dombycom;

import org.w3c.dom.Element;

/**
 * The extended interface of the {@link Element}
 */
public interface IElementEx extends Element, INodeEx{
	/**
	 * An instance of Position represents a nth position of a element in a radio
	 * button group and list group.
	 */
	public class Position {
		public int index;
		public int total;
	}

	/**
	 * @return the CSS style object.
	 */
	public IStyle getStyle();

	/**
	 * @return the position of the element in the radio group. This is valid if
	 *         the element is a radio button in the radio group.
	 */
	public Position getRadioPosition();

	/**
	 * @return the position of the element in the list. This is valid if the
	 *         element is an item of the list.
	 */
	public Position getListPosition();

	/**
	 * @return the label object annotating the element. This is valid if the
	 *         element is a form element and a label element for the element is
	 *         existing.
	 */
	public Element getFormLabel();

	/** 
	 * @return the attribute value originally specified to the element. If no value is specified, returns null.
	 * @see org.w3c.dom.Element#getAttribute(java.lang.String)
	 */
	public String getAttribute(String name);
	
	/**
	 * @return the attribute value currently assigned to the element. (same as getAttribute method of IE DOM)
	 */
	public String getCurrentAttribute(String name);
}
