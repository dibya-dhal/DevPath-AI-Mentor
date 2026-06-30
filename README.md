# DevPath - Your AI-Powered Career Mentor 🚀

**DevPath** is a modern Android application designed to bridge the gap between learning and employment for freshers. It provides structured roadmaps, real-time AI mentoring, and mock interview feedback.

## 📱 Features
- **AI Career Mentor:** A real-time chat interface powered by Google Gemini for technical and career guidance.
- **Interview Prep:** Dynamic mock interviews where the AI grades your answers and suggests improvements.
- **Visual Roadmap:** A custom-built vertical timeline showing the path to becoming a developer.
- **Dashboard & Progress:** Track your daily tasks, learning streaks, and overall career readiness.

## 🛠 Tech Stack
- **Language:** Kotlin
- **UI:** Jetpack Compose & Material 3
- **Networking:** Retrofit & OkHttp (Optimized with custom timeouts for LLM latency)
- **AI Integration:** Google Gemini API (Direct REST implementation)
- **Asynchronous Logic:** Kotlin Coroutines
- **Layouts:** Lazy Lists, Grids, and Custom Drawing logic

## 🧠 Technical Highlights
- **Solved Networking Challenges:** Pivoted from standard SDKs to a manual Retrofit implementation to solve 404 and timeout issues during complex AI generations.
- **State-Driven UI:** Leveraged Compose state management to create a reactive experience that handles loading states and dynamic question generation.