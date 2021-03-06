# 线程学习示例

## uu-thread-basic
> 该模块主要演示基本的线程概念

## uu-thread-core
> 该模块为线程核心模块

#### core包
> 包括线程的创建、启动、终止、状态、常用方法、属性、异常处理、线程安全等

- createthreads: 如何创建线程案例
- startthread: 启动线程的方式案例
- stopthread: 停止线程的方式，以及错误停止案例
- statusthread: 线程的状态案例
- objectandthreadmethod: object类和thread类的常用方法
- threadattribute: thread类的常用属性
- uncaughtexception: thread异常处理机制，以及线程池异常处理机制
- threadsafe: 线程安全问题

#### jmm包
> 包括JMM内存模型相关案例

#### singleton包
> 包括单例的八种写法的演变，以及最佳生产实践案例

#### deadlock包
> 包括常见的转账、哲学家就餐等常见死锁问题，以及相关的修复方案

#### synchronized_包
> synchronized关键字相关案例

## uu-thread-juc
> 该模块为常用并发工具包案例

- threadpool: 线程池相关案例
- threadlocal: threadlocal相关案例代码
- lock: Lock锁相关案例代码
- atomic: 原子类相关案例代码
- cas: CAS相关案例代码
- collections: 容器相关案例代码
- flowcontroller: 流程控制相关案例代码
- aqs: AQS相关案例
- futureandcallable: Future和Callable相关案例