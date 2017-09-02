package net.christophermerrill.ShadowboxFx;

import javafx.css.*;
import javafx.scene.*;
import javafx.scene.control.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ShadowboxedAlert
    {
    public ShadowboxedAlert(Alert.AlertType type, String header, String message, ButtonType... buttons)
        {
        if (type.equals(Alert.AlertType.NONE))
            throw new IllegalArgumentException("Unsupported AlertType: " + type);
        _dialog = new DialogPane();
        _dialog.setHeaderText(header);
        _dialog.setContentText(message);
        _dialog.getStyleClass().add("alert");
        _dialog.getStyleClass().add(type.name().toLowerCase());

        if (buttons == null || buttons.length < 1)
            {
            if (type.equals(Alert.AlertType.CONFIRMATION))
                buttons = new ButtonType[]{ButtonType.OK, ButtonType.CANCEL};
            else
                buttons = new ButtonType[]{ButtonType.OK};
            }
        for (ButtonType button_type : buttons)
            _dialog.getButtonTypes().add(button_type);

        _dialog.pseudoClassStateChanged(HEADER_CLASS, true);
        _dialog.pseudoClassStateChanged(NOHEADER_CLASS, false);
        }

    public void showInShadowbox(Node node)
        {
        _shadowbox = ShadowboxPane.findFromNode(node);
        _shadowbox.showOverlayOnShadowbox(_dialog);

        setupButtonListeners();
        }

    private void setupButtonListeners()
        {
        for (ButtonType type : _dialog.getButtonTypes())
            {
            Button button = (Button) _dialog.lookupButton(type);
            button.setOnAction(event ->
                {
                _button_pushed = type;
                boolean close = true;
                if (_action != null)
                    close = _action.pushed(type);
                if (close)
                    _shadowbox.removeOverlay();
                });
            }
        }

    public ButtonType getResult()
        {
        return _button_pushed;
        }

    public void onButtonPush(OnButtonPush action)
        {
        _action = action;
        }

    public interface OnButtonPush
        {
        /**
         * Return true to accept and close the alert.
         */
        boolean pushed(ButtonType button);
        }

    private final DialogPane _dialog;
    private ShadowboxPane _shadowbox;
    private ButtonType _button_pushed = null;
    private OnButtonPush _action;

    private final static PseudoClass HEADER_CLASS = PseudoClass.getPseudoClass("header");
    private final static PseudoClass NOHEADER_CLASS = PseudoClass.getPseudoClass("no-header");
    }


