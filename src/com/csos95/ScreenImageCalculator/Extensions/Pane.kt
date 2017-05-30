//This package isn't really necessary, but is to experiment with kotlin extension functions
package com.csos95.ScreenImageCalculator.Extensions

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.*
import javafx.scene.layout.GridPane

fun GridPane.addLabel(title: String, x: Int = 0, y: Int = 0, width: Int = 1, height: Int = 1): Label {
    val label = Label(title)
    add(label, x, y, width, height)
    return label
}

fun GridPane.addButton(title: String, listener: EventHandler<ActionEvent>, x: Int = 0, y: Int = 0): Button {
    val button = Button(title)
    button.onAction = listener
    add(button, x, y)
    return button
}

fun GridPane.addTextField(x: Int = 0, y: Int = 0, width: Int = 1, height: Int = 1): TextField {
    val textField = TextField()
    add(textField, x, y, width, height)
    return textField
}