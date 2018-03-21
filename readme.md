# 念 - 告别

念是一个一天不更就会被删号的记事应用。它不把那一个个的记本称为"记本"，而叫做"梦想"。要你记录着一天天的一天天的在梦想上留下的脚印，丝毫不能忘记。

在5年里，你能看到最刻苦的画手是如何一天天成长、设计师的作品是怎样一天天变化、生病的人是如何坚强以及那些不开心的生活如何一天天的更加不开心。

更有意思的是，你会看到 “就算一个人早餐也要好好吃”，这样坚持做好自己的事情来让世界变美好的念本。以及能够让小心翼翼的人们可以坦然问出疑问的 “所有人问所有人” （从气氛来说比知乎好太多了。。。

Sa 保护着他的念和念主们，没有一丝一毫的广告，虽然遗憾与逃不过经济和精力的问题。走到了尽头，就意味着那些有趣的世界，要消失了。

# 念 - 存档

念里的，是梦想，你能看到在这条路上你一步步走了这么远的体味和回忆。这样的记录如果随着念的下架而消失，会感觉那段时光像是被抹掉了一样。

我是一个程序员小哥，就算是在春招的关键时期，我还是加班熬夜，做了这样的一个小程序。我想要帮大家把那些，暗淡的或者璀璨的梦想，都存储成一个个漂亮的小册子，然后互相分享、纪念和收藏。

<!--more-->

# 上手备份

## 你需要什么

- 电脑 （什么系统都可以
- Java 1.8 的环境 （文科生不要怕，这很简单
- 在念里绑定了邮箱和密码

## 你会获得什么

- 一个保存了你所有数据的 json 文件
- 一个保存了你所有图片的文件夹（可选，按记本和进展有序存放）
- 每一个念本会生成 md 结尾的文档，本身已经排版且易于转换为其他格式，比如pdf

## 效果

- 数据文件

