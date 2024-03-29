package cn.pande.eclipsex.fullscreen.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.Preferences;

public class FullScreenActivator extends AbstractUIPlugin {

	public static final String ID = "cn.pande.eclipsex.fullscreen"; //$NON-NLS-1$
	public static final String HIDE_MENU_BAR = "hide_menu_bar"; //$NON-NLS-1$

	private static FullScreenActivator INSTANCE;
	private Map controlLists;
	private Map menuBars;

	public static FullScreenActivator getDefault() {
		return INSTANCE;
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		controlLists = new HashMap();
		menuBars = new HashMap();
		INSTANCE = this;
	}

	public void stop(BundleContext context) throws Exception {
		INSTANCE = null;
		controlLists.clear();
		controlLists = null;
		menuBars.clear();
		menuBars = null;
		super.stop(context);
	}

	/**
	 * Set the workbench window full screen state.
	 * 
	 * @param window the workbench window
	 * @param fullScreen new full screen state
	 */
	public void setFullScreen(Shell mainShell, boolean fullScreen) {
		if (mainShell == null || mainShell.isDisposed())
			return;

		if (fullScreen) {
			List controls = hideTrimControls(mainShell);
			controlLists.put(mainShell, controls);
			if (getHideMenuBar()) {
				Menu menuBar = mainShell.getMenuBar();
				mainShell.setMenuBar(null);
				menuBars.put(mainShell, menuBar);
			}
		} else {
			showTrimControls(mainShell);
			controlLists.remove(mainShell);
			Menu menuBar = mainShell.getMenuBar();
			if (menuBar == null) {
				menuBar = (Menu) menuBars.get(mainShell);
				mainShell.setMenuBar(menuBar);
				menuBars.remove(mainShell);
			}
		}

		mainShell.setFullScreen(fullScreen);
		mainShell.layout();
	}

	private void showTrimControls(Shell mainShell) {
		List controls = (List) controlLists.get(mainShell);
		if (controls != null) {
			for (int i = 0; i < controls.size(); i++) {
				Control control = (Control) controls.get(i);
				control.setVisible(true);
			}
		}
	}

	private List hideTrimControls(Shell mainShell) {
		List controls = new ArrayList();
		Control[] children = mainShell.getChildren();
		for (int i = 0; i < children.length; i++) {
			Control child = children[i];
			if (child.isDisposed() || !child.isVisible())
				continue;
			if (child.getClass().equals(Canvas.class))
				continue;
			if (child.getClass().equals(Composite.class))
				continue;
			child.setVisible(false);
			controls.add(child);
		}
		return controls;
	}

	private boolean getHideMenuBar() {
		Preferences preferences = Platform.getPreferencesService()
				.getRootNode().node(InstanceScope.SCOPE).node(ID);
		return preferences.getBoolean(HIDE_MENU_BAR, true);
	}
}
