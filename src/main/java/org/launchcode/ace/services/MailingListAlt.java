package org.launchcode.ace.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.launchcode.ace.models.Student;
import org.springframework.stereotype.Component;

@Component
public class MailingListAlt {
	
	//An alternate attempt at mailing list export without the use of the SuperCsv library
	//file is created successfully, but error is generated at end:
	//java.lang.IllegalStateException: getOutputStream() has already been called for this response
	//Link to tutorial: http://javahonk.com/spring-mvc-csv-download/
	
	public void exportMailListAlt(List<Student> names, HttpServletResponse response) throws IOException { 
	    
		response.setContentType("text/csv");
		String fileName = "mkm-mail-list-alt.csv";
		response.setHeader("Content-disposition", "attachment;filename="+fileName);	
		
		ArrayList<String> rows = new ArrayList<String>();
		rows.add("First Name,Last Name,Street,Apt,City,State,Zip");
		for (int i=0; i<names.size(); i++) {
			rows.add(names.get(i).getFirstName() + "," + 
					names.get(i).getLastName() + "," + 
					names.get(i).getAddressStreet() + "," + 
					names.get(i).getAddressApt() + "," + 
					names.get(i).getCity() + "," + 
					names.get(i).getState() + "," + 
					names.get(i).getZip());
		}
		
		//populate rows with Student address info
		Iterator<String> it = rows.iterator();
		
		ServletOutputStream outStream = response.getOutputStream();

		while(it.hasNext()) {

			String outputString = (String)it.next();
    		
			try {
				//response.getOutputStream().print(outputString);
				outStream.print(outputString + "\n");
				System.out.println(outputString);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
		try {
			//response.getOutputStream().flush();
			outStream.close();
		}
		catch(Exception e) {
    		e.printStackTrace();
		} 
		
			
	}
		
}
