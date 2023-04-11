package com.raghav.samplekmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raghav.samplekmm.SpaceXSDK
import com.raghav.samplekmm.cache.DatabaseDriverFactory
import com.raghav.samplekmm.entity.RocketLaunch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SampleKmmApp()
                }
            }
        }
    }
}

@Composable
fun SampleKmmApp(modifier: Modifier = Modifier) {
    val sdk = SpaceXSDK(DatabaseDriverFactory(LocalContext.current))

    var launches by remember {
        mutableStateOf(emptyList<RocketLaunch>())
    }

    LaunchedEffect(true) {
        launches = sdk.getLaunches(false)
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(items = launches) {
            LaunchCard(launch = it)
        }
    }
}

@Composable
fun LaunchCard(launch: RocketLaunch, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth(), elevation = 8.dp) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = stringResource(R.string.launch, launch.missionName))
            Text(
                text = if (launch.launchSuccess == true) stringResource(R.string.success) else stringResource(
                    R.string.unsuccessful
                ),
                color = if (launch.launchSuccess == true) Color.Green else Color.Red
            )
            Text(text = stringResource(R.string.launch_year, launch.launchYear.toString()))
            Text(text = stringResource(R.string.launch_details, launch.details.toString()))
        }
    }
}
