/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     
 *******************************************************************************/
package org.eclipse.wst.dtd.ui.internal.preferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.wst.dtd.core.internal.parser.DTDRegionTypes;
import org.eclipse.wst.dtd.core.internal.provisional.contenttype.ContentTypeIdForDTD;
import org.eclipse.wst.dtd.ui.internal.DTDUIMessages;
import org.eclipse.wst.dtd.ui.internal.DTDUIPlugin;
import org.eclipse.wst.dtd.ui.internal.editor.IHelpContextIds;
import org.eclipse.wst.dtd.ui.internal.style.IStyleConstantsDTD;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionList;
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore.OverlayKey;
import org.eclipse.wst.sse.ui.internal.preferences.ui.ColorHelper;
import org.eclipse.wst.sse.ui.internal.util.EditorUtility;

import com.ibm.icu.text.Collator;

/**
 * A preference page to configure our XML syntax color. It resembles the JDT
 * and CDT pages far more than our original color page while retaining the
 * extra "click-to-find" functionality.
 */
public final class DTDSyntaxColoringPage extends PreferencePage implements IWorkbenchPreferencePage {

	private Button fBold;
	private Label fForegroundLabel;
	private Label fBackgroundLabel;
	private Button fClearStyle;
	private Map fContextToStyleMap;
	private Color fDefaultForeground = null;
	private Color fDefaultBackground = null;
	private IStructuredDocument fDocument;
	private ColorSelector fForegroundColorEditor;
	private ColorSelector fBackgroundColorEditor;
	private Button fItalic;
	private OverlayPreferenceStore fOverlayStore;
	private Button fStrike;
	private Collection fStylePreferenceKeys;
	private StructuredViewer fStylesViewer = null;
	private Map fStyleToDescriptionMap;
	private StyledText fText;
	private Button fUnderline;

	// activate controls based on the given local color type
	private void activate(String namedStyle) {
		Color foreground = fDefaultForeground;
		Color background = fDefaultBackground;
		if (namedStyle == null) {
			fClearStyle.setEnabled(false);
			fBold.setEnabled(false);
			fItalic.setEnabled(false);
			fStrike.setEnabled(false);
			fUnderline.setEnabled(false);
			fForegroundLabel.setEnabled(false);
			fBackgroundLabel.setEnabled(false);
			fForegroundColorEditor.setEnabled(false);
			fBackgroundColorEditor.setEnabled(false);
			fBold.setSelection(false);
			fItalic.setSelection(false);
			fStrike.setSelection(false);
			fUnderline.setSelection(false);
		}
		else {
			TextAttribute attribute = getAttributeFor(namedStyle);
			fClearStyle.setEnabled(true);
			fBold.setEnabled(true);
			fItalic.setEnabled(true);
			fStrike.setEnabled(true);
			fUnderline.setEnabled(true);
			fForegroundLabel.setEnabled(true);
			fBackgroundLabel.setEnabled(true);
			fForegroundColorEditor.setEnabled(true);
			fBackgroundColorEditor.setEnabled(true);
			fBold.setSelection((attribute.getStyle() & SWT.BOLD) != 0);
			fItalic.setSelection((attribute.getStyle() & SWT.ITALIC) != 0);
			fStrike.setSelection((attribute.getStyle() & TextAttribute.STRIKETHROUGH) != 0);
			fUnderline.setSelection((attribute.getStyle() & TextAttribute.UNDERLINE) != 0);
			if (attribute.getForeground() != null) {
				foreground = attribute.getForeground();
			}
			if (attribute.getBackground() != null) {
				background = attribute.getBackground();
			}
		}

		fForegroundColorEditor.setColorValue(foreground.getRGB());
		fBackgroundColorEditor.setColorValue(background.getRGB());
	}

