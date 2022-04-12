/* Copyright 2022 Braden Farmer
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

package com.farmerbb.openingcrawl

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// This should work okay on phones, probably breaks on larger devices due to hardcoded values

@ExperimentalAnimationApi
@Composable
fun OpeningCrawl() {
    val newlines = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
    val crawlHeader = "${newlines}Episode IV\nA NEW HOPE\n"

    val crawlText =
        "It is a period of civil war. Rebel spaceships, striking from a hidden base, have won their first victory against the evil Galactic Empire.\n\n" +
                "During the battle, Rebel spies managed to steal secret plans to the Empire's ultimate weapon, the DEATH STAR, an armored space station with enough power to destroy an entire planet.\n\n" +
                "Pursued by the Empire's sinister agents, Princess Leia races home aboard her starship, custodian of the stolen plans that can save her people and restore freedom to the galaxy...." +
                newlines

    val logoVisible = remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.background(color = Color.Black)
    ) {
        Scaffold(
            modifier = Modifier.graphicsLayer {
                rotationX = 40f
            },
            content = {
                Column(
                    modifier = Modifier
                        .background(color = Color.Black)
                        .verticalScroll(
                            state = scrollState
                        )
                ) {
                    Text(
                        text = crawlHeader,
                        style = TextStyle(
                            color = colorResource(R.color.star_wars_logo),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = crawlText,
                        style = TextStyle(
                            color = colorResource(R.color.star_wars_logo),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify
                        ),
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp
                            )
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                }
            }
        )

        AnimatedVisibility(
            visible = logoVisible.value,
            exit = fadeOut(
                animationSpec = TweenSpec(
                    durationMillis = 5000
                )
            ),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.star_wars_logo),
                contentDescription = "Star Wars logo",
            )
        }
    }

    LaunchedEffect(Unit) {
        logoVisible.value = false
        scrollState.animateScrollTo(
            value = 10000,
            animationSpec = TweenSpec(
                durationMillis = 100000,
                easing = LinearEasing
            )
        )
    }
}