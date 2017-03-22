# Tiger
A compiler of simplified C in full-featured Java to MIPS.

## Construction Notes
这个project是我目前为止写的最复杂的一个了，前前后后写了数月，中间经历曲曲折折而且没人交流，作为一个对非科班小白，只能说个中滋味只有自己知晓了。本打算最后用英文写个report来做个像样子的总结，转念一想，这其实也就是给自己看的，索性记录一下自己一路学过来的经历，权当是对自己的一个交代了。如果能够对别人有帮助，也算是功德一件了。囿于英文水平，就用中文讲些大白话了，每天写一点，温故而知新罢。  
### Phase 1. Sytactic Analysis
&emsp; 首先我们对拿到手的c语言程序做词法分析，也就是把程序中的关键字、标识符、常量、符号and so on全都按顺序提取出来，如此才能让computer明白这个由ascii码组成的文件意味着什么。  
&emsp; 从原理上来说，词法分析就是用一个DFA来识别字节流，由于可用正则表达式(RE)来描述这些词素的组成规则，回顾一下从《形式语言与自动机》所学到的知识

### Phase 2. Semantic Analysis
