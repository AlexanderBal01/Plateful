package com.example.plateful.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.plateful.R

/**
 * Custom font family for the Plateful app.
 */
val SingleDayRegular =
    FontFamily(
        Font(R.font.singleday_regular),
    )

/**
 * Typography styles for the Plateful app.
 *
 * This typography provides the basic text styles used throughout the app.
 */
val Typography =
    Typography(
        bodyLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            ),
        titleLarge =
            TextStyle(
                fontFamily = SingleDayRegular,
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp,
                lineHeight = 40.sp,
            ),
    )
