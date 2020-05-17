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

/**
 * IEvalTarget interface should be implemented by the element or node to be
 * checked through the Vocabulary.
 */
public interface IEvalTarget {
	/**
	 * @return the associated term.
	 */
	AbstractTerms getTerms();
}
