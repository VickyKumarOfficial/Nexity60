# Quick Maven Installer for Nexity60 Backend
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Maven Quick Installer" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if Maven already exists
if (Get-Command mvn -ErrorAction SilentlyContinue) {
    Write-Host "✓ Maven is already installed!" -ForegroundColor Green
    mvn -version
    Write-Host ""
    Write-Host "You can now run: cd pwa-backend; mvn spring-boot:run" -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 0
}

Write-Host "Maven not found. Installing..." -ForegroundColor Yellow
Write-Host ""

$mavenVersion = "3.9.5"
$mavenUrl = "https://dlcdn.apache.org/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip"
$installDir = "$env:USERPROFILE\.m2"
$mavenDir = "$installDir\apache-maven-$mavenVersion"
$zipFile = "$installDir\maven.zip"

# Create directory
Write-Host "Creating installation directory..." -ForegroundColor Yellow
New-Item -ItemType Directory -Force -Path $installDir | Out-Null

# Download Maven
Write-Host "Downloading Maven $mavenVersion..." -ForegroundColor Yellow
Write-Host "(This may take 1-2 minutes)" -ForegroundColor Gray
try {
    Invoke-WebRequest -Uri $mavenUrl -OutFile $zipFile -UseBasicParsing
    Write-Host "✓ Download complete!" -ForegroundColor Green
} catch {
    Write-Host "✗ Download failed!" -ForegroundColor Red
    Write-Host "Error: $_" -ForegroundColor Red
    Write-Host ""
    Write-Host "Please download manually from: https://maven.apache.org/download.cgi" -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

# Extract Maven
Write-Host "Extracting Maven..." -ForegroundColor Yellow
try {
    Expand-Archive -Path $zipFile -DestinationPath $installDir -Force
    Remove-Item $zipFile
    Write-Host "✓ Extraction complete!" -ForegroundColor Green
} catch {
    Write-Host "✗ Extraction failed!" -ForegroundColor Red
    Write-Host "Error: $_" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

# Add to PATH for current session
Write-Host ""
Write-Host "Adding Maven to PATH..." -ForegroundColor Yellow
$env:Path += ";$mavenDir\bin"

# Add to PATH permanently (requires admin or user PATH)
try {
    $userPath = [Environment]::GetEnvironmentVariable("Path", "User")
    if ($userPath -notlike "*$mavenDir\bin*") {
        [Environment]::SetEnvironmentVariable("Path", "$userPath;$mavenDir\bin", "User")
        Write-Host "✓ Maven added to PATH permanently!" -ForegroundColor Green
    }
} catch {
    Write-Host "⚠ Could not add to PATH permanently" -ForegroundColor Yellow
    Write-Host "  You may need to add manually: $mavenDir\bin" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "✓ Maven Installation Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Maven version:" -ForegroundColor White
& "$mavenDir\bin\mvn.cmd" -version

Write-Host ""
Write-Host "Next steps:" -ForegroundColor Cyan
Write-Host "1. Restart VS Code (to reload PATH)" -ForegroundColor Yellow
Write-Host "2. Run: cd pwa-backend" -ForegroundColor Yellow
Write-Host "3. Run: mvn spring-boot:run" -ForegroundColor Yellow
Write-Host ""

Read-Host "Press Enter to exit"
