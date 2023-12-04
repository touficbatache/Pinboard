package pobj.pinboard.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.test.SelectionTest;
import pobj.pinboard.editor.tools.*;

import java.io.FileNotFoundException;
import java.util.List;

public class EditorWindow implements EditorInterface, ClipboardListener {
    private final Board board;
    private final Selection selection;
    private Color color;
    private Tool tool;
    private final Canvas canvas;

    private final Label label;
    private final MenuItem menuItemPaste;

    public EditorWindow(Stage stage) {
        board = new Board();

        selection = new Selection();

        Clipboard.getInstance().addListener(this);

        color = Color.BLUE;

        ToolRect toolRect = new ToolRect(color);
        ToolEllipse toolEllipse = new ToolEllipse(color);
        ToolSelection toolSelection = new ToolSelection();

        stage.setTitle("PinBoard");

        VBox vBox = new VBox();

        label = new Label();

        tool = toolRect;

        updateLabelText();

        EventHandler<ActionEvent> selectRectangleTool = e -> {
            tool = toolRect;
            tool.setColor(color);
            updateLabelText();
        };

        EventHandler<ActionEvent> selectEllipseTool = e -> {
            tool = toolEllipse;
            tool.setColor(color);
            updateLabelText();
        };

        EventHandler<ActionEvent> selectSelectionTool = e -> {
            tool = toolSelection;
            updateLabelText();
        };

        EventHandler<ActionEvent> selectImageTool = e -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose image");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
                tool = new ToolImage(fileChooser.showOpenDialog(stage));
                updateLabelText();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        };

        // --------- Menu "File" ---------
        Menu menuFile = new Menu("File");

        // Menu "File" -> Item "New"
        MenuItem menuItemNew = new MenuItem("New");
        menuItemNew.setOnAction(e -> new EditorWindow(new Stage()));

        // Menu "File" -> Item "Close"
        MenuItem menuItemClose = new MenuItem("Close");
        menuItemClose.setOnAction(e -> stage.close());

        menuFile.getItems().addAll(menuItemNew, menuItemClose);

        // --------- Menu "Edit" ---------
        Menu menuEdit = new Menu("Edit");

        // Menu "Edit" -> Item "Copy"
        MenuItem menuItemCopy = new MenuItem("Copy");
        menuItemCopy.setOnAction(e -> {
            Clipboard.getInstance().clear();
            Clipboard.getInstance().copyToClipboard(getSelection().getContents());
        });

        // Menu "Edit" -> Item "Paste"
        menuItemPaste = new MenuItem("Paste");
        menuItemPaste.setOnAction(e -> {
            getSelection().clear();
            getBoard().addClip(Clipboard.getInstance().copyFromClipboard());
            Clipboard.getInstance().clear();
            draw();
        });
        menuItemPaste.setDisable(Clipboard.getInstance().isEmpty());

        // Menu "Edit" -> Item "Delete"
        MenuItem menuItemDelete = new MenuItem("Delete");
        menuItemDelete.setOnAction(e -> {
            getSelection().getContents().forEach((clip -> getBoard().removeClip(clip)));
            getSelection().clear();
            draw();
        });

        menuEdit.getItems().addAll(menuItemCopy, menuItemPaste, menuItemDelete);

        // --------- Menu "Tools" ---------
        Menu menuTools = new Menu("Tools");

        // Menu "Tools" -> Item "Rectangle"
        MenuItem menuItemRectangle = new MenuItem("Rectangle");
        menuItemRectangle.setOnAction(selectRectangleTool);

        // Menu "Tools" -> Item "Ellipse"
        MenuItem menuItemEllipse = new MenuItem("Ellipse");
        menuItemEllipse.setOnAction(selectEllipseTool);

        // Menu "Tools" -> Item "Image"
        MenuItem menuItemImage = new MenuItem("Image");
        menuItemImage.setOnAction(selectImageTool);

        // Menu "Tools" -> Item "Selection"
        MenuItem menuItemSelection = new MenuItem("Selection");
        menuItemSelection.setOnAction(selectSelectionTool);

        menuTools.getItems().addAll(menuItemRectangle, menuItemEllipse, menuItemImage, menuItemSelection);

        // --------- MenuBar ---------
        MenuBar menu = new MenuBar(menuFile, menuEdit, menuTools);

        Button btnBox = new Button("Box");
        btnBox.setOnAction(selectRectangleTool);

        Button btnEllipse = new Button("Ellipse");
        btnEllipse.setOnAction(selectEllipseTool);

        Button btnImage = new Button("Img...");
        btnImage.setOnAction(selectImageTool);

        Button btnSelection = new Button("Select");
        btnSelection.setOnAction(selectSelectionTool);

