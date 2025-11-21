# Part3
name: Java CI - Tests

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '17'
      - name: Run tests (Maven)
        run: |
          if [ -f pom.xml ]; then
            mvn -B test
          else
            echo "No pom.xml found - ensure your project uses Maven or update this workflow."
            exit 0
          fi
