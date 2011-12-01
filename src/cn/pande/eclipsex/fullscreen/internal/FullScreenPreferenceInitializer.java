package cn.pande.eclipsex.fullscreen.internal;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;


public class FullScreenPreferenceInitializer extends
		AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		ScopedPreferenceStore preferences = new ScopedPreferenceStore(
				new InstanceScope(), FullScreenActivator.ID);
		preferences.setDefault(FullScreenActivator.HIDE_MENU_BAR, true);
	}

}
