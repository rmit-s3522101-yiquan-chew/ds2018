# Database System 2018
### Github [link](https://github.com/rmit-s3522101-yiquan-chew/ds2018.git)
### Report for Assignment 2 [link](https://docs.google.com/document/d/1X3IlGka3rjPziSM0mx-hXDUwugFMORvbvKsNXM6qboo/edit?usp=sharing)

Disclaimer: This project is meant for RMIT COSC2406 Database System Assignment 1 2018. Any meant for publish, distribute or business used without owner permission is not allowed.

## 1.  Basic command
`make main` : Compiling all .java files

`git log > git.log` : Exporting git log to "git.log" files

## Assignment 1
### 1.  Making Heap file
`java dbload -p pagesize datafile`

Note: A new file "heap.(pagesize)" will be created

Eg:
- `java dbload -p 4096 datafile.csv`
- heap.4096 will be created


### 2.  Searching term
`java dbquery text pagesize`

Eg:
- `java dbquery hello 4096`
- `java dbquery "hello world" 4096`


## Assignment 2
### 1.  Making Index file
`java hashload pagesize`

An index.(pagesize) file based on heap.(pagesize) will be created

### 2.  Querying Index
`java hashquery "query text" pagesize`

Program will shift through index.(pagesize) to find the location of the query text
