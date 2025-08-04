// README.md content for project documentation
/*
# Nexity60 - Smart Summarized News Reader

A Java-based intelligent news application that fetches articles from various online sources, categorizes them, and generates quick 60-second readable summaries.

## Features

- **Smart Summarization**: Uses rule-based algorithms to extract key information
- **Category Management**: Customize news categories (Politics, Sports, Technology, etc.)
- **Offline Reading**: Save articles for later offline access
- **Intuitive UI**: Clean JavaFX interface with hamburger menu navigation
- **Multiple Sources**: Aggregates news from BBC, Reuters, and other sources
- **Reading Timer**: Estimates reading time for each summary

## Requirements

- Java 11 or higher
- JavaFX 17+ (included in dependencies)
- Internet connection for fetching news
- Maven 3.6+ (for building)

## Quick Start

### Using Maven:
```bash
# Clone the repository
git clone <repository-url>
cd Nexity60

# Compile and run
mvn clean compile
mvn javafx:run

# Or build executable JAR
mvn clean package
java -jar target/Nexity60-1.0.0.jar
```

### Using Gradle:
```bash
# Compile and run
./gradlew run

# Build executable JAR
./gradlew build
```

### Manual Compilation:
```bash
# Compile with module path (adjust paths as needed)
javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp lib/* src/main/java/**/*.java

# Run with module path
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp target/classes:lib/* ui.MainWindow
```

## Project Structure

```
Nexity60/
├── src/main/java/
│   ├── ui/
│   │   ├── MainWindow.java      # Main GUI application
│   │   └── NewsCard.java        # Visual component for each summary
│   ├── core/
│   │   ├── NewsFetcher.java     # Web scraping and article fetching
│   │   ├── CategoryManager.java # Manages user category preferences
│   │   └── Summarizer.java      # Text summarization algorithms
│   ├── model/
│   │   └── NewsArticle.java     # Data model for news items
│   ├── storage/
│   │   └── FileManager.java     # Handles reading/writing saved summaries
│   ├── config/
│   │   └── ApplicationConfig.java # Application configuration
│   └── main/
│       └── Main.java            # Alternative main class
├── src/main/resources/
│   └── styles.css               # UI styling
├── src/test/java/               # Unit tests
├── lib/                         # External JAR dependencies
├── assets/icons/                # UI icons and assets
├── pom.xml                      # Maven build configuration
└── README.md                    # Project documentation
```

## Configuration

### Categories
Edit categories through the Settings dialog in the application, or modify `categories.properties`.

### News Sources
Add new sources by extending the `NewsFetcher` class and implementing source-specific scraping logic.

### Summarization
Adjust summarization parameters in `ApplicationConfig.java`:
- `TARGET_READING_TIME_SECONDS`: Target reading time for summaries
- `MAX_SUMMARY_SENTENCES`: Maximum sentences in summary
- `AVERAGE_READING_SPEED_WPM`: Average reading speed for time estimation

## Dependencies

- **JavaFX 17.0.2**: GUI framework
- **Jsoup 1.15.3**: HTML parsing and web scraping
- **Gson 2.10.1**: JSON serialization for data persistence
- **JUnit 5.9.0**: Unit testing framework
- **SLF4J 1.7.36**: Logging framework

## Usage

1. **Launch Application**: Run the main class to open the GUI
2. **Select Category**: Choose from sidebar categories (Trending, Politics, Sports, etc.)
3. **Fetch News**: Click "Refresh News" to fetch latest articles
4. **Read Summaries**: Expand articles to read full summaries and key points
5. **Save Articles**: Click "Save for Later" to store articles offline
6. **Access Saved**: Use "Saved Articles" to view offline content

## Testing

Run unit tests with:
```bash
mvn test
# or
./gradlew test
```

## Troubleshooting

### JavaFX Issues
- Ensure JavaFX is in module path for Java 11+
- For Java 8, JavaFX is included by default

### Network Issues
- Check internet connection
- Some news sites may block automated requests
- Consider adding delay between requests to avoid rate limiting

### Performance
- Large articles may take time to summarize
- Adjust `MAX_ARTICLES_PER_CATEGORY` to limit memory usage

## Future Enhancements

- [ ] Machine learning-based summarization
- [ ] Additional news sources
- [ ] Dark mode theme
- [ ] Push notifications for breaking news
- [ ] Export summaries to PDF/email
- [ ] Advanced filtering and search
- [ ] Multi-language support

## Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/new-feature`)
3. Commit changes (`git commit -am 'Add new feature'`)
4. Push to branch (`git push origin feature/new-feature`)
5. Create Pull Request

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Contact

For questions or support, please open an issue in the repository.
*/