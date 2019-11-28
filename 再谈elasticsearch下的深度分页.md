### 再谈elasticsearch下的From/Size和Scroll和Search After

Elasticsearch 在业务系统中使用也越来越广，一些开发规范也需要慢慢重视起来。
我们知道在关系型数据库中，我们被告知要注意甚至被明确禁止使用深度分页，在es中也应该尽量避免使用深度分页。

[es提供的分页查询](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-request-from-size.html)是通过`from`和`size`参数来完成，`from`默认是0，`size`默认为10，比如：

> {
>     "from" : 100000, "size" : 50,
>     "query" : {
>         "term" : { "user" : "alex" }
>     }
> }

<font color=#FF0000>注： from+size不能大于 `index.max_result_window` 的默认设置10000 </font>

**回归mysql**

当我们使用深度分页，此时 <font color=#00ffff> select * from base_product_shop_sap limit 100000,50</font> 时就会出现慢查询，它相当于先遍历了前100000个，然后取了第100000到100050个，舍弃了前100000个。
通常我们的优化方式是依赖覆盖索引（ 只遍历索引本身--  查询的列均是索引字段  ） ，而表现形式也不外乎以下几种：

1. 子查询，比如 ：<font color=#00ffff> SELECT * FROM base_product_shop_sap WHERE ID > =(select id from product limit 100000, 1) limit 50</font> 
2. join，比如：<font color=#00ffff> SELECT * FROM base_product_shop_sap a JOIN (select id from base_product_shop_sap limit 100000, 50) b ON a.ID = b.id</font> 

当然，这种方法的好处性能较好，可以实现快速查询，但局限性也很明显：依赖于主键的自增长特性，不适合复杂查询条件的分页逻辑。

**es分页查询原理**

在es中， 搜索一般包括query 和 fetch 阶段 两个阶段，关于es默认的搜索类型为 `QUERY_THEN_FETCH` 

> [之前也有分享过](https://blog.csdn.net/alex_xfboy/article/details/101030963 )
>
>  query  过程：首先 *Client 发送一次搜索请求，node1 接收到请求，然后，node1 创建一个大小为 from + size 的优先级队列用来存结果，我们管 node1 叫 coordinating node* ；然后 *coordinating node将请求广播到涉及到的 shards，每个 shard 在内部执行搜索请求，然后，将结果存到内部的大小同样为 from + size 的优先级队列里，可以把优先级队列理解为一个包含 top N 结果的列表*； *每个 shard 把暂存在自身优先级队列里的数据返回给 coordinating node，coordinating node 拿到各个 shards 返回的结果后对结果进行一次合并，产生一个全局的优先级队列，存到自身的优先级队列里。* 
>
>  fetch 过程：首先 *coordinating node 发送 GET 请求到相关shards*；然后 *shard 根据 doc 的 _id 取到数据详情，然后返回给 coordinating node* 。其中 coordinating node 优先级队列里有 from + size 个 _docId 

那上面的分页在es中执行， CPU、内存、IO和网络带宽消耗非常明显， 在 query 阶段即使是每条数据只返回`_docId`和 `_score` ，这数据量也很大了 ，而且这个数据量是很多 shards 中获取的。

既然 深度分页的请求并不合理 （ 很少人为的看很后面的请求 ），因此很多公司坚持二八原则（20%的才需要深度分页） 直接限制分页，不允许深度分页。

但是， 深度分页确实存在 ，在很多情况下无法回避，因此es官方也给出了两种解决方案：`scroll` 和  `search_after`。 

<font color=#FF0000>注：也有人提到过 search_type=scan，其实在 2.1.0 系列已经被官方废弃，可[参阅](https://www.elastic.co/guide/en/elasticsearch/reference/5.4/breaking_50_search_changes.html#_literal_search_type_scan_literal_removed)</font>

**scroll** 

`scroll` 其实不难理解，有点类似关系型数据库的游标，so， `scroll` 并不适合用来做实时搜索，而更适用于后台批处理任务（接受明显的延迟）。

 `scroll` 会一次性给你生成**所有数据的一个快照**，然后每次滑动向后翻页就是通过**游标** `scroll_id` 移动，获取下一页下一页这样子，性能会比上面说的那种分页性能要高很多很多，基本上都是毫秒级的。

用法这里就不展开讲，具体可参阅[官方文档](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-request-scroll.html) 

缺点也和明显：

-  不能随机地跳跃分页 
- 时效性，初始化时必须指定 `scroll` 参数 用于指定保存搜索结果的时间，会存在超时而失败 。通常可以通过，每次请求都要传参数 `scroll`来刷新搜索结果的缓存时间
- 要关注内存空间的消耗，毕竟空间有限，可通过 [nodes stats API](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/cluster-nodes-stats.html)进行监控

<font color=#FF0000>注： scroll 分为初始化和遍历两步，初始化时将搜索结果缓存起来，可以理解为快照，在遍历时直接从这个快照里取数据，但是一旦初始化后再对索引插入、删除、更新数据都不会影响遍历结果 </font>

**searchAfter**

大概从 es 5.0版本开始，es提供了新的参数 `search_after` 来解决分页的性能及时效性问题，search_after 提供了一个活的游标来拉取从上次返回的最后一个请求开始拉取下一页的数据。`search_after`有点mysql的依赖主键id的味道，它是无状态的， 可以并行的拉取大量数据， 能用于用户的实时搜索。 

用法上[官方文档](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-request-search-after.html)已经足够的详细，这里再逻辑一遍：

- 不能随机地跳跃分页 
- 依赖排序， `sort`参数里必须至少使用一个唯一的字段来进行排序，推荐的做法是使用 `_id`字段 
- 首次查询，` search_after `参数可以为空字符串不能为null，下次的` search_after `参数是上次查询结果返回的SearchAfterResult.searchAfter

这里有个小技巧，如果搜索结果有几万条，可以通过` search_after `来分页完成多次查询的功能，据说排序后的查询比默认查询的方式速度更快。

**form&size / scroll / search_after 性能比较**

曾有es专家对该话题进行了性能比较，无非是为了证明`search_after` 更值得推荐使用。

 【1 - 10】【49000 - 49010】【 99000 - 99010】范围各10条数据（前提10w条） 

|              | 1～10 | 49000～49010 | 99000～99010 |
| ------------ | ----- | ------------ | ------------ |
| form/size    | 8ms   | 30ms         | 117ms        |
| scroll       | 7ms   | 66ms         | 36ms         |
| search_after | 5ms   | 8ms          | 7ms          |

尽管性能是非功能性需求，但它带来的挑战值得每个人去探索。

