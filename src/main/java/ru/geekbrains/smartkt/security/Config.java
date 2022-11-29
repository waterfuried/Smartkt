package ru.geekbrains.smartkt.security;

import ru.geekbrains.smartkt.security.jwt.RequestFilter;

import org.springframework.context.annotation.*;
import org.springframework.http.*;

import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.web.authentication.*;

import lombok.*;

@Configuration
// отключить стандартные настройки Spring Security и использовать прописанные здесь
@EnableWebSecurity
// активировать защиту на уровне методов - помечаются аннотациями @Secured/@PreAuthorized
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
// разрешить использование для методов аннотаций PreAuthorize, PostAuthorize, PreFilter, and PostFilter
//@EnableGlobalMethodSecurity(prePostEnabled = true)
// с 2021 года класс Web... помечен @Deprecated, рекомендуется не наследовать от него
// и реализовавать настройки через бин @Bean public SecurityFilterChain filterChain(HttpSecurity http)
public class Config extends WebSecurityConfigurerAdapter {
    private final RequestFilter requestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Cross-Site Request Forgery (CSRF)
                // есть защита от возможных "атак" со стороны браузера - по умолчанию включена
                // если в HTML-документе есть ссылки или изображения вида
                // <a href="http://bank.com/transfer?accountNo=5678&amount=1000">Текст</a>
                // <img src="http://bank.com/transfer?accountNo=5678&amount=1000"/>,
                // со включенной защитой они будут блокированы (игнорированы),
                // однако это касается любых запросов со стороны документа,
                // поэтому и нужные POST/GET/DELETE запросы будут проигнорированы
                .csrf().disable()
                // настроить доступность использования "запросов" в адресной строке для разных категорий лиц:
                .authorizeRequests()
                //.httpBasic(withDefaults());
                //.requestMatchers("/api/v1/products/**").authenticated()
                //.requestMatchers("/api/v1/profile").authenticated()
                //.requestMatchers("/h2-console/**").permitAll()
                .anyRequest().permitAll()
                // .requestMatchers("/").hasAnyRole("USER")
                // .requestMatchers("/admin/**").hasRole("ADMIN")
                .and()
                // настроить процесс логина:
                //.formLogin()
                //  .loginPage("/login")
                // URL, на который будут отправляться введеные данные пользователя - имя и пароль
                //  .loginProcessingUrl("/authenticateTheUser")
                // URL, на который будет перенаправлен пользователь при неудачной авторизации
                //  .failureUrl("/...")
                //.permitAll();
                // и логаута:
                // .logout()
                // URL, на который будет перенаправлен завершивший работу пользователь
                // .logoutSuccessUrl("/...")
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                // при неудачной авторизации вернуть соответствующий код ошибки
                // вместо перенаправления на стандартную Spring Security форму
                // ввода логина и пароля
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    // если не наследоваться от WebSecurityConfigurerAdapter, этот бин не нужен
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

/*public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /*private DataSource dataSource;
    @Autowired
    public void setDataSource(DataSource dataSource) { this.dataSource = dataSource; }

    //private final JwtRequestFilter jwtRequestFilter;

    // настроить способ(ы) аутентификации (и защиты приложения - HttpSecurity)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
           1. если нужно, чтобы при проверке логина/пароля поиск происходил в стандартной паре таблиц -
              users и authorities, - требуется указать использование DataSource (см. выше), создать
              таблицы в БД и добавить в них пользователей, в этом случае конфиграция будет иметь вид:

              protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                  auth.jdbcAuthentication().dataSource(dataSource);
              }

              Бин DataSource настраивается в файле application.properties:

              spring.datasource.driver-class-name=org.postgresql.Driver
              spring.datasource.url=jdbc:postgresql://localhost:5432/postgres?currentSchema=spring_app
              spring.datasource.username=postgres
              spring.datasource.password=iejfJ1Kw

           2. если нет необходимости в создании новых пользователей и хранении их в БД - когда достаточно
              хранить в памяти стандартный набор пользователей и работать с ними (при этом бин DataSource
              не нужен). В этом случае можно использовать такой вариант:

              protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                  // создание пользователей - их логина, пароля и ролей
                  User.UserBuilder users = User.withDefaultPasswordEncoder();
                  // создаваемые пользователи хранятся в памяти
                  auth.inMemoryAuthentication()
                      .withUser(users.username("user1").password("pass1").roles("USER", "ADMIN"))
                      .withUser(users.username("user2").password("pass2").roles("USER"));
              }

           3. при хранении пользователей и их ролей в БД, можно воспользоваться бином DaoAuthenticationProvider,
              при этом метод configure не нужен, а бин UserService, реализующий интерфейс UserDetailsService,
              описывает, как должна производиться загрузка пользовательских данных из БД.
              BCryptPasswordEncoder указывает, что пароли хранятся не в открытом виде,
              а хэшируются с помощью BCrypt:

              private UserService userService;

              @Autowired
              public void setUserService(UserService userService) { this.userService = userService; }

              @Bean
              public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

              @Bean
              public DaoAuthenticationProvider authenticationProvider() {
                  DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
                  auth.setUserDetailsService(userService);
                  auth.setPasswordEncoder(passwordEncoder());
                  return auth;
              }
    }
}*/