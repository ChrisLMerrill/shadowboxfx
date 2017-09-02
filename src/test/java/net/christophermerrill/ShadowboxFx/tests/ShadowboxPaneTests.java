package net.christophermerrill.ShadowboxFx.tests;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import net.christophermerrill.ShadowboxFx.*;
import org.junit.*;
import org.testfx.framework.junit.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ShadowboxPaneTests extends ApplicationTest
    {
    @Test
    public void clickability()
        {
        _overlay_action = () ->
            {
            Label overlay = new Label("show me\nin the\nshadowbox");
            overlay.setStyle("-fx-background-color: green");
            overlay.setId("overlay");
            overlay.setOnMouseClicked(event1 ->
                {
                System.out.println("overlay clicked");
                _overlay_clicked = true;
                });
            _shadowbox.showOverlayOnShadowbox(overlay);
            };

        String to_click = "cell1";
        clickOn(to_click);
        Assert.assertEquals("Unable to click on underlying comonent", to_click, _clicked);

        clickOn("#overlay");
        Assert.assertTrue("Unable to click overlay component", _overlay_clicked);

        _clicked = null;
        clickOn("cell9");
        Assert.assertNull("Was allowed to click through the overlay", _clicked);

        _shadowbox.removeOverlay();

        clickOn("cell9");
        Assert.assertNotNull("Was unable to click button after removing overlay", _clicked);
        }

    @Test
    public void alertInfo()
        {
        _overlay_action = () ->
            {
            _alert = new ShadowboxedAlert(Alert.AlertType.INFORMATION, "alert title", "alert message");
            _alert.showInShadowbox(_shadowbox);
            };

        clickOn("cell1");
        Assert.assertTrue(_shadowbox.isShowingOverlay());
        Assert.assertNotNull(lookup("alert title").query());
        Assert.assertNotNull(lookup("alert message").query());
        clickOn(ButtonType.OK.getText());
        Assert.assertFalse(_shadowbox.isShowingOverlay());
        Assert.assertEquals(ButtonType.OK, _alert.getResult());
        }

    @Test
    public void alertConfirmationCancel()
        {
        _overlay_action = () ->
            {
            _alert = new ShadowboxedAlert(Alert.AlertType.CONFIRMATION, "alert title", "alert message");
            _alert.showInShadowbox(_shadowbox);
            };

        clickOn("cell1");
        Assert.assertNotNull(lookup(ButtonType.OK.getText()).query());
        Assert.assertNotNull(lookup(ButtonType.CANCEL.getText()).query());
        clickOn(ButtonType.CANCEL.getText());
        Assert.assertEquals(ButtonType.CANCEL, _alert.getResult());
        }

    @Override
    public void start(Stage stage) throws Exception
        {
        _shadowbox = new ShadowboxPane();

        GridPane grid = new GridPane();
        for (int column = 0; column < 3; column++)
            for (int row = 0; row < 3; row++)
                {
                String cell_name = "cell" + ((column * 3 + 1) + row);
                Button button = new Button(cell_name);
                button.setId(cell_name);
                GridPane.setHgrow(button, Priority.ALWAYS);
                GridPane.setVgrow(button, Priority.ALWAYS);
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                grid.add(button, column, row);

                button.setOnAction(event ->
                    {
                    System.out.println(cell_name + " clicked");
                    _clicked = cell_name;

                    if (_overlay_action != null)
                        _overlay_action.showOverlay();
                    });
                }
        _shadowbox.getChildren().add(grid);

        _shadowbox.setPrefWidth(400);
        _shadowbox.setPrefHeight(400);
        stage.setScene(new Scene(_shadowbox));
        stage.show();
        }

    private ShadowboxPane _shadowbox;
    private String _clicked = null;
    private boolean _overlay_clicked = false;
    private OverlayAction _overlay_action = null;
    private ShadowboxedAlert _alert;

    private interface OverlayAction
        {
        void showOverlay();
        }
    }


