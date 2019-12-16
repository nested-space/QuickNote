package com.edenrump.ui.boards.components;

import com.edenrump.ui.Parameterisable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kvcb654 on 21/09/2017.
 */
public class HolderRectangle extends VBox implements Parameterisable {

    private final String HEADER_TEXT_KEY = "HEADER_TEXT";
    private final String CHILD_TEXT_KEY = "CHILD_KEY";

    private final double DEFAULT_PADDING = 0;
    private final CornerRadii DEFAULT_CORNER_RADII = CornerRadii.EMPTY;
    private final Color DEFAULT_COLOR = Color.ALICEBLUE;
    private final Insets DEFAULT_INSETS = Insets.EMPTY;

    private final String uuid = "uuid";

    /* ****************************************************************************************************************
     *   Header Box
     * ***************************************************************************************************************/

    private NamedRectangle mHeaderRectangle = new NamedRectangle();
    private VBox headerHolderVBox = new VBox();
    private ImageView headerImage = new ImageView();
    /* ****************************************************************************************************************
     *   Children
     * ***************************************************************************************************************/
    //Create a VBox to hold child (impurity, CQA etc) rectangles
    private VBox childHolderVBox = new VBox();
    private Map<String, String> parameterMap = new HashMap<>();

    public HolderRectangle() {
        super();
        setUpLayout();
    }

    public void addHeaderBox(String substanceName, String materialID, Color materialColor) {
        mHeaderRectangle.setText(substanceName);
        setHeaderColor(materialColor);

        if (materialID != null) {
            storeParameter("headerID", materialID);
        }

    }

    public String getHeaderID() {
        return (parameterMap.containsKey("headerID") ? "" : parameterMap.get("headerID"));
    }

    public String getHeaderText() {
        return mHeaderRectangle.getText();
    }

    public void setHeaderText(String text) {
        mHeaderRectangle.setText(text);
    }

    public Color getHeaderColor() {
        return (Color) headerHolderVBox.getBackground().getFills().get(0).getFill();
    }

    public void setHeaderColor(Color color) {
        headerHolderVBox.setBackground(new Background(new BackgroundFill(color, DEFAULT_CORNER_RADII, DEFAULT_INSETS)));
    }

    //Image
    public void addImage(Image image) {
        headerImage.setImage(image);
    }

    public void removeImage() {
        headerImage.setImage(null);
    }

    /**
     * Method to add named rectangle containing text to HolderRectangle.
     *
     * @param name       text to be displayed on child NamedRectangle
     * @param presetUUID identifier - used for retrieving later.
     */
    public void addChildRectangle(String name, String presetUUID) {
        //create child named rectangle
        NamedRectangle child = new NamedRectangle(name);

        //create UUID and store in child AND in HolderRectangle parameter map for future recall see getChildRectangle();
        child.storeParameter(uuid, presetUUID);

        //add child rectangle
        childHolderVBox.getChildren().add(child);
    }

    public void setChildRectangleColour(String childUUID, Color colour) {
        NamedRectangle namedRectangle = null;
        for (Node n : childHolderVBox.getChildren()) {
            if (n instanceof NamedRectangle) {
                NamedRectangle nr = (NamedRectangle) n;
                if (nr.getParameterValue(uuid).equals(childUUID)) {
                    nr.setColor(colour);
                }
            }
        }
    }

    /* ****************************************************************************************************************
     *   Parameters
     * ***************************************************************************************************************/

    public NamedRectangle getChildRectangle(String childUUID) {
        for (Node n : childHolderVBox.getChildren()) {
            if (n instanceof NamedRectangle) {
                NamedRectangle nr = (NamedRectangle) n;
                if (nr.getParameterValue(uuid).equals(childUUID)) {
                    return nr;
                }
            }
        }
        return null;
    }

    public List<NamedRectangle> getAllChildRectangles() {
        List<NamedRectangle> namedRectangleList = new ArrayList<>();
        for (Node n : childHolderVBox.getChildren()) {
            if (n instanceof NamedRectangle) {
                namedRectangleList.add((NamedRectangle) n);
            }
        }
        return namedRectangleList;
    }

    public void storeParameter(String key, String value) {
        parameterMap.put(key, value);
    }

    public String getParameterValue(String key) {
        return (parameterMap.getOrDefault(key, ""));
    }

    /* ****************************************************************************************************************
     *   Constructors
     * ***************************************************************************************************************/

    public List<String> getParameterKeys() {
        return new ArrayList<>(parameterMap.keySet());
    }

    private void setUpLayout() {
        setSpacing(DEFAULT_PADDING * 2);
        setPadding(new Insets(DEFAULT_PADDING, 0, DEFAULT_PADDING, 0));
        setBackground(new Background(new BackgroundFill(DEFAULT_COLOR, DEFAULT_CORNER_RADII, DEFAULT_INSETS)));

        //set up layout of top box (holds title and image).
        headerHolderVBox.getChildren().addAll(mHeaderRectangle);
        headerHolderVBox.getChildren().add(0, headerImage);
        headerHolderVBox.setAlignment(Pos.CENTER);

        //set up layout of child vbox
        childHolderVBox.setSpacing(DEFAULT_PADDING);

        mHeaderRectangle.setTextAlignment(Pos.CENTER);

//        DropShadow ds = new DropShadow(5.0, Color.LIGHTGRAY);
//        ds.setOffsetX(5.0);
//        ds.setOffsetY(5.0);
//        this.setEffect(ds);

        //add top and bottom VBoxes
        getChildren().add(0, headerHolderVBox);
        getChildren().add(childHolderVBox);

        lowlight();
    }

    /* *************************************************
     *   Styling
     * ************************************************/

    public void highlight() {
        String cssLayout = "-fx-border-color: red;\n";
        setStyle(cssLayout);
    }

    public void lowlight() {
        String cssLayout = "-fx-border-color: blue;\n";
        setStyle(cssLayout);

    }

}
