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
package org.eclipse.wst.jsdt.web.css.core.internal.modelhandler;

import org.eclipse.wst.jsdt.web.css.core.internal.encoding.JSPedCSSDocumentLoader;
import org.eclipse.wst.css.core.internal.encoding.CSSDocumentCharsetDetector;
import org.eclipse.wst.sse.core.internal.document.IDocumentCharsetDetector;
import org.eclipse.wst.sse.core.internal.document.IDocumentLoader;
import org.eclipse.wst.sse.core.internal.ltk.modelhandler.AbstractModelHandler;
import org.eclipse.wst.sse.core.internal.ltk.modelhandler.IModelHandler;
import org.eclipse.wst.sse.core.internal.provisional.IModelLoader;

public class ModelHandlerForJSPedCSS extends AbstractModelHandler implements
		IModelHandler {
	static String AssociatedContentTypeID = "org.eclipse.wst.jsdt.web.core.cssjspsource"; //$NON-NLS-1$

	private static String ModelHandlerID = "org.eclipse.wst.jsdt.web.css.core.modelhandler"; //$NON-NLS-1$

	public ModelHandlerForJSPedCSS() {
		super();
		setId(ModelHandlerID);
		setAssociatedContentTypeId(AssociatedContentTypeID);
	}

	public IModelLoader getModelLoader() {
		return new JSPedCSSModelLoader();
	}

	@Override
	public IDocumentCharsetDetector getEncodingDetector() {
		return new CSSDocumentCharsetDetector();
	}

	public IDocumentLoader getDocumentLoader() {
		return new JSPedCSSDocumentLoader();
	}
}
