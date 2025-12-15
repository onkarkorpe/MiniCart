# Mini Shopping Cart Android App

# Overview
This is a mini Android shopping cart application built as part of an assignment.  
The app demonstrates product listing, cart management, coupon eligibility rules, tax calculation, and a checkout success animation using modern Android development practices.

---

## Features
- Product list with:
  - Original prices
  - Pre-discounted prices
  - Mixed tax groups (5% and 18%)
- Add to cart with quantity management
- Cart badge showing item count
- Coupon application with rules:
  - Minimum cart value: ₹1000
  - Discount: 20%
  - Maximum discount cap: ₹300
  - Coupon not applicable on pre-discounted items
- Clear inline messages for coupon eligibility and status
- Accurate subtotal, tax, discount, and final amount calculation
- Checkout success screen with celebratory animation
- Empty cart state with navigation back to products

---

## Tech Stack
- Kotlin
- Jetpack Compose
- Material 3
- MVVM Architecture
- StateFlow

---

## Architecture & Design Decisions
- All business logic (cart calculations, coupon eligibility, tax computation) is handled in the ViewModel.
- UI observes state via StateFlow to ensure consistency across screens.
- Product data is stored in-memory as per assignment requirements.
- Monetary values are rounded at the ViewModel level to avoid floating-point inconsistencies.
- Clear separation of UI, state, and logic for maintainability.

---

## Notes
- The coupon logic supports mixed carts containing discounted and non-discounted items.
- The maximum discount cap is enforced automatically.
- Cart state is reset after successful checkout.
- The app uses a signed release APK for testing and demonstration.

---

## Demo
A short demo video and a signed release APK are provided separately as part of the assignment submission.
