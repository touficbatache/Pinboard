package pobj.pinboard.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolImage;
import pobj.pinboard.editor.tools.ToolRect;

import java.io.FileNotFoundException;

public class EditorWindow implements EditorInterface {
    private Board board;
    private Tool tool;
    private Canvas canvas;

    public EditorWindow(Stage stage) {
        board = new Board();

        ToolRect toolRect = new ToolRect();
        ToolEllipse toolEllipse = new ToolEllipse();
        tool = toolRect;

        stage.setTitle("PinBoard");

        VBox vBox = new VBox();

        Label label = new Label("Pin Board");

        EventHandler<ActionEvent> selectRectangleTool = e -> {
            tool = toolRect;
            label.textProperty().set("Rectangle tool");
        };

        EventHandler<ActionEvent> selectEllipseTool = e -> {
            tool = toolEllipse;
            label.textProperty().set("Ellipse tool");
        };

        EventHandler<ActionEvent> selectImageTool = e -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose image");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
                tool = new ToolImage(fileChooser.showOpenDialog(stage));
                label.textProperty().set("Image tool");
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
        menuItemNew.setOnAction(e -> stage.close());

        menuFile.getItems().addAll(menuItemNew, menuItemClose);

        // --------- Menu "Edit" ---------
        Menu menuEdit = new Menu("Edit");

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

        menuTools.getItems().addAll(menuItemRectangle, menuItemEllipse);

        // --------- MenuBar ---------
        MenuBar menu = new MenuBar(menuFile, menuEdit, menuTools);

        Button btnBox = new Button("Box");
        btnBox.setOnAction(selectRectangleTool);

        Button btnEllipse = new Button("Ellipse");
        btnEllipse.setOnAction(selectEllipseTool);

        Button btnImage = new Button("Img...");
        btnImage.setOnAction(selectImageTool);

        ToolBar toolbar = new ToolBar(btnBox, btnEllipse, btnImage);

        canvas = new Canvas(800, 600);

        canvas.setOnMousePressed(e -> tool.press(this, e));

        canvas.setOnMouseDragged(e -> {
            tool.drag(this, e);
            tool.drawFeedback(this, canvas.getGraphicsContext2D());
        });

        canvas.setOnMouseReleased(e -> {
            tool.release(this, e);
            draw();
        });

        vBox.getChildren().addAll(menu, toolbar, canvas, new Separator(), label);

        Scene scene = new Scene(vBox);

        stage.setScene(scene);

        stage.show();
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Selection getSelection() {
        return null;
    }

    @Override
    public CommandStack getUndoStack() {
        return null;
    }

    private void draw() {
        board.draw(canvas.getGraphicsContext2D());
    }
}
