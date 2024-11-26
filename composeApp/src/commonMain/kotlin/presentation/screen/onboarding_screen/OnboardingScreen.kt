package presentation.screen.onboarding_screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.launch
import models.OnboardingPage
import navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.screen.main_screen.MyHomeScreen
import offer.composeapp.generated.resources.Res
import offer.composeapp.generated.resources.walk2
import offer.composeapp.generated.resources.walk3
import utils.AppColors

class OnboardingScreen() : Screen {

    @OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()

        val onboardingPages = listOf(
            OnboardingPage(
                imageRes = Res.drawable.walk2,
                title = "The Most Trusted Insurance Platform",
                description = "Compare top offers and get insured in just 90 seconds"
            ),
            OnboardingPage(
                imageRes = Res.drawable.walk2,
                title = "Discover",
                description = "Discover amazing features."
            ),
            OnboardingPage(
                imageRes = Res.drawable.walk3,
                title = "Get Started",
                description = "Let's get started!"
            )
        )
        val pagerState = rememberPagerState(pageCount = { onboardingPages.size })

        Box(modifier = Modifier.fillMaxSize()) {


            HorizontalPager(
//                pageCount = onboardingPages.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val onboardingPage = onboardingPages[page]

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(onboardingPage.imageRes),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = onboardingPage.title,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 25.sp,
                            //color = Color(0xFF333333),
                            fontWeight = FontWeight.Bold,
                            //letterSpacing = 0.15.sp,
                            //lineHeight = 24.sp,
                            //fontFamily = FontFamily.Default // You can use a custom font here
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        textAlign = TextAlign.Center,
                        text = onboardingPage.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }

            Box(
                modifier =
                Modifier.padding(10.dp)
                    .align(Alignment.TopEnd),
            ) {

                Button(
                    onClick = {
                        moveToMainActivity(navigator)
                    },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.height(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    contentPadding = PaddingValues(
                        10.dp,
                        0.dp,
                        0.dp,
                        0.dp
                    ) // Remove default padding
                ) {
                    // Skip text
                    Text(
                        color = Color.White,
                        text = "Skip",// Makes the text take up remaining space
                        style = MaterialTheme.typography.labelSmall,
                        overflow = TextOverflow.Ellipsis // Handles text overflow
                    )

                    // Forward arrow icon
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Forward arrow",
                        tint = Color.White
                    )
                }

            }


            // Page Indicator and Forward Button
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Page Indicator
                Row {

                    repeat(onboardingPages.size) { index ->

                        // Calculate animation based on the current page and offset fraction
                        val isSelected = pagerState.currentPage == index
                        //val nextToSelect = pagerState.currentPage + 1 == index
                        val offsetFraction = pagerState.currentPageOffsetFraction.coerceIn(-1f, 1f)
                        val progress =
                            1f - (if (isSelected /*|| nextToSelect*/) offsetFraction else 0f).coerceAtLeast(
                                0f
                            )

                        val animatedWidth = animateDpAsState(
                            targetValue = if (isSelected /*|| nextToSelect*/) 5.dp + (20.dp * progress) else 10.dp
                        ).value
                        val animatedCornerRadius = animateFloatAsState(50f).value
                        val color = if (isSelected) AppColors.AppColor else Color.Gray

                        Spacer(modifier = Modifier.width(5.dp))

                        Canvas(
                            modifier = Modifier.size(
                                width = animatedWidth,
                                height = 18.dp
                            )
                        ) {
                            drawRoundRect(
                                color = color,
                                size = Size(animatedWidth.toPx(), 10.dp.toPx()),
                                cornerRadius = CornerRadius(
                                    animatedCornerRadius,
                                    animatedCornerRadius
                                )
                            )
                        }
                    }
                }


                // Forward Button
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            if (pagerState.currentPage == onboardingPages.size - 1) {
                                // Call finish action on the last page
                                moveToMainActivity(navigator)
                            } else {
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    modifier = Modifier.size(40.dp).background(AppColors.AppColor, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Next",
                        tint = Color.White
                    )
                }
            }
        }
    }

    private fun moveToMainActivity(navigator: Navigator) {
        navigator.replace(MyHomeScreen())
        //SHARED_PREFERENCE.put("isAlreadyViewed", true)
    }
}