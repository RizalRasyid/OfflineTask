
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.offlinetask.ui.viewmodel.TaskViewModel

private val EditBackground = Color(0xFF101010)
private val EditCard = Color(0xFF1D1D1D)
private val EditCardLight = Color(0xFF292929)
private val EditOrange = Color(0xFFFF7629)
private val EditDeepOrange = Color(0xFFE84D0E)
private val EditWhite = Color(0xFFF7F7F7)
private val EditGray = Color(0xFF999999)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    taskId: Int,
    viewModel: TaskViewModel,
    onBackClick: () -> Unit
) {
    val taskFlow = remember(taskId) {
        viewModel.getTaskById(taskId)
    }

    val task by taskFlow.collectAsState(initial = null)

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("Sedang") }

    var expanded by remember { mutableStateOf(false) }
    var initialized by remember(taskId) {
        mutableStateOf(false)
    }

    val priorities = listOf("Rendah", "Sedang", "Tinggi")

    LaunchedEffect(task) {
        if (task != null && !initialized) {
            title = task!!.title
            description = task!!.description
            deadline = task!!.deadline
            priority = task!!.priority
            initialized = true
        }
    }

    Scaffold(
        containerColor = EditBackground
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            EditTaskHeader(
                onBackClick = onBackClick
            )

            if (task == null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(EditCard)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "!",
                        color = EditOrange,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        text = "Tugas tidak ditemukan",
                        color = EditWhite,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Data tugas mungkin sudah dihapus.",
                        color = EditGray,
                        fontSize = 13.sp
                    )
                }
            } else {

                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "TASK MANAGEMENT",
                        color = EditOrange,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )

                    Text(
                        text = "Sempurnakan\nrencanamu.",
                        color = EditWhite,
                        fontSize = 27.sp,
                        lineHeight = 33.sp,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        text = "Perbarui informasi tugas sesuai kebutuhan.",
                        color = EditGray,
                        fontSize = 13.sp
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = EditCard
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

                        EditFormLabel("01", "Nama Tugas")

                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    "Masukkan judul tugas",
                                    color = EditGray
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            colors = editFieldColors()
                        )

                        EditFormLabel("02", "Deskripsi")

                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(125.dp),
                            placeholder = {
                                Text(
                                    "Jelaskan detail tugas...",
                                    color = EditGray
                                )
                            },
                            shape = RoundedCornerShape(14.dp),
                            colors = editFieldColors()
                        )

                        EditFormLabel("03", "Deadline")

                        OutlinedTextField(
                            value = deadline,
                            onValueChange = { deadline = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    "Contoh: 30-11-2026",
                                    color = EditGray
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            colors = editFieldColors()
                        )

                        EditFormLabel("04", "Prioritas")

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(EditCardLight)
                                    .border(
                                        1.dp,
                                        Color(0xFF444444),
                                        RoundedCornerShape(14.dp)
                                    )
                                    .clickable {
                                        expanded = !expanded
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
                                    color = EditWhite,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.weight(1f)
                                )

                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            }

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = {
                                    expanded = false
                                }
                            ) {
                                priorities.forEach { item ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(item)
                                        },
                                        onClick = {
                                            priority = item
                                            expanded = false
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
                            val updatedTask = task!!.copy(
                                title = title.trim(),
                                description = description.trim(),
                                deadline = deadline.trim(),
                                priority = priority
                            )

                            viewModel.updateTask(updatedTask)
                            onBackClick()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = title.isNotBlank() &&
                            deadline.isNotBlank(),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EditOrange,
                            contentColor = Color.White,
                            disabledContainerColor = Color(0xFF514239),
                            disabledContentColor = EditGray
                        )
                    ) {
                        Text(
                            text = "SIMPAN PERUBAHAN  →",
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
                            containerColor = EditCardLight,
                            contentColor = EditGray
                        )
                    ) {
                        Text(
                            text = "BATAL",
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            Text(
                text = "OFFLINETASK  •  MAKE IT BETTER",
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
private fun EditTaskHeader(
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
                        EditOrange,
                        EditDeepOrange,
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
                    text = "EDIT TASK",
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )

                Text(
                    text = "Perbaiki dan\nlanjutkan progres.",
                    color = Color.White,
                    fontSize = 27.sp,
                    lineHeight = 31.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Text(
            text = "✎",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp),
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 30.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
private fun EditFormLabel(
    number: String,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = number,
            color = EditOrange,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            text = label,
            color = EditWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun editFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = EditWhite,
    unfocusedTextColor = EditWhite,
    focusedContainerColor = EditCardLight,
    unfocusedContainerColor = EditCardLight,
    focusedBorderColor = EditOrange,
    unfocusedBorderColor = Color(0xFF444444),
    cursorColor = EditOrange,
    focusedLabelColor = EditOrange,
    unfocusedLabelColor = EditGray
)
