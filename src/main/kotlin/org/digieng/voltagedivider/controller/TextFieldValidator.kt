/*
 * Copyright 2016 [Nick Apperley
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

package org.digieng.voltagedivider.controller

import javafx.scene.control.Control
import javafx.scene.control.TextField
import org.controlsfx.validation.ValidationResult
import org.controlsfx.validation.Validator

object TextFieldValidator {
    private val MAX_LENGTH = 6
    private val MAX_LENGTH_MSG = "Field exceeds the maximum length ($MAX_LENGTH) allowed"
    private val NUM_MSG = "A valid number must be entered"
    val maxLengthValidator = Validator { control: Control, newVal: String ->
        ValidationResult.fromErrorIf(
                control, MAX_LENGTH_MSG, if (control is TextField) control.length > MAX_LENGTH else false
        )
    }
    val doubleValidator = Validator {
        control: Control, newVal: String ->
        ValidationResult.fromErrorIf(
                control, NUM_MSG, if (control is TextField) !control.text.isDouble() else false
        )
    }
}