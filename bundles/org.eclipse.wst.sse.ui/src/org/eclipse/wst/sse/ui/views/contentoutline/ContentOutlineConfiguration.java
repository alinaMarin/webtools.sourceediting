/*******************************************************************************
 * Copyright (c) 2001, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Jens Lukowski/Innoopract - initial renaming/restructuring
 *     
 *******************************************************************************/
package org.eclipse.wst.sse.ui.views.contentoutline;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.TransferDragSourceListener;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;
import org.eclipse.wst.sse.ui.internal.contentoutline.PropertyChangeUpdateAction;
import org.eclipse.wst.sse.ui.internal.contentoutline.PropertyChangeUpdateActionContributionItem;
import org.eclipse.wst.sse.ui.internal.editor.EditorPluginImageHelper;
import org.eclipse.wst.sse.ui.internal.editor.EditorPluginImages;

/**
 * Basic Configuration class for Outline Pages
 * 
 * @plannedfor 1.0
 * 
 */
public abstract class ContentOutlineConfiguration {
	/**
	 * Add a collapse action to help with navigation.
	 */
	private class CollapseTreeAction extends Action {
		private TreeViewer fTreeViewer = null;

		public CollapseTreeAction(TreeViewer viewer) {
			super(SSEUIMessages.ContentOutlineConfiguration_0, AS_PUSH_BUTTON); //$NON-NLS-1$
			setImageDescriptor(COLLAPSE_E);
			setDisabledImageDescriptor(COLLAPSE_D);
			setToolTipText(getText());
			fTreeViewer = viewer;
		}

		public void run() {
			super.run();
			fTreeViewer.collapseAll();
		}
	}

	/**
	 * Toggles whether incoming selection notification affects us
	 */
	private class ToggleLinkAction extends PropertyChangeUpdateAction {
		public ToggleLinkAction(IPreferenceStore store, String preference) {
			super(SSEUIMessages.ContentOutlineConfiguration_1, store, preference, true); //$NON-NLS-1$
			setToolTipText(getText());
			setDisabledImageDescriptor(SYNCED_D);
			setImageDescriptor(SYNCED_E);
			update();
		}

		public void update() {
			super.update();
			setLinkWithEditor(isChecked());
		}
	}

	protected ImageDescriptor COLLAPSE_D = EditorPluginImageHelper.getInstance().getImageDescriptor(EditorPluginImages.IMG_DLCL_COLLAPSEALL);
	protected ImageDescriptor COLLAPSE_E = EditorPluginImageHelper.getInstance().getImageDescriptor(EditorPluginImages.IMG_ELCL_COLLAPSEALL);

	private boolean fIsLinkWithEditor = false;

	private ILabelProvider fLabelProvider;
	private IContributionItem[] fMenuContributions = null;
	private IContributionItem[] fToolbarContributions = null;
	private final String OUTLINE_LINK_PREF = "outline-link-editor"; //$NON-NLS-1$
	ImageDescriptor SYNCED_D = EditorPluginImageHelper.getInstance().getImageDescriptor(EditorPluginImages.IMG_DLCL_SYNCED);
	ImageDescriptor SYNCED_E = EditorPluginImageHelper.getInstance().getImageDescriptor(EditorPluginImages.IMG_ELCL_SYNCED);

	public ContentOutlineConfiguration() {
		super();
	}

	/**
	 * Creates the contributions for the view's local menu.  Subclasses should merge their contributions with these.
	 * @param viewer
	 * @return menu contributions
	 */
	protected IContributionItem[] createMenuContributions(TreeViewer viewer) {
		IContributionItem toggleLinkItem = new PropertyChangeUpdateActionContributionItem(new ToggleLinkAction(getPreferenceStore(), OUTLINE_LINK_PREF));
		IContributionItem[] items = new IContributionItem[]{toggleLinkItem};
		return items;
	}

	/**
	 * Creates the toolbar contributions.  Subclasses should merge their contributions with these.
	 * @param viewer
	 * @return toolbar contributions
	 */
	protected IContributionItem[] createToolbarContributions(TreeViewer viewer) {
		IContributionItem collapseAllItem = new ActionContributionItem(new CollapseTreeAction(viewer));
		IContributionItem[] items = new IContributionItem[]{collapseAllItem};
		return items;
	}

	/**
	 * @param viewer
	 * @return the ITreeContentProvider to use with this viewer
	 */
	public abstract IContentProvider getContentProvider(TreeViewer viewer);

