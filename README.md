# Journal Buddy

An application specially designed for research scholars to examine and organise collections of research papers and journals using **tf–idf** based document ranking. This uses **Callable** and **Future** interfaces to construct **inverted index** which is used along with parallelized **Levenshtein** distance algorithm to enable deep text search through a **Map-Reduce model** based on parallel streams.

**Master File**: _/JournalBuddy/src/com/journalbuddy/SwingUI/JournalBuddyFront.java_

This performs text analysis using fork-join pool to generate term frequency, document frequency, and inverse document frequency. This also includes a levenshtein distance-based similarity-based word search.

CompletionService, which is is an interface from java.util.concurrent package, has been used here to simplify the separation between production and consumption of realised tasks. It means that we are separately submiting new tasks and analysing the tasks which already produced one result. This is used in the process of generating Inverted Index for documents 

The fork/join framework, which is an implementation of the ExecutorService, has been used here to implement deep text search. As with any ExecutorService implementation, the fork/join framework distributes tasks to worker threads in a thread pool. The fork/join framework is distinct because it uses a work-stealing algorithm. Worker threads that run out of things to do can steal tasks from other threads that are still busy. The fork() method is used to start the asynchronous execution of a task. The join() method is used to await the result of the computation.

## 🛠 Skills
Java, Swing

