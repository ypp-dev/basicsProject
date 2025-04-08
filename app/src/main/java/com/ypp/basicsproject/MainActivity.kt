package com.ypp.basicsproject

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ypp.basicsproject.ui.theme.BasicsProjectTheme
import com.ypp.datastore.UserInfo
import com.ypp.domain.HomeConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
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
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
    homeConfig: HomeConfig,
    login: (String, String) -> Unit,
    upDateBanner: () -> Unit,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var bannerText by remember { mutableStateOf("欢迎使用我们的应用") }
    val pageState = rememberPagerState( ){
        homeConfig.banner.bannerCount
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Banner区域
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
           HorizontalPager(pageState) {

               AsyncImage(homeConfig.banner.getBanner(it).imagePath,
                   modifier = Modifier.fillMaxSize(),
                   contentScale = ContentScale.Crop,
                   contentDescription = "")
           }
        }

        Spacer(modifier = Modifier.height(16.dp))


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
        Button(
            onClick = {
                // 处理登录逻辑

                login(username,password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("登录")
        }
        Spacer(modifier = Modifier.height(24.dp))
        // 修改刷新Banner按钮的逻辑
        Button(
            onClick = {
                upDateBanner()
                bannerText = "Banner已更新 ${System.currentTimeMillis()}"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("刷新Banner")
        }
    }
}

@Composable
fun Greeting(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier=Modifier) {
    val context=LocalContext.current
    val homeUiState by  viewModel.homeUiState.collectAsStateWithLifecycle()
    val scope= rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch (Dispatchers.IO){
//            viewModel.homeUiState.collectLatest {
//                Log.e("TAG", "getHomeConfig$it: ", )
//            }
        }
    }
    when(homeUiState){
        HomeUiState.EmptyQuery -> {

        }
        HomeUiState.LoadFailed -> {}
        HomeUiState.Loading -> {

        }
        is HomeUiState.Success -> {
            LoginScreen(homeConfig = (homeUiState as HomeUiState.Success).homeConfig,{
                    username,password->
                viewModel.addUserInfo(UserInfo(userName = username, password = password),)
                Toast.makeText(context, "当前登录账号为:$username,密码为:$password", Toast.LENGTH_SHORT).show()
            }){
                viewModel.updateBanner()
            }
        }
    }

}

