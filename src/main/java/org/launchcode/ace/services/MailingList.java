package org.launchcode.ace.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.launchcode.ace.models.Student;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

@Component
public class MailingList {

	public void exportMailList(List<Student> names, HttpServletResponse response) throws IOException { 
		
		//set response content and header
		String csvFileName = "mkm-mail-list.csv";
		 
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                csvFileName);
        response.setHeader(headerKey, headerValue);

		// uses the Super CSV API to generate CSV data from the model data
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
 
        String[] header = { "firstName", "lastName", "addressStreet", "addressApt",
                "city", "state", "zip" };
 
        csvWriter.writeHeader(header);
        
        for (Student name : names) {
        	csvWriter.write(name, header);
        }
         
        csvWriter.close();
		
			
	}
		
}