	/**
	 * Color the text in the sample area according to the current preferences
	 */
	void applyStyles() {
		if (fText == null || fText.isDisposed())
			return;
		IStructuredDocumentRegion documentRegion = fDocument.getFirstStructuredDocumentRegion();
		while (documentRegion != null) {
			ITextRegionList regions = documentRegion.getRegions();
			for (int i = 0; i < regions.size(); i++) {
				ITextRegion currentRegion = regions.get(i);
				// lookup the local coloring type and apply it
				String namedStyle = (String) fContextToStyleMap.get(currentRegion.getType());
				if (namedStyle == null)
					continue;
				TextAttribute attribute = getAttributeFor(namedStyle);
				if (attribute == null)
					continue;
				StyleRange style = new StyleRange(documentRegion.getStartOffset(currentRegion), currentRegion.getTextLength(), attribute.getForeground(), attribute.getBackground(), attribute.getStyle());
				style.strikeout = (attribute.getStyle() & TextAttribute.STRIKETHROUGH) != 0;
				style.underline = (attribute.getStyle() & TextAttribute.UNDERLINE) != 0;
				fText.setStyleRange(style);
			}
			documentRegion = documentRegion.getNext();
		}
	}

	Button createCheckbox(Composite parent, String label) {
		Button button = new Button(parent, SWT.CHECK);
		button.setText(label);
		button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		return button;
	}

	/**
	 * Creates composite control and sets the default layout data.
	 */
	private Composite createComposite(Composite parent, int numColumns) {
		Composite composite = new Composite(parent, SWT.NULL);

		// GridLayout
		GridLayout layout = new GridLayout();
		layout.numColumns = numColumns;
		layout.makeColumnsEqualWidth = false;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);

