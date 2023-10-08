package com.example.queuemanagementapp.presentationlayer.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.queuemanagementapp.R
import com.example.queuemanagementapp.ui.theme.QueueManagementAppTheme
import com.example.queuemanagementapp.ui.theme.fontFamily

@Composable
fun StoreCard(
  name :String,
  counter : Int,
  customers : Int,
  address: String,
  waitingTime: List<Double>
) {
    val cornerRadius = 12.dp
    Card(
        modifier = Modifier
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(cornerRadius))
            .size(184.dp)
            .padding(1.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ){
            Spacer(modifier = Modifier.heightIn(2.dp))
            Text(text = name,
                style = TextStyle(
                  fontWeight = FontWeight.SemiBold,
                    fontFamily = fontFamily,
                    fontSize = 25.sp
                )
            )
            Spacer(modifier = Modifier.heightIn(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(painter = painterResource(id = R.drawable.counter)
                    ,contentDescription = "counter",
                    modifier = Modifier.size(25.dp))
                Box (
                       modifier = Modifier
                           .padding(4.dp)
                           .background(Color(0xFFf9db6d), shape = CircleShape)
                           .size(20.dp),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = counter.toString(),
                        color  = Color.Black,
                        style = TextStyle(
                            fontFamily = fontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Spacer(modifier = Modifier.fillMaxWidth(0.08f))
                Icon(painter = painterResource(id = R.drawable.customers)
                    ,contentDescription = "counter",
                    modifier = Modifier.size(25.dp))
                Box (
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color(0xFFf9db6d), shape = CircleShape)
                        .size(20.dp),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = customers.toString(),
                        color  = Color.Black,
                        style = TextStyle(
                            fontFamily = fontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
               Text(
                   text= "Counters",
                   style = TextStyle(
                       fontFamily = fontFamily,
                       fontWeight = FontWeight.Normal,
                       fontSize = 12.sp
                   )
               )
                Spacer(modifier = Modifier.fillMaxWidth(0.08f))
                Text(
                    text= "Costumers",
                    style = TextStyle(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                )
            }
            Spacer(modifier = Modifier.heightIn(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
                ) {
               Icon(
                   painter = painterResource(id = R.drawable.location_sign_svgrepo_com),
                   contentDescription = "location",
                   modifier = Modifier.size(12.dp)
               )
                Spacer(modifier = Modifier.fillMaxWidth(0.04f))
                Text(
                    text = address,
                    style = TextStyle(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                    )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_timer_24),
                    contentDescription = "timer",
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text= "Waiting Time",
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 8.sp
                    )
                )

            }
            Box (
                modifier = Modifier
                    .padding(4.dp)
                    .background(Color(0xFFf9db6d))
                    .fillMaxWidth()
            ){

            }
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(25.dp),
                contentPadding = PaddingValues(vertical = 0.dp, horizontal = 0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF41D3BD)
                )
                ) {
              Text(text = "Join Queue",
                  color =   Color.Black,
                  style = TextStyle(
                      fontFamily = fontFamily,
                      fontWeight = FontWeight.Bold,
                  )
              )
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun Preview(){
    QueueManagementAppTheme {
        StoreCard("Qatar Store" , 2,10,"Address of the store here aeellaaa jaduuu",)
    }
}