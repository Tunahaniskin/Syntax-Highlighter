h1 Java Syntax Highlighter with GUI

This project is a real-time syntax highlighter and parser for a simplified Java-like language. It is developed as part of a programming languages course project and showcases lexical and syntactic analysis using Java's Swing for GUI.

✨ Features

Real-time syntax highlighting with at least 5 token types

Lexical Analysis using Regular Expressions

Top-Down Parsing with recursive descent parser

Support for control structures: if, else, while, switch, for

Variable declarations: int, float, char, string, boolean

GUI-based code editor using JTextPane

⚙ Technologies Used

Java SE

Swing (for GUI)

Regex (for tokenization)

Recursive descent (for parsing)

✍ Example Code Input

int x = 5;
if (x > 3) {
    float y = 4.2;
} else {
    string msg = "too small";
}
switch(x) {
    case 1:
        break;
    case 5:
        return x;
}
for(int i = 0; i < 10; i++) {
    // loop body
}

⚡ How It Works

Lexical Analyzer (Tokenization)

Uses a Pattern matcher with regex to break the input into tokens.

Supports tokens like IF, FOR, INT, FLOAT, STRINGMETIN, CHARMETIN, NUMBER, FLOATNUMBER, etc.

Parser

Top-down recursive parser (KodAnalizci) processes token stream

Supports blocks for if, else, for, while, switch, etc.

Validates matching braces, parentheses, and structure.

Real-time Highlighting

Highlights keywords, types, operators, literals, and comments dynamically as you type.

📚 How to Run

Clone the repository:

git clone https://github.com/yourusername/JavaSyntaxHighlighter
cd JavaSyntaxHighlighter

Compile and run the project:

javac programlama_dilleri_proje/proje_main.java
java programlama_dilleri_proje.proje_main

Java 8 or above is required.

🌐 Project Deliverables

Deliverable

Status

Lexical Analyzer

✅ Completed

Top-down Parser

✅ Completed

GUI

✅ Completed

Syntax Highlighting

✅ Completed

Video Demo

⏳ In Progress

Public Article

⏳ In Progress

🏆 Evaluation Criteria

Criterion

Weight

Highlighting Accuracy

20%

GUI Implementation

20%

Real-time Responsiveness

10%

Documentation (This file + Report)

30%

Video Demo (Publicly Shared)

10%

Public Article

10%

🚀 Next Steps



🙏 Author

Your Name

University / Course Name

For any questions or suggestions, feel free to open an issue or contact me via GitHub.

© 2025 Java Syntax Highlighter Project

