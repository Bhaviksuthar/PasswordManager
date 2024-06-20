package com.mew.practicalpasswordmanager.Screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import com.mew.practicalpasswordmanager.DataAndDB.PasswordModel
import com.mew.practicalpasswordmanager.DataAndDB.Utlis.Utility
import com.mew.practicalpasswordmanager.DataAndDB.ViewModel.PasswordViewmodel
import com.mew.practicalpasswordmanager.R
import eu.wewox.modalsheet.ExperimentalSheetApi
import eu.wewox.modalsheet.ModalSheet


@Composable
fun ListOfPasswords(context: Context, viewmodel: PasswordViewmodel, owner: LifecycleOwner) {
    //getting list
    ListOfItems(context, viewmodel, owner)
}

@Composable
fun ListOfItems(context: Context, viewmodel: PasswordViewmodel, owner: LifecycleOwner) {

    val password by viewmodel.passwords.observeAsState(initial = emptyList())

    //checking if their is empty list
    if (password.isEmpty()) {
        TextItem()
    } else {
        //list
        LazyColumn() {
            itemsIndexed(password, itemContent = { index, item ->
                //item for list
                Items(context, viewmodel, item)
            })
        }
    }

}

//function for if their is empty list it shows text message of no saved password
@Composable
fun TextItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center, text = "No Saved Password Found",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }

}

//items of list
@OptIn(ExperimentalSheetApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Items(context: Context, viewmodel: PasswordViewmodel, item: PasswordModel) {

    var visible by rememberSaveable {
        mutableStateOf(false)
    }

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    //custom card
    Card(
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Transparent,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(20.dp), modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
            .clickable {
                visible = true
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.accountType, color = Color.Black, style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                )
            )
            val starString = String(CharArray(item.password.length)).replace('\u0000', '*')
            Text(maxLines = 16, text = starString, color = colorResource(id = R.color.grey))

            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.next),
                contentDescription = ""
            )
        }
    }

    //bottom sheet for editing and deleting password data
    ModalSheet(
        shape = RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp),
        visible = visible,
        onVisibleChange = { visible = it },
    ) {
        Box(Modifier.height(400.dp)) {

            val getKey = Utility.convertStringToAESKey(item.key)
            val passDecrypt = Utility.decrypt(item.password,getKey)

            var accountType by rememberSaveable {
                mutableStateOf(item.accountType)
            }

            var email by rememberSaveable {
                mutableStateOf(item.username_Gmail)
            }

            var password by rememberSaveable {
                mutableStateOf(passDecrypt)
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(modifier = Modifier.padding(15.dp), text = "Account Details", style = TextStyle(
                        fontSize = 24.sp,
                        color = colorResource(id = R.color.blue),
                        fontWeight = FontWeight.SemiBold
                    ))
                }

                Spacer(modifier = Modifier.size(0.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp), text = "Account Type", style = TextStyle(
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.lightgrey)
                    ))
                }
                OutlinedTextField(
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 0.dp),
                    keyboardOptions = KeyboardOptions(autoCorrect = true, keyboardType = KeyboardType.Text),

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    ),
                    value = accountType,
                    onValueChange = {
                        accountType = it
                    })

                Spacer(modifier = Modifier.size(0.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp), text = "Username/Email", style = TextStyle(
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.lightgrey)
                    ))
                }
                OutlinedTextField(
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 0.dp),
                    keyboardOptions = KeyboardOptions(autoCorrect = true, keyboardType = KeyboardType.Email),

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    ),
                    value = email,
                    onValueChange = {
                        email = it
                    })

                Spacer(modifier = Modifier.size(0.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp), text = "Password", style = TextStyle(
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.lightgrey)
                    ))
                }
                OutlinedTextField(
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 0.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(autoCorrect = true, keyboardType = KeyboardType.Password),

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    ),
                    value = password,
                    onValueChange = {
                        password = it
                    })

                Spacer(modifier = Modifier.size(20.dp))

                // row for button edit and delete
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {

                    //edit button
                    Button(modifier = Modifier
                        .height(50.dp)
                        .width(180.dp)
                        .padding(20.dp, 0.dp, 20.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        onClick = {
                            if(accountType.isEmpty()){
                                Toast.makeText(context,"Account Name is Empty", Toast.LENGTH_SHORT).show()
                            }
                            else if(email.isEmpty()){
                                Toast.makeText(context,"Email is Empty", Toast.LENGTH_SHORT).show()
                            }
//                else if (!email.contains("@gmail.com") || !email.contains("@mail.com")){
//                    Toast.makeText(context,"Email is not valid",Toast.LENGTH_SHORT).show()
//                }
                            else if (!containsNumbers(password)){
                                Toast.makeText(context,"Please enter any letter in password",
                                    Toast.LENGTH_SHORT).show()
                            }
                            else if (!containsSpecialCharacters(password)){
                                Toast.makeText(context,"Please enter any special character in password",
                                    Toast.LENGTH_SHORT).show()
                            }
                            else if (password.isEmpty()){
                                Toast.makeText(context,"Password is Empty", Toast.LENGTH_SHORT).show()
                            }
                            else if (password.length <=7){
                                Toast.makeText(context,"Password Length Should be more than 7 characters",
                                    Toast.LENGTH_SHORT).show()
                            }
                            else{
                                val key = Utility.generateAESKey()
                                val stringKey = Utility.convertAESKeyToString(key)
                                val encrytedPass = Utility.encrypt(password,key)
                                viewmodel.update(PasswordModel(item.id,accountType,email,encrytedPass,stringKey))
                                Toast.makeText(context,"Password Updated", Toast.LENGTH_SHORT).show()
                                visible = false
                            }

                        }) {

                        Text(text = "Edit", style = TextStyle(
                            fontSize = 17.sp,
                            color = Color.White
                        ))
                    }

                    //delete button
                    Button(modifier = Modifier
                        .height(50.dp)
                        .width(180.dp)
                        .padding(20.dp, 0.dp, 20.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.red)),
                        onClick = {
                            viewmodel.delete(item.id)
                            Toast.makeText(context,"Password Deleted",Toast.LENGTH_SHORT).show()
                            visible = false

                        }) {

                        Text(text = "Delete", style = TextStyle(
                            fontSize = 17.sp,
                            color = Color.White
                        ))
                    }
                }

            }
        }
    }
}
