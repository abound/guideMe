# **1 为什么Kafka是基于pull而不是基于push的？ 我同意 Kafka 提供了我所经历的高吞吐量，但我不知道如果基于推送，Kafka 吞吐量会如何下降? 关于基于推送如何降低性能的任何想法？**

**push-based缺点**：

 push-based 的系统中，当消费速率低于生产速率时，consumer 往往会不堪重负（本质上类似于拒绝服务攻击）。而 push-based 系统必须选择立即发送请求或者积累更多的数据，然后在不知道下游的 consumer 能否立即处理它的情况下发送这些数据。如果系统调整为低延迟状态，这就会导致一次只发送一条消息，以至于传输的数据不再被缓冲，这种方式是极度浪费的。

我们设计系统基于push还是pull，可扩展性是主要的驱动因素，Kafka 的主要优势之一是可以非常轻松地添加大量消费者，而不会影响性能和停机时间。
Kafka 可以以每秒 10 万+ 的速率处理来自生产者的事件。 由于 Kafka 消费者从主题中提取数据，因此不同的消费者可以以不同的速度消费消息。 
Kafka 还支持不同的消费模型。 您可以让一个使用者实时处理消息，另一个使用者以批处理模式处理消息。

另一个原因可能是 Kafka 不仅是为像 Hadoop 这样的单一消费者而设计的。 不同的消费者可能有不同的需求和能力。

基于拉动的系统有一些缺陷，例如由于定期轮询而浪费资源。 Kafka 支持“长轮询”等待模式，直到真实数据通过以缓解此缺点。

拉动更好地处理多样化的消费者（无需经纪人确定所有人的数据传输速率）；
消费者可以更有效地控制自己的个人消费率；
更简单、更优化的批处理实施。

另一个 pull-based 系统的优点在于：它可以大批量生产要发送给 consumer 的数据。而 push-based 系统必须选择立即发送请求或者积累更多的数据，然后在不知道下游的 consumer 能否立即处理它的情况下发送这些数据。如果系统调整为低延迟状态，这就会导致一次只发送一条消息，以至于传输的数据不再被缓冲，这种方式是极度浪费的。 而 pull-based 的设计修复了该问题，因为 consumer 总是将所有可用的（或者达到配置的最大长度）消息 pull 到 log 当前位置的后面，从而使得数据能够得到最佳的处理而不会引入不必要的延迟。

简单的 pull-based 系统的不足之处在于：如果 broker 中没有数据，consumer 可能会在一个紧密的循环中结束轮询，实际上 busy-waiting 直到数据到来。为了避免 busy-waiting，我们在 pull 请求中加入参数，使得 consumer 在一个“long pull”中阻塞等待，直到数据到来（还可以选择等待给定字节长度的数据来确保传输长度）。

topic 与partitions ，consumer GroupId  、consumer区别

每个与partitions的消息只能被同一consumer GroupId 的一个consumer进行消费，
每个consumer对应一个分区，如果consumer数量多于分区，那么多出来的数量的consumer将不工作
一个topic对应多个partition，partition分布在多broker上，多broker一起提供kafka服务。

kafka中，Topic是一个存储消息的逻辑概念，可认为是一个消息的集合。物理上，不同Topic的消息分开存储，每个Topic可划分多个partition，同一个Topic下的不同的partition包含不同消息。每个消息被添加至分区时，分配唯一offset，以此保证partition内消息的顺序性。
kafka中，以broker区分集群内服务器，同一个topic下，多个partition经hash到不同的broker。
Brokers 就是kafka服务器分区中，只要如果分区规则设置的合理，那么所有的消息将会被均匀的分布到不同的分区中，
这样就实现了负载均衡和水平扩展。另外，多个订阅者可以从一个或者多个分区中同时消费 
一个Topic对应很多Partition，在Partition当中选取一个Partition作为老大就是leader，其余Partition作为follower
producer和consumer 只是跟leader做交互
Partition(分区)： 每个Topic包含一个或者多个Partition，每个分区由一系列有序的、不可变的消息组成，是一个有序的队列。每个分区在物理上对应的一个文件夹，分区的命名规则为主题-分区编号，分区编号从0开始，表示第一个分区