package com.farmerbb.openingcrawl

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

@Composable
actual fun starWarsLogoPainter(): Painter = painterResource("drawable/star_wars_logo.xml")