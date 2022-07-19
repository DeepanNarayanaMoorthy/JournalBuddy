

# Journal Buddy

An application specially designed for research scholars to examine and organise collections of research papers and journals using **tf–idf** based document ranking. This uses **Callable** and **Future** interfaces to construct **inverted index** which is used along with parallelized **Levenshtein** distance algorithm to enable deep text search through a **Map-Reduce model** based on parallel streams.

This uses fork-join pool to do test analysis by generating term frequency document frequency and inverse document frequency to do text analysis. This also comes with threads based similarity based word search using levenshtein distance. 

CompletionService is an interface from java.util.concurrent package used to simplify the separation between production and consumption of realised tasks. It means that you can separately submit new tasks and analyse the tasks which already produced one result. 

The fork/join framework is an implementation of the ExecutorService As with any ExecutorService implementation, the fork/join framework distributes tasks to worker threads in a thread pool. The fork/join framework is distinct because it uses a work-stealing algorithm. Worker threads that run out of things to do can steal tasks from other threads that are still busy. The fork() method is used to start the asynchronous execution of a task. The join() method is used to await the result of the computation.

## 🛠 Skills
Java, Swing

