package presentation.fragments.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import models.Data
import navigator
import presentation.components.ErrorScreen
import presentation.components.LoadingScreen
import presentation.screen.insurance_type.InsuranceTypeScreen
import presentation.screen.login.LoginScreen
import utils.AppColors
import utils.LogInManager
import models.enums.ApiStatus

val title1 = "Car Insurance";
val title2 = "Travel Insurance";
val title3 = "Health Insurance";
val title4 = "Professional indemnity Insurance";

@Composable
fun HomeScreen() {
    val viewModel = getViewModel(Unit, viewModelFactory { HomeFragmentViewModel() })
    val state: HomeUiState by viewModel.uiState.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Make the entire con tent scrollable
    Column(
        modifier = Modifier.fillMaxSize()
            /*.verticalScroll(rememberScrollState())*/
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {

        if (!LogInManager.loggedIn) {
            // Login/signup banner
            Row(
                modifier = Modifier.fillMaxWidth().clip(CircleShape)
                    .background(AppColors.tabBackgroundColor)
                    .clickable {
                        navigator.push(LoginScreen())
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person, // Use a relevant icon
                    contentDescription = "Login icon",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Login or signup", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Start now",
                    style = MaterialTheme.typography.bodyMedium.copy(color = AppColors.AppColor)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Arrow",
                    tint = AppColors.AppColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section title
        Text(
            text = "Select your insurance",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (state.apiStatus) {
            ApiStatus.LOADING -> {
                LoadingScreen()
            }


            ApiStatus.SUCCESS -> {

                if (state.servicesResponse.success) {
                    if (isRefreshing) {
                        LoadingScreen()
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(8.dp)
                            .pointerInput(Unit) {
                                detectVerticalDragGestures { _, dragAmount ->
                                    run {
                                        if (dragAmount > 20 && !isRefreshing) { // Start refresh if dragged enough
                                            scope.launch {
                                                isRefreshing = true
                                                viewModel.getServices() // Call refresh function
                                                isRefreshing = false
                                            }
                                        }
                                    }
                                }
                            },
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Main list items
                        items(state.servicesResponse.data.size) { index ->
                            InsuranceCard(state.servicesResponse.data[index], Color(0xFFE0F2FF))
                        }
                    }
                }
            }

            ApiStatus.ERROR -> {
                ErrorScreen(state.servicesResponse.message)
            }

            else -> {}
        }


        /* val data = Data(false, "", "", "", "", 1, "", "", "", "", "", "", "", false, "", "", 1)
         data.title = title1
         // Insurance options
         InsuranceCard(data, Color(0xFFE0F2FF))
         data.title = title2
         InsuranceCard(data, Color(0xFFD0F8CE))
         data.title = title3

         InsuranceCard(data, Color(0xFFE0F7FA))
         Spacer(modifier = Modifier.width(8.dp))
         data.title = title4
         InsuranceCard(data, Color(0xFFE0F2FF))*/
    }
}


@Composable
fun InsuranceCard(service: Data, backgroundColor: Color) {

    Box(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clip(RoundedCornerShape(16.dp))
            .background(backgroundColor).clickable {
                navigator.push(InsuranceTypeScreen())
            }.padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1.5f)) {
                Text(
                    modifier = Modifier.fillMaxHeight(),
                    text = service.title.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = service.title,
                    modifier = Modifier.clip(CircleShape).size(30.dp)
                        .border(2.dp, AppColors.AppColor, CircleShape)
                        .padding(5.dp),
                    tint = Color.Unspecified
                )
            }

            KamelImage(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(5.dp)),
                resource = asyncPainterResource(data = service.imageURL),
                contentScale = ContentScale.Crop,
                contentDescription = "A picture of ${service.title}"
            )

            /*Icon(
                painter = painterResource(Res.drawable.star), // Replace with appropriate icon resource
                contentDescription = "Arrow",
                tint = AppColors.AppColor,
                modifier = Modifier.weight(.5f).size(80.dp)
            )*/
        }
    }
}
