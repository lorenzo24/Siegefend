package sgf.model.bullet;

import sgf.model.map.Position;

/**
 * Interface that represents a bullet.
 */
public interface Bullet {

    /**
     * @return speed.
     */
    double getSpeed();

    /**
     * @return bullet's position.
     */
    Position getPosition();

    /**
     * @return target's position.
     */
    Position getTargetPosition();

    /**
     * @return damage. 
     */
    double getDamage();

    /**
     * Moves the bullet to the given coordinate.
     * @param x the 
     * @param y
     */
    void move(double x, double y);

}
