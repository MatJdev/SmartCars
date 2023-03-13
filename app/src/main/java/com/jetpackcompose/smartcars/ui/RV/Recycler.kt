package com.jetpackcompose.smartcars.ui.RV

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.MaterialTheme
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
fun RvScreen() {

    val context = LocalContext.current
    val rvState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        state = rvState,
        verticalArrangement = Arrangement.spacedBy(7.dp),
        modifier = Modifier.background(Color.White),
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
        Coche("Tesla", "S", "Electrico", R.drawable.tesla1),
        Coche("Tesla", "S", "Electrico", R.drawable.tesla1),
        Coche("Tesla", "S", "Electrico", R.drawable.tesla1),
        Coche("Tesla", "S", "Electrico", R.drawable.tesla1),
        Coche("Tesla", "S", "Electrico", R.drawable.tesla1),
        Coche("Tesla", "S", "Electrico", R.drawable.tesla1),
        Coche("Tesla", "S", "Electrico", R.drawable.tesla1),
        Coche("Tesla", "S", "Electrico", R.drawable.tesla1),
        Coche("Tesla", "S", "Electrico", R.drawable.tesla1),
        Coche("Tesla", "S", "Electrico", R.drawable.tesla1),
        Coche("Tesla", "S", "Electrico", R.drawable.tesla1),

    )
}

@Composable
fun ItemCoche(Coche: Coche, onItemSelected: (Coche) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor =  Color(0xFF2C2B34),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        border = BorderStroke(6.dp,Color.Black),


        ) {
        Row {
            Image(
                painter = painterResource(id = Coche.imagen),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = Coche.marca, style = MaterialTheme.typography.h5,color = Color.White)
                Text(text = Coche.modelo, style = MaterialTheme.typography.subtitle1,color = Color.White)
                Text(text = Coche.tipo, style = MaterialTheme.typography.subtitle2,color = Color.White)
            }
        }
    }
}
