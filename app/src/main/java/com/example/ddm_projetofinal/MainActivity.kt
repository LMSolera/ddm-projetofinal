package com.example.ddm_projetofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ddm_projetofinal.model.user1
import com.example.ddm_projetofinal.ui.feature.expenses.ExpensesScreen
import com.example.ddm_projetofinal.ui.feature.trips.TripsScreen
import com.example.ddm_projetofinal.ui.feature.trips.TripsScreenPreview
import com.example.ddm_projetofinal.ui.theme.DDMprojetofinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DDMprojetofinalTheme {
                ExpensesScreen(user1)
            }
        }
    }
}
