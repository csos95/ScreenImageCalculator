package com.csos95.ScreenImageCalculator

import com.csos95.ScreenImageCalculator.Extensions.*

import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import javafx.stage.Stage

class App : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "ScreenImageCalculator"

        val layout = GridPane()
        layout.alignment = Pos.CENTER
        layout.hgap = 10.0
        layout.vgap = 10.0
        layout.padding = Insets(25.0, 25.0, 25.0, 25.0)

        //screen inputs
        layout.addLabel("Screen Diagonal", x = 0, y = 0)
        val screenDiagonalInput = layout.addTextField(x = 1, y = 0)
        layout.addLabel("Screen Ratio Width", x = 0, y = 1)
        val screenWidthInput = layout.addTextField(x = 1, y = 1)
        layout.addLabel("Screen Ratio height", x = 0, y = 2)
        val screenHeightInput = layout.addTextField(x = 1, y = 2)

        //image inputs
        layout.addLabel("Image Width", x = 0, y = 3)
        val imageWidthInput = layout.addTextField(x = 1, y = 3)
        layout.addLabel("Image height", x = 0, y = 4)
        val imageHeightInput = layout.addTextField(x = 1, y = 4)

        //image diagonal output
        layout.addLabel("Image Diagonal", x = 0, y = 5)
        val maxImageDiagonal = layout.addLabel("", x = 1, y = 5)

        layout.addButton("Calculate", EventHandler {
            //get screen inputs
            val screenDiagonalMeasurement = screenDiagonalInput.text.toDoubleOrNull() ?: 0.0
            val screenWidthPixels = screenWidthInput.text.toIntOrNull() ?: 0
            val screenHeightPixels = screenHeightInput.text.toIntOrNull() ?: 0

            //calculate screen ration and physical width/height measurements
            val screenRatio = screenWidthPixels.toDouble() / screenHeightPixels.toDouble()
            val screenIntermediateValue = Math.sqrt(Math.pow(screenDiagonalMeasurement, 2.0) / (Math.pow(screenRatio, 2.0)+1))
            val screenWidthMeasurement = screenRatio * screenIntermediateValue
            val screenHeightMeasurement = screenIntermediateValue

            //get image inputs and calculate image ratio
            val imageWidthPixels = imageWidthInput.text.toDoubleOrNull() ?: 0.0
            val imageHeightPixels = imageHeightInput.text.toDoubleOrNull() ?: 0.0
            val imageRatio = imageWidthPixels / imageHeightPixels

            //calculate the landscape ratios (to ensure we are using the largest ratio numbers for screen and image)
            val screenLandscapeRatio = if (screenRatio >= 1.0) screenRatio else 1.0 / screenRatio
            val imageLandscapeRatio = if (imageRatio >= 1.0) imageRatio else 1.0 / imageRatio

            //calculate the image width and height measurements in one of four ways depending on the aspect ratios
            //of the screen and image
            val imageWidthMeasurement: Double
            val imageHeightMeasurement: Double
            val imageDiagonalMeasurement: Double
            if ((screenLandscapeRatio > imageLandscapeRatio && screenRatio < 1.0 && imageRatio < 1.0) ||
                    (screenLandscapeRatio <= imageLandscapeRatio && screenRatio > 1.0 && imageRatio > 1.0)) {
                imageWidthMeasurement = screenWidthMeasurement
                imageHeightMeasurement = screenWidthMeasurement / imageWidthPixels * imageHeightPixels
            } else if ((screenLandscapeRatio > imageLandscapeRatio && screenRatio < 1.0) ||
                    (screenLandscapeRatio <= imageLandscapeRatio && screenRatio > 1.0)) {
                imageHeightMeasurement = screenWidthMeasurement
                imageWidthMeasurement = screenWidthMeasurement / imageHeightPixels * imageWidthPixels
            } else if ((screenLandscapeRatio > imageLandscapeRatio && imageRatio < 1.0) ||
                    (screenLandscapeRatio <= imageLandscapeRatio && imageRatio > 1.0)) {
                imageWidthMeasurement = screenHeightMeasurement
                imageHeightMeasurement = screenHeightMeasurement / imageWidthPixels * imageHeightPixels
            } else {
                imageHeightMeasurement = screenHeightMeasurement
                imageWidthMeasurement = screenHeightMeasurement / imageHeightPixels * imageWidthPixels
            }
            //calculate the image diagonal measurement
            imageDiagonalMeasurement = Math.sqrt(Math.pow(imageWidthMeasurement, 2.0) + Math.pow(imageHeightMeasurement, 2.0))

            //set it with a cutoff after two decimals
            maxImageDiagonal.text = "%.2f".format(imageDiagonalMeasurement)
        }, x = 0, y = 6)

        primaryStage.scene = Scene(layout, 400.0, 300.0)
        primaryStage.show()
    }
}