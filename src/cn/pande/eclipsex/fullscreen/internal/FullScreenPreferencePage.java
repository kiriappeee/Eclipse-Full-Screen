package cn.pande.eclipsex.fullscreen.internal;

import java.io.IOException;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class FullScreenPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	private ScopedPreferenceStore preferences;

	public FullScreenPreferencePage() {
		super(GRID);
		preferences = new ScopedPreferenceStore(new InstanceScope(),
				FullScreenActivator.ID);
		setPreferenceStore(preferences);
	}

	protected void createFieldEditors() {
		BooleanFieldEditor boolEditor = new BooleanFieldEditor(
				FullScreenActivator.HIDE_MENU_BAR,
				Messages.FullScreenPreferencePage_0, getFieldEditorParent());
		addField(boolEditor);
	}

	public void init(IWorkbench workbench) {
	}

	public boolean performOk() {
		try {
			preferences.save();
		} catch (IOException e) {
		}
		return super.performOk();
	}
}
