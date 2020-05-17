/*******************************************************************************
 * Copyright (c) 2007, 2011 IBM Corporation and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Hisashi MIYASHITA - initial API and implementation
 *******************************************************************************/

package org.eclipse.actf.model.dom.dombycom;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * The extended interface of the {@link Document}.
 */
public interface IDocumentEx extends Document {
    /**
     * CSS compatible mode (IE)
     */
    String CSS1_COMPAT = "CSS1Compat"; //$NON-NLS-1$
    
    /**
     * Back compatible mode (IE)
     */
    String BACK_COMPAT = "BackCompat"; //$NON-NLS-1$
    
    /**
     * getElementById will return a node, but actual content sometimes
     * has more than one objects having save ID and it can't search in the
     * document of iframes in the top document. 
     * 
     * @param id to be used for the search
     * @return the objects which has <i>id</i> as its id attribute.
     */
    List<Node> getElementsByIdInAllFrames(String id);
    
    /**
     * @return the CSS compatible mode (IE)
     */
    String getCompatMode();
    
    /**
     * @param target the anchor name to be searched.
     * @return the first element whose name is <i>target</i>.
     */
    Element getTargetElement(String target);
    
    /**
     * @return the {@link IStyleSheets} of the document.
     */
    IStyleSheets getStyleSheets();
}
