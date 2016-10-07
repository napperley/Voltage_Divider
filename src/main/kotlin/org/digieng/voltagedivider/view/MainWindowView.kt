/*
 * Copyright 2016 Nick Apperley
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.digieng.voltagedivider.view

import javafx.scene.control.Button
import javafx.scene.control.Control
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import org.controlsfx.validation.ValidationResult
import org.controlsfx.validation.ValidationSupport
import tornadofx.View

fun String.isDouble(): Boolean {
    var result = true

    if (this.isNotEmpty()) {
        try {
            this.toDouble()
        } catch (ex: NumberFormatException) {
            result = false
        }
    }
    return result
}

fun TextField.doubleValue() = this.text.toDouble()

class MainWindowView : View("Voltage Divider") {
    override val root: VBox by fxml("/view/main_window.fxml")
    private val inputVoltageTxt: TextField by fxid()
    private val outputVoltageTxt: TextField by fxid()
    private val resistance1Txt: TextField by fxid()
    private val resistance2Txt: TextField by fxid()
    private val calculateBtn: Button by fxid()

    init {
        val NUM_MSG = "A valid number must be entered"
        val validation = ValidationSupport()
        val doubleValidator = { control: Control, newVal: String ->
            ValidationResult.fromErrorIf(
                    control, NUM_MSG, if (control is TextField) !control.text.isDouble() else false
            )
        }

        validation.registerValidator(inputVoltageTxt, false, doubleValidator)
        validation.registerValidator(outputVoltageTxt, false, doubleValidator)
        validation.registerValidator(resistance1Txt, false, doubleValidator)
        validation.registerValidator(resistance2Txt, false, doubleValidator)
        validation.validationResultProperty().addListener { o, oldVal, newVal ->
            calculateBtn.isDisable = newVal.errors.isNotEmpty()
        }
    }

    fun clearTextFields() {
        inputVoltageTxt.clear()
        outputVoltageTxt.clear()
        resistance1Txt.clear()
        resistance2Txt.clear()
        inputVoltageTxt.requestFocus()
    }

    fun doCalculation() {
        if (outputVoltageTxt.text.isEmpty() && inputVoltageTxt.text.isNotEmpty() && resistance1Txt.text.isNotEmpty() &&
                resistance2Txt.text.isNotEmpty()) {
            calculateOutputVoltage()
        } else if (inputVoltageTxt.text.isEmpty() && outputVoltageTxt.text.isNotEmpty() &&
                resistance1Txt.text.isNotEmpty() && resistance2Txt.text.isNotEmpty()) {
            calculateInputVoltage()
        } else if (resistance1Txt.text.isEmpty() && outputVoltageTxt.text.isNotEmpty() &&
                inputVoltageTxt.text.isNotEmpty() && resistance2Txt.text.isNotEmpty()) {
            calculateResistance1()
        } else if (resistance2Txt.text.isEmpty() && outputVoltageTxt.text.isNotEmpty() &&
                inputVoltageTxt.text.isNotEmpty() && resistance1Txt.text.isNotEmpty()) {
            calculateResistance2()
        }
    }

    private fun calculateInputVoltage() {
        inputVoltageTxt.text = "${outputVoltageTxt.doubleValue() * (resistance1Txt.doubleValue() +
                resistance2Txt.doubleValue()) / resistance2Txt.doubleValue()}"
    }

    private fun calculateOutputVoltage() {
        outputVoltageTxt.text = "${inputVoltageTxt.doubleValue() * resistance2Txt.doubleValue() /
                (resistance1Txt.doubleValue() + resistance2Txt.doubleValue())}"
    }

    private fun calculateResistance1() {
        val outputVoltage = outputVoltageTxt.doubleValue()
        val inputVoltage = inputVoltageTxt.doubleValue()

        resistance1Txt.text = "${resistance2Txt.doubleValue() * (1 - outputVoltage / inputVoltage) *
                inputVoltage / outputVoltage}"
    }

    private fun calculateResistance2() {
        val outputVoltage = outputVoltageTxt.doubleValue()
        val inputVoltage = inputVoltageTxt.doubleValue()

        resistance2Txt.text = "${resistance1Txt.doubleValue() * outputVoltage / inputVoltage /
                (1 - outputVoltage / inputVoltage)}"
    }
}