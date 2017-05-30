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
        val screenDiagonalInput = layout.addFloatField(x = 1, y = 0)
        layout.addLabel("Screen Ratio Width", x = 0, y = 1)
        val screenWidthInput = layout.addIntField(x = 1, y = 1)
        layout.addLabel("Screen Ratio height", x = 0, y = 2)
        val screenHeightInput = layout.addIntField(x = 1, y = 2)

        //image inputs
        layout.addLabel("Image Width", x = 0, y = 3)
        val imageWidthInput = layout.addIntField(x = 1, y = 3)
        layout.addLabel("Image height", x = 0, y = 4)
        val imageHeightInput = layout.addIntField(x = 1, y = 4)

        //image diagonal output
        layout.addLabel("Image Diagonal", x = 0, y = 5)
        val maxImageDiagonal = layout.addLabel("", x = 1, y = 5)

        //this is all really ugly now, but it seems to be correct.
        //TODO cleanup
        layout.addButton("Calculate", EventHandler {
            val screenDiagonalMeasurement = screenDiagonalInput.text.toDouble()
            val screenWidthPixels = screenWidthInput.text.toInt()
            val screenHeightPixels = screenHeightInput.text.toInt()

            val screenRatio = screenWidthPixels.toDouble() / screenHeightPixels.toDouble()
            val screenWidthMeasurement = screenRatio * Math.sqrt( Math.pow(screenDiagonalMeasurement, 2.0) / (Math.pow(screenRatio, 2.0) + 1 ) )
            val screenHeightMeasurement = Math.sqrt( Math.pow(screenDiagonalMeasurement, 2.0) / (Math.pow(screenRatio, 2.0) + 1) )

            val imageWidthPixels = imageWidthInput.text.toDouble()
            val imageHeightPixels = imageHeightInput.text.toDouble()

            val imageRatio = imageWidthPixels.toDouble() / imageHeightPixels.toDouble()

            val screenLandscapeRatio = if (screenRatio >= 1.0) screenRatio else 1.0 / screenRatio
            val imageLandscapeRatio = if (imageRatio >= 1.0) imageRatio else 1.0 / imageRatio

            var imageWidthMeasurement = 0.0
            var imageHeightMeasurement = 0.0
            var imageDiagonalMeasurement = 0.0
            if (screenLandscapeRatio > imageLandscapeRatio) {
                if (screenRatio < 1.0 && imageRatio < 1.0) {
                    imageWidthMeasurement = screenWidthMeasurement
                    imageHeightMeasurement = screenWidthMeasurement / imageWidthPixels * imageHeightPixels
                } else if (screenRatio < 1.0) {
                    imageHeightMeasurement = screenWidthMeasurement
                    imageWidthMeasurement = screenWidthMeasurement / imageHeightPixels * imageWidthPixels
                } else if (imageRatio < 1.0) {
                    imageWidthMeasurement = screenHeightMeasurement
                    imageHeightMeasurement = screenHeightMeasurement / imageWidthPixels * imageHeightPixels
                } else {
                    imageHeightMeasurement = screenHeightMeasurement
                    imageWidthMeasurement = screenHeightMeasurement / imageHeightPixels * imageWidthPixels
                }
            } else {
                if (screenRatio > 1.0 && imageRatio > 1.0) {
                    imageWidthMeasurement = screenWidthMeasurement
                    imageHeightMeasurement = screenWidthMeasurement / imageWidthPixels * imageHeightPixels
                } else if (screenRatio > 1.0) {
                    imageHeightMeasurement = screenWidthMeasurement
                    imageWidthMeasurement = screenWidthMeasurement / imageHeightPixels * imageWidthPixels
                } else if (imageRatio > 1.0) {
                    imageWidthMeasurement = screenHeightMeasurement
                    imageHeightMeasurement = screenHeightMeasurement / imageWidthPixels * imageHeightPixels
                } else {
                    imageHeightMeasurement = screenHeightMeasurement
                    imageWidthMeasurement = screenHeightMeasurement / imageHeightPixels * imageWidthPixels
                }
            }
            imageDiagonalMeasurement = Math.sqrt(Math.pow(imageWidthMeasurement, 2.0) + Math.pow(imageHeightMeasurement, 2.0))

            maxImageDiagonal.text = imageDiagonalMeasurement.toString()
        }, x = 0, y = 6)

        primaryStage.scene = Scene(layout, 400.0, 300.0)
        primaryStage.show()
    }
}