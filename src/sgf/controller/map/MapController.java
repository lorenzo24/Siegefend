package sgf.controller.map;


import sgf.controller.Controller;
import sgf.model.GridPosition;
import sgf.model.Map;
import sgf.model.Position;
import sgf.view.MapView;


/**
 *
 */
public interface MapController extends Controller<MapView> {
    /**
     * Simple getter for map field.
     * @return the map.
     */
    Map getMap();

    /**
     * Method to convert a GridPosition into a Position.
     * @param position Is the position expressed as GridPosition.
     * @return the equivalent position expressed as Position.
     */
    Position convertAGridPosition(GridPosition position);

}
