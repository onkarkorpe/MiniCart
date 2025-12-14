package com.kalcreations.minicart.ui.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CouponSection(
    isCouponApplied: Boolean,
    isApplyEnabled: Boolean,
    disabledMessage: String?,
    successMessage: String?,
    onApplyCoupon: () -> Unit,
    onRemoveCoupon: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // Title
            Text(
                text = "Coupon",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            // Coupon label (always visible)
            Text(
                text = "SAVE20 • 20% off (Max ₹300)",
                style = MaterialTheme.typography.bodyMedium
            )

            // Disabled reason (only when needed)
            if (!isApplyEnabled && !isCouponApplied && !disabledMessage.isNullOrBlank()) {
                Text(
                    text = disabledMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if(isCouponApplied && !successMessage.isNullOrBlank()) {
                Text(
                    text = successMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            // Apply button
            Button(
                onClick = if(isCouponApplied) onRemoveCoupon else onApplyCoupon,
                enabled = isCouponApplied || isApplyEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if(isCouponApplied) "Remove Coupon" else "Apply Coupon",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
