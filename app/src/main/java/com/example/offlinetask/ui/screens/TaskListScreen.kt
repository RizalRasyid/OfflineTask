
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.offlinetask.data.local.TaskEntity
import com.example.offlinetask.ui.viewmodel.TaskViewModel

private val BackgroundDark = Color(0xFF101010)
private val CardDark = Color(0xFF1D1D1D)
private val CardLight = Color(0xFF292929)
private val AccentOrange = Color(0xFFFF7629)
private val DeepOrange = Color(0xFFE84D0E)
private val PrimaryText = Color(0xFFF7F7F7)
private val SecondaryText = Color(0xFF999999)
private val GreenStatus = Color(0xFF49C98B)
private val RedPriority = Color(0xFFFF5555)
private val YellowPriority = Color(0xFFFFC247)

@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onAddClick: () -> Unit,
    onTaskClick: (Int) -> Unit
) {
    val tasks by viewModel.allTasks.collectAsState()

    val completedTasks = tasks.count {
        it.status == "Selesai"
    }

    val pendingTasks = tasks.size - completedTasks

    Scaffold(
        containerColor = BackgroundDark,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = AccentOrange,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Text(
                    text = "+",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark)
                .padding(paddingValues),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 18.dp,
                bottom = 100.dp
            ),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {

            item {
                DashboardHeader(
                    totalTasks = tasks.size,
                    completedTasks = completedTasks
                )
            }

            item {
                Column {
                    SectionHeading(
                        eyebrow = "OVERVIEW",
                        title = "Ringkasan Aktivitas",
                        subtitle = "Pantau progres pekerjaanmu."
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SummaryCard(
                            title = "Total",
                            value = tasks.size.toString(),
                            symbol = "▦",
                            modifier = Modifier.weight(1f),
                            accent = AccentOrange
                        )

                        SummaryCard(
                            title = "Selesai",
                            value = completedTasks.toString(),
                            symbol = "✓",
                            modifier = Modifier.weight(1f),
                            accent = GreenStatus
                        )

                        SummaryCard(
                            title = "Pending",
                            value = pendingTasks.toString(),
                            symbol = "◷",
                            modifier = Modifier.weight(1f),
                            accent = YellowPriority
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "YOUR WORKSPACE",
                            color = AccentOrange,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "Daftar Tugas",
                            color = PrimaryText,
                            fontSize = 23.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Surface(
                        color = CardLight,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "${tasks.size} TUGAS",
                            color = SecondaryText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                horizontal = 11.dp,
                                vertical = 8.dp
                            )
                        )
                    }
                }
            }

            if (tasks.isEmpty()) {
                item {
                    EmptyTaskState()
                }
            } else {
                items(
                    items = tasks,
                    key = { it.id }
                ) { task ->
                    TaskItem(
                        task = task,
                        onClick = {
                            onTaskClick(task.id)
                        }
                    )
                }
            }

            item {
                Text(
                    text = "OFFLINETASK  •  STAY PRODUCTIVE",
                    color = Color(0xFF555555),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun DashboardHeader(
    totalTasks: Int,
    completedTasks: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(218.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFF8A3D),
                        AccentOrange,
                        DeepOrange
                    )
                )
            )
    ) {
        // Ornamen geometris dekoratif
        Box(
            modifier = Modifier
                .size(190.dp)
                .offset(x = 215.dp, y = (-55).dp)
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.20f),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(145.dp)
                .offset(x = 245.dp, y = 5.dp)
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.18f),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .offset(x = 270.dp, y = 40.dp)
                .background(
                    Color.White.copy(alpha = 0.08f),
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 27.dp, end = 28.dp)
                .size(48.dp)
                .rotate(25f)
                .border(
                    1.dp,
                    Color.White.copy(alpha = 0.4f),
                    RoundedCornerShape(12.dp)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .background(Color.White, CircleShape)
                    )

                    Text(
                        text = "OFFLINETASK",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    )
                }

                Text(
                    text = "WORKSPACE / 01",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column {
                Text(
                    text = "MAKE EVERY",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )

                Text(
                    text = "TASK COUNT.",
                    color = Color.White,
                    fontSize = 31.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-1).sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Atur prioritas. Selesaikan target.",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$completedTasks / $totalTasks TASKS COMPLETED",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "✦",
                    color = Color.White,
                    fontSize = 23.sp
                )
            }
        }
    }
}

@Composable
private fun SectionHeading(
    eyebrow: String,
    title: String,
    subtitle: String
) {
    Column {
        Text(
            text = eyebrow,
            color = AccentOrange,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = title,
            color = PrimaryText,
            fontSize = 21.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subtitle,
            color = SecondaryText,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun SummaryCard(
    title: String,
    value: String,
    symbol: String,
    modifier: Modifier = Modifier,
    accent: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = SecondaryText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = symbol,
                    color = accent,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = value,
                color = PrimaryText,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(9.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .clip(CircleShape)
                    .background(accent.copy(alpha = 0.18f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .height(3.dp)
                        .clip(CircleShape)
                        .background(accent)
                )
            }
        }
    }
}

@Composable
private fun TaskItem(
    task: TaskEntity,
    onClick: () -> Unit
) {
    val priorityColor = when (task.priority) {
        "Tinggi" -> RedPriority
        "Sedang" -> YellowPriority
        else -> GreenStatus
    }

    val isCompleted = task.status == "Selesai"

    val statusColor = if (isCompleted) {
        GreenStatus
    } else {
        AccentOrange
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(19.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Garis aksen di sisi kiri kartu
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height(126.dp)
                    .background(
                        if (isCompleted) GreenStatus else AccentOrange
                    )
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = priorityColor.copy(alpha = 0.13f),
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        Text(
                            text = "◆ ${task.priority.uppercase()}",
                            color = priorityColor,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.7.sp,
                            modifier = Modifier.padding(
                                horizontal = 9.dp,
                                vertical = 6.dp
                            )
                        )
                    }

                    Text(
                        text = if (isCompleted) "✓ DONE" else "◷ ACTIVE",
                        color = statusColor,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.7.sp
                    )
                }

                Spacer(modifier = Modifier.height(13.dp))

                Text(
                    text = task.title,
                    color = PrimaryText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(9.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "◷",
                        color = AccentOrange,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.size(7.dp))

                    Text(
                        text = "Deadline: ${task.deadline}",
                        color = SecondaryText,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "VIEW  ↗",
                        color = AccentOrange,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyTaskState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    Color(0xFF302016),
                    RoundedCornerShape(30.dp)
                )
                .border(
                    1.dp,
                    AccentOrange.copy(alpha = 0.35f),
                    RoundedCornerShape(30.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✦",
                color = AccentOrange,
                fontSize = 43.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "NOTHING ON YOUR LIST",
            color = PrimaryText,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Mulai dengan menambahkan tugas pertamamu.",
            color = SecondaryText,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "STAY FOCUSED  •  KEEP MOVING",
            color = AccentOrange,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )
    }
}
