# FastMart - E-Commerce Android Application

---

## 📌 Project Overview
FastMart is a robust e-commerce mobile application designed with a focus on modern UI/UX principles and efficient data management. The application guides users through an onboarding process, a secure login/sign-up phase, and into a feature-rich shopping environment.

---

## 🚀 Key Technical Implementations

### 1. Home & Product Logic
* **20-Product Pattern:** Implements a logical repeating pattern (every 4 items) for 20 hard-coded products to ensure data consistency across Home, Favorites, and Search.
* **Dual RecyclerViews:** Features a horizontal "Deals of the Day" list and a 2-column "Recommended" grid.

### 2. Search System & Algorithm
* **SearchHashKey:** Includes a mandatory `SearchHashKey` variable for unique data identification.
* **Search History:** Queries are saved into `SharedPreferences` and displayed in a dedicated list with "Clear All" functionality.
* **Product Search Algorithm:** A custom algorithm checks user queries against the hard-coded product database, triggering a "Product Found" `AlertDialog` upon a match.
* **Keyboard Management:** Integrated `InputMethodManager` logic to hide the soft keyboard via the back-arrow button.

### 3. Favourites & Real-time Sync
* **Persistent Wishlist:** Favorited items are stored using `StringSet` in `SharedPreferences`.
* **Lifecycle Sync:** Uses the `onResume()` fragment hook to ensure the Home screen hearts update instantly if an item is removed from the Favorites page.

### 4. Shopping Cart & SMS Checkout
* **In-Memory Cart:** Utilizes a static `CartManager` for high-speed performance without redundant local storage.
* **Dynamic Pricing:** Total cost calculation updates in real-time as users modify quantities via the "+" and "-" buttons.
* **Automated SMS:** Compiles a full order summary string and dispatches it via `SmsManager`.
* **Post-Checkout Cleanup:** Automatically clears the cart and resets the UI total to $0.00 after a successful order.

### 5. Account Management
* **Non-Editable Profile:** Displays user details (Cody Fisher) in a read-only format as per project specifications.
* **Secure Strings:** The password `12345567` is strictly managed within the `strings.xml` resource file.

---

## 🛠 Tech Stack
* **Language:** Java
* **UI Layout:** XML (Material Design)
* **Storage:** SharedPreferences
* **Components:** Fragments, RecyclerView, Adapters, BottomNavigationView, SmsManager

---

## 📂 Installation
1. Clone or download the project.
2. Open in Android Studio.
3. Ensure `SEND_SMS` permission is enabled in the device/emulator settings.
4. Run on an emulator or physical device (Minimum SDK 24).

---

**FastMart** - *Academic Project 2026*
