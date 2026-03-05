# Getting Started with IntelliJ IDEA

This guide walks you through downloading IntelliJ IDEA, opening it, and cloning a GitHub repository.

---

## 1. Download IntelliJ IDEA

1. Visit [jetbrains.com/idea/download](https://www.jetbrains.com/idea/download/)
2. Choose your edition:
   - **Community Edition** — Free, open-source
   - **Ultimate Edition** — Paid, free trial available
3. Download the installer for your OS (Windows, macOS, or Linux)
4. Run the installer and follow the setup wizard

---

## 2. Open IntelliJ IDEA

1. Launch IntelliJ IDEA from your applications menu or desktop shortcut
2. On first launch:
   - Import settings (optional) or select **Do not import settings**
   - Accept the license agreement
   - Choose your UI theme (Darcula or Light)
   - Select any plugins you want to install (or skip)

---

## 3. Clone a GitHub Repository

### Method A: From the Welcome Screen

1. On the Welcome screen, click **Get from VCS** (or **Clone Repository**)
2. Paste the GitHub repository URL (e.g., `https://github.com/username/repo-name.git`)
3. Choose a local directory to save the project
4. Click **Clone**

### Method B: From an Open Project

1. Go to **File** → **New** → **Project from Version Control...**
2. Select **Git** as the version control system
3. Paste the GitHub repository URL
4. Choose the directory and click **Clone**

---

## 4. Build & Run

Once cloned:
- IntelliJ will automatically detect the project type and prompt you to import it
- Click **Trust Project** if prompted
- Wait for indexing and dependency resolution to complete
- Use the **Run** button (▶) or press `Shift + F10` to start the application

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Git not found | Install Git from [git-scm.com](https://git-scm.com/) and restart IntelliJ |
| Clone fails | Check the URL and your internet connection |
| Import errors | Ensure required SDKs (JDK, Python, etc.) are installed |

---

Happy coding! 🚀