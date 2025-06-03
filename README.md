# iiFTP Server 📶

A lightweight, Android-native FTP server using [Apache FTPServer](https://mina.apache.org/ftpserver-project/), designed for easy file transfers within local networks. iiFTP Server supports both Active and Passive modes and is suitable for background data transfer tasks.

---

## 📊 Key Features

* Runs on Android (API 21+)
* Apache FTPServer-based backend
* Both Active and Passive FTP modes
* Seamless file sharing over local Wi-Fi
* Supports background operation

---

## 🛠️ Tech Stack

| Tool/Library     | Purpose                          |
| ---------------- | -------------------------------- |
| Android SDK (31) | Native app framework             |
| Java 8           | Application logic                |
| Apache FTPServer | Core FTP protocol implementation |
| Mina Core        | Network I/O support              |
| SLF4J            | Logging interface                |

---

## ⚙️ Setup Instructions

1. **Clone the repository:**

   ```bash
   git clone https://github.com/me-sharif-hasan/iiFTP-Server.git
   ```

2. **Open in Android Studio** (Arctic Fox or later).

3. **Gradle sync** should pick up all dependencies. Ensure you have:

   * Android SDK 31
   * Gradle 7.0.2
   * Java 8

4. **Build & Run** the app on a device or emulator.

---

## 🛡️ Dependencies

Included as `.jar` files in `src/main/java/com/iishanto/libs/`:

* `ftpserver-core-1.1.1.jar`
* `ftplet-api-1.1.1.jar`
* `mina-core-2.1.2.jar`
* `slf4j-api-1.7.25.jar`
* `slf4j-simple-1.7.25.jar`
* `hamcrest-core-1.3.jar`

Test libraries via Gradle:

* JUnit 4
* AndroidX JUnit & Espresso

---

## 🚀 Usage

Once installed:

* Open the app to start the FTP server
* Connect via FTP client using local IP

  * Example: `ftp://192.168.1.10:21`
* Choose between Active or Passive mode in your FTP client

Ensure both devices are on the same network.

---

## 📚 About

* **Author**: Sharif Hasan
* **Blog**: [iishanto.com](https://iishanto.com)
* **Portfolio**: [iishanto.com/portfolio](https://iishanto.com/portfolio)

---

## 📁 License

MIT License.

---

Contributions and suggestions are welcome. Feel free to fork and improve!
