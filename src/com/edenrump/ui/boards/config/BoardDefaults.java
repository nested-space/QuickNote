package com.edenrump.ui.boards.config;

public class BoardDefaults {

    public static int ZOOM_LEVEL = 0;

    public static double[] getHighlightDimensions(int zoomLevel){
        double[] dim = new double[2];
        switch (zoomLevel){
            case 0:
            default:
                dim[0] = 50d;
                dim[1] = 5d;
        }
        return dim;
    }


    public static double[] getTicketDimensions(int zoomLevel) {
        double[] dim = new double[2];
        switch (zoomLevel){
            case 0:
            default:
                dim[0] = 50d;
                dim[1] = 150d;
        }
        return dim;
    }
}
