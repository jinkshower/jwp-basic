#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* Tomcat 서버가 시작되면

1. 서블릿 컨테이너가 웹 에플리케이션 상태를 관리하는 ServletContext를 생성한다
2. ServletContext가 초기화 되면 컨텍스트의 초기화 이벤트가 발생된다.
3. ServletContextListner를 상속한 ContextLoadListener의 contextInitialiazed가 실행되며 ResourceDatabasePopulator가 jwp.sql을 실행한다.
4. @WebServlet으로 어노테이션이 붙은 클래스들을 생성한다. DispatcherServlet이 LoadonStartup= 1으로 두었기 때문에 가장 먼저 생성된다.
5. Servlet은 생성 - init() - service() - destroy()의 라이프 사이클을 가진다. Servlet Container는 web.xml 이나 어노테이션(@WebServlet)으로 등록된 클래스를 싱글톤으로 관리한다.
6. 따라서 생성 이후 DispatcherServlet의 init()이 호출되고 RequestMapping이 새롭게 생성되고 initMapping이 호출되어 각 Url에 맞는 Controlller가 등록된다. (준비 완료)

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
1. [localhost:8080](http://localhost:8080)으로 접근시 http Get으로 GET / HTTP1.1 Host : localhost:8080의 메시지를 보낸다.
2. ServerSocket은 accept로 Socket을 만들고(요청에 대한 Socket)해당 socket을 기반으로  HttpServeltRequest,HttpServeltResponse를 생성한다  http메시지를 일고 파싱하여 url을 추출하는 과정이 Request를 만들때 일어난다.
3. ResourceFilter와 CharacterEncodingFilter의 doFilter()메소드가 실행된다. (이 요청은 css, 자바스크립트, 이미지같은 정적 소스 요청이 아니기에 doFilter이후가 실행된다)
4. RequestHandling을 실행한다. (어댑터 패턴으로 이 url을 service할 수 있는 HttpServlet의 구현체를 찾음
5. “urlPattern”에 “/”를 DispatcherServlet에 등록해놓았으므로 DispatcherServlet의 service를 실행한다. 이때 인자로 2에서 생성한 req, resp가 넘어오므로 우리는 service에서 두 인자를 쓸 수 있음
6. 우리만의 RequestMapping에서 우리는 req.getRequestURI()에서 해당 메소드를 execute할 Controller를 찾을 수 있다.
7. HomeController를 매핑해놨으므로 HomeController의 execute가 실행된다.
8. home.jsp로 포워딩을 하도록 되어 있음. new jspView라는 AbstractController의 상위 메소드를 사용하고 ModelAnd View의 model의 맵에는 모든 Question을 담아 준다.
9. DispatcherServlet은 반환받은 ModelAndView에서 View를 반환받음 (jspview) jspview의 render를 호출. 이때 ModelAndView에서 model을 준다
10. jspview는 model에 담긴 모든 정보는 HttpServletRequest에 담아주고 RequestDispatcher의 forward로 home.jsp로 포워딩을 호출한다.

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 문제가 될 만한 이유- question. answers가 인스턴스 변수로 선언되어 있고 이를 할당하는 execute가 있음. 쓰레드마다 상태가 달라질 수 있는 여지가 있다는 것. (questionId가 1인 요청과 2인 요청에 따라 question과 answers는 달라져야 한다)
멀티 쓰레드에서 QuestionDao나 AnswerDao에서 찾은 것들이 question에 할당 될때 같은 인스턴스 변수를 사용하기 때문에 다른 값이 갱신 될 수 있음
* 따라서 상태(인스턴스 변수)를 가지지 않고 Question question으로 새로운 객체를 생성하여 쓰레드마다 다른 객체를 가리키게 하여서 해결하면 된다.
