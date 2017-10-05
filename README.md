# SimpleRe2J

SimpleRe2J 是一个从**设计模式**的角度出发而实现的正则引擎学习库，通过GOF设计模式能够更加**清楚**、**简单**地了解正则引擎基本的实现方式，同时也使其更加容易**扩展**。

## Features

* Character classes， 支持非集、并集和差集操作

* Boundary，支持行头、行尾匹配

* Quantifier，支持贪婪、非贪婪模式

* Capture，支持命名捕获及非捕获

## Doc

请参考java文档：

* [Pattern](http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html)

* [Matcher](http://docs.oracle.com/javase/8/docs/api/java/util/regex/Matcher.html)

## Refer

* [Regular Expression Matching Can Be Simple And Fast](https://swtch.com/~rsc/regexp/regexp1.html)

* [Regular Expression Matching: the Virtual Machine Approach](https://swtch.com/~rsc/regexp/regexp2.html)

## Thanks

* 感谢[re2j](https://github.com/google/re2j)提供的代码参考

* 感谢[轮子哥](http://www.cnblogs.com/geniusvczh/)神一样的博客（膜拜）
