package com.eBook.Backend.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderStatusConfig 
{
	public static String confirmed="Confirmed";
	public static String delivered="Delivered";
	public static String onTheWay="On the Way";
	public static String cancelled="Cancelled";
	public static String addToCart="Added to cart";
	

}
