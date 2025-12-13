package com.kalcreations.minicart.ui.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.kalcreations.minicart.R
import com.kalcreations.minicart.data.model.CartItem
import com.kalcreations.minicart.ui.components.QuantitySelector

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {

            // Product image
            Box(
                modifier = Modifier
                    .size(116.dp) // compact cart thumbnail
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = cartItem.product.image ?: R.drawable.ic_launcher_background
                    ),
                    contentDescription = cartItem.product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }


            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                // Product name
                Text(
                    text = cartItem.product.name,
                    style = MaterialTheme.typography.titleMedium
                )

                // Price row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val effectivePrice =
                        cartItem.product.preDiscountPrice ?: cartItem.product.originalPrice

                    Text(
                        text = "₹$effectivePrice",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    if (cartItem.product.isPreDiscounted) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "₹${cartItem.product.originalPrice}",
                            style = MaterialTheme.typography.bodySmall,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }

                // Tax chip
                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            text = "${cartItem.product.taxGroupPercent}% Tax",
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    modifier = Modifier.height(26.dp)
                )

                // Quantity controls (smart delete)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    QuantitySelector(
                        quantity = cartItem.quantity,
                        onIncrease = onIncrease,
                        onDecrease = onDecrease,
                        onRemove = onRemove
                    )

                    TextButton(onClick = onRemove) {
                        Text("Remove")
                    }
                }
            }
        }
    }
}

/*
@Composable
fun CartItemCard(
    cartItem: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(
                text = cartItem.product.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "₹${cartItem.product.preDiscountPrice ?: cartItem.product.originalPrice}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                QuantitySelector(
                    quantity = cartItem.quantity,
                    onIncrease = onIncrease,
                    onDecrease = onDecrease,
                    onRemove = onRemove
                )

                TextButton(onClick = onRemove) {
                    Text("Remove")
                }
            }
        }
    }
}

 */
