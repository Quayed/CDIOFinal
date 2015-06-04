package ase.entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dtu.cdio_final.shared.dto.MaterialDTO;
import dtu.cdio_final.shared.dto.UserDTO;

public class DAO implements IDAO {

	private BufferedReader reader;
	private BufferedWriter writer;
	
	@Override
	public UserDTO getUser(int userID) {
		return new UserDTO(userID, "Test User", null, null, null);
	}
	
	@Override
	public MaterialDTO getMaterialBatch(int materialBatchId) {
		String productName = null;
		
		try {
			reader = new BufferedReader(new FileReader("store.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line;
		try {
			while(true){
				line = reader.readLine();
				if(line != null){
					if(line.startsWith(String.valueOf(materialBatchId))){
						String[] segments = line.split(",");
						productName = segments[1];
						return new MaterialDTO(materialBatchId, productName, ""+1);
					}
				}
				else
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void updateMaterial(int getMaterialID, double netto, int userID) throws IOException {
		reader = new BufferedReader(new FileReader("store.txt"));
		
		double materialLeft = 0;
		String line;
		ArrayList<String> file= new ArrayList<String>();
		while((line = reader.readLine()) != null){
			file.add(line);
		}
		reader.close();
		
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("store.txt"), "utf-8"));
		for(String singleLine : file){
			if(singleLine.startsWith(String.valueOf(1))){
				String[] segments = singleLine.split(",");
				materialLeft = Double.parseDouble(segments[2]) - 2;
				segments[2] = String.valueOf(materialLeft);
				singleLine = segments[0] + "," + segments[1] + "," + segments[2];
			}
			writer.write(singleLine);
			writer.newLine();
			
		}
		writer.close();
		
		updateLog(getMaterialID, netto, userID, materialLeft);
	}

	private void updateLog(int materialID, double netto, int userID, double materialLeft) throws IOException{
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String currentDate = String.valueOf(format.format(new Date()));
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("log.txt"), "utf-8"));
		writer.append(currentDate + "," + String.valueOf(userID) + "," + String.valueOf(materialID) + "," + String.valueOf(netto) + "," + String.valueOf(materialLeft));
		writer.close();
	}
	
}
