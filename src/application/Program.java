package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		List <Product> list = new ArrayList <Product>();
		
		
		System.out.println("Type a path from a file: ");
		String path = sc.nextLine();
		
		// gerando um arquivo com base na pasta do arquivo path em uma subpasta chamada out
		File sourceFile = new File (path); 
		String sourceFolderStr = sourceFile.getParent();
		
		boolean success = new File (sourceFolderStr + "\\out").mkdir(); // pasta out criada
		String targetFileStr = sourceFolderStr + "\\out\\summary.csv";   // arquivo summary.csv criado na pasta out
		// subpasta e arquivo criados!!		
		
		// lendo as linhas do arquivo
		try (BufferedReader br = new BufferedReader(new FileReader(path))) { // LEITURA DOS DADOS
			
			String line = br.readLine();
			while (line != null) {   // até chegar na linha nula
				
				String[] vect = line.split(","); // recortar o String conforme a posição das virgulas e armazenar no vetor conforme posição
				String name = vect[0];
				double price = Double.parseDouble(vect[1]); // conversao de String para Double
				int quantity = Integer.parseInt(vect[2]);
				
				list.add(new Product(name, price, quantity));   
				
				line = br.readLine();
			}
			
			// escrevendo a lista no novo arquivo gerado
			try (BufferedWriter bw = new BufferedWriter (new FileWriter(targetFileStr, true))){  //ESCREVENDO OS DADOS
				for (Product item : list) {
					bw.write(item.getName() + "," + String.format("%.2f", item.totalValue()));
					bw.newLine();
				}
				
				System.out.println(targetFileStr + " CREATED!");
				
				//Tratando erros
				
			}catch(IOException e) {														
				System.out.println("Error writing file:  " + e.getMessage());
			}		
			
			
		}catch (IOException e) {
			System.out.println("Error: reading file: " + e.getMessage());
		}
		
		
		sc.close();
	}

}
