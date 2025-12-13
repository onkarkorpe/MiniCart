package com.kalcreations.minicart.ui.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TotalsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            TotalsRow("Subtotal", "₹0.00")
            TotalsRow("Discount", "-₹0.00")
            TotalsRow("Tax", "₹0.00")

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            TotalsRow(
                label = "Final Amount",
                value = "₹0.00",
                bold = true
            )
        }
    }
}

@Composable
private fun TotalsRow(
    label: String,
    value: String,
    bold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = if (bold) MaterialTheme.typography.titleMedium
            else MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = if (bold) MaterialTheme.typography.titleMedium
            else MaterialTheme.typography.bodyMedium
        )
    }
}
