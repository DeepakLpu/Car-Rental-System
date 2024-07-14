import java.util.*;
import java.util.List;
import java.util.ArrayList;

class Car
{
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    Car(String carId, String brand, String model, double basePricePerDay)
    {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId()
    {
        return carId;
    }

    public String getBrand()
    {
        return brand;
    }

    public String getModel()
    {
        return model;
    }

    public double calculatePrice(int rentalDays)
    {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable()
    {
        return isAvailable;
    }

    public void rent()
    {
        isAvailable = false;
    } 

    public void returnCar()
    {
        isAvailable = true;
    } 
}

class Customer
{
    private String customerId;
    private String name;

    Customer(String customerId, String name)
    {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public String getName()
    {
        return name;
    }
}

class Rental
{
    private Car car;
    private Customer customer;
    private int days;

    Rental(Car car, Customer customer, int days)
    {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar()
    {
        return car;
    }
    public Customer getCustomer()
    {
        return customer;
    }
    public int getDays()
    {
        return days;
    }
}

class CarRentalSystem
{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    CarRentalSystem()
    {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car)
    {
        cars.add(car);
    }

    public void addCustomer(Customer customer)
    {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days)
    {
        if(car.isAvailable())
        {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        }
        else
        {
            System.out.println("Car is not available for rent");
        }
    }

    public void returnCar(Car car)
    {
        car.returnCar();
        Rental rentalToRemove = null;

        for(Rental rental : rentals)
        {
            if(rental.getCar() == car)
            {
                rentalToRemove = rental;
                break;
            } 
        }

        if(rentalToRemove != null)
        {
            rentals.remove(rentalToRemove);
            System.out.println("Car returned successfully");
        }
        else
        {
            System.out.println("Car was not returned");
        }
    }

    public void main()
    {
        Scanner sc= new Scanner(System.in);

        while(true)
        {
            System.out.println("****** Car Rental System *****");
            System.out.println("1. Rent a car");
            System.out.println("2. Returned a car");
            System.out.println("3. Exit");
            System.out.println("Enter your choice here");
            int choice = sc.nextInt();
            sc.nextLine();

            if(choice == 1)
            {
                System.out.println("\n ** Rent a car ** \n");
                System.out.print("Enter your name : ");
                String customerName = sc.nextLine();

                System.out.println("\n Available Cars : ");
                for(Car car : cars)
                {
                    if(car.isAvailable())
                    {
                        System.out.println(car.getCarId()+" "+ car.getBrand()+" "+ car.getModel());
                    }
                }

                System.out.println("\n Enter the car ID which you want to rent : ");
                String carId = sc.nextLine();

                System.out.println("Enter the number of days for rental : ");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size()+1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for(Car car : cars)
                {
                    if(car.getCarId().equals(carId) && car.isAvailable())
                    {
                        selectedCar = car;
                        break;
                    }
                }


                if (selectedCar != null)
                {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n ** Rental Information **");
                    System.out.println("Customer ID : " +newCustomer.getCustomerId());
                    System.out.println("Customer Name : " +newCustomer.getName());
                    System.out.println("Car : " + selectedCar.getBrand()+"  "+selectedCar.getModel());
                    System.out.println("Rental Days : "+ rentalDays);
                    System.out.printf("Total Price : $%.2fn",totalPrice);

                    System.out.println("\n Confirm rental (Y/N) : ");
                    String confirm = sc.nextLine();


                    if(confirm.equalsIgnoreCase("Y"))
                    {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\n Car rented successfully.");
                    }
                    else
                    {
                        System.out.println("\n Rental canceled.");
                    }
                }
                else
                {
                    System.out.println("\n Invalid car selection or car not available for rent");
                }
            }
                else if(choice == 2)
                {
                    System.out.println("\n **Return a car**");
                    System.out.println("Enter the car ID which you want to return : ");
                    String carId = sc.nextLine();

                    Car carToReturn = null;
                    for(Car car : cars)
                    {
                        if(car.getCarId().equals(carId) && !car.isAvailable())
                        {
                            carToReturn = car;
                            break;
                        }
                    }
                    if(carToReturn != null)
                    {
                        Customer customer = null;
                        for(Rental rental : rentals)
                        {
                            if(rental.getCar() == carToReturn)
                            {
                                customer = rental.getCustomer();
                                break;
                            }
                        }
                        if(customer != null)
                        {
                            returnCar(carToReturn);
                            System.out.println("\n Car returned successfully by : "+ customer.getName());
                        }
                        else
                        {
                            System.out.println("Car was not returned or rental information is invalid.");
                        }
                    }
                    else
                    {
                        System.out.println("Invalid car ID or car is not rented");
                    }
                }
                else if(choice ==3)
                {
                    break;
                }
                else 
                {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
        }
        System.out.println("\n Thank you for using Car Rental System!");
    }
}
public class Car_Rental_System {
    public static void main(String[] args) {
        CarRentalSystem crs = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Fortuner", 80.0);
        Car car2 = new Car("C002", "Monda", "Accord", 70.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150.0);

        crs.addCar(car1);
        crs.addCar(car2);
        crs.addCar(car3);

        crs.main();
    }
}
