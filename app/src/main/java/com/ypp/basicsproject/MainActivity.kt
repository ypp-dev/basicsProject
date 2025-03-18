package com.ypp.basicsproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ypp.basicsproject.ui.theme.BasicsProjectTheme
import com.ypp.datastore.UserInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicsProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@Composable
fun LoginScreen(
    homeConfigState:HomeUiState
    ,login:(String,String)->Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(homeConfigState){
            HomeUiState.EmptyQuery -> {  Text(text = "$homeConfigState", modifier = Modifier)}
            HomeUiState.LoadFailed ->{  Text(text = "$homeConfigState", modifier = Modifier)}
            HomeUiState.Loading -> {
                Text(text = "$homeConfigState", modifier = Modifier)
            }
            is HomeUiState.Success -> {
                Text(
                    text = "用户信息${homeConfigState.homeConfig.userInfo}",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }

        }

        Text(
            text = "登录",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("用户名") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("密码") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(24.dp))
//2222
        Button(
            onClick = {
                // 处理登录逻辑
                login(username,password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("登录")
        }
    }
}

@Composable
fun Greeting(
    name: String,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier) {
    val homeConfigState by  viewModel.getHomeConfig.collectAsStateWithLifecycle()
    val scope= rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch (Dispatchers.IO){
            viewModel.getHomeConfig.collectLatest {
                Log.e("TAG", "getHomeConfig$it: ", )
            }
        }
    }
//    LoginScreen()
    LoginScreen(homeConfigState){username,password->
        viewModel.addUserInfo(UserInfo(userName = username, password = password))
    }
//    when(homeConfigState){
//        HomeUiState.EmptyQuery -> {  Text(text = "$homeConfigState", modifier = Modifier)}
//        HomeUiState.LoadFailed ->{  Text(text = "$homeConfigState", modifier = Modifier)}
//        HomeUiState.Loading -> {
//            Text(text = "$homeConfigState", modifier = Modifier)
//        }
//        //face1分支的内容
//        is HomeUiState.Success -> {
//            Column (modifier = Modifier.fillMaxSize()){
//                Text(
//                    text = "Hello${(homeConfigState as HomeUiState.Success).homeConfig.banner}",
//                    modifier = modifier,
//                )
//
//                Button(onClick = {
//                    viewModel.updateBanner()
//
//                }) {
//                    Text("主线程崩溃测试")
//
//                }
//                Button(onClick = {
//                    viewModel.updateTopJson()
//
//                }) {
//                    Text("子线程崩溃测试")
//
//                }
//            }
//
//        }
//    }



}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicsProjectTheme {
        Greeting("Android")
    }
}