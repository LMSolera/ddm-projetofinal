package com.example.ddm_projetofinal.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomMenuElement (
    disabledIndex: Int
) {
    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .drawBehind {
                drawLine(
                    color = Color(0xFFE4E4E4),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 10f
                )
            },
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val scale: Float = 1.2f
            val tintEnabled: Color = Color(0xFF818181)
            val tintDisabled: Color = Color(0xFF000000)
            IconButton (
                onClick = {},
                enabled = if (disabledIndex == 1) false else true
            ) {
                Icon (
                    imageVector = Icons.Default.Home,
                    contentDescription = "Um ícone de casa",
                    modifier = Modifier
                        .scale(scale),
                    tint = if (disabledIndex == 1) tintDisabled else tintEnabled
                )
            }
            IconButton (
                onClick = {},
                enabled = if (disabledIndex == 2) false else true
            ) {
                Icon (
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Um ícone de perfil",
                    modifier = Modifier
                        .scale(scale),
                    tint = if (disabledIndex == 2) tintDisabled else tintEnabled
                )
            }
            IconButton (
                onClick = {},
                enabled = if (disabledIndex == 3) false else true
            ) {
                Icon (
                    imageVector = Icons.Default.Place,
                    contentDescription = "Um ícone de local",
                    modifier = Modifier
                        .scale(scale),
                    tint = if (disabledIndex == 3) tintDisabled else tintEnabled
                )
            }
            IconButton (
                onClick = {},
                enabled = if (disabledIndex == 4) false else true
            ) {
                Icon (
                    imageVector = Icons.Default.Wallet,
                    contentDescription = "Um ícone de carteira",
                    modifier = Modifier
                        .scale(scale),
                    tint = if (disabledIndex == 4) tintDisabled else tintEnabled
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomMenuElementPreview1 () {
    BottomMenuElement(1)
}
@Preview
@Composable
fun BottomMenuElementPreview2 () {
    BottomMenuElement(2)
}
@Preview
@Composable
fun BottomMenuElementPreview3 () {
    BottomMenuElement(3)
}
@Preview
@Composable
fun BottomMenuElementPreview4 () {
    BottomMenuElement(4)
}