![image.png](https://upload-images.jianshu.io/upload_images/1846712-01d47f2a40bddf8e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- markdown 文档 (举个栗子 这是左边是文档内容，右边是 markdown 的编辑器打开以后的效果，相等于导出pdf后的效果

>进展的效果：

![image.png](https://upload-images.jianshu.io/upload_images/1846712-41b8ca477baed3bc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>多图的效果:

![image.png](https://upload-images.jianshu.io/upload_images/1846712-fce6bd3572f2034f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>评论的效果:
  
![image.png](https://upload-images.jianshu.io/upload_images/1846712-fbe6b91b8a82a1ac.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


- 最终的pdf

    - 会带有一个封面，加上一个简单优雅的目录，然后跟上你所有的记本

![image.png](https://upload-images.jianshu.io/upload_images/1846712-50f3611c2adbfd7d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![image.png](https://upload-images.jianshu.io/upload_images/1846712-f932d83a70de3781.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 操作流程

这里需要一点点的命令行操作，因为这是一个小工具。。。

从总的步骤上来说，分为这样几步啦：

1. 确保安装 Java （如果不明白下节有详细的教程
2. 下载工具压缩包
3. 解压以后放在一个文件夹里
4. 查看一下配置文件，做必要的修改  （一般只需要打开填上邮箱密码即可
5. 双击 `确认配置后点我启动.bat` 启动
6. 确认你要导出的记本
6. 等待完成
7. 查看文件
8. 给 Sa 的念 [github/念](https://github.com/sapherise/NianiOS) 的右上角点个 star 吧
9. 如果帮到你，再给我的 [github/念的备份工具](https://github.com/hh23485/record-nian-forever) 的右上角点个 star 吧
10. 如果需要制作pdf，将数据文件 **按照指定的格式** 发给我吧
10. 如果遇到问题，给我反馈吧 `hh23485@126.com`


最终你会得到一个文件夹`nian`，包括了所有的图片、json 数据和 Markdown 格式(适合阅读且容易转换为其他格式)的文档

因为很多人不是学计算机的啦，所以下面有详细的步骤，请确认你看清楚了每一个步骤，并在你的网络和 [nian.so](http://nian.so) 的网络都良好的情况下(凌晨比较好)进行操作。

# 操作细节

## 安装 Java

安装 Java 有很多的教程，我找了一些比较靠谱的放在这里。如果你已经有 Java 的运行环境，请确认他的版本是 `1.8.*`

### 下载 jre

jre 是 Java 程序运行必须依赖的环境，所以你不得不下载啦。

你可以到 [Java 官方](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) 下载jre。

![image.png](https://upload-images.jianshu.io/upload_images/1846712-e3e76e6989580965.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

安装教程可以查看 [教程](http://blog.csdn.net/zhangyong01245/article/details/75142870)，你可以忽略其中 `jdk` 和 `jre` 的区别，按我给的官方下载地址下载就好。

安装完成后，进入下一步 → 查看版本

### 查看 jre 版本

- 在命令提示符中输入 `java -version`，看是否会输出如下的反馈。

![image.png](https://upload-images.jianshu.io/upload_images/1846712-222c17b19d573e4d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如果可以的话，说明你的 java 是可以使用的，否则你可能需要参考一下，你需要保证 version 是 1.8 开头的。


## 下载工具包并放到同一个文件夹中

这是一个开源 Java 应用，你可以在我的 [github](https://github.com/hh23485/record-nian-forever/releases) 上下载它。

下载解压之后呢，你会看到三个文件：

- `config.json`: 用于存放相关的配置参数
- `record-nian-forever-1.0-jar-with-dependencies.jar`: 主要的工具
- `确认配置后点我启动.bat`：用于启动工具

放在同一个文件夹就如下所示了：

![image.png](https://upload-images.jianshu.io/upload_images/1846712-0f55e233b5937600.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如果你是 windows，就先进入配置填写好邮箱和密码并保存，然后双击 `点我启动` 来启动吧，你会看到一个黑框口，告诉你实时的进展。你可以最小化，但是你不能关上它，否则就会停止工作。


## 配置

配置非常重要，但默认的配置已经足够了。

默认的配置可以帮你做如下事情：

- 帮你下载所有的**文字记录**
- 帮你把每个**个人记本**按照**时间正序**组装成一个文档
- 帮你备份一次json数据库用于以后恢复
- 文档中包含图片，但图片是 念服务器 上的图片，但你可以导出为 pdf 格式就囊括了所有图片

你**必须要做的几件事情**:

- 你的念已经绑定的邮箱，可以使用邮箱登陆
- 在配置文件中添加你的邮箱
- 在配置文件中添加你的密码

默认的配置文件如下所示：

``` js
{
    {
    "EMAIL": "email@host.com",
    "PASSWORD": "password",
    "REVERSE": false,
    "TEST": false,
    "IMG_MAX_SIZE": "500",
    "DOWNLOAD_IMAGES": false,
    "USE_JSON_DATA": false,
    "WANT_COMMENTS": true,
    "WANT_MULTIDREAM": false,
    "WHITE_LIST": [],
    "BLACK_LIST": [
        "所有人问所有人",
        "念的相亲记本 看眼简介求你们了"
    ]
}
```

你不必弄清楚每个参数，只需要添加 **邮箱 EMAIL** 和 **密码 PASSWORD** 工具就可以开始工作了。

> 需要注意的是，尽量工具在凌晨使用，否则可能由于网络问题并不能获得完整的数据。

### 配置说明

稍微解释一下配置，这些配置是我在准备的时候想到的：


| 配置 | 默认 | 说明 |
| :-: | :-: | :-: |
| EMAIL | 默认为空 | 邮箱，必填项 |
| PASSWORD | 默认为空 | 密码，必填项 |
| REVERSE | false | 意思是逆向；如果为true, 生成文档的时候会将最后发出的进展放在前面 你可以写 true 或者 false，但不要是其他值 |
| IMG_MAX_SIZE | 500 | 这是生成的图片的大小，当然我会帮你下载原图，这里的尺寸是指放在文档中显示的大小，希望你不要超过500，否则看起来很难看呢 |
| DOWNLOAD_IMAGES | false | 是否下载你的念本中所有的图片 默认为 false，你也可以改为 true，因为念服务器最近的压力很大，所以尽量不要下载，除非你的念本中有很多的动图。 |
| USE_JSON_DATA | false | 这是一种数据格式，包括了你所有的念本信息。这些信息可以用于在念下线之后恢复你的数据。当你设置为 true 的时候，该工具会直接从json中获取数据生成文档，而不再去网上获取，所以会非常快速的完成。所以如果你已经完整的获取了一次数据，那么之后请设置为 true |
| TEST | false  | 测试用的选项，如果设置为 true，只会导出一个笔记本 |
| WANT_COMMENTS | true | 是否获取评论，true为获取，false为不获取 |
| WANT_MULTIDREAM | false | 是否获取你**关注的和加入**的多人本，false为不抓取**别人创建**的多人本，因为很多多人本数据量非常大！！ 如果设置为true则获取所有的多人本|
| WHITE_LIST | [] | 这是一个白名单设置，如果不填默认为获取所有的记本，如果填写了会只获取你填写的记本。 |
| BLACK_LIST | [] | 不想要获取的记本，用于排除碎碎念和超大记本，默认留下了两个，只是为了展示数组的格式写法，并不表示对该念本有意见！(注意：黑名单的优先级高于白名单） |

虽然表格里的内容有点乱，总的来说，**除了必填**的 **邮箱**和**密码**外，你可以根据你的场景，**修改特定项**（只修改对应的项）

#### 你想要所有的原图

``` js
    "DOWNLOAD_IMAGES": true,
```

重新运行，会获取所有的图片，并一个个下载到`nian`文件夹中。

***


#### 你只想要名为`日记本`和`日三省`的念本

这里使用的是前缀匹配，也就是说，你只要在white_list中写下你想要导出的念本的前缀就可以了，当然最好写全。

``` js
    "WHITE_LIST": [
        "日记本",
        "日三省"
    ]

```

删除nian文件夹，重新运行即可。

***


#### 希望逆序，将最后的进展放在前面

``` js
    "REVERSE": false
```

***


#### 感觉图太大了，希望能够缩小

``` js
    "IMG_MAX_SIZE": "400"
```

***

#### 不想要生成 `所有人问所有人` 和 `念的相亲记本 看眼简介求你们了`

念本的名字一定要写全！

``` js
    "BLACK_LIST": [
        "所有人问所有人",
        "念的相亲记本 看眼简介求你们了"
    ]
```


### 启动

#### windows

如果你在 windows 下面使用，那你需要先确认 java 是否安装成功：

- 在命令提示符中输入 `java -version`，看是否会输出如下的反馈。

![image.png](https://upload-images.jianshu.io/upload_images/1846712-222c17b19d573e4d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如果可以的话，说明你的 java 是可以使用的，否则你可能回顾一下上面的[安装 java]。

在**确认配置没有问题的情况下！！**，你可以双击`确认配置后点我启动.bat`启动了。

启动以后工具会做如下几件事情：

1. 登陆你的账号
2. 根据配置获取你所有要导出的念本，并向你确认
3. 询问你是否继续，输入 `y` 表示继续，输入 `n` 表示停止
4. 按下回车继续
5. 获取你所有的念本信息
6. 获取你所有的所有进展信息
7. 获取你所有评论的信息
8. 如果需要保存原图片会依次下载（但不建议
8. 保存成 json
9. 保存成 markdown

#### linux 和 mac

都用 *nix 了就不用多说了吧。。。

### 疑问

如果有疑问请在群里@我，如果请求失败，那就是服务器被你们刷爆啦，到凌晨再处理就可以了。

## 运行结果


![image.png](https://upload-images.jianshu.io/upload_images/1846712-c2fc8eb0b8c0dc88.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![image.png](https://upload-images.jianshu.io/upload_images/1846712-f3c0b00d33c289e0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

在完成以后，你可以在文件夹 `nian` 中查看你的数据，其中 markdown 是一种标记语言文档，能够以简单的语法进行优雅的排版，有非常多的工具将其转换为 pdf 格式做成小册子~

希望你喜欢。

## 查看 Markdown 并变成 PDF

市面上有非常多的 markdown 编辑或者阅读器，可以帮你转换成 pdf。

例如:

- [typora](https://www.typora.io/) 是跨平台最优秀的 markdown 阅读和编辑器了


![image.png](https://upload-images.jianshu.io/upload_images/1846712-6c452da9fe131fd1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

你可以用它导出：

![image.png](https://upload-images.jianshu.io/upload_images/1846712-7fb5ca5d4282922b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

并且生成一个还不错的 PDF

![image.png](https://upload-images.jianshu.io/upload_images/1846712-7e9c6412cfefd08c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>每一个编辑器的样式是编辑器自己定义的，所以有所不同，但是排版上我尽可能的选择了简洁的方式进行。

# 如何变成最好看的 PDF

我在小范围测试了一些念本的导出并询问了他们的意见，在添加了评论、tags、念本介绍、多图后，现在绝大多数的人认为我给他们的 PDF 是最优雅的导出方式。

但是事实上，工具能做的是 80% 的工作，最后生成 PDF 的工作，需要我来完成。虽然 Markdown 转换为 pdf 在各个 markdown 编辑器都可以导出，但是存在以下局限：

- windows 对于 emoji 的支持可以说烂到了极点
- windows 渲染能力很差劲，所以很多人会使用 MacType 来修改显示的效果，但是这很麻烦
- 一般的 Markdown 很难导出足够优雅的 PDF，例如避免图被截断、能够选择合适的配色等等

在多年的经验中，我一直以来都是用 MarkEditor 来作为我的导出工具，但是这是一款收费的应用原价是 128 的，也不建议大家寻找盗版，并且 mac 版本和 win 版本的效果也有差异。

![image.png](https://upload-images.jianshu.io/upload_images/1846712-6b9d0ad15ec81251.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


因此如何生成一个好看的 PDF 只能找我啦。

## 获取好看的 PDF

你可以将工具生成的 json 文件发送给我，并在邮件里按照下面格式填写，发送至 `hh23485@126.com`：

```
是否需要拆分单个念本：【需要】/【不需要】
念的昵称：【念的昵称】
允许公开分享的念本：【本名称】
```

解释一下：

- 关于拆分：默认情况下，我会将所有的念本导出成一个pdf，并且添加一个好看的封面和目录。但是如果你需要拆分，那么这个工作量就很大了，所以这是一个付费的
- 关于名称：我会用来做封面
- 公开分享：念里有很多有意思的公开的本，例如 "一个人的早饭也要好好吃" 这样的本希望能够在导出后，被大家分享和收藏，所以如果你允许公开，我会在最下方放上下载链接供大家下载~ （公开分享的念本会免费拆分出来），当然你可以不提供任何公开的。

![image.png](https://upload-images.jianshu.io/upload_images/1846712-12bb25f6d8b8e712.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如果不按格式发，我会忽略掉的。

# 最后

争取在4月1日之前把宝贝的记忆收藏起来。

我是胡小瓢，如果使用的时候有什么疑问，可以发送邮件 hh23485@126.com 或者在 sa的念群 里找到我。

**如果你觉得帮到了你，希望你能在 [github](https://github.com/hh23485/record-nian-forever) 上给我点一个star。**

**也给 Sa 点一个[star](https://github.com/sapherise/NianiOS)**

![image.png](https://upload-images.jianshu.io/upload_images/1846712-26d6e7f5e2c94af7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>右上角~，如果是手机端的话，可以在右下角点击 Desktop version 就能看到啦


# 告别

天高水长，期待念2和sa的下一个app。


# 公开的念本

下面是经过授权，同意公开的念本，你可以下载和查看样式，决定是否使用工具，也可以用于收藏和分享~ 
**但转发仍然需要联系授权**

- [如xo酱-茶楼](https://pan.baidu.com/s/11H7nEzCuFzFVKiPEtDDNjA)
- [如xo酱-唯有好吃的不能辜负](https://pan.baidu.com/s/1tN9KcbryBIh-4LMIWgi3Uw)
- [如xo酱-随手写的估计](https://pan.baidu.com/s/1O_v9iWQTbCYXEXjfCPttaA)
- [混沌馄饨-你拍一我拍一，馄饨店里做游戏](https://pan.baidu.com/s/1tHvQrLNfKtg6AZsBekvWaQ)

待续


