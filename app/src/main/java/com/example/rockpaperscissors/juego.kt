package com.example.rockpaperscissors

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rockpaperscissors.ui.theme.RockPaperScissorsTheme
import kotlin.random.Random

@Composable
fun juego(navController: NavHostController, modifier: Modifier = Modifier) {

    var armaElegidaJug by remember { //Variable que va a determinar la imagen que escoge el jugador
        mutableStateOf(R.drawable.interrogation) //La iniciamos con una imagen neutral
    }
    var armaElegidaMaquina by remember { //Variable que va a determinar la imagen que escoge la maquina
        mutableStateOf(R.drawable.interrogation) //La iniciamos con una imagen neutral
    }

    var puntuacionMaquina by remember { //Variable que guarda la puntuación de la máquina
        mutableStateOf(0) //Inicializamos a 0
    }
    var puntuacionJugador by remember { //Variable que guarda la puntuación del jugador
        mutableStateOf(0) //Inicializamos a 0
    }
    var eleccionMaquina by remember { //Variable que muestra por texto el objeto que ha escogido la maquina
        mutableStateOf("") //Inicializamos a ""
    }
    var eleccionJugador by remember { //Variable que muestra por texto el objeto que ha escogido el jugador
        mutableStateOf("") //Inicializamos a ""
    }

    when (armaElegidaJug){ //Switch-case para el arma elegida por el jugador
        1-> armaElegidaJug = R.drawable.piedra //Si es 1 -> Es piedra
        2-> armaElegidaJug = R.drawable.paper //Si es 2 -> Es papel
        3-> armaElegidaJug = R.drawable.tijera //Si es 3 -> Es tijera
    }


    when (armaElegidaMaquina){ //Switch case para el arma elegida por la máquina
        1-> armaElegidaMaquina = R.drawable.piedra //Si es 1 -> Es piedra
        2-> armaElegidaMaquina = R.drawable.paper //Si es 2 -> Es papel
        3-> armaElegidaMaquina = R.drawable.tijera //Si es 3 -> Es tijera
    }

    //Contexto para el toast
    val context = LocalContext.current
    //Columna principal del layout, en ella estarán todos los rows y columns de la interfaz
    Column (
        modifier
            .fillMaxSize()
            .padding(10.dp, 20.dp, 10.dp, 20.dp), //Márgenes internos
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Row( //Fila que sitúa las opciones de la máquina en la parte superior de la pantalla
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = modifier
                .weight(1f) //Le damos un peso de 1 dentro de la columna externa
                .fillMaxWidth()
        ){//A cada imagen le damos un peso de 1
            Image(painter = painterResource(id = R.drawable.piedra), contentDescription = "piedra", modifier = Modifier
                .weight(1F)
                .fillMaxSize())
            Image(painter = painterResource(id = R.drawable.paper), contentDescription = "papel", modifier = Modifier
                .weight(1F)
                .fillMaxSize())
            Image(painter = painterResource(id = R.drawable.tijera), contentDescription = "tijeras", modifier = Modifier
                .weight(1F)
                .fillMaxSize())
        }
        Column ( //Columna que va a almacenar las acciones de la máquina
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .weight(1f) //Le damos un peso de uno dentro de la columna externa
                .fillMaxHeight()
                .background(Color.Red)
        ) {
            Row ( //Fila que va a mostrar un texto con la elección de la máquina en cada turno
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = modifier
                    .weight(1f) //Le damos un peso de 1 dentro de la columna interna
                    .fillMaxWidth()
            ){
                Text(text = "$eleccionMaquina", //Texto con la elección de la máquina
                    fontSize = 25.sp
                )
            }
            Row ( //Fila que va a mostrar una imagen con la elección de la máquina
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .weight(1f) //Le damos un peso de 1 dentro de la columna interna
                    .fillMaxWidth()
            ){// Imagen del arma de la máquina
                Image(painter = painterResource(id = armaElegidaMaquina), contentDescription = "esperando")
            }
        } //Fin columna interna

        Row ( //Fila que contendrá la puntuación de la partida
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .weight(1f) //Le damos un peso de 1 dentro de la columna externa
                .fillMaxWidth()
                .background(Color.Yellow)
        ){
            //Llamamos a la variable textoGanador para ajustar el texto final al ganador
            val textoGanador = textoGanador(puntuacionJugador, puntuacionMaquina)
            //Si aún no se han llegado a los 3 puntos, se sigue jugando
            if (siganJugando(puntuacionJugador, puntuacionMaquina)){
                Text(text = "Máquina $puntuacionMaquina - $puntuacionJugador Jugador",
                    fontSize = 30.sp
                )
            } else { //Si alguno de los jugadores llega a los 3 puntos, se muestra el mensaje final
                Text(text = "Máquina $puntuacionMaquina - $puntuacionJugador Jugador " + "\n" +
                        "\n" + "Ha ganado $textoGanador",
                    fontSize = 30.sp
                )
            }

        } //Fin de la fila central
        Column ( //Columna interna que va a almacenar las acciones del jugador
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .weight(1f) //Le damos un peso de 1 dentro de la columna externa
                .fillMaxHeight()
                .background(Color.Red)
        ) {
            Row(  //Fila que va a mostrar una imagen con la elección del jugador
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Image( //Imagen del arma del jugador
                    painter = painterResource(id = armaElegidaJug),
                    contentDescription = "esperando"
                )
            }
            Row( //Fila que va a almacenar un texto con el arma escogida por el jugador
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text( //Texto en el que se muestra la eleccion del jugador escrita
                    text = "$eleccionJugador",
                    fontSize = 25.sp
                )
            }
        } //Fin columna interna
        Row ( //Fila en la que se mostrarán las imágenes/opciones de juego del jugador
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
            modifier = modifier
                .weight(1f) //Le damos un peso de 1 en la columna externa
                .fillMaxWidth()
        ){
            Image(painter = painterResource(id = R.drawable.piedra), //Piedra
                contentDescription = "piedra",
                modifier = Modifier
                    .weight(1F) // Valor de peso dentro de la fila = 1
                    .fillMaxSize()
                    //Será clicable mientras no se llegue a 3 puntos
                    .clickable(enabled = siganJugando(puntuacionJugador, puntuacionMaquina)) {
                        armaElegidaJug = 1 //La piedra es el arma 1
                        eleccionJugador = "Piedra" //Texto de elección del arma de usuario
                        armaElegidaMaquina =
                            numeroRandom() //La máquina elegirá un arma random entre 1 y 3

                        eleccionMaquina =
                            textoArmaMaquina(armaElegidaMaquina) //Texto de elección del arma de la máquina

                        //Switch-case del ganador entre las armas escogidas
                        //Se llama a una función que determina el ganador y devuelve:
                        // 0 para empate, 1 para victoria jugador, 2 para victoria máquina
                        when (eligeGanador(armaElegidaJug, armaElegidaMaquina)) {
                            0 -> Toast
                                .makeText(context, "Empate", Toast.LENGTH_SHORT)
                                .show()

                            1 -> puntuacionMaquina += 1 //La máquina suma 1 punto
                            2 -> puntuacionJugador += 1 //El jugador suma 1 punto
                        }
                    }
            )
            Image(painter = painterResource(id = R.drawable.paper), //Papel
                contentDescription = "papel",
                modifier = Modifier
                    .weight(1F) //Valor de peso dentro de la fila = 1
                    .fillMaxSize()
                    //Cliclable mientras no se llegue a 3 puntos
                    .clickable(enabled = siganJugando(puntuacionJugador, puntuacionMaquina)) {
                        armaElegidaJug = 2 //El papel es el arma 2
                        eleccionJugador = "Papel"
                        armaElegidaMaquina =
                            numeroRandom() //Se le asigna un arma aleatoria a la máquina

                        eleccionMaquina = textoArmaMaquina(armaElegidaMaquina)

                        //Mismo código que en 'piedra'
                        when (eligeGanador(armaElegidaJug, armaElegidaMaquina)) {
                            0 -> Toast
                                .makeText(context, "Empate", Toast.LENGTH_SHORT)
                                .show()

                            1 -> puntuacionMaquina += 1
                            2 -> puntuacionJugador += 1
                        }
                    }
            )
            Image(painter = painterResource(id = R.drawable.tijera), //Tijera
                contentDescription = "tijeras",
                modifier = Modifier
                    .weight(1F) //Valor de peso dentro de la fila = 1
                    .fillMaxSize()
                    .clickable(enabled = siganJugando(puntuacionJugador, puntuacionMaquina)) {
                        armaElegidaJug = 3 //Las tijeras son el arma 3
                        eleccionJugador = "Tijeras"
                        armaElegidaMaquina =
                            numeroRandom() //Se le asigna un arma aleatoria a la máquina

                        eleccionMaquina = textoArmaMaquina(armaElegidaMaquina)

                        //Mismo código que en las otras armas
                        when (eligeGanador(armaElegidaJug, armaElegidaMaquina)) {
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

/**
 * Función numeroRandom, devuelve un entero aleatorio entre 1 y 4 sin incluir a este último
 */
fun numeroRandom():Int{
    return Random.nextInt(1,4)
}

/**
 * Función eligeGanador, recine por parámetros las armas de cada jugador y devuelve un entero en
 * función del ganador resultante
 */
fun eligeGanador(armaJug:Int, armaMaq:Int):Int{
    var ganador = 0 // Variable que guarda el ganador del duelo

    when(armaJug){ //switch case del arma del jugador
        1 -> when (armaMaq){ //Cuando valga 1 (piedra), entra switch case del arma de la máquina
            1-> ganador = 0 //Máquina = Piedra, empate
            2-> ganador = 1 //Máquina = Papel, gana la máquina
            3-> ganador = 2 //Máquina = Tijeras, gana el jugador
        }
        2 -> when (armaMaq){ //Cuando valga 2 (papel)
            1-> ganador = 2 //Máquina = Piedra, gana la máquina
            2-> ganador = 0 //Máquina = Papel, empate
            3-> ganador = 1 //Máquina = Tijeras, gana el jugador
        }
        3 -> when (armaMaq){ //Cuando valga 3 (tijeras)
            1-> ganador = 1 //Máquina = Piedra, gana la máquina
            2-> ganador = 2 //Máquina = Papel, gana el jugador
            3-> ganador = 0 //Máquina = Tijeras, empate
        }
    }

    return ganador
}

/**
 * Función textoArmaMáquina, que recibe por parámetros el arma asignada a la máquina
 * y devuelve un string con el texto del arma
 */
fun textoArmaMaquina(armaMaq: Int):String{
    var eleccionMaquina = ""
    when(armaMaq){
        1-> eleccionMaquina = "Piedra"
        2-> eleccionMaquina = "Papel"
        3-> eleccionMaquina = "Tijeras"
    }
    return eleccionMaquina
}

/**
 * Función siganJugando, que recibe por parámetros los puntos de ambos jugadores y
 * devuelve un booleano en función de si alguno de los dos ha llegado ya a 3 o no
 */
fun siganJugando(puntosJug:Int, puntosMaq:Int):Boolean{
    var siguenJugando = true
    if (puntosMaq >= 3 || puntosJug >= 3){
        siguenJugando = false
    }
    return siguenJugando
}

/**
 * Función texto ganador, que recibe por parámetros las puntuaciones de ambos jugadores y
 * devuelven un string en función del que haya resultado vencedor
 */
fun textoGanador(puntosJug:Int, puntosMaq:Int):String{
    var texto = ""
    if (puntosJug > puntosMaq){
        texto = "el jugador"
    } else {
        texto = "la máquina"
    }
    return texto
}

/**
 * Función que permite tener una vista preliminar de cómo se va a ver la aplicación una vez ejecutada
 */
@Preview(showBackground = true)
@Composable
fun juegoPreview() {
    RockPaperScissorsTheme {
        juego(navController = NavHostController(LocalContext.current))
    }
}