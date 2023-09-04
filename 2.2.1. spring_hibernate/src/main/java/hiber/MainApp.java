package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;



public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);

// создаём пользователей с машинами
        User user1 = new User("Ivan", "Petrov", "Petrov@mail.ru");
        User user2 = new User("Petr", "Ivanov", "Ivanov@mail.ru");
        User user3 = new User("Oleg", "Udov", "Udov@mail.ru");
        User user4 = new User("Maria", "Markova", "Markova@mail.ru");

        Car car1 = new Car("Lada", 2109);
        Car car2 = new Car("Reno", 1206);
        Car car3 = new Car("Mazda", 5);
        Car car4 = new Car("Kia", 187);

        // добавляем  пользователей с машинами в таблицу
        userService.add(user1.setCar(car1).setUser(user1));
        userService.add(user2.setCar(car2).setUser(user2));
        userService.add(user3.setCar(car3).setUser(user3));
        userService.add(user4.setCar(car4).setUser(user4));

        // выводим список пользователей из таблицы
        System.out.println("1. Пользователи с машинами :");
        for (User user : userService.listUsers()) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Model = " + user.getCar());
            System.out.println();
        }

        //ищем и выводим пользователя по марке и серии авто
        System.out.println("2. Поиск пользователя по модели и серии автомобиля :");
        try {
            System.out.println(userService.getUserByCar("Lada", 2109));
        } catch (NoResultException e) {
            System.out.println("нет пользователя с таким автомобилем");
        }

        context.close();
    }
}
