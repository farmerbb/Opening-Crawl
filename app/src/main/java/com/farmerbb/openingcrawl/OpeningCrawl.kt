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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CrawlData(
    val episodeNumber: Int,
    val episodeTitle: String,
    val crawlText: List<String>
)

@Composable
private fun defaultLogoPainter() = painterResource(id = R.drawable.star_wars_logo)

private val defaultCrawlData = CrawlData(
    episodeNumber = 4,
    episodeTitle = "A New Hope",
    crawlText = listOf(
        "It is a period of civil war. Rebel spaceships, striking from a hidden base, have won their first victory against the evil Galactic Empire.",
        "During the battle, Rebel spies managed to steal secret plans to the Empire's ultimate weapon, the DEATH STAR, an armored space station with enough power to destroy an entire planet.",
        "Pursued by the Empire's sinister agents, Princess Leia races home aboard her starship, custodian of the stolen plans that can save her people and restore freedom to the galaxy."
    )
)

// Based on https://github.com/luisrovirosa/roman-numerals-kotlin/blob/master/src/main/java/RomanNumerals.kt
fun Int.asRomanNumeral(): String {
    val numerals = linkedMapOf(
        1000 to "M",
        900 to "CM",
        500 to "D",
        400 to "CD",
        100 to "C",
        90 to "XC",
        50 to "L",
        40 to "XL",
        10 to "X",
        9 to "IX",
        5 to "V",
        4 to "IV",
        1 to "I"
    )

    for (i in numerals.keys) {
        if (this >= i) {
            return numerals[i] + (this - i).asRomanNumeral()
        }
    }

    return ""
}

@ExperimentalAnimationApi
@Composable
fun OpeningCrawl(
    logoPainter: Painter = defaultLogoPainter(),
    crawlData: CrawlData = defaultCrawlData
) = with(crawlData) {
    val crawlHeader = "Episode ${episodeNumber.asRomanNumeral()}\n${episodeTitle.uppercase()}\n"
    val crawlTextAsString = buildString {
        crawlText.forEachIndexed { index, string ->
            append(string)
            if (index != crawlText.lastIndex) {
                append("\n\n")
            }
        }

        append("...")
    }

    var logoVisible by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()

    val density = LocalDensity.current.density
    var boxHeight by remember { mutableStateOf(0.dp) }
    var boxWidth by remember { mutableStateOf(0.dp) }

    Box(
        modifier = Modifier
            .background(color = Color.Black)
            .onGloballyPositioned { coordinates ->
                boxHeight = (coordinates.size.height / density).dp
                boxWidth = (coordinates.size.width / density).dp
            }
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
                    Spacer(modifier = Modifier.height(boxHeight))

                    Text(
                        text = crawlHeader,
                        style = TextStyle(
                            color = colorResource(R.color.star_wars_logo),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Text(
                        text = crawlTextAsString,
                        style = TextStyle(
                            color = colorResource(R.color.star_wars_logo),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify
                        ),
                        modifier = Modifier
                            .padding(
                                start = 16.dp
                            )
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )

                    Spacer(modifier = Modifier.height(boxHeight))
                }
            }
        )

        AnimatedVisibility(
            visible = logoVisible,
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
                painter = logoPainter,
                colorFilter = ColorFilter.tint(color = colorResource(R.color.star_wars_logo)),
                contentDescription = "Star Wars logo",
            )
        }
    }

    LaunchedEffect(Unit) {
        logoVisible = false
        scrollState.animateScrollTo(
            value = 10000,
            animationSpec = TweenSpec(
                durationMillis = 100000,
                easing = LinearEasing
            )
        )
    }
}