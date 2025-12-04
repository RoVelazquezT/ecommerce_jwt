package com.rovelazquez.ecommerce_jwt.config;

import com.rovelazquez.ecommerce_jwt.entity.Product;
import com.rovelazquez.ecommerce_jwt.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

	private final ProductRepository productRepository;

	@Override
	public void run(String... args) throws Exception {

		if (productRepository.count() == 0) {
			loadProducts();
		}
	}

	private void loadProducts() {

		// --- Vegetables ---
		Product p1 = Product.builder().name("Broccoli").category("Vegetables").price(2.50).description(
				"Fresh farm broccoli, packed with vitamins C and K. Perfect for steaming, roasting, or adding to your favorite stir-fry dishes.")
				.imageUrl("broccoli.jpg").build();

		Product p2 = Product.builder().name("Papa").category("Vegetables").price(1.20).description(
				"Versatile white potatoes, sold by the kg. Ideal for mashing until creamy, frying until golden, or baking for a hearty meal.")
				.imageUrl("potato.jpg").build();

		Product p3 = Product.builder().name("Perejil").category("Vegetables").price(0.99).description(
				"A fresh, aromatic bunch of flat-leaf parsley. Adds a clean, peppery flavor to sauces, salads, and garnishes. A kitchen essential.")
				.imageUrl("parsley.jpg").build();

		Product p4 = Product.builder().name("Pimiento").category("Vegetables").price(1.80).description(
				"Sweet and crunchy red bell pepper. Adds vibrant color and a mild, sweet flavor to any dish, raw or cooked. Excellent source of Vitamin A.")
				.imageUrl("pepper.jpg").build();

		Product p5 = Product.builder().name("Tomate").category("Vegetables").price(2.10).description(
				"Ripe, juicy, vine-ripened tomatoes. Bursting with classic garden flavor, they are a must-have for robust sauces, fresh salads, and sandwiches.")
				.imageUrl("tomato.jpg").build();

		// --- Fruits ---
		Product p6 = Product.builder().name("Frambuesa").category("Fruits").price(4.50).description(
				"A 6oz (170g) container of fresh, sweet, and delicate raspberries. An excellent source of antioxidants, perfect for desserts, yogurt, or breakfast.")
				.imageUrl("raspberry.jpg").build();

		Product p7 = Product.builder().name("Albaricoque").category("Fruits").price(3.20).description(
				"Fresh seasonal apricots (damask). Sweet, slightly tart, and velvety. Perfect for snacking, baking into tarts, or making delicious jams.")
				.imageUrl("damask.jpg").build();

		Product p8 = Product.builder().name("Naranja").category("Fruits").price(2.80).description(
				"Seedless navel oranges, bursting with sweet juice and Vitamin C. The perfect healthy snack to brighten your day or for fresh-squeezed juice.")
				.imageUrl("orange.jpg").build();

		Product p9 = Product.builder().name("Uvas").category("Fruits").price(3.90).description(
				"Sweet and crunchy green seedless grapes. A refreshing and healthy snack for all ages, great for lunchboxes or cheese boards.")
				.imageUrl("grape.jpg").build();

		Product p10 = Product.builder().name("Banana").category("Fruits").price(2.30).description(
				"A bunch of perfectly ripe bananas. A great source of potassium, ideal for smoothies, baking banana bread, or a quick energy boost.")
				.imageUrl("banana.jpg").build();

		Product p11 = Product.builder().name("Manzana").category("Fruits").price(2.60).description(
				"Crisp, sweet, and juicy red apples. The perfect classic snack. Great for pies, salads, or eating fresh right out of the hand.")
				.imageUrl("apple.jpg").build();


		productRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		System.out.println("-----> 11 sample products with detailed descriptions loaded <-----");
	}
}