package org.eclipse.wst.html.core.text;

import org.eclipse.wst.sse.core.text.IStructuredPartitions;

/**
 * This interface is not intended to be implemented.
 * It defines the partitioning for HTML and all its partitions.
 * Clients should reference the partition type Strings defined here directly.
 * 
 * @since 1.0
 */
public interface IHTMLPartitions {
	
	String HTML_PARTITIONING = IStructuredPartitions.STRUCTURED_PARTITIONING;
	
	String HTML_DEFAULT = "org.eclipse.wst.html.HTML_DEFAULT"; //$NON-NLS-1$
	String HTML_DECLARATION = "org.eclipse.wst.html.HTML_DECLARATION"; //$NON-NLS-1$
	String HTML_COMMENT = "org.eclipse.wst.html.HTML_COMMENT"; //$NON-NLS-1$

	String SCRIPT = "org.eclipse.wst.html.SCRIPT"; //$NON-NLS-1$
}
