package com.example.tipcalculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Calculator()
                }
            }

        }//setContent
    }//onCreate
}//MainActivity

@Composable
fun Calculator() {
    var billTotalState by remember {
        mutableStateOf(0.0)
    }

    var customTipState by remember {
        mutableStateOf(0.18f)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        BillRow(
            billTotal = billTotalState,
            updateTotal = { newTotal ->
                billTotalState = newTotal

            }
        )//BillRow

        HeadingRow()

        TipRow(billTotal = billTotalState)

        TotalRow(billTotal = billTotalState)

        CustomRow(customTip = customTipState,
            updateCustom = { newCustom ->
                Log.d("nana", "value${newCustom}")
            }
        )//CustomRow

        CustomTotalRow(billTotal = billTotalState, customTip = customTipState)
    }//Column
}//Calculator

@Composable
fun Label(labelText: String, align: TextAlign, modifier: Modifier = Modifier){
    Text(
        text = labelText,
        modifier = modifier.padding(start = 8.dp, end = 8.dp),
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textAlign = align,
    )
}//Label

@Composable
fun TipAndValueField(
    value: Double,
    align: TextAlign,
    modifier: Modifier
) {
    BasicTextField(
        value = String.format("%.02f",value),
        onValueChange = {},
        enabled = false,
        textStyle = TextStyle(
            textAlign = align,
            color = Color.Black,
            fontSize = 18.sp
        ),
        modifier = Modifier
    )
}//TipAndValueField



@Composable
fun CustomRow(customTip: Float, updateCustom: (Float) -> Unit) {

} // CustomRow


@Composable
fun BillRow(billTotal: Double, updateTotal: (Double) -> Unit) {
    
    var badInput by remember {
        mutableStateOf(false)
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
       Label(labelText = "Bill Total", align = TextAlign.End)
        
        OutlinedTextField(
            value = billTotal.toString(), 
            label = {
                Text(text = "Bill Total")
            },
            onValueChange = {
                //update our state with the entered value,
                // use a max value so that it wont affect the layout
                val parsed = it.toDoubleOrNull() ?: 0.0
                if ( parsed < 999_999.99 ) {
                    badInput = false
                    updateTotal(parsed)
                } else {
                    badInput = true
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            isError = badInput,
            modifier = Modifier.fillMaxWidth()

        )//OutlinedTextField
    }

} // BillRow

@Composable
fun HeadingRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Label(labelText = "", align = TextAlign.End)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Label(labelText = "10%", align = TextAlign.Center)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Label(labelText = "15%", align = TextAlign.Center)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Label(labelText = "20%", align = TextAlign.Center)
        }
    }

}//HeadingRow

@Composable
fun TipAndTotalValueField() {

}

@Composable
fun TipRow(billTotal: Double) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Label(labelText = "Tip", align = TextAlign.End)
        }

        val tips = arrayOf(0.10, 0.15, 0.20)

        for (tip in tips) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TipAndTotalValueField(

                )
            }
        }
    }
}//TipRow

    @Composable
    fun TotalRow(billTotal: Double) {

    }//TotalRow

    @Composable
    fun CustomTotalRow(billTotal: Double, customTip: Float) {

    }//CustomTotalRow

    @Preview(showBackground = true)
    @Composable
    fun CalculatorPreview() {
        TipCalculatorTheme {
            Calculator()
        }
    }
