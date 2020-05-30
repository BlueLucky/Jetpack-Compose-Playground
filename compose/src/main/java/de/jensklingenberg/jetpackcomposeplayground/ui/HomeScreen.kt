
/*
 * Copyright 2019 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.jensklingenberg.jetpackcomposeplayground.ui


import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.DrawerState
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ModalDrawerLayout
import androidx.ui.unit.dp
import de.jensklingenberg.jetpackcomposeplayground.ui.samples.layout.ColumnExample
import de.jensklingenberg.jetpackcomposeplayground.ui.samples.layout.HorizontalScrollerExample
import de.jensklingenberg.jetpackcomposeplayground.ui.samples.layout.PaddingDemo
import de.jensklingenberg.jetpackcomposeplayground.ui.samples.layout.VerticalScrollerExample
import de.jensklingenberg.jetpackcomposeplayground.ui.samples.material.CheckBoxDemo
import de.jensklingenberg.jetpackcomposeplayground.ui.samples.mysamples.layout.RowExample
import de.jensklingenberg.jetpackcomposeplayground.ui.samples.mysamples.other.RadioGroupSample
import de.jensklingenberg.jetpackcomposeplayground.ui.samples.mysamples.other.SwitchDemo
import de.jensklingenberg.jetpackcomposeplayground.ui.samples.other.AlertDialogSample
import de.jensklingenberg.jetpackcomposeplayground.ui.samples.other.AndroidContextComposeDemo
import de.jensklingenberg.jetpackcomposeplayground.ui.samples.other.CounterModelDemo


@Composable
fun HomeScreen() {

    MaterialTheme {
        val (drawerState: androidx.ui.material.DrawerState, onDrawerStateChange: (DrawerState) -> Unit) = state { DrawerState.Closed }

        var currentScreen by state<ScreenState>(init = {
            ScreenState.Overview
        })

        ModalDrawerLayout(
            drawerState = drawerState,
            onStateChange = onDrawerStateChange,
            gesturesEnabled = drawerState == DrawerState.Opened,
            drawerContent = {
               myDrawerContent(onDrawerStateChange){
                   currentScreen=it
               }

            },
            bodyContent = {

                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button( onClick = {
                            onDrawerStateChange(DrawerState.Opened)
                        }){
                            Text("Open Drawer")
                        }

                        Button( onClick = {
                            currentScreen = ScreenState.Overview
                        }){
                            Text("Go Back")
                        }
                    }

                    if (currentScreen == ScreenState.Overview) {
                        myOverView(){
                            currentScreen = it
                        }
                    } else {
                        currentScreen.body()
                    }

                }
            }
        )


    }

}




@Composable
fun myDrawerContent(onDrawerStateChange: (DrawerState) -> Unit, fn:(ScreenState)->Unit) {
    val allScreens = ScreenState.values().toList()

    VerticalScroller {
        Column() {
            allScreens.forEach {
                Spacer(Modifier.height(10.dp))
                Button( onClick = {
                    fn(it)
                    onDrawerStateChange(DrawerState.Closed)
                }){
                    Text(it.name)
                }

            }

        }
    }
}

@Composable
fun myOverView(fn:(ScreenState)->Unit){
    val allScreens = ScreenState.values().toList()

    VerticalScroller {
        Column() {
            allScreens.forEach {
                Spacer(Modifier.height(10.dp))
                Button( onClick = {
                    fn(it)
                }){
                    Text(it.name)
                }

            }

        }
    }
}


enum class ScreenState( ) {
    Overview, AndroidContextComposeDemo, ColumnExample,VerticalScroller,HorizontalScrollerExample,RowExample,PaddingDemo,SwitchDemo,CheckBoxDemo,RadioGroupSample,AlertDialogSample,CounterModelDemo
    //,DrawlayerDemo
}

@Composable
fun ScreenState.body() = when (this) {
    ScreenState.Overview -> AndroidContextComposeDemo()
    ScreenState.AndroidContextComposeDemo -> AndroidContextComposeDemo()
    ScreenState.ColumnExample -> ColumnExample()
    ScreenState.VerticalScroller -> VerticalScrollerExample()
    ScreenState.HorizontalScrollerExample -> HorizontalScrollerExample()
    ScreenState.RowExample -> RowExample()
    ScreenState.PaddingDemo -> PaddingDemo()

    ScreenState.SwitchDemo -> SwitchDemo()
    ScreenState.CheckBoxDemo -> CheckBoxDemo()
    ScreenState.RadioGroupSample -> RadioGroupSample()
    ScreenState.AlertDialogSample -> AlertDialogSample()
    ScreenState.CounterModelDemo -> CounterModelDemo()
  //  ScreenState.DrawlayerDemo -> DrawLayerDemo()
}
