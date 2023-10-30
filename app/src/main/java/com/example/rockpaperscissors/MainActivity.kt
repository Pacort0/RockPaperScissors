package com.example.rockpaperscissors

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    var imagenCambianteJug by remember {
        mutableStateOf(R.drawable.interrogation)
    }
    var imagenCambianteMaquina by remember {
        mutableStateOf(R.drawable.interrogation)
    }

    var puntuacionMaquina by remember {
        mutableStateOf(0)
    }
    var puntuacionJugador by remember {
        mutableStateOf(0)
    }
    var eleccionMaquina by remember {
        mutableStateOf("")
    }
    var eleccionJugador by remember {
        mutableStateOf("")
    }

    var armaElegidaJug by remember {
        mutableStateOf(0)
    }
    var armaElegidaMaq by remember {
        mutableStateOf(0)
    }

    when (armaElegidaJug){
        1-> imagenCambianteJug = R.drawable.piedra
        2-> imagenCambianteJug = R.drawable.paper
        3-> imagenCambianteJug = R.drawable.tijera
    }


    when (armaElegidaMaq){
        1-> imagenCambianteMaquina = R.drawable.piedra
        2-> imagenCambianteMaquina = R.drawable.paper
        3-> imagenCambianteMaquina = R.drawable.tijera
    }

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
               Image(painter = painterResource(id = imagenCambianteMaquina), contentDescription = "esperando")
            }
        }

        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
        ){
            val textoGanador = textoGanador(puntuacionJugador, puntuacionMaquina)
            if (siganJugando(puntuacionJugador, puntuacionMaquina)){
                Text(text = "Máquina $puntuacionMaquina - $puntuacionJugador Jugador",
                    fontSize = 30.sp
                )
            } else {
                Text(text = "Máquina $puntuacionMaquina - $puntuacionJugador Jugador " + "\n" +
                        "\n" + "Ha ganado $textoGanador",
                    fontSize = 30.sp
                )
            }

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
                    painter = painterResource(id = imagenCambianteJug),
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
                    .clickable(enabled = siganJugando(puntuacionJugador, puntuacionMaquina)) {
                        armaElegidaJug = 1
                        eleccionJugador = "Piedra"
                        armaElegidaMaq = numeroRandom()

                        eleccionMaquina = textoArmaMaquina(armaElegidaMaq)

                        when (eligeGanador(armaElegidaJug, armaElegidaMaq)) {
                            0 -> Toast
                                .makeText(context, "Empate", Toast.LENGTH_SHORT)
                                .show()

                            1 -> puntuacionMaquina += 1
                            2 -> puntuacionJugador += 1
                        }
                    }
            )
            Image(painter = painterResource(id = R.drawable.paper),
                contentDescription = "papel",
                modifier = Modifier
                    .weight(1F)
                    .clickable(enabled = siganJugando(puntuacionJugador, puntuacionMaquina)) {
                        armaElegidaJug = 2
                        eleccionJugador = "Papel"
                        armaElegidaMaq = numeroRandom()

                        eleccionMaquina = textoArmaMaquina(armaElegidaMaq)

                        when (eligeGanador(armaElegidaJug, armaElegidaMaq)) {
                            0 -> Toast
                                .makeText(context, "Empate", Toast.LENGTH_SHORT)
                                .show()

                            1 -> puntuacionJugador += 1
                            2 -> puntuacionMaquina += 1
                        }
                    }
            )
            Image(painter = painterResource(id = R.drawable.tijera),
                contentDescription = "tijeras",
                modifier = Modifier
                    .weight(1F)
                    .clickable(enabled = siganJugando(puntuacionJugador, puntuacionMaquina)) {
                        armaElegidaJug = 3
                        eleccionJugador = "Tijeras"
                        armaElegidaMaq = numeroRandom()

                        eleccionMaquina = textoArmaMaquina(armaElegidaMaq)

                        when (eligeGanador(armaElegidaJug, armaElegidaMaq)) {
                            0 -> Toast
                                .makeText(context, "Empate", Toast.LENGTH_SHORT)
                                .show()

                            1 -> puntuacionMaquina += 1
                            2 -> puntuacionJugador += 1
                        }
                    }
            )
        }
    }
}

fun numeroRandom():Int{
    val numero = Random.nextInt(1,4)
    return numero
}

fun eligeGanador(armaJug:Int, armaMaq:Int):Int{
    var ganador = 0

    when(armaJug){
        1 -> when (armaMaq){
            1-> ganador = 0
            2-> ganador = 1
            3-> ganador = 2
        }
        2 -> when (armaMaq){
            1-> ganador = 1
            2-> ganador = 0
            3-> ganador = 2
        }
        3 -> when (armaMaq){
            1-> ganador = 1
            2-> ganador = 2
            3-> ganador = 0
        }
    }

    return ganador
}

fun textoArmaMaquina(armaMaq: Int):String{
    var eleccionMaquina = ""
    when(armaMaq){
        1-> eleccionMaquina = "Piedra"
        2-> eleccionMaquina = "Papel"
        3-> eleccionMaquina = "Tijeras"
    }
    return eleccionMaquina
}

fun siganJugando(puntosJug:Int, puntosMaq:Int):Boolean{
    var siguenJugando = true
    if (puntosMaq >= 5 || puntosJug >= 5){
        siguenJugando = false
    }
    return siguenJugando
}

fun textoGanador(puntosJug:Int, puntosMaq:Int):String{
    var texto = ""
    if (puntosJug > puntosMaq){
        texto = "el jugador"
    } else {
        texto = "la máquina"
    }
    return texto
}

@Preview(showBackground = true)
@Composable
fun juegoPreview() {
    RockPaperScissorsTheme {
        juego()
    }
}