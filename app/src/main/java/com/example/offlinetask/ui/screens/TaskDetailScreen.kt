
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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

private val DetailBackground = Color(0xFF101010)
private val DetailCard = Color(0xFF1D1D1D)
private val DetailCardLight = Color(0xFF292929)
private val DetailOrange = Color(0xFFFF7629)
private val DetailDeepOrange = Color(0xFFE84D0E)
private val DetailWhite = Color(0xFFF7F7F7)
private val DetailGray = Color(0xFF999999)
private val DetailGreen = Color(0xFF49C98B)
private val DetailRed = Color(0xFFFF5555)
private val DetailYellow = Color(0xFFFFC247)

@Composable
fun TaskDetailScreen(
    taskId: Int,
    viewModel: TaskViewModel,
    onBackClick: () -> Unit,
    onEditClick: (Int) -> Unit
) {
    val taskFlow = remember(taskId) {
        viewModel.getTaskById(taskId)
    }

    val task by taskFlow.collectAsState(initial = null)

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        containerColor = DetailBackground
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            DetailHeader(
                onBackClick = onBackClick
            )

            if (task == null) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DetailCard
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "!",
                            color = DetailOrange,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Black
                        )

                        Text(
                            text = "Tugas tidak ditemukan",
                            color = DetailWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Data tugas mungkin sudah dihapus.",
                            color = DetailGray,
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onBackClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DetailOrange
                            )
                        ) {
                            Text("Kembali")
                        }
                    }
                }

            } else {

                val currentTask = task!!
                val isCompleted = currentTask.status == "Selesai"

                val priorityColor = when (currentTask.priority) {
                    "Tinggi" -> DetailRed
                    "Sedang" -> DetailYellow
                    else -> DetailGreen
                }

                val statusColor = if (isCompleted) {
                    DetailGreen
                } else {
                    DetailOrange
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Text(
                        text = "TASK OVERVIEW",
                        color = DetailOrange,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )

                    Text(
                        text = currentTask.title,
                        color = DetailWhite,
                        fontSize = 28.sp,
                        lineHeight = 34.sp,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        text = "Berikut informasi lengkap tugasmu.",
                        color = DetailGray,
                        fontSize = 13.sp
                    )
                }

                // Status dan prioritas
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    DetailBadge(
                        label = currentTask.priority,
                        caption = "PRIORITAS",
                        color = priorityColor,
                        modifier = Modifier.weight(1f)
                    )

                    DetailBadge(
                        label = currentTask.status,
                        caption = "STATUS",
                        color = statusColor,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Informasi tugas
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DetailCard
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        Color(0xFF333333)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        DetailOrange.copy(alpha = 0.15f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "▤",
                                    color = DetailOrange,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = "INFORMASI TUGAS",
                                    color = DetailWhite,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp
                                )

                                Text(
                                    text = "Detail dan keterangan",
                                    color = DetailGray,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        DetailInfoItem(
                            symbol = "✎",
                            label = "DESKRIPSI",
                            value = currentTask.description.ifBlank {
                                "Tidak ada deskripsi."
                            }
                        )

                        DetailDivider()

                        DetailInfoItem(
                            symbol = "◷",
                            label = "DEADLINE",
                            value = currentTask.deadline.ifBlank {
                                "Belum ditentukan"
                            },
                            valueColor = DetailOrange
                        )

                        DetailDivider()

                        DetailInfoItem(
                            symbol = "⚑",
                            label = "TINGKAT PRIORITAS",
                            value = currentTask.priority,
                            valueColor = priorityColor
                        )

                        DetailDivider()

                        DetailInfoItem(
                            symbol = "✓",
                            label = "PROGRES TUGAS",
                            value = if (isCompleted) {
                                "Tugas telah diselesaikan"
                            } else {
                                "Tugas belum diselesaikan"
                            },
                            valueColor = statusColor
                        )
                    }
                }

                // Status action
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DetailCardLight
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "PROGRESS CONTROL",
                            color = DetailOrange,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp
                        )

                        Text(
                            text = if (isCompleted) {
                                "Tugas sudah selesai!"
                            } else {
                                "Masih ada yang harus dikerjakan."
                            },
                            color = DetailWhite,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Button(
                            onClick = {
                                val newStatus = if (isCompleted) {
                                    "Belum Selesai"
                                } else {
                                    "Selesai"
                                }

                                viewModel.updateTask(
                                    currentTask.copy(status = newStatus)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCompleted) {
                                    DetailCard
                                } else {
                                    DetailGreen
                                },
                                contentColor = DetailWhite
                            )
                        ) {
                            Text(
                                text = if (isCompleted) {
                                    "↻  TANDAI BELUM SELESAI"
                                } else {
                                    "✓  TANDAI SELESAI"
                                },
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }

                // Edit button
                Button(
                    onClick = {
                        onEditClick(taskId)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DetailOrange,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "✎  EDIT TUGAS",
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }

                // Delete button
                Button(
                    onClick = {
                        showDeleteDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3A2020),
                        contentColor = DetailRed
                    )
                ) {
                    Text(
                        text = "HAPUS TUGAS",
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }

                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "←  KEMBALI KE DASHBOARD",
                        color = DetailWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Text(
                text = "OFFLINETASK  •  KEEP MOVING FORWARD",
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

    // Dialog konfirmasi hapus
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            containerColor = DetailCard,
            shape = RoundedCornerShape(22.dp),
            title = {
                Text(
                    text = "Hapus Tugas?",
                    color = DetailWhite,
                    fontWeight = FontWeight.Black
                )
            },
            text = {
                Text(
                    text = "Apakah kamu yakin ingin menghapus tugas ini? " +
                        "Data yang dihapus tidak dapat dikembalikan.",
                    color = DetailGray,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        task?.let {
                            viewModel.deleteTask(it)
                            onBackClick()
                        }
                        showDeleteDialog = false
                    }
                ) {
                    Text(
                        text = "HAPUS",
                        color = DetailRed,
                        fontWeight = FontWeight.Black
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text(
                        text = "BATAL",
                        color = DetailWhite,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }
}

@Composable
private fun DetailHeader(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(155.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        DetailOrange,
                        DetailDeepOrange,
                        Color(0xFF9E310B)
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier
                .size(145.dp)
                .offset(x = 230.dp, y = (-45).dp)
                .border(
                    2.dp,
                    Color.White.copy(alpha = 0.16f),
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(95.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 20.dp, y = 25.dp)
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
                    text = "TASK DETAILS",
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )

                Text(
                    text = "Kenali tugasmu.\nSelesaikan targetmu.",
                    color = Color.White,
                    fontSize = 24.sp,
                    lineHeight = 29.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Text(
            text = "▤",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp),
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DetailBadge(
    label: String,
    caption: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DetailCard)
            .border(
                1.dp,
                Color(0xFF333333),
                RoundedCornerShape(16.dp)
            )
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(
            text = caption,
            color = DetailGray,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )

            Spacer(modifier = Modifier.width(7.dp))

            Text(
                text = label,
                color = color,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
private fun DetailInfoItem(
    symbol: String,
    label: String,
    value: String,
    valueColor: Color = DetailWhite
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(DetailOrange.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = symbol,
                color = DetailOrange,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = label,
                color = DetailGray,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.3.sp
            )

            Text(
                text = value,
                color = valueColor,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun DetailDivider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xFF353535))
    )
}
