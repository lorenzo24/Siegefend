package sgf.controller.thread;

import java.util.ArrayList;
import java.util.List;

import sgf.controller.map.MapFileLoader;
import sgf.model.Enemy;
import sgf.model.EnemyImpl;
import sgf.model.EnemyType;
import sgf.model.Position;
import sgf.view.GameView;

/**
 * This class refers to the implementation of the interface GameController.
 * The goal of this class is to manage the whole game thread.
 */
public class GameControllerImpl implements GameController {
    private GameView gameView;
    private final MapFileLoader mapLoader;
    private volatile boolean threadRun = true; // Boolean that manages the thread loop.
    private final List<Enemy> enemyList = new ArrayList<>(); // TO DELETE
    private final EnemyThreadController enemyThread; // TO DELETE
    private boolean isControllerSet;


    /**
     * Constructor that starts the thread.
     */
    public GameControllerImpl() {
        this.mapLoader = new MapFileLoader(1);       // Loads the correct map according to the current level.
        this.fillEnemyList(); // TO DELETE
        this.enemyThread = new EnemyThreadController(this.enemyList); // TO DELETE
        this.startGameThread();
    }

    private void fillEnemyList() { // TO DELETE
        this.enemyList.add(new EnemyImpl(0, new Position(20, 20), 100, 100, EnemyType.TANK));
        this.enemyList.add(new EnemyImpl(0, new Position(50, 50), 100, 100, EnemyType.TANK));
        this.enemyList.add(new EnemyImpl(0, new Position(80, 80), 100, 100, EnemyType.TANK));
        this.enemyList.add(new EnemyImpl(0, new Position(110, 110), 100, 100, EnemyType.TANK));
    }

    private void startGameThread() {
        final Thread gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int ups = 0;    // Updates per second.
                long lastTime = System.currentTimeMillis();
                while (threadRun) {
                    // Print how many updates has been done in 1 second.
                    if (System.currentTimeMillis() - lastTime >= 1000) {
                        System.out.print("UPS:" + ups + "\n");
                        ups = 0;
                        lastTime = System.currentTimeMillis();
                    }
                    ups++;
                    try {
                        gameView.update();
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        gameThread.start();
    }

    @Override
    public void stopThread() {
        this.threadRun = false;
    }

    @Override
    public void resumeThread() {
        this.threadRun = true;
    }

    @Override
    public void setView(final GameView view) {
        if (!isControllerSet) {
            this.isControllerSet = true;
            this.gameView = view;
        }
    }
}
