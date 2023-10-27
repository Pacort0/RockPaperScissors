package com.example.rockpaperscissors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rockpaperscissors.ui.theme.RockPaperScissorsTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RockPaperScissorsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    juego()
                }
            }
        }
    }
}

@Composable
fun juego(modifier: Modifier = Modifier) {
    var puntuacionMaquina = 0
    var puntuacionJugador = 0
    var eleccionMaquina = ""
    var eleccionJugador = ""

    val context = LocalContext.current
    Column (
        modifier
            .fillMaxSize()
            .padding(10.dp, 20.dp, 10.dp, 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
        ){
            Image(painter = painterResource(id = R.drawable.piedra), contentDescription = "piedra", modifier = Modifier.weight(1F))
            Image(painter = painterResource(id = R.drawable.paper), contentDescription = "papel", modifier = Modifier.weight(1F))
            Image(painter = painterResource(id = R.drawable.tijera), contentDescription = "tijeras", modifier = Modifier.weight(1F))
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            ){
                Text(text = "$eleccionMaquina",
                    fontSize = 30.sp
                )
            }
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            ){
               Image(painter = painterResource(id = R.drawable.interrogation), contentDescription = "esperando")
            }
        }

        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
        ){
            Text(text = "Máquina $puntuacionMaquina - $puntuacionJugador Jugador",
                fontSize = 30.sp
            )
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.interrogation),
                    contentDescription = "esperando"
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "$eleccionJugador",
                    fontSize = 30.sp
                )
            }
        }
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
        ){
            Image(painter = painterResource(id = R.drawable.piedra),
                contentDescription = "piedra",
                modifier = Modifier
                    .weight(1F)
                    .clickable {
                    }
            )
            Image(painter = painterResource(id = R.drawable.paper),
                contentDescription = "papel",
                modifier = Modifier
                    .weight(1F)
                    .clickable {
                    }
            )
            Image(painter = painterResource(id = R.drawable.tijera),
                contentDescription = "tijeras",
                modifier = Modifier
                    .weight(1F)
                    .clickable {
                    }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun juegoPreview() {
    RockPaperScissorsTheme {
        juego()
    }
}

@Composable
fun numeroRandom():Int{
    val numero = Random.nextInt(0,3)
    return numero
}