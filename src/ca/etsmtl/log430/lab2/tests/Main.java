package ca.etsmtl.log430.lab2.tests;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		Process jar = Runtime.getRuntime().exec("java -jar lab2.jar deliveries.txt drivers.txt");
		OutputStreamWriter out = new OutputStreamWriter(jar.getOutputStream());
		Scanner in = new Scanner(new InputStreamReader(jar.getInputStream()));
		
		while (in.hasNextLine()) {
			String line = in.nextLine();
			System.out.println("\"" + line + "\"");
			if (line.equals("Enter your choice and press return >> ")) {
				System.out.println("Lorem ipsum");

				out.write("1\r\n");
				out.flush();
			}
		}
	}
}
