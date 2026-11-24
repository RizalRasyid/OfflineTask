
package com.example.offlinetask.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.offlinetask.data.local.TaskEntity
import com.example.offlinetask.ui.viewmodel.TaskViewModel

private val AddBackground = Color(0xFF101010)
private val AddCard = Color(0xFF1D1D1D)
private val AddCardLight = Color(0xFF292929)
private val AddOrange = Color(0xFFFF7629)
private val AddDeepOrange = Color(0xFFE84D0E)
private val AddWhite = Color(0xFFF7F7F7)
private val AddGray = Color(0xFF999999)

@Composable
fun AddTaskScreen(
    viewModel: TaskViewModel,
    onBackClick: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("Sedang") }
    var priorityExpanded by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = AddBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            AddTaskHeader(onBackClick = onBackClick)

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "DETAIL TUGAS",
                    color = AddOrange,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Apa yang ingin\nkamu selesaikan?",
                    color = AddWhite,
                    fontSize = 27.sp,
                    lineHeight = 33.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Isi informasi tugasmu di bawah ini.",
                    color = AddGray,
                    fontSize = 13.sp
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AddCard
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Color(0xFF333333)
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(17.dp)
                ) {
                    AddFormLabel(number = "01", label = "Nama Tugas")

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Isi",
                                color = AddGray
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = addFieldColors()
                    )

                    AddFormLabel(number = "02", label = "Deskripsi")

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(125.dp),
                        placeholder = {
                            Text(
                                "Tuliskan detail tugas...",
                                color = AddGray
                            )
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = addFieldColors()
                    )

                    AddFormLabel(number = "03", label = "Deadline")

                    OutlinedTextField(
                        value = deadline,
                        onValueChange = { deadline = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Isi",
                                color = AddGray
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = addFieldColors()
                    )

                    AddFormLabel(number = "04", label = "Prioritas")

                    Box {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(AddCardLight)
                                .border(
                                    1.dp,
                                    Color(0xFF444444),
                                    RoundedCornerShape(14.dp)
                                )
                                .clickable {
                                    priorityExpanded = true
                                }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when (priority) {
                                            "Tinggi" -> Color(0xFFFF5555)
                                            "Rendah" -> Color(0xFF49C98B)
                                            else -> Color(0xFFFFC247)
                                        }
                                    )
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = priority,
                                color = AddWhite,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "⌄",
                                color = AddOrange,
                                fontSize = 22.sp
                            )
                        }

                        DropdownMenu(
                            expanded = priorityExpanded,
                            onDismissRequest = {
                                priorityExpanded = false
                            },
                            modifier = Modifier
                                .background(AddCardLight)
                        ) {
                            listOf("Tinggi", "Sedang", "Rendah").forEach { item ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = item,
                                            color = AddWhite
                                        )
                                    },
                                    onClick = {
                                        priority = item
                                        priorityExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            viewModel.addTask(
                                TaskEntity(
                                    title = title.trim(),
                                    description = description.trim(),
                                    deadline = deadline.trim(),
                                    priority = priority
                                )
                            )
                            onBackClick()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AddOrange,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "SIMPAN TUGAS  →",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }

                Button(
                    onClick = onBackClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AddCardLight,
                        contentColor = AddGray
                    )
                ) {
                    Text(
                        text = "BATAL",
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }

            Text(
                text = "OFFLINETASK  •  STAY FOCUSED",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                color = Color(0xFF666666),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
        }
    }
}

@Composable
private fun AddTaskHeader(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        AddOrange,
                        AddDeepOrange,
                        Color(0xFF9E310B)
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .offset(x = 230.dp, y = (-45).dp)
                .border(
                    2.dp,
                    Color.White.copy(alpha = 0.16f),
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(105.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 25.dp, y = 30.dp)
                .border(
                    2.dp,
                    Color.White.copy(alpha = 0.18f),
                    CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "←  KEMBALI",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    onBackClick()
                }
            )

            Column {
                Text(
                    text = "NEW TASK",
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )
                Text(
                    text = "Buat sesuatu\njadi selesai.",
                    color = Color.White,
                    fontSize = 27.sp,
                    lineHeight = 31.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Text(
            text = "+",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp),
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 35.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
private fun AddFormLabel(
    number: String,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = number,
            color = AddOrange,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = label,
            color = AddWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun addFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = AddWhite,
    unfocusedTextColor = AddWhite,
    focusedContainerColor = AddCardLight,
    unfocusedContainerColor = AddCardLight,
    focusedBorderColor = AddOrange,
    unfocusedBorderColor = Color(0xFF444444),
    cursorColor = AddOrange,
    focusedLabelColor = AddOrange,
    unfocusedLabelColor = AddGray
)
