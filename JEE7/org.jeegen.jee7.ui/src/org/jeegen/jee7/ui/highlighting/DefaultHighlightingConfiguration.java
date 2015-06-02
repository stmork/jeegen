package org.jeegen.jee7.ui.highlighting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class DefaultHighlightingConfiguration extends org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration
{
	public static final String KEYWORD_ID = "keyword";

	@Override
	public TextStyle keywordTextStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(new RGB(0x12, 0x0a, 0x8f));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}
}
