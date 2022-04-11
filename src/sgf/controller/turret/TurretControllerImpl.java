package sgf.controller.turret;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;
import java.util.Optional;

import sgf.controller.shop.ShopController;
import sgf.model.map.GridPosition;
import sgf.model.map.Map;
import sgf.model.turret.Turret;
import sgf.view.turret.TurretView;

/**
 * 
 * 
 *
 */
public class TurretControllerImpl implements TurretController {

    private TurretView turretView;
    private boolean isViewSet;
    private Map map;
    private ShopController shopController;
    private java.util.Map<GridPosition, Turret> turrets;
    private final Semaphore semaphore;

    /**
     * 
     * @param map
     * @param shopController
     */
    public TurretControllerImpl(final Map map, final ShopController shopController, final Semaphore semaphore) {
        this.map = map;
        this.shopController = shopController;
        this.semaphore = semaphore;
    }

    @Override
    public void setView(final TurretView view) {
        if (!isViewSet) {
            this.isViewSet = true;
            this.turretView = view;
        }
    }

    @Override
    public void addSelectedTurret(final GridPosition gpos) {
        if (shopController.getSelectedTurret().isPresent() && isTileEmpty(gpos)) {
            final Optional<Turret> t = shopController.buy();
            if (t.isPresent()) {
                semaphore.acquireUninterruptibly();
                turrets.put(new GridPosition(gpos), t.get().getClone());
                semaphore.release();
            }
        }
    }

    @Override
    public boolean isTileEmpty(final GridPosition gpos) {
        return turrets.containsKey(gpos);
    }

    @Override
    public Iterator<Entry<GridPosition, Turret>> getTurretsIterator() {
        return turrets.entrySet().iterator();
    }

}
