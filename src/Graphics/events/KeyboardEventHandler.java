package Graphics.events;

import Graphics.CanvasWindow;

/**
 * A listener that can receive events when the mouse moves.
 *
 * @author Paul Cantrell
 * @see CanvasWindow#onKeyDown(KeyboardEventHandler)
 * @see CanvasWindow#onKeyUp(KeyboardEventHandler)
 */
public interface KeyboardEventHandler {
    void handleEvent(KeyboardEvent event);
}
