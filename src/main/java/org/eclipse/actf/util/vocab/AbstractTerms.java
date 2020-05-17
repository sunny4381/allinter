/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Daisuke SATO - initial API and implementation
 *******************************************************************************/

package org.eclipse.actf.util.vocab;

import org.w3c.dom.Node;

/**
 * AbstractTerms is the default term definition. All methods return <i>false</i>.
 * Each class extends AbstractTerms should implements methods which used in
 * itself.
 * 
 * @see Vocabulary
 */
public abstract class AbstractTerms {

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isClickable()
	 */
	public boolean isClickable(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isEditable()
	 */
	public boolean isEditable(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#hasContent()
	 */
	public boolean hasContent(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 */
	public boolean isEnabled(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isInputable()
	 */
	public boolean isInputable(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isSelectable()
	 */
	public boolean isSelectable(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isButton()
	 */
	public boolean isButton(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isCheckbox()
	 */
	public boolean isCheckbox(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isLabel()
	 */
	public boolean isLabel(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isLink()
	 */
	public boolean isLink(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isRadio()
	 */
	public boolean isRadio(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isCombobox()
	 */
	public boolean isCombobox(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isSubmit()
	 */
	public boolean isSubmit(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isTextarea()
	 */
	public boolean isTextarea(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isTextbox()
	 */
	public boolean isTextbox(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isPassword()
	 */
	public boolean isPassword(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isValidNode()
	 */
	public boolean isValidNode(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isVisibleNode()
	 */
	public boolean isVisibleNode(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isEmbeddedObject()
	 */
	public boolean isEmbeddedObject(IEvalTarget target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isImage()
	 */
	public boolean isImage(IEvalTarget target) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isMultilineEdit()
	 */
	public boolean isMultilineEdit(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isReducible()
	 */
	public boolean isReducible(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isConnectable()
	 */
	public boolean isConnectable(IEvalTarget node) {
		return false;
	}

	/**
	 * @param str the str to be searched in the text of the node
	 * @param caseSensitive whether the matching is case sensitive or not
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#find(String, boolean)
	 */
	public boolean find(String str, boolean caseSensitive, IEvalTarget node) {
		return false;
	}

	/**
	 * @param str the str to be checked in the text of the node
	 * @param caseSensitive whether the matching is case sensitive or not
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#startsWith(String, boolean)
	 */
	public boolean startsWith(String str, boolean exact, IEvalTarget node) {
		return false;
	}

	/**
	 * @param level the level of the heading
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isHeading()
	 */
	public boolean isHeading(int level, IEvalTarget node) {
		return false;
	}

	/**
	 * @param refNode the reference node for the evaluation
	 * @param backward the direction of the evaluation
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#nodeLocation(Node, boolean)
	 */
	public boolean nodeLocation(Node refNode, boolean backward, IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 */
	public boolean isHeadingJumpPoint(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#hasReadingContent()
	 */
	public boolean hasReadingContent(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 */
	public boolean isListItemJumpPoint(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isBlockJumpPointF()
	 */
	public boolean isBlockJumpPointF(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isBlockJumpPointB()
	 */
	public boolean isBlockJumpPointB(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isFileEdit()
	 */
	public boolean isFileEdit(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isChecked()
	 */
	public boolean isChecked(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isListItem()
	 */
	public boolean isListItem(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isListTop()
	 */
	public boolean isListTop(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isSelectOption()
	 */
	public boolean isSelectOption(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isVisitedLink()
	 */
	public boolean isVisitedLink(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isMultiSelectable()
	 */
	public boolean isMultiSelectable(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isAccessKey(char)
	 */
	public boolean isAccessKey(char key, IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isMedia()
	 */
	public boolean isMedia(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isFlashTopNode()
	 */
	public boolean isFlashTopNode(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isFlashLastNode()
	 */
	public boolean isFlashLastNode(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isMSAAFlash()
	 */
	public boolean isMSAAFlash(IEvalTarget node) {
		return false;
	}

	/**
	 * @param node the node to be evaluated
	 * @param target the target node for the evaluation of reaching
	 * @return whether the node matches the condition
	 * @see Vocabulary#isReachable(Node)
	 */
	public boolean isReachable(IEvalTarget node, Node target) {
		return false;
	}

	/**
	 * @param target the node to be evaluated
	 * @return whether the node matches the condition
	 * @see Vocabulary#isAlterable()
	 */
	public boolean isAlterable(IEvalTarget target) {
		return false;
	}

}
