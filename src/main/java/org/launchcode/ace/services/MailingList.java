package org.launchcode.ace.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.launchcode.ace.models.Student;
import org.launchcode.ace.models.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

@Component
public class MailingList {
	
	@Autowired 
	protected StudentDao studentDao;
	
	public void cleanMailList(HttpServletResponse response) { 
		//Get list of students where Maillist == true
		List<Student> addrList = studentDao.findByMailListTrueOrderByLastName();				
		
		//Create new list to store errors
		List<Student> errorList = new ArrayList<>();
		
		//Validate addresses
		for (int i=0; i<addrList.size(); i++) {
			//if any address field is null 
			if (addrList.get(i).getFirstName().isEmpty() || addrList.get(i).getLastName().isEmpty() || 
				addrList.get(i).getAddressStreet().isEmpty() || addrList.get(i).getCity().isEmpty() ||
				addrList.get(i).getState().isEmpty() || addrList.get(i).getZip().isEmpty()) {
				
				//add to errorList
				errorList.add(addrList.get(i));
			}
		}
		
		String filename = "Errors-";
		
		try {
			writeFile(filename, errorList, response);
		}
		catch (IOException e) {
			
		}

	}
	
	public void exportMailList(HttpServletResponse response) { 
		
		//Get list of students where Maillist == true
		List<Student> addrList = studentDao.findByMailListTrueOrderByLastName();
		
		String filename = "ACEMaillist-";
		
		try {
			writeFile(filename, addrList, response);
		}
		catch (IOException e) {
			
		}
	}
	
	public void writeFile(String filename, List<Student> maillist, HttpServletResponse response) throws IOException {
		//create filename using current date and time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
		LocalDateTime now = LocalDateTime.now();
		String file = filename + dtf.format(now) + ".csv";
		
        //set header
		String[] header = { "firstName", "lastName", "addressStreet", "addressApt",
                "city", "state", "zip" };
        
		//Write file
		//set response header and content
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                file);
        response.setHeader(headerKey, headerValue);

		// uses the Super CSV API to generate CSV data from the model data
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
 
        //write the header
        csvWriter.writeHeader(header);
        
        //write the individual records
        for (Student s : maillist) {
        	csvWriter.write(s, header);
        }
    
        csvWriter.close();	
	}
		
}
