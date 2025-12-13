package com.kalcreations.minicart.ui.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CouponSection(
    isApplyEnabled: Boolean,
    disabledMessage: String?,
    onApplyCoupon: () -> Unit
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
            if (!isApplyEnabled && !disabledMessage.isNullOrBlank()) {
                Text(
                    text = disabledMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // Apply button
            Button(
                onClick = onApplyCoupon,
                enabled = isApplyEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Apply Coupon",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CouponSectionEnabledPreview() {
    CouponSection(
        isApplyEnabled = true,
        disabledMessage = null,
        onApplyCoupon = {}
    )
}

@Preview(showBackground = true)
@Composable
fun CouponSectionDisabledPreview() {
    CouponSection(
        isApplyEnabled = false,
        disabledMessage = "Add items worth ₹1000 to apply this coupon",
        onApplyCoupon = {}
    )
}
