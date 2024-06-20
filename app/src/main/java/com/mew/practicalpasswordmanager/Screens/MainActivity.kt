package com.mew.practicalpasswordmanager.Screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.mew.practicalpasswordmanager.DataAndDB.PasswordModel
import com.mew.practicalpasswordmanager.DataAndDB.Utlis.Utility
import com.mew.practicalpasswordmanager.DataAndDB.ViewModel.PasswordViewmodel
import com.mew.practicalpasswordmanager.R
import com.mew.practicalpasswordmanager.ui.theme.PracticalPasswordManagerTheme
import eu.wewox.modalsheet.ExperimentalSheetApi
import eu.wewox.modalsheet.ModalSheet


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalSheetApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewmodel = ViewModelProvider(this)[PasswordViewmodel::class.java]

            var visible by rememberSaveable {
                mutableStateOf(false)
            }

            var passwordVisible by rememberSaveable { mutableStateOf(false) }
            

            // bottom sheet for adding Passwords
            ModalSheet(
                shape = RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp),
                visible = visible,
                onVisibleChange = { visible = it },
            ) {
                Box(Modifier.height(400.dp)) {
                    var accountType by rememberSaveable {
                        mutableStateOf("")
                    }

                    var email by rememberSaveable {
                        mutableStateOf("")
                    }

                    var password by rememberSaveable {
                        mutableStateOf("")
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

                        //textfield for accoun type
                        OutlinedTextField(
                            textStyle = TextStyle(
                                fontSize = 15.sp,
                                color = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 0.dp, 20.dp, 0.dp),
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = true,
                                keyboardType = KeyboardType.Text
                            ),
                            label = {
                                Text(
                                    text = "Account Name",
                                    color = colorResource(id = R.color.lightgrey)
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = colorResource(id = R.color.lightgrey),
                                focusedBorderColor = colorResource(id = R.color.lightgrey)
                            ),
                            value = accountType,
                            onValueChange = {
                                accountType = it
                            })

                        //textfield for username
                        OutlinedTextField(
                            textStyle = TextStyle(
                                fontSize = 15.sp,
                                color = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 8.dp, 20.dp, 8.dp),
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = true,
                                keyboardType = KeyboardType.Email
                            ),
                            label = {
                                Text(
                                    text = "Username/Email",
                                    color = colorResource(id = R.color.lightgrey)
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = colorResource(id = R.color.lightgrey),
                                focusedBorderColor = colorResource(id = R.color.lightgrey)
                            ),
                            value = email,
                            onValueChange = {
                                email = it
                            })

                        //textfield for password
                        OutlinedTextField(
                            textStyle = TextStyle(
                                fontSize = 15.sp,
                                color = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 0.dp, 20.dp, 0.dp),
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = true,
                                keyboardType = KeyboardType.Password
                            ),
                            label = {
                                Text(
                                    text = "Password",
                                    color = colorResource(id = R.color.lightgrey)
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = colorResource(id = R.color.lightgrey),
                                focusedBorderColor = colorResource(id = R.color.lightgrey)
                            ),
                            value = password,
                            onValueChange = {
                                password = it
                            })

                        Spacer(modifier = Modifier.size(20.dp))

                        //button to add password
                        Button(modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .padding(20.dp, 0.dp, 20.dp, 0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            onClick = {
                                //logic of add new password
                                if (accountType.isEmpty()) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Account Name is Empty",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (email.isEmpty()) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Email is Empty",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
//                else if (!email.contains("@gmail.com") || !email.contains("@mail.com")){
//                    Toast.makeText(context,"Email is not valid",Toast.LENGTH_SHORT).show()
//                }
                                else if (!containsNumbers(password)) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Please enter any letter in password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (!containsSpecialCharacters(password)) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Please enter any special character in password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (password.isEmpty()) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Password is Empty",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (password.length <= 7) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Password Length Should be more than 7 characters",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {

                                    //here generating key
                                    val key = Utility.generateAESKey()
                                    //converting key to string
                                    val stringKey = Utility.convertAESKeyToString(key)
                                    //encrypting password
                                    val encrytedPass = Utility.encrypt(password, key)

                                    //addng in database
                                    viewmodel.insert(
                                        PasswordModel(
                                            0,
                                            accountType,
                                            email,
                                            encrytedPass,
                                            stringKey
                                        )
                                    )
                                    Toast.makeText(
                                        applicationContext,
                                        "Password Added",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    visible = false
                                }

                            }) {

                            Text(
                                text = "Add New Account", style = TextStyle(
                                    fontSize = 17.sp,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }

            //MainScreen
            PracticalPasswordManagerTheme {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(0.dp, 0.dp, 0.dp, 10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {
                        //Toolbar
                        TopAppBar(
                            title = {
                                Text(text = "Password Manager")
                            }, Modifier.background(Color.White),
                            colors = TopAppBarColors(
                                containerColor = Color.White,
                                scrolledContainerColor = Color.Transparent,
                                navigationIconContentColor = Color.Transparent,
                                titleContentColor = Color.Black,
                                actionIconContentColor = Color.Transparent
                            )
                        )
                        //divider
                        Divider(color = colorResource(id = R.color.divider_color))
                        ListOfPasswords(applicationContext, viewmodel, this@MainActivity)

                    }
                    //add Button
                    Image(
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.BottomEnd)
                            .clickable {
                                visible = true
                            },
                        painter = painterResource(id = R.drawable.add_btn),
                        contentDescription = ""
                    )
                }

            }
        }
    }


}


//function for checking number in password or not
fun containsNumbers(str: String): Boolean {

    val regex = ".*[0-9].*"
    return str.matches(regex.toRegex())
}
//function for checking special character in password or not
fun containsSpecialCharacters(str: String): Boolean {
    val regex = ".*[^a-zA-Z0-9].*"
    return str.matches(regex.toRegex())
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PracticalPasswordManagerTheme {
        Greeting("Android")
    }
}