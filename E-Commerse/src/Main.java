public class Main {
    public static void main(String[] args) {
        Product cheese = new PerishableProduct("Cheese", 100, 5, false, 0.2);
        Product biscuits = new PerishableProduct("Biscuits", 150, 2, false, 0.7);
        Product tv = new NonPerishableShippableProduct("TV", 300, 3, 5);
        Product scratchCard = new DigitalProduct("Scratch Card", 50, 10);

        Customer customer = new Customer("Ali", 500);

        Cart cart = new Cart();
        cart.addProduct(cheese, 2);
        cart.addProduct(biscuits, 1);
        cart.addProduct(scratchCard, 1);

        cart.checkout(customer);
    }
}