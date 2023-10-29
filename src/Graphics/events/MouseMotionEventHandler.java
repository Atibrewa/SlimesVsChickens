package Graphics.events;

import Graphics.CanvasWindow;

/**
 * A listener that can receive events when the mouse moves.
 *
 * @author Paul Cantrell
 * @see CanvasWindow#onMouseMove(MouseMotionEventHandler)
 * @see CanvasWindow#onDrag(MouseMotionEventHandler)
 */
public interface MouseMotionEventHandler {
    void handleEvent(MouseMotionEvent event);
}
