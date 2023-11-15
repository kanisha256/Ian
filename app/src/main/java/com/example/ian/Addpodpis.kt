package com.example.ian

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.work.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class Addpodpis  : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThreeFieldsScreen()
        }
    }

    @Composable
    fun ThreeFieldsScreen() {
        var name by remember { mutableStateOf("") }
        var price by remember { mutableStateOf("") }
        var date by remember { mutableStateOf("") }
        var db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser!!.email
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val isMenuOpen = remember { mutableStateOf(false) }
            val menuItems = listOf("Яндекс", "Кинопоиск", "Okko", "Youtube", "Netflix")
            val selectedItem = remember { mutableStateOf("") }

            Column {
                Text(text = selectedItem.value)
                Text(
                    text = "Click to open menu",
                    modifier = Modifier.clickable { isMenuOpen.value = true }
                )

                DropdownMenu(
                    expanded = isMenuOpen.value,
                    onDismissRequest = { isMenuOpen.value = false }
                ) {
                    menuItems.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedItem.value = item
                                isMenuOpen.value = false
                            }
                        ) {
                            Text(text = item)
                        }
                    }
                }
            }
            name = selectedItem.value
            TextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Palata") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Palata") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {

                    val workManager = WorkManager.getInstance(this@Addpodpis)

                    // Определяем задачу, которую необходимо выполнить
                    val data = Data.Builder()
                        .putString("name", name)
                        .putString("price", price)
                        .putString("date", date)
                        .build()

                    val delay = 30L// Задержка в днях, 30 дней соответствуют месяцу
                    val constraints = Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()

                    val oneTimeWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                        .setInputData(data)
                        .setInitialDelay(delay, TimeUnit.DAYS)
                        .setConstraints(constraints)
                        .build()

                    workManager.enqueue(oneTimeWorkRequest)

                    db.collection(currentUser.toString()).get().addOnSuccessListener {documents ->
                        db.collection(currentUser.toString()).document().set(Addpod(name = name, price = price,date=date))
                        val intent = Intent(this@Addpodpis, Podpiski::class.java)
                        startActivity(intent)
                        finish()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add")
            }
        }
    }


}