		// GridData
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		composite.setLayoutData(data);
		return composite;
	}

	protected Control createContents(final Composite parent) {
		initializeDialogUnits(parent);

		fDefaultForeground = parent.getDisplay().getSystemColor(SWT.COLOR_LIST_FOREGROUND);
		fDefaultBackground = parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
		Composite pageComponent = createComposite(parent, 2);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(pageComponent, IHelpContextIds.DTD_PREFWEBX_STYLES_HELPID);

		Link link = new Link(pageComponent, SWT.WRAP);
		link.setText(SSEUIMessages.SyntaxColoring_Link);
		link.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PreferencesUtil.createPreferenceDialogOn(parent.getShell(), e.text, null, null);
			}
		});

		GridData linkData= new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1);
		linkData.widthHint= 150; // only expand further if anyone else requires it
		link.setLayoutData(linkData);

		new Label(pageComponent, SWT.NONE).setLayoutData(new GridData());
		new Label(pageComponent, SWT.NONE).setLayoutData(new GridData());

		SashForm editor = new SashForm(pageComponent, SWT.VERTICAL);
		GridData gridData2 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData2.horizontalSpan = 2;
		editor.setLayoutData(gridData2);
		SashForm top = new SashForm(editor, SWT.HORIZONTAL);
		Composite styleEditor = createComposite(top, 1);
		((GridLayout) styleEditor.getLayout()).marginRight = 5;
		((GridLayout) styleEditor.getLayout()).marginLeft = 0;
		createLabel(styleEditor, DTDUIMessages.SyntaxColoringPage_0);
		fStylesViewer = createStylesViewer(styleEditor);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalIndent = 0;
		Iterator iterator = fStyleToDescriptionMap.values().iterator();
		while (iterator.hasNext()) {
			gridData.widthHint = Math.max(gridData.widthHint, convertWidthInCharsToPixels(iterator.next().toString().length()));
		}
		gridData.heightHint = convertHeightInCharsToPixels(5);
		fStylesViewer.getControl().setLayoutData(gridData);

		Composite editingComposite = createComposite(top, 1);
		((GridLayout) styleEditor.getLayout()).marginLeft = 5;
		createLabel(editingComposite, ""); //$NON-NLS-1$
		Button enabler = createCheckbox(editingComposite, DTDUIMessages.SyntaxColoringPage_2);
		enabler.setEnabled(false);
		enabler.setSelection(true);
		Composite editControls = createComposite(editingComposite, 2);
		((GridLayout) editControls.getLayout()).marginLeft = 20;

		fForegroundLabel = createLabel(editControls, SSEUIMessages.Foreground_UI_);
		((GridData) fForegroundLabel.getLayoutData()).verticalAlignment = SWT.CENTER;
		fForegroundLabel.setEnabled(false);

		fForegroundColorEditor = new ColorSelector(editControls);
		Button fForegroundColor = fForegroundColorEditor.getButton();
		GridData gd = new GridData(SWT.BEGINNING, SWT.FILL, false, false);
		fForegroundColor.setLayoutData(gd);
		fForegroundColorEditor.setEnabled(false);

		fBackgroundLabel = createLabel(editControls, SSEUIMessages.Background_UI_);
		((GridData) fBackgroundLabel.getLayoutData()).verticalAlignment = SWT.CENTER;
		fBackgroundLabel.setEnabled(false);

		fBackgroundColorEditor = new ColorSelector(editControls);
		Button fBackgroundColor = fBackgroundColorEditor.getButton();
		gd = new GridData(SWT.BEGINNING, SWT.FILL, false, false);
		fBackgroundColor.setLayoutData(gd);
		fBackgroundColorEditor.setEnabled(false);

		fBold = createCheckbox(editControls, DTDUIMessages.SyntaxColoringPage_3);
		fBold.setEnabled(false);
		((GridData) fBold.getLayoutData()).horizontalSpan = 2;
		fItalic = createCheckbox(editControls, DTDUIMessages.SyntaxColoringPage_4);
		fItalic.setEnabled(false);
		((GridData) fItalic.getLayoutData()).horizontalSpan = 2;
		fStrike = createCheckbox(editControls, DTDUIMessages.SyntaxColoringPage_5);
		fStrike.setEnabled(false);
		((GridData) fStrike.getLayoutData()).horizontalSpan = 2;
		fUnderline = createCheckbox(editControls, DTDUIMessages.SyntaxColoringPage_6);
		fUnderline.setEnabled(false);
		((GridData) fUnderline.getLayoutData()).horizontalSpan = 2;
		fClearStyle = new Button(editingComposite, SWT.PUSH);
		fClearStyle.setText(SSEUIMessages.Restore_Default_UI_); //$NON-NLS-1$ = "Restore Default"
		fClearStyle.setLayoutData(new GridData(SWT.BEGINNING));
		((GridData)fClearStyle.getLayoutData()).horizontalIndent = 20;
		fClearStyle.setEnabled(false);

		Composite sampleArea = createComposite(editor, 1);

		((GridLayout) sampleArea.getLayout()).marginLeft = 5;
		((GridLayout) sampleArea.getLayout()).marginTop = 5;
		createLabel(sampleArea, SSEUIMessages.Sample_text__UI_); //$NON-NLS-1$ = "&Sample text:"
		SourceViewer viewer = new SourceViewer(sampleArea, null, SWT.BORDER | SWT.LEFT_TO_RIGHT | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		fText = viewer.getTextWidget();
		GridData gridData3 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData3.widthHint = convertWidthInCharsToPixels(20);
		gridData3.heightHint = convertHeightInCharsToPixels(5);
		gridData3.horizontalSpan = 2;
		fText.setLayoutData(gridData3);
		fText.setEditable(false);
		fText.setFont(JFaceResources.getFont("org.eclipse.wst.sse.ui.textfont"));//$NON-NLS-1$
		fText.addKeyListener(getTextKeyListener());
		fText.addSelectionListener(getTextSelectionListener());
		fText.addMouseListener(getTextMouseListener());
		fText.addTraverseListener(getTraverseListener());
		setAccessible(fText, SSEUIMessages.Sample_text__UI_);
		fDocument = StructuredModelManager.getModelManager().createStructuredDocumentFor(ContentTypeIdForDTD.ContentTypeID_DTD);
		fDocument.set(getExampleText());
		viewer.setDocument(fDocument);

		top.setWeights(new int[]{1, 1});
		editor.setWeights(new int[]{1, 1});
		PlatformUI.getWorkbench().getHelpSystem().setHelp(pageComponent, IHelpContextIds.DTD_PREFWEBX_STYLES_HELPID);

		fStylesViewer.setInput(getStylePreferenceKeys());

		applyStyles();

		fStylesViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (!event.getSelection().isEmpty()) {
					Object o = ((IStructuredSelection) event.getSelection()).getFirstElement();
					String namedStyle = o.toString();
					activate(namedStyle);
					if (namedStyle == null)
						return;
				}
			}
		});

		fForegroundColorEditor.addListener(new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(ColorSelector.PROP_COLORCHANGE)) {
					Object o = ((IStructuredSelection) fStylesViewer.getSelection()).getFirstElement();
					String namedStyle = o.toString();
					String prefString = getOverlayStore().getString(namedStyle);
					String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
					if (stylePrefs != null) {
						String oldValue = stylePrefs[0];
						// open color dialog to get new color
						String newValue = ColorHelper.toRGBString(fForegroundColorEditor.getColorValue());

						if (!newValue.equals(oldValue)) {
							stylePrefs[0] = newValue;
							String newPrefString = ColorHelper.packStylePreferences(stylePrefs);
							getOverlayStore().setValue(namedStyle, newPrefString);
							applyStyles();
							fText.redraw();
						}
					}
				}
			}
		});

		fBackgroundColorEditor.addListener(new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(ColorSelector.PROP_COLORCHANGE)) {
					Object o = ((IStructuredSelection) fStylesViewer.getSelection()).getFirstElement();
					String namedStyle = o.toString();
					String prefString = getOverlayStore().getString(namedStyle);
					String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
					if (stylePrefs != null) {
						String oldValue = stylePrefs[1];
						// open color dialog to get new color
						String newValue = ColorHelper.toRGBString(fBackgroundColorEditor.getColorValue());

						if (!newValue.equals(oldValue)) {
							stylePrefs[1] = newValue;
							String newPrefString = ColorHelper.packStylePreferences(stylePrefs);
							getOverlayStore().setValue(namedStyle, newPrefString);
							applyStyles();
							fText.redraw();
							activate(namedStyle);
						}
					}
				}
			}
		});

		fBold.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				// get current (newly old) style
				Object o = ((IStructuredSelection) fStylesViewer.getSelection()).getFirstElement();
				String namedStyle = o.toString();
				String prefString = getOverlayStore().getString(namedStyle);
				String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
				if (stylePrefs != null) {
					String oldValue = stylePrefs[2];
					String newValue = String.valueOf(fBold.getSelection());
					if (!newValue.equals(oldValue)) {
						stylePrefs[2] = newValue;
						String newPrefString = ColorHelper.packStylePreferences(stylePrefs);
						getOverlayStore().setValue(namedStyle, newPrefString);
						applyStyles();
						fText.redraw();
					}
				}
			}
		});

		fItalic.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				// get current (newly old) style
				Object o = ((IStructuredSelection) fStylesViewer.getSelection()).getFirstElement();
				String namedStyle = o.toString();
				String prefString = getOverlayStore().getString(namedStyle);
				String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
				if (stylePrefs != null) {
					String oldValue = stylePrefs[3];
					String newValue = String.valueOf(fItalic.getSelection());
					if (!newValue.equals(oldValue)) {
						stylePrefs[3] = newValue;
						String newPrefString = ColorHelper.packStylePreferences(stylePrefs);
						getOverlayStore().setValue(namedStyle, newPrefString);
						applyStyles();
						fText.redraw();
					}
				}
			}
		});

		fStrike.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				// get current (newly old) style
				Object o = ((IStructuredSelection) fStylesViewer.getSelection()).getFirstElement();
				String namedStyle = o.toString();
				String prefString = getOverlayStore().getString(namedStyle);
				String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
				if (stylePrefs != null) {
					String oldValue = stylePrefs[4];
					String newValue = String.valueOf(fStrike.getSelection());
					if (!newValue.equals(oldValue)) {
						stylePrefs[4] = newValue;
						String newPrefString = ColorHelper.packStylePreferences(stylePrefs);
						getOverlayStore().setValue(namedStyle, newPrefString);
						applyStyles();
						fText.redraw();
					}
				}
			}
		});

		fUnderline.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				// get current (newly old) style
				Object o = ((IStructuredSelection) fStylesViewer.getSelection()).getFirstElement();
				String namedStyle = o.toString();
				String prefString = getOverlayStore().getString(namedStyle);
				String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
				if (stylePrefs != null) {
					String oldValue = stylePrefs[5];
					String newValue = String.valueOf(fUnderline.getSelection());
					if (!newValue.equals(oldValue)) {
						stylePrefs[5] = newValue;
						String newPrefString = ColorHelper.packStylePreferences(stylePrefs);
						getOverlayStore().setValue(namedStyle, newPrefString);
						applyStyles();
						fText.redraw();
					}
				}
			}
		});
		
		fClearStyle.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (fStylesViewer.getSelection().isEmpty())
					return;
				String namedStyle = ((IStructuredSelection) fStylesViewer.getSelection()).getFirstElement().toString();
				getOverlayStore().setToDefault(namedStyle);
				applyStyles();
				fText.redraw();
				activate(namedStyle);
			}
		});

		return pageComponent;
	}

	private Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.WRAP);
		label.setText(text);
		GridData data = new GridData(SWT.FILL, SWT.FILL, false, false);
		label.setLayoutData(data);
		label.setBackground(parent.getBackground());
		return label;
	}

	// protected Label createDescriptionLabel(Composite parent) {
	// return null;
	// }

	/**
	 * Set up all the style preference keys in the overlay store
	 */
	private OverlayKey[] createOverlayStoreKeys() {
		List overlayKeys = new ArrayList();

		Iterator i = getStylePreferenceKeys().iterator();
		while (i.hasNext()) {
			overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, (String) i.next()));
		}

		OverlayPreferenceStore.OverlayKey[] keys = new OverlayPreferenceStore.OverlayKey[overlayKeys.size()];
		overlayKeys.toArray(keys);
		return keys;
	}

	/**
	 * Creates the List viewer where we see the various syntax element display
	 * names--would it ever be a Tree like JDT's?
	 * 
	 * @param parent
	 * @return
	 */
	private StructuredViewer createStylesViewer(Composite parent) {
		StructuredViewer stylesViewer = new ListViewer(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		stylesViewer.setComparator(new ViewerComparator(Collator.getInstance()));
		stylesViewer.setLabelProvider(new LabelProvider() {
			public String getText(Object element) {
				Object description = fStyleToDescriptionMap.get(element);
				if (description != null)
					return description.toString();
				return super.getText(element);
			}
		});
		stylesViewer.setContentProvider(new ITreeContentProvider() {
			public void dispose() {
			}

			public Object[] getChildren(Object parentElement) {
				return getStylePreferenceKeys().toArray();
			}

			public Object[] getElements(Object inputElement) {
				return getChildren(inputElement);
			}

			public Object getParent(Object element) {
				return getStylePreferenceKeys();
			}

			public boolean hasChildren(Object element) {
				return false;
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		});
		return stylesViewer;
	}

	public void dispose() {
		if (fOverlayStore != null) {
			fOverlayStore.stop();
		}
		super.dispose();
	}

	protected IPreferenceStore doGetPreferenceStore() {
		return DTDUIPlugin.getDefault().getPreferenceStore();
	}

	private TextAttribute getAttributeFor(String namedStyle) {
		TextAttribute ta = new TextAttribute(fDefaultForeground, fDefaultBackground, SWT.NORMAL);

		if (namedStyle != null && fOverlayStore != null) {
			// note: "namedStyle" *is* the preference key
			String prefString = getOverlayStore().getString(namedStyle);
			String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
			if (stylePrefs != null) {
				RGB foreground = ColorHelper.toRGB(stylePrefs[0]);
				RGB background = ColorHelper.toRGB(stylePrefs[1]);

				int fontModifier = SWT.NORMAL;

				if (stylePrefs.length > 2) {
					boolean on = Boolean.valueOf(stylePrefs[2]).booleanValue();
					if (on)
						fontModifier = fontModifier | SWT.BOLD;
				}
				if (stylePrefs.length > 3) {
					boolean on = Boolean.valueOf(stylePrefs[3]).booleanValue();
					if (on)
						fontModifier = fontModifier | SWT.ITALIC;
				}
				if (stylePrefs.length > 4) {
					boolean on = Boolean.valueOf(stylePrefs[4]).booleanValue();
					if (on)
						fontModifier = fontModifier | TextAttribute.STRIKETHROUGH;
				}
				if (stylePrefs.length > 5) {
					boolean on = Boolean.valueOf(stylePrefs[5]).booleanValue();
					if (on)
						fontModifier = fontModifier | TextAttribute.UNDERLINE;
				}

				ta = new TextAttribute((foreground != null) ? EditorUtility.getColor(foreground) : null, (background != null) ? EditorUtility.getColor(background) : null, fontModifier);
			}
		}
		return ta;
	}

	private String getExampleText() {
		return DTDUIMessages.DTDColorPage_0;
	}

	private String getNamedStyleAtOffset(int offset) {
		// ensure the offset is clean
		if (offset >= fDocument.getLength())
			return getNamedStyleAtOffset(fDocument.getLength() - 1);
		else if (offset < 0)
			return getNamedStyleAtOffset(0);
		IStructuredDocumentRegion documentRegion = fDocument.getFirstStructuredDocumentRegion();
		while (documentRegion != null && !documentRegion.containsOffset(offset)) {
			documentRegion = documentRegion.getNext();
		}
		if (documentRegion != null) {
			// find the ITextRegion's Context at this offset
			ITextRegion interest = documentRegion.getRegionAtCharacterOffset(offset);
			if (interest == null)
				return null;
			if (offset > documentRegion.getTextEndOffset(interest))
				return null;
			String regionContext = interest.getType();
			if (regionContext == null)
				return null;
			// find the named style (internal/selectable name) for that
			// context
			String namedStyle = (String) fContextToStyleMap.get(regionContext);
			if (namedStyle != null) {
				return namedStyle;
			}
		}
		return null;
	}

	private OverlayPreferenceStore getOverlayStore() {
		return fOverlayStore;
	}

	private Collection getStylePreferenceKeys() {
		if (fStylePreferenceKeys == null) {
			List styles = new ArrayList();
			styles.add(IStyleConstantsDTD.DTD_COMMENT); //$NON-NLS-1$
			styles.add(IStyleConstantsDTD.DTD_DATA); //$NON-NLS-1$
			styles.add(IStyleConstantsDTD.DTD_DEFAULT); //$NON-NLS-1$
			styles.add(IStyleConstantsDTD.DTD_KEYWORD); //$NON-NLS-1$
			styles.add(IStyleConstantsDTD.DTD_STRING); //$NON-NLS-1$
			styles.add(IStyleConstantsDTD.DTD_SYMBOL); //$NON-NLS-1$
			styles.add(IStyleConstantsDTD.DTD_TAG); //$NON-NLS-1$
			styles.add(IStyleConstantsDTD.DTD_TAGNAME); //$NON-NLS-1$
			fStylePreferenceKeys = styles;
		}
		return fStylePreferenceKeys;
	}

	private KeyListener getTextKeyListener() {
		return new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.widget instanceof StyledText) {
					int x = ((StyledText) e.widget).getCaretOffset();
					selectColorAtOffset(x);
				}
			}

			public void keyReleased(KeyEvent e) {
				if (e.widget instanceof StyledText) {
					int x = ((StyledText) e.widget).getCaretOffset();
					selectColorAtOffset(x);
				}
			}
		};
	}

	private MouseListener getTextMouseListener() {
		return new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseUp(MouseEvent e) {
				if (e.widget instanceof StyledText) {
					int x = ((StyledText) e.widget).getCaretOffset();
					selectColorAtOffset(x);
				}
			}
		};
	}

	private SelectionListener getTextSelectionListener() {
		return new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				selectColorAtOffset(e.x);
				if (e.widget instanceof StyledText) {
					((StyledText) e.widget).setSelection(e.x);
				}
			}

			public void widgetSelected(SelectionEvent e) {
				selectColorAtOffset(e.x);
				if (e.widget instanceof StyledText) {
					((StyledText) e.widget).setSelection(e.x);
				}
			}
		};
	}

	private TraverseListener getTraverseListener() {
		return new TraverseListener() {
			/**
			 * @see org.eclipse.swt.events.TraverseListener#keyTraversed(TraverseEvent)
			 */
			public void keyTraversed(TraverseEvent e) {
				if (e.widget instanceof StyledText) {
					if ((e.detail == SWT.TRAVERSE_TAB_NEXT) || (e.detail == SWT.TRAVERSE_TAB_PREVIOUS))
						e.doit = true;
				}
			}
		};
	}

	public void init(IWorkbench workbench) {
		setDescription(SSEUIMessages.SyntaxColoring_Description);

		fStyleToDescriptionMap = new HashMap();
		fContextToStyleMap = new HashMap();

		initStyleToDescriptionMap();
		initRegionContextToStyleMap();

		fOverlayStore = new OverlayPreferenceStore(getPreferenceStore(), createOverlayStoreKeys());
		fOverlayStore.load();
		fOverlayStore.start();
	}

	private void initRegionContextToStyleMap() {
		fContextToStyleMap.put(DTDRegionTypes.CONTENT_EMPTY, IStyleConstantsDTD.DTD_DATA);
		fContextToStyleMap.put(DTDRegionTypes.CONTENT_ANY, IStyleConstantsDTD.DTD_DATA);
		fContextToStyleMap.put(DTDRegionTypes.CONTENT_PCDATA, IStyleConstantsDTD.DTD_DATA);
		fContextToStyleMap.put(DTDRegionTypes.NDATA_VALUE, IStyleConstantsDTD.DTD_DATA);
		fContextToStyleMap.put(DTDRegionTypes.NAME, IStyleConstantsDTD.DTD_DATA);
		fContextToStyleMap.put(DTDRegionTypes.ENTITY_PARM, IStyleConstantsDTD.DTD_DATA);

		fContextToStyleMap.put(DTDRegionTypes.ELEMENT_TAG, IStyleConstantsDTD.DTD_TAGNAME);
		fContextToStyleMap.put(DTDRegionTypes.ENTITY_TAG, IStyleConstantsDTD.DTD_TAGNAME);
		fContextToStyleMap.put(DTDRegionTypes.ATTLIST_TAG, IStyleConstantsDTD.DTD_TAGNAME);
		fContextToStyleMap.put(DTDRegionTypes.NOTATION_TAG, IStyleConstantsDTD.DTD_TAGNAME);

		fContextToStyleMap.put(DTDRegionTypes.CONNECTOR, IStyleConstantsDTD.DTD_SYMBOL);
		fContextToStyleMap.put(DTDRegionTypes.OCCUR_TYPE, IStyleConstantsDTD.DTD_SYMBOL);

		fContextToStyleMap.put(DTDRegionTypes.START_TAG, IStyleConstantsDTD.DTD_TAG);
		fContextToStyleMap.put(DTDRegionTypes.END_TAG, IStyleConstantsDTD.DTD_TAG);
		fContextToStyleMap.put(DTDRegionTypes.EXCLAMATION, IStyleConstantsDTD.DTD_TAG);

		fContextToStyleMap.put(DTDRegionTypes.COMMENT_START, IStyleConstantsDTD.DTD_COMMENT);
		fContextToStyleMap.put(DTDRegionTypes.COMMENT_CONTENT, IStyleConstantsDTD.DTD_COMMENT);
		fContextToStyleMap.put(DTDRegionTypes.COMMENT_END, IStyleConstantsDTD.DTD_COMMENT);

		fContextToStyleMap.put(DTDRegionTypes.SINGLEQUOTED_LITERAL, IStyleConstantsDTD.DTD_STRING);
		fContextToStyleMap.put(DTDRegionTypes.DOUBLEQUOTED_LITERAL, IStyleConstantsDTD.DTD_STRING);

		fContextToStyleMap.put(DTDRegionTypes.SYSTEM_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
		fContextToStyleMap.put(DTDRegionTypes.PUBLIC_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
		fContextToStyleMap.put(DTDRegionTypes.NDATA_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
		fContextToStyleMap.put(DTDRegionTypes.CDATA_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
		fContextToStyleMap.put(DTDRegionTypes.ID_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
		fContextToStyleMap.put(DTDRegionTypes.IDREF_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
		fContextToStyleMap.put(DTDRegionTypes.IDREFS_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
		fContextToStyleMap.put(DTDRegionTypes.ENTITY_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
		fContextToStyleMap.put(DTDRegionTypes.ENTITIES_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
		fContextToStyleMap.put(DTDRegionTypes.NMTOKEN_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
		fContextToStyleMap.put(DTDRegionTypes.NMTOKENS_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
		fContextToStyleMap.put(DTDRegionTypes.NOTATION_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
		fContextToStyleMap.put(DTDRegionTypes.REQUIRED_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
		fContextToStyleMap.put(DTDRegionTypes.IMPLIED_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
		fContextToStyleMap.put(DTDRegionTypes.FIXED_KEYWORD, IStyleConstantsDTD.DTD_KEYWORD);
	}

	private void initStyleToDescriptionMap() {
		fStyleToDescriptionMap.put(IStyleConstantsDTD.DTD_COMMENT, DTDUIMessages.DTDColorPage_1); //$NON-NLS-1$
		fStyleToDescriptionMap.put(IStyleConstantsDTD.DTD_DATA, DTDUIMessages.DTDColorPage_2); //$NON-NLS-1$
		fStyleToDescriptionMap.put(IStyleConstantsDTD.DTD_DEFAULT, DTDUIMessages.DTDColorPage_3); //$NON-NLS-1$
		fStyleToDescriptionMap.put(IStyleConstantsDTD.DTD_KEYWORD, DTDUIMessages.DTDColorPage_4); //$NON-NLS-1$
		fStyleToDescriptionMap.put(IStyleConstantsDTD.DTD_STRING, DTDUIMessages.DTDColorPage_5); //$NON-NLS-1$
		fStyleToDescriptionMap.put(IStyleConstantsDTD.DTD_SYMBOL, DTDUIMessages.DTDColorPage_6); //$NON-NLS-1$
		fStyleToDescriptionMap.put(IStyleConstantsDTD.DTD_TAG, DTDUIMessages.DTDColorPage_7); //$NON-NLS-1$
		fStyleToDescriptionMap.put(IStyleConstantsDTD.DTD_TAGNAME, DTDUIMessages.DTDColorPage_8); //$NON-NLS-1$
	}

	protected void performDefaults() {
		super.performDefaults();
		getOverlayStore().loadDefaults();
		applyStyles();
		fStylesViewer.setSelection(StructuredSelection.EMPTY);
		activate(null);
		fText.redraw();
	}

	public boolean performOk() {
		getOverlayStore().propagate();

		DTDUIPlugin.getDefault().savePluginPreferences();
		SSEUIPlugin.getDefault().savePluginPreferences();
		return true;
	}

	private void selectColorAtOffset(int offset) {
		String namedStyle = getNamedStyleAtOffset(offset);
		if (namedStyle != null) {
			fStylesViewer.setSelection(new StructuredSelection(namedStyle));
			fStylesViewer.reveal(namedStyle);
		}
		else {
			fStylesViewer.setSelection(StructuredSelection.EMPTY);
		}
		activate(namedStyle);
	}

	/**
	 * Specifically set the reporting name of a control for accessibility
	 */
	private void setAccessible(Control control, String name) {
		if (control == null)
			return;
		final String n = name;
		control.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				if (e.childID == ACC.CHILDID_SELF)
					e.result = n;
			}
		});
	}
}