        // --------- Color Pickers ---------

        Rectangle bgRedPicker = new Rectangle();
        bgRedPicker.setWidth(20);
        bgRedPicker.setHeight(20);
        bgRedPicker.setFill(Color.RED);
        Button btnRedPicker = new Button("Red", bgRedPicker);
        btnRedPicker.setOnAction(e -> {
            color = Color.RED;
            tool.setColor(color);
            updateLabelText();
        });

        Rectangle bgBluePicker = new Rectangle();
        bgBluePicker.setWidth(20);
        bgBluePicker.setHeight(20);
        bgBluePicker.setFill(Color.BLUE);
        Button btnBluePicker = new Button("Blue", bgBluePicker);
        btnBluePicker.setOnAction(e -> {
            color = Color.BLUE;
            tool.setColor(color);
            updateLabelText();
        });

        Rectangle bgGreenPicker = new Rectangle();
        bgGreenPicker.setWidth(20);
        bgGreenPicker.setHeight(20);
        bgGreenPicker.setFill(Color.GREEN);
        Button btnGreenPicker = new Button("Green", bgGreenPicker);
        btnGreenPicker.setOnAction(e -> {
            color = Color.GREEN;
            tool.setColor(color);
            updateLabelText();
        });

        Rectangle bgYellowPicker = new Rectangle();
        bgYellowPicker.setWidth(20);
        bgYellowPicker.setHeight(20);
        bgYellowPicker.setFill(Color.YELLOW);
        Button btnYellowPicker = new Button("Yellow", bgYellowPicker);
        btnYellowPicker.setOnAction(e -> {
            color = Color.YELLOW;
            tool.setColor(color);
            updateLabelText();
        });

        Rectangle bgOrangePicker = new Rectangle();
        bgOrangePicker.setWidth(20);
        bgOrangePicker.setHeight(20);
        bgOrangePicker.setFill(Color.ORANGE);
        Button btnOrangePicker = new Button("Orange", bgOrangePicker);
        btnOrangePicker.setOnAction(e -> {
            color = Color.ORANGE;
            tool.setColor(color);
            updateLabelText();
        });

        Rectangle bgPurplePicker = new Rectangle();
        bgPurplePicker.setWidth(20);
        bgPurplePicker.setHeight(20);
        bgPurplePicker.setFill(Color.PURPLE);
        Button btnPurplePicker = new Button("Purple", bgPurplePicker);
        btnPurplePicker.setOnAction(e -> {
            color = Color.PURPLE;
            tool.setColor(color);
            updateLabelText();
        });

        ToolBar toolbar = new ToolBar(
                btnBox,
                btnEllipse,
                btnImage,
                btnSelection,
                btnRedPicker,
                btnBluePicker,
                btnGreenPicker,
                btnYellowPicker,
                btnOrangePicker,
                btnPurplePicker
        );

        canvas = new Canvas(800, 600);

        canvas.setOnMousePressed(e -> {
            tool.press(this, e);
            draw();
        });

        canvas.setOnMouseDragged(e -> {
            tool.drag(this, e);
            draw();
        });

        canvas.setOnMouseReleased(e -> {
            tool.release(this, e);
            draw();
        });

        vBox.getChildren().addAll(menu, toolbar, canvas, new Separator(), label);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> Clipboard.getInstance().removeListener(this));
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Selection getSelection() {
        return selection;
    }

    @Override
    public CommandStack getUndoStack() {
        return null;
    }

    private void draw() {
        board.draw(canvas.getGraphicsContext2D());
        tool.drawFeedback(this, canvas.getGraphicsContext2D());
    }

    @Override
    public void clipboardChanged() {
        if (menuItemPaste != null) {
            menuItemPaste.setDisable(Clipboard.getInstance().isEmpty());
        }
    }

    private void updateLabelText() {
        String toolText = "No tool selected";
        String colorText = "No color selected";

        if (tool instanceof ToolRect) {
            toolText = "Rectangle tool";
        } else if (tool instanceof ToolEllipse) {
            toolText = "Ellipse tool";
        } else if (tool instanceof ToolImage) {
            toolText = "Image tool";
        } else if (tool instanceof ToolSelection) {
            toolText = "Selection tool";
        }

        if (color == Color.RED) {
            colorText = "RED color";
        } else if (color == Color.BLUE) {
            colorText = "BLUE color";
        } else if (color == Color.GREEN) {
            colorText = "GREEN color";
        } else if (color == Color.YELLOW) {
            colorText = "YELLOW color";
        } else if (color == Color.ORANGE) {
            colorText = "ORANGE color";
        } else if (color == Color.PURPLE) {
            colorText = "PURPLE color";
        }

        label.textProperty().set(toolText + " | " + colorText);
    }
}
