package com.jetpackcompose.smartcars.ui.RV

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jetpackcompose.smartcars.R
import com.jetpackcompose.smartcars.ui.RV.model.Coche
import kotlinx.coroutines.launch

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RvScreen(navController: NavController) {

    val context = LocalContext.current
    val rvState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        state = rvState,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.background(Color.Black),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(getCoches()) { Coche ->
            ItemCoche(Coche = Coche) {
                Toast.makeText(context, it.marca, Toast.LENGTH_LONG).show()
            }
        }
    }
    val showbutton by remember {
        derivedStateOf {
            rvState.firstVisibleItemIndex > 1
        }
    }
    if (showbutton) {
        Button(
            onClick = { coroutineScope.launch { rvState.animateScrollToItem(9) } },
            modifier = Modifier.padding(15.dp)
        ) {
            Text(text = "Bajar")
        }
    }

}

fun getCoches(): List<Coche> {
    return listOf(
        Coche("Pepe", "Perez", "Electrico", R.drawable.cocheelectrico),
        Coche("Jose", "Ortega", "Electrico", R.drawable.cocheelectrico),
        Coche("Miguel", "Garcia", "Electrico", R.drawable.cocheelectrico),
        Coche("Manuel", "Romero", "Electrico", R.drawable.cocheelectrico),
        Coche("Saul", "Jimenez", "Electrico", R.drawable.cocheelectrico),
        Coche("Daniel", "De la Vega", "Electrico", R.drawable.cocheelectrico),
        Coche("Jose Mari", "Castor", "Electrico", R.drawable.cocheelectrico),
        Coche("Fernando", "Lobo", "Electrico", R.drawable.cocheelectrico),
        Coche("Paco", "Ramirez", "Electrico", R.drawable.cocheelectrico),
        Coche("Fernando", "Alonso", "Electrico", R.drawable.cocheelectrico),
        Coche("Fernando", "Alonso", "Electrico", R.drawable.cocheelectrico)

    )
}

@Composable
fun ItemCoche(Coche: Coche, onItemSelected: (Coche) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.LightGray,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(modifier = Modifier.padding(20.dp)) {
            Column(modifier = Modifier.background(color = Color.Black)) {
                Image(
                    painter = painterResource(id = Coche.imagen),
                    contentDescription = "Coche",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .size(110.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,

                    )
                Text(
                    text = Coche.marca,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.White,
                    fontSize = 28.sp
                )
                Text(
                    text = Coche.modelo,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 15.sp,
                    color = Color.White
                )
                Text(
                    text = Coche.tipo,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 22.sp,
                    color = Color.White
                )

            }

        }
    }
}
