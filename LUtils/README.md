# *参考链接*
gradle 统一配置
参考：
https://blog.csdn.net/myboyer/article/details/79229151
https://blog.csdn.net/cai_iac/article/details/51850291
https://blog.csdn.net/wangshixu2015/article/details/80391591

Gradle使用详解：
https://blog.gokit.info/post/android-gradle-deep-dive/

blankj工具集(已经移除)
git：https://github.com/Blankj/AndroidUtilCode
说明：https://blankj.com/2016/07/31/android-utils-code/

gitflow工作流，部分流程

    feature 分支开发完 merge 到dev分支
    dev 分支检查无错误，push，之后 merge 到 master分支
    master 分支检查无错误，push 之后，打Tag值，之后 push
    最新从 master 迁出新分支到 release 下，分部新版本
    最后删除无用的featrue分支，并迁出新的feature分支继续开发
    
Tag操作
    
    参考：    
    https://blog.csdn.net/u011458979/article/details/78063803
    https://blog.csdn.net/revitalizing/article/details/49056411 
    1.创建分支
    
    git branch branchname
    //创建并切换到新分支
    git checkout -b branchname
    //远程分支
    git push origin branchname
    //查看远程分支
    git branch -r
    2.删除分支
    
    2.1删除本地分支（-D强制删除）
    
    git branch -d branchname
    2.2删除远程分支
    
    git push origin :branchname
    3.打tag
    
    git tag -a v1.1 -m "注释"
    git push origin v1.1
    //查看所有tag
    git tag -l
    4.删除tag
    
    4.1删除本地tag
    
    git tag -d v1.1
    4.2删除远程tag
    
    git push origin :v1.1 
    //也可以这样
    git push origin --delete tag V1.1
    
    --------------------- 
    

# *点击效果说明*
1. 按钮圆形波纹效果，比如标题栏左右按钮，音乐播放器按钮
换成 ImageButton 并增加 3 个属性
<ImageButton
    android:layout_width="45dp"
    android:layout_height="45dp"
    android:radius="50dp"
    android:background="?android:attr/selectableItemBackgroundBorderless"
    android:colorControlHighlight="@color/colorRipple"/>

2. 列表项按压、点击波纹效果
根布局增加 style RippleWhiteRect
<android.support.constraint.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:theme="@style/RippleWhiteRect">
....
</android.support.constraint.ConstraintLayout>

3. 图片按压变暗效果
图片控件更换为 DarkableRoundImageView，其他不变
<xiongdixingqiu.haier.com.xiongdixingqiu.view.DarkableRoundImageView/>
要想有点击效果，ImageView 需要有点击事件，你们可以用 helper.itemView.performClick()  转一下

# *RxJava1*
1.Observable 被观察者 

通过Observable的create创建一个可观察的序列 

通过subscribe去注册一个观察者

2.Observer 观察者 

作为Observable的Subscribe方法参数

接口 
	
	{ 
	    void onCompleted()
	    void onError(Throwable e)
	    void onNext(T t)
	}
	

3.Subscribe 订购

4.Subscription 订阅费：

用于秒数观察者和被观察者之间的关系
接口 
	
	{
	    void unSubscribe() 解除订阅 
	    boolean isUnSubscribe() 是否解除订阅
	}
	
5.Subscriber 订户 

实现了Observer,Subscription

抽象类 

	{
	实现接口 implements Observer<T>,Subscription
	}
	
6.OnSubscribe 在订购 订阅时会触发此接口，实际作用是向订阅者发送数据
Observable里面的接口，实现了Action1

	Subscription 订阅费 -> Observable 被观察者 -> OnSubscribe 在订购
	Subscribe 订购 Observer 观察者
## 举例
打电话：

	打电话的人A(Observable) 拨打(Subscribe)了一个接电话的人B(Subscriber)
	通过通信电路(OnSubscribe)向B发送数据
	B可以随时挂掉电话(Subscription)

##UML



# *RxJava2*

1.Observable 被观察者 不支持背压

通过Observable的create创建一个可观察的序列 

通过subscribe去注册一个观察者

抽象类 实现接口

    ObservableSource<T>{
        void subscirbe(Observer<? super T> observer);
    }

2.Observer 观察者

作为Observable的subscribe方法参数

接口 
	
	{
	    void onSubscribe(Disposable d) 
	    void onComplete()
	    void onError(Throwable e)
	    void onNext(T t)
	}
	
3.Disposable

取消订阅和获取订阅状态

接口 类似 Rxjava1中的Subscription

    {
        void dispose() //处理
        boolean isDispose(); // 是否处理
    }
4.ObservableOnSubscribe 在订购
 
 订阅时触发此接口
 在Observable内部，向观察者发射数据
 接口
 
    {
        void subscribe(ObservableEmitter<T> e) throws Exception
    }

5.Emitter 发射器

发射数据接口
对Observer和Subscriber的包装

接口

    {
    	    void onComplete()
    	    void onError(Throwable e)
    	    void onNext(T t)
    }

##背压

背压问题（生产者生产速度大于消费者消费速度）
    异步环境下产生的问题，是由于发送和处理速度不同一的产生的，是一种流速控制解决策略

背压策略

1.Flowable

通过Flowable的create创建一个可观察的序列 

通过subscribe去注册一个观察者

实现接口

    {
        void subcribe(Subscriber<? super T> s)
    }

2.Subscriber

一个单独接口，和Observer的方法类似

作为Flowable的subscribe的参数

接口

    {
        void OnSubscribe(Subscription s);
        void onComplete()
        void onError(Throwable e)
        void onNext(T t)
    }

3.Subscription

支持被压，有用于背压的request方法

接口

    {
        void request(long n) 响应式拉取解决背压问题
        void cancel()
    }
        


4.FlowableOnSubscribe

订阅时触发此接口

在Flowable内部，用于向观察者发射数据

接口

    {
        void subscribe(FlowableEmitter<T> e) throws Exception       
    }

5.Emitter

发射数据的接口

是对Observer和Subscribere的包装

    {
    	    void onComplete()
    	    void onError(Throwable e)
    	    void onNext(T t)
    }



 
