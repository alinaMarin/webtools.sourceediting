package org.eclipse.jst.jsp.core.text;

import org.eclipse.wst.sse.core.text.IStructuredPartitions;

/**
 * This interface is not intended to be implemented.
 * It defines the partitioning for JSP and all its partitions.
 * Clients should reference the partition type Strings defined here directly.
 * 
 * @since 1.0
 */
public interface IJSPPartitions {

	String JSP_PARTITIONING = IStructuredPartitions.STRUCTURED_PARTITIONING;
	
	String JSP_DEFAULT = "org.eclipse.jst.jsp.DEFAULT_JSP"; //$NON-NLS-1$
	String JSP_COMMENT = "org.eclipse.jst.jsp.JSP_COMMENT"; //$NON-NLS-1$
	
	String JSP_SCRIPT_PREFIX = "org.eclipse.jst.jsp.SCRIPT."; //$NON-NLS-1$
	String JSP_CONTENT_DELIMITER = JSP_SCRIPT_PREFIX + "DELIMITER"; //$NON-NLS-1$
	String JSP_CONTENT_JAVA = JSP_SCRIPT_PREFIX + "JAVA"; //$NON-NLS-1$
	String JSP_CONTENT_JAVASCRIPT = JSP_SCRIPT_PREFIX + "JAVASCRIPT"; //$NON-NLS-1$
	String JSP_DEFAULT_EL = JSP_SCRIPT_PREFIX + "JSP_EL"; //$NON-NLS-1$
	
	String JSP_DIRECTIVE = "org.eclipse.jst.jsp.JSP_DIRECTIVE"; //$NON-NLS-1$
}
