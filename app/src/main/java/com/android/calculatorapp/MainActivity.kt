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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Divider
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.calculatorapp.components.InputField
import com.android.calculatorapp.ui.theme.lightPurple
import com.android.calculatorapp.util.calculateTotalPerPerson
import com.android.calculatorapp.util.calculateTotalTip
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

    val splitValue = remember { mutableStateOf(1) }

    val tipAmountState = remember {
        mutableStateOf(0.0)
    }

    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }

            BillForm(
                splitValue = splitValue,
                tipAmountState = tipAmountState,
                totalPerPersonState = totalPerPersonState){ billAmt ->
                Log.d("AMT", "MainContent: $billAmt")
            }

    }

@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    splitValue: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalPerPersonState: MutableState<Double>,
    onValChange: (String) -> Unit = {}
             )
{



    var sliderPositionState by remember { mutableStateOf(0f) }
    val tipPercentage = (sliderPositionState * 100).toInt()


    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember (totalBillState.value){
        totalBillState.value.trim().isNotEmpty()
    }


    val keyboardController = LocalSoftwareKeyboardController.current

    Column (modifier = Modifier.padding(12.dp)){

        Text(text = "JET TIP",
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                fontStyle = FontStyle.Italic
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Divider(modifier = Modifier.height(4.dp))

        Spacer(modifier = Modifier.height(30.dp))

        TopHeader(totalPerPerson = totalPerPersonState.value)

        Spacer(modifier = Modifier.height(16.dp))
        Surface (modifier = modifier

            .padding(2.dp)
            .fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            border = BorderStroke(width = 1.dp, color = Color.LightGray)
        ){
            Column (modifier = modifier.padding(8.dp),
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
                    Row (modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "Split",
                            modifier = Modifier.padding(8.dp),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )

                        Row (modifier = modifier.padding(horizontal = 3.dp)){
                            RoundButton(imageVector = Icons.Default.Remove,
                                onClick = {
                                    if (splitValue.value > 1)
                                        splitValue.value = splitValue.value - 1

                                    totalPerPersonState.value =
                                        calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                            splitBy = splitValue.value,
                                            tipPercentage = tipPercentage)

                                })

                            Text(text = splitValue.value.toString(),
                                modifier = modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 9.dp, end = 9.dp))

                            RoundButton(imageVector = Icons.Default.Add,
                                onClick = {
                                    splitValue.value = splitValue.value + 1

                                    totalPerPersonState.value =
                                        calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                            splitBy = splitValue.value,
                                            tipPercentage = tipPercentage)


                                })
                        }
                    }


                    // Tip Row
                    Row (modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "Tip",
                            modifier = modifier.align(alignment = Alignment.CenterVertically),
                            style = TextStyle(fontSize = 16.sp)
                        )

                        Text(text = "$ ${tipAmountState.value}",
                            modifier = modifier.align(alignment = Alignment.CenterVertically),
                            style = TextStyle(fontSize = 16.sp))
                    }


                    Column (verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally){
                        Text(text = "$tipPercentage %")

                        Spacer(modifier = Modifier.height(14.dp))

                        //Slider
                        Slider(value = sliderPositionState,
                            onValueChange = { newVal ->

                                sliderPositionState = newVal

                                tipAmountState.value = calculateTotalTip( totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage)

                                totalPerPersonState.value =
                                    calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                        splitBy = splitValue.value,
                                        tipPercentage = tipPercentage)

                                Log.d("Slider", "BillForm: $newVal")
                            },
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            steps = 5)

                    }


                }
                else
                    Box{

                    }
            }

        }
    }

}







