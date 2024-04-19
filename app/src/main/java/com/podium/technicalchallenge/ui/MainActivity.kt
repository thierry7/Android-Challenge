package com.podium.technicalchallenge.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.podium.technicalchallenge.navigation.MovieNavGraph
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.geometry.Offset


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MaterialTheme{
                val myGradient = Brush.linearGradient(
                    colors = listOf(
                        Color(0xffb226e1),
                        Color(0xfffc6601),
                        Color(0xff5995ee),
                        Color(0xff303535)
                    ),
                    start = Offset(Float.POSITIVE_INFINITY, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY)

                )
                Box(Modifier.fillMaxSize().background(myGradient)){
                    MovieNavGraph()

                }

            }

        }

    }
}
