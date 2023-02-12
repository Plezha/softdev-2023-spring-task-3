package com.example.myapplication.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import java.io.File



@Composable
fun Page2(
    filesDir: File?,
) {
    var execInput by remember {
        mutableStateOf(
            if (filesDir != null) {
                val file = File(filesDir, "toExec.py")
                val reader = file.reader()
                reader.readText().also { reader.close() }
            } else {
                "execInput will be here"
            }
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            value = execInput,
            onValueChange = { execInput = it },
        )
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(60.dp),
            onClick = {
                if (filesDir != null) {
                    val file = File(filesDir, "toExec.py")
                    file.createNewFile()
                    val writer = file.writer()
                    writer.write(execInput)
                    writer.close()
                }
            },
        ) {
            Icon(Icons.Filled.Save, contentDescription = "")
        }
    }
}