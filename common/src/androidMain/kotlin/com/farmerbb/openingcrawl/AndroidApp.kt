package com.farmerbb.openingcrawl

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.farmerbb.openingcrawl.common.R

@Composable
actual fun starWarsLogoPainter(): Painter = painterResource(id = R.drawable.star_wars_logo)