v10
利用Tomcat安装目录中conf/web.xml,将所有MimeType进行支持。
该web.xml中定义了所有的资源文件后缀名与对应的MimeType类型(Content-Type对应的值)

1:在当前目录下新建目录conf
2:将Tomcat的web.xml文件复制到该目录中
3:修改HttpContext类中初始化MimeMapping的方法，改为
     通过解析web.xml进行初始化的操作。

v11
使服务端支持处理业务操作。
以注册用户为例:用户在注册页面中输入注册信息，点击注册按钮后，服务端经过处理响应注册成功页面。

这里分为两步:
1:服务端要正确接收客户端提交的用户数据。
2:根据客户端请求的业务操作对提交的数据进行处理并回馈


本版本主要解决在请求中正确解析用户提交的数据。而用户提交数据时通常使用form表单提交，form有两种提交模式一种为:GET，一种为:POST，
这里暂时先只处理GET方式提交。

GET请求:地址栏形式提交数据，即:用户输入的数据会被拼接到请求的抽象路径中，以"?"分隔，左侧为请求的抽象路径部分，
右侧为用户传递的若干参数。每个参数以"&"分隔。
例如:http://localhost:8088/login?name=zs&pwd=123


实现:
1:在webapps/myweb/添加注册页面:reg.html

2:当GET方式提交表单后，发现请求中的url部分会带有参数对此我们要对请求行中的url部分进行再一次解析。

3:在HttpRequest中添加三个属性，用于保存url中的各部分
  String requestURI:url中的请求部分(?左侧内容)    
  String queryString:url中的参数部分(?右侧内容)
  Map parameters:保存参数部分中的每个参数


v12
使服务端支持处理业务操作。
以注册用户为例:
用户在注册页面中输入注册信息，点击注册按钮后，服务端经过处理响应注册成功页面。

这里分为两步:
1:服务端要正确接收客户端提交的用户数据。
2:根据客户端请求的业务操作对提交的数据进行处理并回馈


解决处理注册业务。
首先在ClientHandler处理请求的环节中，先根据请求中的requestURI来判断此请求是否是处理注册业务，若时
则执行处理注册业务。否则再判断该请求是否为请求一个静态资源。

而注册业务不直接在ClientHandler中处理。我们单独定义一个类:RegServlet.使用这个类完成注册业务的处理。
ClientHandler只是根据请求判断是否调用该类来处理。


实现:
1:新建一个包:com.webserver.servlet
2:在servlet包中新建处理注册业务的类:RegServletRegServlet要定义一个用于处理业务的类:service
3:为ClientHandler处理业务的环节处添加一个新的分支根据request获取请求(requestURI),若该请求是请求注册业务，
则实例化RegServlet并调用其service方法

v13
完成用户登录功能
用户请求登录页面，并在页面上输入用户名及密码，点击登录按钮后，服务端进行处理，并响应登录结果。

实现步骤:
1:在webapps/myweb/中添加三个页面:
  login.html:登录页面
    该页面中的form表单指定的路径:action="login"
    并且表单中包含两个输入框:用户名和密码  

  login_success.html:登录成功提示页面
    显示一行字:登陆成功，欢迎回来。
    
  login_fail.html:登录失败提示页面
    显示一行字:登录失败,用户名或密码不正确。

2:在com.webserver.servlet包中添加用于处理登录
    业务的类:LoginServlet
        
3:修改ClientHandler，在第二个环节处理请求中再判断是否为注册请求后添加一个分支，判断是否为请求登录若是请求登录，
则实例化LoginServlet并调用其service方法处理登录。否则执行后续操作判断是否为请求一个静态资源。

LoginServlet的实现:
1:首先通过request获取用户在登录页面上输入的用户名及密码
    
2:通过RandomAccessFile读取user.dat文件，对比每个注册用户，当用户输入的用户名与密码与该文件中某个注册用户信息一致时，
响应用户登录成功页面。若用户名一致但密码不一致或者该用户名在user.dat文件中不存在时都响应用户登录失败页面。

v14
在com.webserver.servlet包中定义一个超类：HttpServlet.该类作为所有处理具体业务的Servlet的统一超类使用。
规定所有子类的行为，并定义子类可复用的方法。

1:由于每个Servlet都有service方法用于处理业务，所以该方法是Servlet必有的方法，但是每个业务不同，
Servlet该方法中的逻辑不同，对此我们在HttpServlet中定义该方法时为抽象方法，这样所有派生类必须具备该方法，
但是实现逻辑可以不同。

2:Servlet在处理完业务后会响应对应的页面给客户端。
对此我们可以在HttpServlet中定义一个方法用于设置响应对象HttpResponse.这样所有的Servlet就无序在写重复代码了。


在ClientHandler中利用反射机制，根据请求动态加载并实例化对应的Servlet并调用其service方法。从而做到
无论将来增删哪个Servlet，ClientHandler都无需为其再修改分支了。
我们要定义一个Map，用来维护请求与对应的Servlet名字的关系。ClientHandler是根据该Map来决定哪个请求调用
哪个Servlet来处理。而Map的初始化操作完全可以用一个xml文件进行配置。


实现:
1:在com.webserver.core包中定义一个类:ServerContext
    用于维护服务端的一些配置信息

2:在ServerContext中定义一个静态属性:
  Map servletMapping
  key:请求路径    
  value:对应Servlet的完全限定名

3:定义一个初始化方法和根据请求路径获取对应Servlet名字的方法

4:在静态块中调用初始化操作，对servletMapping初始化    


v15
在WebServer主类中使用线程池来管理用于处理客户端请求的线程。






