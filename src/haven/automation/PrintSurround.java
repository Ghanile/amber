//[Cosper] PrintSurround

package haven.automation;


import haven.*;

import static haven.OCache.posres;

public class PrintSurround implements Runnable {
    private GameUI gui;
    private Gob object;

    public PrintSurround(GameUI gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
		gui.error("******************************************");
		synchronized (gui.map.glob.oc) {
			for (Gob gob : gui.map.glob.oc) {
				try {
					Resource res = gob.getres();
					gui.error(res.toString());
				}catch (Exception e) {
				}
			}
		gui.error("******************************************");
		}
	}
}
