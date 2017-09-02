package net.christophermerrill.ShadowboxFx;

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ShadowboxPane extends StackPane
    {
    public void showOverlayOnShadowbox(Node overlay)
        {
        CenteredPane glass = new CenteredPane();
        glass.getChildren().add(overlay);

        glass.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
        StackPane.setAlignment(glass, Pos.CENTER);

        getChildren().add(glass);
        _glass = glass;
        }

    public void removeOverlay()
        {
        if (_glass != null)
            {
            Platform.runLater(() ->
                {
                getChildren().remove(_glass);
                _glass = null;
                });
            }
        }

    public boolean isShowingOverlay()
        {
        return _glass != null;
        }

    @SuppressWarnings("WeakerAccess") // public API
    public static ShadowboxPane findFromNode(Node node)
        {
        while (!(node instanceof ShadowboxPane))
            {
            node = node.getParent();
            if (node == null)
                throw new IllegalArgumentException("Unable to locate a ShadowboxDialogPane in the ancestry of the provided node");
            }
        return (ShadowboxPane) node;
        }

    Node _glass = null;
    }


