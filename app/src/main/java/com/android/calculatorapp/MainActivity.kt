package com.android.calculatorapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.calculatorapp.components.InputField
import com.android.calculatorapp.ui.theme.lightPurple
import com.android.calculatorapp.widgets.RoundButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            TopHeader()
            MainContent()
        }
    }
}

//@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 134.0){
    Surface (modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = lightPurple)
    {
        Column (
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text = "Total Per Person",
                style = TextStyle(
                    fontSize = 16.sp
                )
            )

            val total = "%.2f".format(totalPerPerson)
            Text(text = "$$total",
                style = TextStyle
                (
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
            )   )

        }
    }
}

@Preview
@Composable
fun MainContent(){

            BillForm(){ billAmt ->
                Log.d("AMT", "MainContent: $billAmt")

            }

    }

@Composable
fun BillForm(modifier: Modifier = Modifier,
             onValChange: (String) -> Unit = {})
{

    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember (totalBillState.value){
        totalBillState.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current


    Surface (modifier = Modifier


        .padding(2.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ){
        Column (modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start){
            InputField(valueState = totalBillState ,
                labelID = "Enter Bill" ,
                enabled = true,
                isSingleLine =true,
                modifier = Modifier.fillMaxWidth(),
                onAction = KeyboardActions{
                    if (!validState) return@KeyboardActions
                   onValChange(totalBillState.value.trim())

                    keyboardController?.hide()
                }
            )

            if (validState){
                Row (modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Text(text = "Split",
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )

                    Row (modifier = Modifier.padding(horizontal = 3.dp)){
                        RoundButton(imageVector = Icons.Default.Remove,
                            onClick = {  })

                        RoundButton(imageVector = Icons.Default.Add,
                            onClick = { })
                    }
                }


            }
            else
                Box{

                }
        }

    }
}