	/**
	 * 
	 * @param viewer
	 * @return an array of KeyListeners to attach to the TreeViewer's Control.
	 *         The listeners should adhere to the KeyEvent.doit field to
	 *         ensure proper behaviors. Ordering of the event notifications is
	 *         dependent on the Control in the TreeViewer.
	 */
	public KeyListener[] getKeyListeners(TreeViewer viewer) {
		return null;
	}

	/**
	 * @param viewer
	 * @return the ILabelProvider for items within the viewer
	 */
	public ILabelProvider getLabelProvider(TreeViewer viewer) {
		if (fLabelProvider == null)
			fLabelProvider = new LabelProvider();
		return fLabelProvider;
	}

	/**
	 * @param viewer
	 * @return IContributionItem[] for the local menu
	 */
	public final IContributionItem[] getMenuContributions(TreeViewer viewer) {
		if (fMenuContributions == null) {
			fMenuContributions = createMenuContributions(viewer);
		}
		return fMenuContributions;
	}

	/**
	 * @param viewer
	 * @return the IMenuListener to notify when the viewer's context menu is
	 *         about to be shown
	 */
	public IMenuListener getMenuListener(TreeViewer viewer) {
		return null;
	}

	/**
	 * @param viewer
	 * @return the ISelectionChangedListener to notify when the viewer's
	 *         selection changes
	 */
	public ISelectionChangedListener getPostSelectionChangedListener(TreeViewer viewer) {
		return null;
	}

	/**
	 * @return the preference store in which to remember preferences (such as the link-with-editor toggle state)
	 */
	protected IPreferenceStore getPreferenceStore() {
		return SSEUIPlugin.getInstance().getPreferenceStore();
	}

	/**
	 * @param event
	 * @return The (filtered) selection from this event. Uses include mapping
	 *         model selection onto elements provided by the content provider.
	 *         Should only return elements that will be shown in the Tree
	 *         Control.
	 */
	public ISelection getSelection(TreeViewer viewer, ISelection selection) {
		return selection;
	}



	/**
	 * @param viewer
	 * @return the ISelectionChangedListener to notify when the viewer's
	 *         selection changes
	 */
	public ISelectionChangedListener getSelectionChangedListener(TreeViewer viewer) {
		return null;
	}


	/**
	 * @param viewer
	 * @return IContributionItem[] for the local toolbar
	 */
	public final IContributionItem[] getToolbarContributions(TreeViewer viewer) {
		if (fToolbarContributions == null) {
			fToolbarContributions = createToolbarContributions(viewer);
		}
		return fToolbarContributions;
	}

	/**
	 * Adopted since you can't easily removeDragSupport from StructuredViewers
	 * 
	 * @param treeViewer
	 * @return
	 */
	public TransferDragSourceListener[] getTransferDragSourceListeners(TreeViewer treeViewer) {
		return new TransferDragSourceListener[0];
	}

	/**
	 * Adopted since you can't easily removeDropSupport from StructuredViewers
	 * 
	 * @param treeViewer
	 * @return
	 */
	public TransferDropTargetListener[] getTransferDropTargetListeners(TreeViewer treeViewer) {
		return new TransferDropTargetListener[0];
	}

	/**
	 * Should node selection changes affect selection in the TreeViewer?
	 * 
	 * @return
	 */
	public boolean isLinkedWithEditor(TreeViewer treeViewer) {
		return fIsLinkWithEditor;
	}

	/**
	 * @param isLinkWithEditor
	 *            The isLinkWithEditor to set.
	 */
	protected void setLinkWithEditor(boolean isLinkWithEditor) {
		fIsLinkWithEditor = isLinkWithEditor;
	}

	/**
	 * General hook for resource releasing and listener removal when
	 * configurations change or the viewer is disposed of. This implementation
	 * stops of any remaining PropertyChangeUpdateActionContributionItem from
	 * preference listening.
	 * 
	 * @param viewer
	 */
	public void unconfigure(TreeViewer viewer) {
		if (fToolbarContributions != null) {
			for (int i = 0; i < fToolbarContributions.length; i++) {
				if (fToolbarContributions[i] instanceof PropertyChangeUpdateActionContributionItem) {
					((PropertyChangeUpdateActionContributionItem) fToolbarContributions[i]).disconnect();
				}
			}
			fToolbarContributions = null;
		}
		if (fMenuContributions != null) {
			for (int i = 0; i < fMenuContributions.length; i++) {
				if (fMenuContributions[i] instanceof PropertyChangeUpdateActionContributionItem) {
					((PropertyChangeUpdateActionContributionItem) fMenuContributions[i]).disconnect();
				}
			}
			fMenuContributions = null;
		}
	}
}
