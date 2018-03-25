# Database System 2018
Disclaimer: This project is meant for RMIT COSC2406 Database System Assignment 1 2018. Any meant for publish, distribute or business used without owner permission is not allowed.

## 1.  To compile
`make main`

## 2.  Making Heap file
`java dbload -p pagesize datafile`

eg:
- `java dbload -p 4096 datafile.csv`

## 3.  Searching term
`java dbquery text pagesize`

eg:
- `java dbquery hello 4096`
- `java dbquery "hello world" 4096`
