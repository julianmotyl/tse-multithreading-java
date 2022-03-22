Implement a Java multithreaded File Crawler.

The program starts a shell in main thread, for asking the user a word to search.

It will ask its database to answer the list of file using this word, by order of pertinence.

Periodically, in background threads, the program will recursively crawls a configurable root directory, analyzing its files and sub-directories.

It scans for .txt files and indexes them in the database.

It stores for each file the number of occurence of each world.

Crawling has also to be done in a multithreaded way to optimize performances.
