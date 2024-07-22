//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.example.ebookapp.presentation.viewModels.BookViewModel
//
////package com.example.ebookapp.dummy
////
////import android.Manifest
////import androidx.activity.viewModels
////import androidx.compose.material3.AlertDialog
////import androidx.compose.material3.Button
////import androidx.compose.material3.Text
////import androidx.compose.runtime.Composable
////import androidx.compose.ui.Modifier
////
//////to show the user that why we want the permission
////@Composable
////fun PermissionDialog(modifier: Modifier = Modifier, onDismiss: () -> Unit, onConfirm: () -> Unit) {
////
////    AlertDialog(onDismissRequest = onDismiss, confirmButton = {
////
////        Button(onClick = { onConfirm }) {
////            Text(text = "Ok")
////        }
////
////    }, title = {
////        Text(text = "Camera and microphone permission required")
////    }, text = {
////        Text(text = "This app requires Camera and microphone permission to handle")
////
////    })
////
////}
//////
//////fun permissions(){
//////    val showDialog = permissionViewModel.showDialog.collectAsState().value
//////    val launchAppSettings = permissionViewModel.launchAppSettings.collectAsState().value
//////
//////
//////    val permissionsResultActivityLauncher = rememberLauncherForActivityResult(contract =
//////    ActivityResultContracts.RequestMultiplePermissions(), onResult = { result ->
//////        permissions.forEach { permission ->
//////            if (result[permission] == false) {
//////                if (!shouldShowRequestPermissionRationale(permission)) {
//////                    permissionViewModel.updateLaunchAppSettings(true)
//////
//////                }
//////                permissionViewModel.updateShowDialog(true)
//////            }
//////        }
//////
//////    })
//////    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//////        Box(
//////            modifier = Modifier
//////                .fillMaxSize()
//////                .padding(innerPadding), contentAlignment = Alignment.Center
//////        ) {
////////                        NavApp()
//////            Button(onClick = {
//////
////////                            check if we have the permission already
//////                permissions.forEach { permission ->
//////                    val isGranted =
//////                        checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
//////
//////
//////                    if (!isGranted) {
//////                        if (shouldShowRequestPermissionRationale(permission)) {
//////                            permissionViewModel.updateShowDialog(true)
//////                        } else {
//////                            permissionsResultActivityLauncher.launch(permissions)
//////                        }
//////                    }
//////                }
//////
//////
//////            }) {
//////                Text(text = "request permissions")
//////            }
//////
//////
//////        }
//////        if (showDialog) {
//////            PermissionDialog(onDismiss = {
//////                permissionViewModel.updateShowDialog(false)
//////            },
//////                onConfirm = {
//////                    permissionViewModel.updateShowDialog(false)
//////
//////                    if (launchAppSettings) {
//////                        Intent(
//////                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//////                            Uri.fromParts("package", packageName, null)
//////                        ).also {
//////                            startActivity(it)
//////                        }
//////                    } else {
//////                        permissionsResultActivityLauncher.launch(permissions)
//////                    }
//////
//////                })
//////        }
//////    }
//////}
////private val permissionViewModel by viewModels<PermissionViewModel>()
////private val permissions = arrayOf(
////    Manifest.permission.RECORD_AUDIO,
////    Manifest.permission.READ_EXTERNAL_STORAGE,
////    Manifest.permission.CAMERA
////)
//
//
//
//
//
//@Composable
//fun UploadImg(modifier: Modifier = Modifier, viewModel: BookViewModel = hiltViewModel()) {
//    var byteArrImg = byteArrayOf()
//    val context = LocalContext.current
//    val launcher =
//        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
//            if (uri != null) {
//                val inputStream = context.contentResolver.openInputStream(uri)
//                val byteImg = inputStream?.readBytes()
//                if (byteImg != null) {
//                    byteArrImg = byteImg
//                }
//            }
//
//        }
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Button(onClick = {
//            launcher.launch("*/*")
//        }) {
//            Text(text = "select")
//        }
//        Button(onClick = {
//            viewModel.uploadPdf(byteArrImg)
//        }) {
//            Text(text = "upload")
//        }
//
//    }
//}
