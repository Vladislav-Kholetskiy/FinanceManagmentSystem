package usersInterface;

import model.User;
import service.FinanceService;

import java.util.Scanner;

public class UsersInterface {
    private final FinanceService financeService;

    public UsersInterface(FinanceService financeService) {
        this.financeService = financeService;
    }

    public void start(Scanner scanner) {
        while (true) {
            System.out.println("Добро пожаловать! Выберите действие:");
            System.out.println("1. Регистрация");
            System.out.println("2. Вход");
            System.out.println("3. Выход");
            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerUser(scanner);
                    break;
                case "2":
                    loginUser(scanner);
                    break;
                case "3":
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    public void registerUser(Scanner scanner) {
        System.out.println("Регистрация нового пользователя");
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        try {
            if (financeService.userExists(username)) {
                System.out.println("Пользователь с таким именем уже существует. Попробуйте другое имя.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Ошибка при проверке имени пользователя: " + e.getMessage());
            return;
        }
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        try {
            financeService.registerUser(username, password);
            System.out.println("Пользователь успешно зарегистрирован!");
            User user = financeService.authenticateUser(username, password);
            mainMenu(scanner, user);
        } catch (Exception e) {
            System.out.println("Ошибка при регистрации пользователя: " + e.getMessage());
        }
    }

    public void loginUser(Scanner scanner) {
        System.out.println("Авторизация");
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        try {
            User user = financeService.authenticateUser(username, password);
            System.out.println("Вы успешно вошли!");
            mainMenu(scanner, user);
        } catch (Exception e) {
            System.out.println("Ошибка при авторизации: " + e.getMessage());
        }
    }

    public void mainMenu(Scanner scanner, User currentUser) {
        while (true) {
            System.out.println("\nОсновное меню");
            System.out.println("1. Добавить категорию расходов");
            System.out.println("2. Добавить категорию доходов");
            System.out.println("3. Изменить бюджет категории расходов");
            System.out.println("4. Добавить доход");
            System.out.println("5. Добавить расход");
            System.out.println("6. Показать баланс");
            System.out.println("7. Показать сводную информацию");
            System.out.println("8. Выйти из учетной записи");
            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addExpenseCategory(scanner, currentUser);
                    break;
                case "2":
                    addIncomeCategory(scanner, currentUser);
                    break;
                case "3":
                    updateExpenseCategoryLimit(scanner, currentUser);
                    break;
                case "4":
                    addIncome(scanner, currentUser);
                    break;
                case "5":
                    addExpense(scanner, currentUser);
                    break;
                case "6":
                    showBalance(currentUser);
                    break;
                case "7":
                    showSummary(currentUser);
                    break;
                case "8":
                    System.out.println("Вы вышли из учетной записи.");
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private void addExpenseCategory(Scanner scanner, User currentUser) {
        System.out.println("Добавление новой категории расходов");
        System.out.print("Введите название категории: ");
        String categoryName = scanner.nextLine();
        System.out.print("Введите лимит для категории: ");
        double limit = Double.parseDouble(scanner.nextLine());

        try {
            financeService.addExpenseCategory(currentUser, categoryName, limit);
            System.out.println("Категория расходов успешно добавлена!");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении категории расходов: " + e.getMessage());
        }
    }

    private void addIncomeCategory(Scanner scanner, User currentUser) {
        System.out.println("Добавление новой категории доходов");
        System.out.print("Введите название категории: ");
        String categoryName = scanner.nextLine();

        try {
            financeService.addIncomeCategory(currentUser, categoryName);
            System.out.println("Категория доходов успешно добавлена!");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении категории доходов: " + e.getMessage());
        }
    }

    private void updateExpenseCategoryLimit(Scanner scanner, User currentUser) {
        System.out.println("Изменение бюджета категории расходов");
        System.out.print("Введите название категории: ");
        String categoryName = scanner.nextLine();
        System.out.print("Введите новый лимит для категории: ");
        double newLimit = Double.parseDouble(scanner.nextLine());

        try {
            financeService.updateExpenseCategoryLimit(currentUser, categoryName, newLimit);
            System.out.println("Лимит категории расходов успешно обновлен!");
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении лимита категории расходов: " + e.getMessage());
        }
    }

    private void addIncome(Scanner scanner, User currentUser) {
        System.out.println("Добавление дохода");
        System.out.print("Введите сумму: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Введите категорию дохода: ");
        String category = scanner.nextLine();

        try {
            financeService.addIncome(currentUser, amount, category);
            System.out.println("Доход успешно добавлен!");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении дохода: " + e.getMessage());
        }
    }

    private void addExpense(Scanner scanner, User currentUser) {
        System.out.println("Добавление расхода");
        System.out.print("Введите сумму: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Введите категорию расхода: ");
        String category = scanner.nextLine();

        try {
            financeService.addExpense(currentUser, amount, category);
            System.out.println("Расход успешно добавлен!");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении расхода: " + e.getMessage());
        }
    }

    private void showBalance(User currentUser) {
        double balance = currentUser.getWallet().getBalance();
        System.out.println("Ваш текущий баланс: " + balance);
    }

    private void showSummary(User currentUser) {
        double totalIncome = financeService.calculateTotalIncome(currentUser);
        double totalExpense = financeService.calculateTotalExpense(currentUser);

        System.out.println("\nСводная информация:");
        System.out.println("Общий доход: " + totalIncome);

        System.out.println("Доходы по категориям:");
        financeService.calculateIncomeByCategory(currentUser).forEach((category, amount) -> {
            System.out.printf("%s: %.2f\n", category, amount);
        });

        System.out.println("Общие расходы: " + totalExpense);

        System.out.println("Бюджет по категориям:");
        financeService.calculateRemainingBudgets(currentUser).forEach((category, remaining) -> {
            double spent = financeService.calculateSpentByCategory(currentUser).getOrDefault(category, 0.0);
            System.out.printf("%s: %.2f, Оставшийся бюджет: %.2f\n", category, spent, remaining);
        });
    }
}

