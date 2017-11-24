package projavafx.stagecoach.ui;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import sun.awt.geom.AreaOp;

public class ExSC extends Application
{
    StringProperty window_title = new SimpleStringProperty();
    Text label_posX, label_posY, label_width, label_height, label_focus;
    CheckBox cb_resizable, cb_fscreen;
    double dragX, dragY;

    public static void main(String... args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        List<String> input_params = getParameters().getUnnamed();
        StageStyle stst = StageStyle.DECORATED;

        if(input_params.size()>0)
        {
            String style_mode = input_params.get(0);
            if(style_mode.equalsIgnoreCase("transparent"))
            {
                stst = StageStyle.TRANSPARENT;
            }
            else if (style_mode.equalsIgnoreCase("undecorated"))
            {
                stst = StageStyle.UNDECORATED;
            }
            else if (style_mode.equalsIgnoreCase("utility"))
            {
                stst = StageStyle.UTILITY;
            }
        }
        //Declare vars
        final Stage stageRef = stage;
        Group rootGroup;
        TextField txt_field_title;

        //Buttons
        Button btn_toback = new Button("to Back");
        btn_toback.setOnAction(e->stageRef.toBack());

        Button btn_tofront = new Button("to Front");
        btn_tofront.setOnAction(e->stageRef.toFront());

        Button btn_close = new Button("Close");
        btn_close.setOnAction(e->stageRef.close());

        //Background
        Rectangle bg = new Rectangle(250,350, Color.SKYBLUE);
        bg.setArcHeight(20);
        bg.setArcWidth(20);

        //Initiate labels
        label_posX = new Text(); label_posX.setTextOrigin(VPos.TOP);
        label_posY = new Text(); label_posY.setTextOrigin(VPos.TOP);
        label_width = new Text(); label_width.setTextOrigin(VPos.TOP);
        label_height = new Text(); label_height.setTextOrigin(VPos.TOP);
        label_focus = new Text(); label_height.setTextOrigin(VPos.TOP);

        //Checkboxes
        cb_fscreen = new CheckBox("Full Screen");
        cb_resizable = new CheckBox("Resizable");
        cb_resizable.setDisable((stst == StageStyle.TRANSPARENT) || (stst==StageStyle.DECORATED));

        txt_field_title = new TextField("Stage Coach Chap02");
        Label label_window_title = new Label("Window Title");
        HBox title_field_row = new HBox(label_window_title, txt_field_title);

        VBox main_area = new VBox(title_field_row, label_posX, label_posY, label_width,
                        label_height, cb_fscreen, cb_resizable, btn_toback, btn_tofront, btn_close);
        main_area.setLayoutX(30);
        main_area.setLayoutY(20);
        main_area.setSpacing(10);

        rootGroup = new Group(bg, main_area);

        Scene scene = new Scene(rootGroup, 370, 370);
        scene.setFill(Color.TRANSPARENT);

        //when mouse button is pressed, save the initial position of screen
        rootGroup.setOnMousePressed((MouseEvent me)->{
            dragX = me.getSceneX();
            dragY = me.getSceneY();
        });

        //when screen is dragged, translate it accordingly
        rootGroup.setOnMouseDragged((MouseEvent me)->{
            stageRef.setX(stageRef.getX() + me.getSceneX() - dragX);
            stageRef.setY(stageRef.getY() + me.getSceneY() - dragY);
        });

        label_posX.textProperty().bind(
                new SimpleStringProperty("X: ").concat(
                        stageRef.xProperty().asString()
                )
        );

        label_posY.textProperty().bind(
                new SimpleStringProperty("Y: ").concat(
                        stageRef.yProperty().asString()
                )
        );

        label_width.textProperty().bind(
                new SimpleStringProperty("Width: ").concat(
                        stageRef.widthProperty().asString()
                )
        );

        label_height.textProperty().bind(
                new SimpleStringProperty("Height: ").concat(
                        stageRef.heightProperty().asString()
                )
        );

        label_focus.textProperty().bind(
                new SimpleStringProperty("Focused: ").concat(
                        stageRef.focusedProperty().asString()
                )
        );

        cb_resizable.selectedProperty().bindBidirectional(stageRef.resizableProperty());

        cb_fscreen.selectedProperty().addListener((ov, oldValue, newValue) -> {
            stageRef.setFullScreen(cb_fscreen.selectedProperty().getValue());
        });

        window_title.bind(txt_field_title.textProperty());

        stage.setResizable(true);
        stage.setScene(scene);
        stage.titleProperty().bind(window_title);
        stage.initStyle(stst);
        stage.setOnCloseRequest((WindowEvent we)->{
            System.out.println("App is closed");
        });

        stage.show();

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

    }





}
