package models

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

data class OnboardingPage @OptIn(ExperimentalResourceApi::class) constructor(
    val imageRes: DrawableResource,
    val title: String,
    val description: String
)
