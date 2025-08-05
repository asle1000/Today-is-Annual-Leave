package com.dayoff.feature.splash

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dayoff.core.model.Screen
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.feature.splash.model.SplashNavigation
import org.koin.androidx.compose.koinViewModel

/**
 *  Created by KyunghyunPark at 2025. 7. 24.
 *
 * Splash Screen
 * (w. remoteConfig for contained latest app version)
 */
@Preview(showBackground = true)
@Composable
fun TialSplashScreen(
    viewModel: TialSplashViewModel = koinViewModel<TialSplashViewModel>(),
    onNavigate: (Screen) -> Unit = { }
) {
    val context = LocalContext.current
    val color = LocalTialColors.current

    val navigationState by viewModel.navigationState.collectAsStateWithLifecycle()

    LaunchedEffect(navigationState) {
        when(navigationState) {
            SplashNavigation.Home -> {
                onNavigate(Screen.Calendar)
            }

            SplashNavigation.Update -> {
                Toast.makeText(context, "업데이트가 필요합니다.", Toast.LENGTH_SHORT).show()
            }

            SplashNavigation.Error -> {
                Toast.makeText(context, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }

            null -> Unit
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = color.background.base.white),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(weight = 1f))

            Image(
                painter = painterResource(id = R.drawable.img_splash_logo),
                contentDescription = stringResource(id = R.string.description_splash_logo),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.text_app_name), style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily(Font(resId = R.font.y_clover_bold)),
                    fontWeight = FontWeight.Bold,
                    color = color.text.brand.primary,
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(modifier = Modifier.weight(weight = 3f))
        }
    }
}