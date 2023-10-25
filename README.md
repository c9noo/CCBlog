# CCBlog
SpringBoot+SpringSecurity+MybatisPlus编写的个人博客系统

## 项目技术栈

| 前端                      | 后端                                                         |
| ------------------------- | ------------------------------------------------------------ |
| Echarts,Vue,ElementUI.... | SpringBoot,MybatisPlus,SpringSecurity,EasyExcel,Swagger3,redis |

## 重点

### SpringSecurity

1. 导入依赖

```xml
<!--添加Spring Security 依赖 -->
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

2. 编写自己的login方法

在默认不进行设置的情况下，直接访问login这个路径，会发现出现了一个登录框。

这个登录框就是security自带的表单页面，并且在控制台中会输出一串随机生成并且加密过后的密码，而账号是user。

我们可以使用这个账号密码进行登录，但是在实际开发中，我们是需要替换成自己的login页面的

```java
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        ......
    }
```

**问题1** security在默认配置的情况下，是怎么知道我们的账号密码是否正确的？

它具有一个完整的过滤链。而其中在默认的情况下，**UsernamePasswordAuthenticationFilter** 会从内存中读取账号密码进行验证。

也就是说它在内部会自己进行调用方法然后进行处理。

**问题2** 如果我重写了Login方法，要如何把前端传送来的数据交给security让它继续帮我完成校验？

在**UsernamePasswordAuthenticationFilter**中会创建一个**UsernamePasswordAuthenticationToken**对象并且传入到**AuthenticationManager**接口的实现类中。

而我们重写了这个login方法，那么我们就要充当这个角色。

在SecurityConfig类中进行注入AuthenticationManager对象

```java
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
```

随后调用这个对象并且自己创建一个**UsernamePasswordAuthenticationToken**对象进行传入

```java
    @Override
    public ResponseResult login(User user) {
        //1. 将账号密码封装成一个UsernamePasswordAuthenticationToken对象，并且调用authenticate 进行认证
        Authentication authenticate =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUserName(), user.getPassword()
                        )
                );
    }
```

这样就完成了数据的传入。

而在**authenticate**这个方法中，它会做两件事情，一个是调用**UserDetailsService**接口进行获取到详细信息，一个是调用抽象方法**additionalAuthenticationChecks**进行完成具体的身份验证

**问题3** 如何自定义读取的方式？并且自定义账号密码

我们需要继承**UserDetailsService**接口，进行重写**loadUserByUsername**方法，在内部编写我们自己的逻辑

```java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //1. 根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,s);
        User user = userMapper.selectOne(queryWrapper);
        //2. 判断是否查询成功
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //3. 返回用户信息
        if(user.getType().equals(SystemConstants.ADMAIN)){
            List<String> list = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,list);
        }
        return new LoginUser(user,null);
    }
}
```

在这个接口中，我们不单单是查询详细信息那么简单，我们还需要查询它的权限信息。这在后面的过滤器中，例如**FilterSecurityInterceptor**将会使用到它

**问题4** 存入数据的密码不能是未加密的，要如何解决？

```java
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
```

这样就能完成密码的加密

**问题5**如何设置哪些接口可以匿名访问，哪些接口不能匿名访问

```java
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    AccessDeniedHandler accessDeniedHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/user/login").anonymous()
                // 除上面外的所有请求全部不需要认证即可访问
                .anyRequest().authenticated();

        //配置异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        //关闭默认的注销功能
        http.logout().disable();
        //把jwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //允许跨域
        http.cors();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
```



**总结**

![](png/Snipaste_2023-10-25_17-14-06.png)

![](png/Snipaste_2023-10-25_17-12-35.png)

3. 校验过滤器

在原先的security过滤链中，**UsernamePasswordAuthenticationFilter**会在验证成功之后创建**Authentication**对象并且返回给**SecurityContextPersistenceFilter**

而**SecurityContextPersistenceFilter**负责创建并且在请求之间共享SecurityContext

它会在请求处理之前从存储介质中加载**SecurityContext**，并在请求处理后将其保存回存储介质

现在因为我们重写了login方法，所以我们需要自己创建一个过滤器去完成返回对象这个操作，而**OncePerRequestFilter**是 Spring Security 提供的过滤器基类之一

```java
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. 获取到token
        String token = request.getHeader("token");
        if (token != null){
            //2. 从token中获取到userId
            Claims claims = null;
            try {
                claims = JwtUtil.parseJWT(token);
            } catch (Exception e) {
                //响应告诉前端需要重新登录
                ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
                WebUtils.renderString(response, JSON.toJSONString(result));
                return;
            }
            String userId = claims.getSubject();
            //3. 从redis中获取用户信息
            LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userId);
            if (Objects.isNull(loginUser)){
                //说明登录过期  提示重新登录
                ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
                WebUtils.renderString(response, JSON.toJSONString(result));
                return;
            }
            //3. 添加到SecurityContextHold中
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            loginUser,
                            null,
                            null
                    );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request,response);

    }
}
```

随后配置到SecurityConfig类的configure方法中

```java
        //把jwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链中
        http
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
```

这样接下来，我们就可以直接在**SecurityContextHolder**中获取到我们想要的信息

4. 异常的处理

在这一整个过程中，我们随时可能会发生异常，如果Security返回的Json格式是不符合我们要求的，所以我们需要进行重新处理一下

**AuthenticationEntryPoint** 认证失败处理器

```java
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        //InsufficientAuthenticationException
        //BadCredentialsException
        ResponseResult result = null;
        if(authException instanceof BadCredentialsException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),authException.getMessage());
        }else if(authException instanceof InsufficientAuthenticationException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else{
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
```

**AccessDeniedHandler** 授权失败处理器

```java
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
```

配置异常处理器，在SecurityConfig中

```java
        //配置异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
```

5. 权限的控制

需要在配置类上加上注解

```java
@EnableGlobalMethodSecurity(prePostEnabled = true)
```

这样就可以使用**PreAuthorize**注解进行权限的控制了

```java
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
```

这边是进行自定义的权限过滤

```java
@Service("ps")
public class PermissionService {
    /**
     * 判断当前用户是否具有permission
     * @param permission 要判断的权限
     * @return
     */
    public boolean hasPermission(String permission){
        //如果是超级管理员  直接返回true
        if(SecurityUtils.isAdmin()){
            return true;
        }
        //否则  获取当前登录用户所具有的权限列表 如何判断是否存在permission
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
```

### EasyExcel

https://github.com/alibaba/easyexcel

https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write#%E7%A4%BA%E4%BE%8B%E4%BB%A3%E7%A0%81-1

1. 导入依赖

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>3.3.2</version>
</dependency>
```



### 七牛云OSS

```java
    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }
```

```java
@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class OssUploadService implements UploadService {
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //判断文件类型
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //对原始文件名进行判断
        if(!originalFilename.endsWith(".png")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        //如果判断通过上传文件到OSS
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img,filePath);//  2099/2/3/wqeqeqe.png
        return ResponseResult.okResult(url);
    }

    private String accessKey;
    private String secretKey;
    private String bucket;


    private String uploadOss(MultipartFile imgFile, String filePath){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return "http://s1t2bb3ld.hn-bkt.clouddn.com/"+key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return "www";
    }
}
```



## 总结

因为月底要考试了，所以在后面的代码都没怎么写注释了....等考完试了会在十一月份中旬拉着朋友一起从产品设计开始设计一个项目出来
