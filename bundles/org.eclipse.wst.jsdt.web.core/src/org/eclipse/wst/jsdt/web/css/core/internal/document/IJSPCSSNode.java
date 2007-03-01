/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     
 *******************************************************************************/
package org.eclipse.wst.jsdt.web.css.core.internal.document;

import org.eclipse.wst.css.core.internal.provisional.document.ICSSNode;

public interface IJSPCSSNode extends ICSSNode {
	short JSP_NODE = 16;

	String getCssText();
}
