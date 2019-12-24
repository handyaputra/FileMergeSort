package Ranker; 

import java.io.DataInputStream; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell; 
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Main {

	static void merge(Float[] average, int l, int m, int r, Float[] total, String[] name) 
    { 
        // Find sizes of two subarrays to be merged 
        int n1 = m - l + 1; 
        int n2 = r - m; 
  
        /* Create temp arrays */
        float L[] = new float [n1]; 
        float R[] = new float [n2];
        float LT[] = new float [n1]; 
        float RT[] = new float [n2];
        String LS[] = new String [n1];
        String RS[] = new String [n2];
  
        /*Copy data to temp arrays*/
        for (int i=0; i<n1; ++i) {
            L[i] = average[l + i];
        	LS[i] = name[l + i];
        	LT[i] = total[l + i];
        }
        for (int j=0; j<n2; ++j) {
            R[j] = average[m + 1 + j];
            RS[j] = name[m + 1 + j];
            RT[j] = total[m + 1 + j];
        }
  
        /* Merge the temp arrays */
  
        // Initial indexes of first and second subarrays 
        int i = 0, j = 0; 
  
        // Initial index of merged subarry array 
        int k = l; 
        while (i < n1 && j < n2) 
        { 
            if (L[i] <= R[j]) 
            { 
                average[k] = L[i];
                name[k] = LS[i];
                total[k] = LT[i];
                i++; 
            } 
            else
            { 
                average[k] = R[j];
                name[k] = RS[j];
                total[k] = RT[j];
                j++; 
            } 
            k++; 
        } 
  
        /* Copy remaining elements of L[] if any */
        while (i < n1) 
        { 
            average[k] = L[i]; 
            name[k] = LS[i];
            total[k] = LT[i];
            i++; 
            k++; 
        } 
  
        /* Copy remaining elements of R[] if any */
        while (j < n2) 
        { 
            average[k] = R[j]; 
            name[k] = RS[j];
            total[k] = RT[j];
            j++; 
            k++; 
        } 
    } 
  
    // Main function that sorts arr[l..r] using 
    // merge() 
    static void sort(Float[] average, int l, int r, Float[] total, String[] name) 
    { 
        if (l < r) 
        { 
            // Find the middle point 
            int m = (l+r)/2; 
  
            // Sort first and second halves 
            sort(average, l, m, total, name); 
            sort(average , m+1, r, total, name); 
  
            // Merge the sorted halves 
            merge(average, l, m, r, total, name); 
        } 
    } 
  
    /* A utility function to print array of size n */
    static void printArray(Float average[], Float total[], String name[]) 
    { 
        int n = average.length;
        for (int i=1; i<n; ++i)
        	if (average[i] != 0 && average[i] != null)
            System.out.print(average[i] + " " + total[i] + " " + name[i] + "  "); 
        System.out.println(); 
    } 
        
	public static void create(String file, Integer status) { 
		ArrayList<ArrayList<String>> RowCol = null; 
		ArrayList<String> Data = null;
		Float value[][] = new Float[256][256];
		Float average[] = new Float[256];
		Float total[] = new Float[256];
		String name[] = new String[256];
		Integer jcolumn = 0;
		Integer jrow = 0;
		
		for(int i=0;i<256;i++) {
			for(int j=0;j<256;j++) {
				value[i][j] = (float) 0;
			}
			average[i] = (float) 0;
			total[i] = (float) 0;
			name[i] = "";
		}
		
		try {
			FileInputStream in = new FileInputStream(file);
			DataInputStream dataIn = new DataInputStream(in);
			int i = 0; int empty = 0;
			String recent;
			boolean cekHeader = true;
			RowCol =  new ArrayList<ArrayList<String>>();
			
			while ((recent = dataIn.readLine()) != null) { 
				Data = new ArrayList<String>();
				String array[] = recent.split("\t"); 
				jcolumn = array.length;
				total[i] = (float) 0;
				for (int j=0; j < jcolumn; j++) {
					Data.add(array[j]); 
					if ((cekHeader == false) && (j > 0) && (i > 0)) {
						Float nilai = Float.parseFloat(array[j]);
						value[j][i] = nilai;
						total[i] += nilai;
					} else {
						name[i] = array[j];
					}
				}
				if (cekHeader == true) {
					Data.add("Rata-rata");
					Data.add("Total Nilai");
				} else {
					average[i] = (float) (total[i] / (jcolumn - 1));
					String rata = Float.toString(average[i]);
					String jml = Float.toString(total[i]);
					Data.add(rata); Data.add(jml);
				}
				cekHeader = false;
				
				RowCol.add(Data); 
				i++;
				
				String x = "Ranking";
				HSSFWorkbook book = new HSSFWorkbook(); 
				HSSFSheet sheet = book.createSheet(x);
				jrow = RowCol.size();
				for (i = 0; i < jrow; i++) {
                    ArrayList<?> arrData = (ArrayList<?>) RowCol.get(i);
                    HSSFRow row = sheet.createRow((short) 0 + i);
                    for (int j = 0; j < arrData.size(); j++) {
                        HSSFCell cell = row.createCell((short) j);
                        cell.setCellValue(arrData.get(j).toString());
                    }
                 
                }
				 FileOutputStream score = new FileOutputStream("src/Ranker/score.xls");
				 book.write(score);
				 score.close();
			}
			
			for (i=1;i<jrow;i++) {
				average[i-1] = average[i];
				total[i-1] = total[i];
				name[i-1] = name[i];
			}

			sort(average, 0, average.length-1, total, name);
			
			for(i=0;i<256;i++) {
				if(average[i] != 0) {
					empty = i;
					break;
				}
			}
			
			for (i=0;i<256-empty;i++) {
				average[i] = average[i+empty];
				total[i] = total[i+empty];
				name[i] = name[i+empty];
			}
			
			String x = "Hasil";
			HSSFWorkbook book = new HSSFWorkbook(); 
			HSSFSheet sheet = book.createSheet(x);
			jrow = RowCol.size();
			for (i = 0; i < jrow; i++) {
                ArrayList<?> arrData = (ArrayList<?>) RowCol.get(i);
            	HSSFRow row = sheet.createRow((short) 0 + i);
                if (i == 0) {
                    for (int j = 0; j < 4; j++) {
                        HSSFCell cell = row.createCell((short) j);
                        if (j == 0) cell.setCellValue("Nama");
                        else if (j == 1) cell.setCellValue("Rata-rata");
                        else if (j == 2) cell.setCellValue("Total Nilai");
                        else cell.setCellValue("Rank");
                    }
                } else {
                    for (int j = 0; j < 4; j++) {
                    	HSSFCell cell = row.createCell((short) j);
                    	if (status == 0) {
                            if (j == 0) cell.setCellValue(name[i]);
                            else if (j == 1) cell.setCellValue(average[i]);
                            else if (j == 2) cell.setCellValue(total[i]);
                            else cell.setCellValue(256-empty-i);
                    	} else if (status == 1) {
                    		if (j == 0) cell.setCellValue(name[256-empty-i]);
                            else if (j == 1) cell.setCellValue(average[256-empty-i]);
                            else if (j == 2) cell.setCellValue(total[256-empty-i]);
                            else cell.setCellValue(i);
                    	}
                    }
                }
            }
			 FileOutputStream result = new FileOutputStream("src/Ranker/result.xls");
			 book.write(result);
			 result.close();
			
			 System.out.println("Proses Selesai :))");
			 
		} catch (FileNotFoundException e) { 
            System.out.println("404! File Not Found :(");
        } catch (Exception ex) { 
            System.out.println("Masukan Error! :(");
        } 
		
	}

	    public static void main(String[] args) { 
	        create("src/Ranker/score.txt",1); 
	    }
	
}
