# kotlin 练习心得

#### 2019-10-12
不知不觉kotlin都发布1.3版本了，之前还写着`main(args: Array<String>)`的代码，现在只需要`main()`了，并且发布了Native，可以和c++甚至和Swift/Objective-c一起混用，感觉可以全栈了2333，有空了试一下前后端都用kotlin的项目

#### 2019-09-23
对于kotlin里的判空相关操作 `?` 和 `!!` 理解的不是很到位，比如 `if(something?.any == null)` 感觉something为null的话可能会造成一些问题？