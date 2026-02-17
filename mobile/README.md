# StudySpace Mobile App (Android/Kotlin)

A simple Android application for user registration, login, and dashboard functionality.

## Features

- 📱 **Login Screen** - Sign in with username/email and password
- ✍️ **Registration Screen** - Create a new account
- 📊 **Dashboard Screen** - View user info and all registered users
- 🔐 **User Authentication** - Secure login with SharedPreferences
- 🌐 **REST API Integration** - Connects to Spring Boot backend

## Tech Stack

- **Kotlin** - Primary programming language
- **Android SDK** - Android development
- **Retrofit** - REST API client
- **Material Design 3** - UI components
- **Coroutines** - Asynchronous programming
- **View Binding** - Type-safe view access

## Setup Instructions

### Prerequisites

- Android Studio (Latest version)
- JDK 17 or higher
- Android SDK 24+ (Android 7.0+)
- Backend server running on port 8080

### Installation

1. **Open in Android Studio**
   ```
   File → Open → Select the 'mobile' folder
   ```

2. **Update Backend URL**
   
   Edit `RetrofitClient.kt` and update the BASE_URL:
   
   - **For Emulator**: Use `http://10.0.2.2:8080/`
   - **For Real Device**: Use `http://YOUR_COMPUTER_IP:8080/`
   
   To find your computer's IP:
   - Windows: Open CMD and run `ipconfig`
   - Look for "IPv4 Address" (e.g., 192.168.1.100)

3. **Sync Gradle Files**
   ```
   File → Sync Project with Gradle Files
   ```

4. **Run the App**
   - Connect your Android device or start an emulator
   - Click the green "Run" button in Android Studio
   - Or use: `Shift + F10`

## Backend Configuration

Make sure your backend is running and accessible:

1. Start XAMPP MySQL service
2. Start the backend:
   ```
   cd backend
   .\mvnw spring-boot:run
   ```
3. Backend should be running on `http://localhost:8080`

## Network Configuration

### For Android Emulator
- The emulator uses `10.0.2.2` to access the host machine's localhost
- No additional configuration needed

### For Real Android Device
1. Connect your phone and computer to the same Wi-Fi network
2. Find your computer's IP address:
   ```
   ipconfig   (Windows)
   ```
3. Update `BASE_URL` in `RetrofitClient.kt`:
   ```kotlin
   private const val BASE_URL = "http://YOUR_IP:8080/"
   ```

## App Structure

```
mobile/
└── app/
    └── src/
        └── main/
            ├── java/com/studyspace/mobile/
            │   ├── api/
            │   │   ├── Models.kt           # Data models
            │   │   ├── ApiService.kt       # API endpoints
            │   │   └── RetrofitClient.kt   # HTTP client config
            │   ├── LoginActivity.kt        # Login screen
            │   ├── RegisterActivity.kt     # Registration screen
            │   └── DashboardActivity.kt    # Dashboard screen
            ├── res/
            │   └── layout/
            │       ├── activity_login.xml
            │       ├── activity_register.xml
            │       └── activity_dashboard.xml
            └── AndroidManifest.xml
```

## API Endpoints Used

- `POST /api/users/register` - Register new user
- `POST /api/users/login` - Authenticate user
- `GET /api/users` - Get all users

## Troubleshooting

### Cannot connect to backend

**Problem**: "Failed to connect" or timeout errors

**Solutions**:
1. Check if backend is running: `netstat -ano | findstr :8080`
2. Verify the BASE_URL in `RetrofitClient.kt`
3. For real device: Ensure computer and phone are on same network
4. Check Windows Firewall settings (allow port 8080)

### Build errors

**Problem**: Gradle sync fails

**Solutions**:
1. Update Gradle: `File → Settings → Build Tools → Gradle`
2. Clean project: `Build → Clean Project`
3. Rebuild: `Build → Rebuild Project`

### App crashes on launch

**Problem**: App closes immediately

**Solutions**:
1. Check Logcat in Android Studio for error messages
2. Verify all dependencies in `build.gradle.kts`
3. Invalidate caches: `File → Invalidate Caches → Invalidate and Restart`

## Testing

1. **Register a new user**
   - Open app → Click "Don't have an account? Register"
   - Fill in all fields
   - Click "Register Now"

2. **Login**
   - Enter username/email and password
   - Click "Sign In"

3. **Dashboard**
   - View your profile info
   - See list of all registered users
   - Click "Refresh Users" to reload
   - Click "Logout" to sign out

## License

This is a lab project for educational purposes.
