# NFU-Page-Replacement-Algorithm
I implemented the NFU (Not Frequently Used) page replacement algorithm for my operating systems course.
The OS (JNACHOS) was based in Java and was fully functioning with its own simulated memory, disk, etc.

When memory is full and a page outside of memory is referenced, the exception handler in JNACHOS would call my algorithm to evict and replace a page in memory based on NFU's policies.

NFU Policy:
- NFU approximates LRU in that it does not increment a counter on each reference of a particular page but instead it only increments the R bit if a page was referenced at all within the clock cycle. 
- If there are multiple references to a page within the same clock cycle, the count stays at 1.
- Upon a clock interrupt, the R bits are added to an array based on their corresponding indices and then the R bits are set back to 0.
- The array is used to keep track of which pages are roughly used the least and then the algorithm evicts the page with the lowest corresponding count. 
