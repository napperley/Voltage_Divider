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

package org.digieng.voltagedivider

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage

class VoltageDividerApplication : Application() {
    private lateinit var primaryStage: Stage

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(VoltageDividerApplication::class.java, *args)
        }
    }

    override fun start(primaryStage: Stage) {
        this.primaryStage = primaryStage
        this.primaryStage.title = "Voltage Divider"
        initView()
        primaryStage.show()
    }

    private fun initView() {
        val FXML_PATH = "/view/main_window.fxml"
        val loader = FXMLLoader()
        val rootLayout: VBox

        loader.location = VoltageDividerApplication::class.java.getResource(FXML_PATH)
        rootLayout = loader.load()
        primaryStage.scene = Scene(rootLayout)
    }
}