//[Cosper] Tester

package haven.automation;


import haven.*;

import static haven.OCache.posres;

public class Tester implements Runnable {
    private GameUI gui;
    private Gob object;

    public Tester(GameUI gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
        object = null;
        synchronized (gui.map.glob.oc) {
            for (Gob gob : gui.map.glob.oc) {
                Resource res = null;
                try {
                    res = gob.getres();
                } catch (Loading l) {
                }
                if (res != null) {
                    if (res.name.startsWith("gfx/terobjs/trees") && !res.name.contains("log") && !res.name.startsWith("gfx/terobjs/trees/stump")) {
                        double distFromPlayer = gob.rc.dist(gui.map.player().rc);
                        if (distFromPlayer <= 20 * 11 && (object == null || distFromPlayer < object.rc.dist(gui.map.player().rc)))
                            object = gob;
                    }
                }
            }
        }
        if (object == null){
			gui.error("Could not find trees.");
            return;
		}else{
			int initialSpace = gui.maininv.getFreeSpace();
			//gui.error("Free space: " + initialSpace);
			//gui.error(object.getres().toString());
			
			FlowerMenu.setNextSelection("Take branch");
			gui.map.wdgmsg("click", object.sc, object.rc.floor(posres), 3, 0, 0, (int) object.id, object.rc.floor(posres), 0, -1);
			
			long now = System.currentTimeMillis();
			while (initialSpace <= gui.maininv.getFreeSpace() && (System.currentTimeMillis() - now) < 5000) {
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					gui.error(e.toString());
				}
			}
			if(initialSpace > gui.maininv.getFreeSpace()){
				gui.error("Got a stick");
			}else{
				gui.error("No stick :(");
			}
			
			WItem branch = gui.maininv.getItemPartial("Branch");
			gui.error(branch.toString());
			branch.item.wdgmsg("take", new Coord(branch.item.sz.x / 2, branch.item.sz.y / 2));

		}
	}
}
