/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.jsdt.web.ui.internal.java.search.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jsdt.core.IJavaElement;
import org.eclipse.jsdt.core.search.SearchPattern;
import org.eclipse.jsdt.core.search.SearchRequestor;
import org.eclipse.jsdt.ui.search.ElementQuerySpecification;
import org.eclipse.jsdt.ui.search.IMatchPresentation;
import org.eclipse.jsdt.ui.search.IQueryParticipant;
import org.eclipse.jsdt.ui.search.ISearchRequestor;
import org.eclipse.jsdt.ui.search.PatternQuerySpecification;
import org.eclipse.jsdt.ui.search.QuerySpecification;
import org.eclipse.wst.jsdt.web.core.internal.java.search.JSPSearchScope;
import org.eclipse.wst.jsdt.web.core.internal.java.search.JSPSearchSupport;
import org.eclipse.wst.jsdt.web.ui.internal.java.search.JSPSearchRequestor;

/**
 * @author pavery
 */
public class JSPQueryParticipant implements IQueryParticipant {

	// for debugging
	private static final boolean DEBUG;
	static {
		String value = Platform
				.getDebugOption("org.eclipse.wst.jsdt.web.core/debug/jspsearch"); //$NON-NLS-1$
		DEBUG = value != null && value.equalsIgnoreCase("true"); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.wst.jsdt.ui.search.IQueryParticipant#search(org.eclipse.wst.jsdt.ui.search.ISearchRequestor,
	 *      org.eclipse.wst.jsdt.ui.search.QuerySpecification,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void search(ISearchRequestor requestor,
			QuerySpecification querySpecification, IProgressMonitor monitor)
			throws CoreException {

		// indexIfNeeded();

		// do search based on the particular Java query
		if (querySpecification instanceof ElementQuerySpecification) {
			// element search (eg. from global find references in Java file)
			ElementQuerySpecification elementQuery = (ElementQuerySpecification) querySpecification;
			IJavaElement element = elementQuery.getElement();

			if (DEBUG) {
				System.out
						.println("JSP Query Participant searching on ELEMENT: " + element); //$NON-NLS-1$
			}

			SearchRequestor jspRequestor = new JSPSearchRequestor(requestor);

			// pa_TODO need to adapt JavaSearchScope to a JSPSearchScope
			JSPSearchSupport.getInstance().search(element,
					new JSPSearchScope(), jspRequestor);

		} else if (querySpecification instanceof PatternQuerySpecification) {

			// pattern search (eg. from Java search page)
			PatternQuerySpecification patternQuery = (PatternQuerySpecification) querySpecification;
			String pattern = patternQuery.getPattern();

			if (DEBUG) {
				System.out
						.println("JSP Query Participant searching on PATTERN: " + pattern); //$NON-NLS-1$
			}

			SearchRequestor jspRequestor = new JSPSearchRequestor(requestor);

			JSPSearchSupport.getInstance().search(pattern,
					new JSPSearchScope(), patternQuery.getSearchFor(),
					patternQuery.getLimitTo(), SearchPattern.R_PATTERN_MATCH,
					false, jspRequestor);
		}
	}

	/**
	 * @see org.eclipse.wst.jsdt.ui.search.IQueryParticipant#estimateTicks(org.eclipse.wst.jsdt.ui.search.QuerySpecification)
	 */
	public int estimateTicks(QuerySpecification data) {
		// pa_TODO use project file counter from JSPSearchSupport...
		return 0;
	}

	/**
	 * @see org.eclipse.wst.jsdt.ui.search.IQueryParticipant#getUIParticipant()
	 */
	public IMatchPresentation getUIParticipant() {
		return new JSPMatchPresentation();
	}

}
