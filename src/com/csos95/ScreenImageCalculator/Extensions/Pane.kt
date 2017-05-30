package com.csos95.ScreenImageCalculator.Extensions

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.GridPane

fun GridPane.addInLocation(node: Node, x: Int = 0, y: Int = 0, width: Int = 1, height: Int = 1) {
    add(node, x, y, width, height)
}

fun GridPane.addLabel(title: String, x: Int = 0, y: Int = 0, width: Int = 1, height: Int = 1): Label {
    val label = Label(title)
    addInLocation(label, x, y, width, height)
    return label
}

fun GridPane.addButton(title: String, listener: EventHandler<ActionEvent>, x: Int = 0, y: Int = 0, width: Int = 1, height: Int = 1): Button {
    val button = Button(title)
    button.onAction = listener
    addInLocation(button, x, y, width, height)
    return button
}

class FloatField : TextField() {
    var dotCount = 0

    override fun replaceText(start: Int, end: Int, text: String) {
        if (this.text.substring(start, end).matches(Regex(".*\\..*"))) {
            dotCount--
        }
        if (validate(text)) {
            super.replaceText(start, end, text)
            if (text.matches(Regex(".*\\..*"))) {
                dotCount++
            }
        }
    }

    override fun replaceSelection(replacement: String) {
        if (validate(text)) {
            super.replaceSelection(replacement)
        }
    }

    override fun deleteText(range: IndexRange?) { super.deleteText(range) }

    fun validate(text: String) = text.matches(Regex("[0-9]*")) || (text.matches(Regex("\\.")) && dotCount == 0)
}

class IntField : TextField() {
    override fun replaceText(start: Int, end: Int, text: String) {
        if (validate(text)) {
            super.replaceText(start, end, text)
        }
    }

    override fun replaceSelection(replacement: String) {
        if (validate(text)) {
            super.replaceSelection(replacement)
        }
    }

    override fun deleteText(range: IndexRange?) { super.deleteText(range) }

    fun validate(text: String) = text.matches(Regex("[0-9]*"))
}

fun GridPane.addFloatField(x: Int = 0, y: Int = 0, width: Int = 1, height: Int = 1): FloatField {
    val floatField = FloatField()
    addInLocation(floatField, x, y, width, height)
    return floatField
}

fun GridPane.addIntField(x: Int = 0, y: Int = 0, width: Int = 1, height: Int = 1): IntField {
    val intField = IntField()
    addInLocation(intField, x, y, width, height)
    return intField
}