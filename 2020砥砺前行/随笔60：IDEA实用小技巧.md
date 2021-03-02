#  Intellij IDEA 实用小技巧 

## 我最爱的【演出模式】

我们可以使用【Presentation Mode】，将`IDEA`弄到最大，可以让你只关注一个类里面的代码，进行毫无干扰的`coding`。

可以使用`Alt+V`快捷键，弹出`View`视图，然后选择`Enter Presentation Mode`。效果如下：![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9Tx4vRvcnrAlLic2Ckn9macer6nkMyYoYf4XELJEdDN4yl1p8BoZHMTw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

这个模式的好处就是，可以让你更加专注，因为你只能看到特定某个类的代码。可能读者会问，进入这个模式后，我想看其他类的代码怎么办？这个时候，就要考验你快捷键的熟练程度了。你可以使用`CTRL+E`弹出最近使用的文件。又或者使用`CTRL+N`和`CTRL+SHIFT+N`定位文件。

如何退出这个模式呢？很简单，使用`ALT+V`弹出view视图，然后选择`Exit Presentation Mode` 即可。但是我强烈建议你不要这么做，因为你是可以在`Enter Presentation Mode`模式下在`IDEA`里面做任何事情的。当然前提是，你对`IDEA`足够熟练。

## 神奇的Inject language

如果你使用`IDEA`在编写`JSON`字符串的时候，然后要一个一个`\`去转义双引号的话，就实在太不应该了，又烦又容易出错。在`IDEA`可以使用`Inject language`帮我们自动转义双引号。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9PKIU0PHwp9BDoJUhBZxRnialictcmqIB1FPmkjYmibicbegpnPOWpsFveA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

先将焦点定位到双引号里面，使用`alt+enter`快捷键弹出`inject language`视图，并选中`Inject language or reference`。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9v4tBW0Me0uqdIuF83zvicXOlbkEtoXyyiatVdAqV2sibWvWv9rTGYu0lQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

选择后,切记，要直接按下`enter`回车键，才能弹出`inject language`列表。在列表中选择 `json`组件。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9ibfeF4ia8JrK6Xj9vibtGbewFwyMAwKmbLesyjxuL0IvVErHCYE4lqhKg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

选择完后。鼠标焦点自动会定位在双引号里面，这个时候你再次使用`alt+enter`就可以看到![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9dRp6Yzibr9ibwt1k8picibEDJA0HT3DIqBWk8dGzCicRlxkNB1ia1MMTPUJQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

选中`Edit JSON Fragment`并回车，就可以看到编辑`JSON`文件的视图了。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9J1KicxcWsBDzRDe5cgjzXJ1KMEo7msLfRq0O7YQ0PG5wfApRmiavzSQQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

可以看到`IDEA`确实帮我们自动转义双引号了。如果要退出编辑`JSON`信息的视图，只需要使用`ctrl+F4`快捷键即可。

`Inject language`可以支持的语言和操作多到你难以想象，读者可以自行研究。

## 使用快捷键移动分割线

假设有下面的场景，某个类的名字在`project`视图里被挡住了某一部分。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9E7jEDkV1Bw9PPHMuP0kMP6jf6pWaLo0J51cqocbviaiabXaDg9tuY6GA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

你想完整的看到类的名字，该怎么做。一般都是使用鼠标来移动分割线，但是这样子效率太低了。可以使用`alt+1`把鼠标焦点定位到`project`视图里，然后直接使用`ctrl+shift+左右箭头`来移动分割线。

## ctrl+shift+enter不只是用来行尾加分号的

`ctrl+shift+enter`其实是表示`为您收尾`的意思，不只是用来给代码加分号的。比如说：![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9XGPicaEFnD1OVetnhO7WXrZB3KoLf4pdVicZ6cUeS05KwS197sHfPLdg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

这段代码，我们还需要为if语句加上大括号才能编译通过，这个时候你直接输入`ctrl+shift+enter`，`IDEA`会自动帮你收尾，加上大括号的。

## 不要动不动就使用IDEA的重构功能

`IDEA`的重构功能非常强大，但是也有时候，在单个类里面，如果只是想批量修改某个文本，大可不必使用到重构的功能。比如说：![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9uXGwvl4jgF5OqMu1aZ4w522EMpoKnbW4L2W9KyDicXXybtehkPABpkQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

上面的代码中，有5个地方用到了rabbitTemplate文本，如何批量修改呢？首先是使用`ctrl+w`选中`rabbitTemplate`这个文本,然后依次使用5次`alt+j`快捷键，逐个选中，这样五个文本就都被选中并且高亮起来了，这个时候就可以直接批量修改了。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9TNRoZWogOtKluPVcXicHNRO6iaRTWyBQUd7zc9D24JrhibPTeQdyPSibrQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

## 去掉导航栏

去掉导航栏，因为平时用的不多。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9FkFsiaZkSTiaMto5vFdCeCJ6w5u8e5bibohz8spZWXhsdNp9PzqhR57hw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

可以把红色的导航栏去掉，让`IDEA`显得更加干净整洁一些。使用`alt+v`，然后去掉`Navigation bar`即可。去掉这个导航栏后，如果你偶尔还是要用的，直接用`alt+home`就可以临时把导航栏显示出来。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9wvMTn21s7zfOkPtv1qia1VQdicseMVr3MbSejKfpQp3U1oNjANaibiadJg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

如果想让这个临时的导航栏消失的话，直接使用`esc`快捷键即可。

## 把鼠标定位到project视图里

当工程里的包和类非常多的时候，有时候我们想知道当前类在project视图里是处在哪个位置。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9ElNYI9JGaR2cWtN5PuicjWXF7VoYPnNgNhVPqflZjYn14hicMOvERVbw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

上面图中的`DemoIDEA`里，你如何知道它是在`spring-cloud-config`工程里的哪个位置呢？可以先使用`alt+F1`，弹出`Select in`视图，然后选择`Project View`中的`Project`，回车，就可以立刻定位到类的位置了。

![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9QrZQ7QUm8SnbmbUWCv9Sckv0aYT2LE834lW9yNaJZj1ozgNicIgmeNA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)这里写图片描述

那如何从`project`跳回代码里呢？可以直接使用`esc`退出`project`视图，或者直接使用`F4`,跳到代码里。

## 强大的symbol

如果你依稀记得某个方法名字几个字母，想在`IDEA`里面找出来，可以怎么做呢？直接使用`ctrl+shift+alt+n`，使用`symbol`来查找即可。比如说：![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9FVicOqwzsGpqXUEE5lyVWzUvia6HD1sdh50sDYk0LgKeeiadibKxLhuIGQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

你想找到checkUser方法。直接输入`user`即可。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ96wRFIa0QZibibF3CYsey7vXiaW3kiaTIdgxtGjqgNsmOaJ1yVn7F7RKFibw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

如果你记得某个业务类里面有某个方法，那也可以使用首字母找到类,然后加个`.`，再输入方法名字也是可以的。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ97rPYUotehhsPKH2JETthWuwcnsM6rhgB1pJU3r3D7b6WJsQxlNcgqw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

## 如何找目录

使用`ctrl+shift+n`后，使用`/`，然后输入目录名字即可.![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9kkBNFrloib16TfxjEwKSiadJ6YgKutCVYGR5dEAibICrFGiaRqRhHQTeew/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

## 自动生成not null判断语句

自动生成not null这种if判断，在`IDEA`里有很多种办法，其中一种办法你可能没想到。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ96CMnFwmjAC4u1NLAtlhG1Chx1RPn5cklzNg5GIJibXPIyuDqTYeeGZQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

当我们使用rabbitTemplate. 后，直接输入`notnull`并回车，`IDEA`就好自动生成if判断了。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9W96oE8SIH98cBboVlym2YocCg3eq5ZcL73ZibZibbl2ibhq8Xy6vEAgNw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

## 按照模板找内容

这个也是我非常喜欢的一个功能，可以根据模板来找到与模板匹配的代码块。比如说：

> 想在整个工程里面找到所有的try catch语句,但是catch语句里面没有做异常处理的。

catch语句里没有处理异常，是极其危险的。我们可以`IDEA`里面方便找到所有这样的代码。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9uSqhdm4qySwda1h0ib6jeoVs7ibXibTNmia7mC17jktSgdoqcBGLKK1I0Q/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

首先使用`ctrl+shift+A`快捷键弹出action框，然后输入`Search Struct`![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ90NXWY0ZZicVxuepYcibXDhIqkUFOricGmRGxNn6ghacZ7D61Yt1cINAgg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

选择`Search Structurally`后，回车，跳转到模板视图。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9vibFJD4SDpqS5PP9EE2iaA9e3fwCh8FEtWLFYOZekl1hs0lLwrrsfw1g/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

点击`Existing Templates`按钮，选择`try`模板。为了能找出catch里面没有处理异常的代码块，我们需要配置一下`CatchStatement`的`Maximum count`的值，将其设置为1。

点击`Edit Variables`按钮，在界面修改`Maximum count`的值。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ91glnTuMh2mKJ1icnZjkyxHIyXcgicyShBcMLM4dE3ym77xHicCskC376Q/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

最后点击`find`按钮，就可以找出catch里面没有处理异常的代码了。![图片](https://mmbiz.qpic.cn/sz_mmbiz_png/knmrNHnmCLFB8R9CjZg2crOT56ZkubQ9tiaI5DJkYWG1YiaIzqA5ULIo0TMHibHxT6BHDINUgGzcib3H4aJYaJko9A/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)