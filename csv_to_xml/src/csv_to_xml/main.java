package csv_to_xml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class main {

	public static void main(String[] args) {

		System.out.println(
				  "*******************************************\n"
				+ "*******************************************\n"
				+ "********** CSV to XMLs Converter **********\n"
				+ "*******************************************\n"
				+ "*******************************************\n"
				+ "Made by Heemoon Yoon in 2019\n"
				+ "Contact : heemoon.yoon@utas.edu.au\n"
				+ "UNIVERSITY OF TASMANIA\n"
				+ "*******************************************\n"
				+ "Enter CSV path (ex :C:/Users/user/Documents/file.csv) : "
				);
		
		Scanner sc = new Scanner(System.in);
		String csvpath = sc.nextLine();

		System.out.println("Enter XML saving path :");
		String xmlpath = sc.nextLine();
			if(!xmlpath.endsWith("/"))
				xmlpath = xmlpath + "/";
			
		
		BufferedReader reader;
		try{
			reader = new BufferedReader(new FileReader(
					csvpath));
			String line = reader.readLine();
			//till EOF
			while(line != null){
				//if first line
				if(line.startsWith("filename,width,height,class,xmin,ymin,xmax,ymax")){
					//skip first line
					line = reader.readLine();
				//from 2nd line
				}else{
					String array[] = new String[8];
					String filename,width,height,classname,xmin,ymin,xmax,ymax,result="";
					//arrange string
					array = line.split(",");
					
					filename = array[0];
					width = array[1];
					height = array[2];
					classname = array[3];
					xmin = array[4];
					ymin = array[5];
					xmax = array[6];
					ymax = array[7];
					
					result = "<annotation>"+"\n"
							+"\t"+"<folder>ratdog</folder>"+"\n"
							+"\t"+"<filename>"+ filename +"</filename>"+"\n"
							+"\t"+"<path>C:\\Users\\user\\Desktop\\ratdog\\"+ filename +"</path>"+"\n"
							+"\t"+"<source>"+"\n"
							+"\t"+"\t"+"<database>Unknown</database>"+"\n"
							+"\t"+"</source>"+"\n"
							+"\t"+"<size>"+"\n"
							+"\t"+"\t"+"<width>"+ width +"</width>"+"\n"
							+"\t"+"\t"+"<height>"+ height +"</height>"+"\n"
							+"\t"+"\t"+"<depth>3</depth>"+"\n"
							+"\t"+"</size>"+"\n"
							+"\t"+"<segmented>0</segmented>"+"\n"
							+"\t"+"<object>"+"\n"
							+"\t"+"\t"+"<name>"+ classname +"</name>"+"\n"
							+"\t"+"\t"+"<pose>Unspecified</pose>"+"\n"
							+"\t"+"\t"+"<truncated>0</truncated>"+"\n"
							+"\t"+"\t"+"<difficult>0</difficult>"+"\n"
							+"\t"+"\t"+"<bndbox>"+"\n"
							+"\t"+"\t"+"\t"+"<xmin>"+ xmin +"</xmin>"+"\n"
							+"\t"+"\t"+"\t"+"<ymin>"+ ymin +"</ymin>"+"\n"
							+"\t"+"\t"+"\t"+"<xmax>"+ xmax +"</xmax>"+"\n"
							+"\t"+"\t"+"\t"+"<ymax>"+ ymax +"</ymax>"+"\n"
							+"\t"+"\t"+"</bndbox>"+"\n"
							+"\t"+"</object>"+"\n";
							
					line = reader.readLine();
					
					//if additional object in same filename
					while(line != null && line.startsWith(filename)){
						array = line.split(",");

						classname = array[3];
						xmin = array[4];
						ymin = array[5];
						xmax = array[6];
						ymax = array[7];
						
						result = result
								+"\t"+"<object>"+"\n"
								+"\t"+"\t"+"<name>"+ classname +"</name>"+"\n"
								+"\t"+"\t"+"<pose>Unspecified</pose>"+"\n"
								+"\t"+"\t"+"<truncated>0</truncated>"+"\n"
								+"\t"+"\t"+"<difficult>0</difficult>"+"\n"
								+"\t"+"\t"+"<bndbox>"+"\n"
								+"\t"+"\t"+"\t"+"<xmin>"+ xmin +"</xmin>"+"\n"
								+"\t"+"\t"+"\t"+"<ymin>"+ ymin +"</ymin>"+"\n"
								+"\t"+"\t"+"\t"+"<xmax>"+ xmax +"</xmax>"+"\n"
								+"\t"+"\t"+"\t"+"<ymax>"+ ymax +"</ymax>"+"\n"
								+"\t"+"\t"+"</bndbox>"+"\n"
								+"\t"+"</object>"+"\n";
						line = reader.readLine();
					}
					
					result = result +"</annotation>";
					

					String savefilename = filename.substring(0,filename.lastIndexOf('.'));
					try (PrintWriter out = new PrintWriter(xmlpath+savefilename+".xml")) {
					    out.println(result);
					    System.out.println("[created]: " + savefilename + ".xml");
					}
				}
			}
			reader.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		System.out.println("Press Any key to close");
		sc.nextLine();
	}
	
}


